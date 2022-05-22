/*
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.hippocp.mycommand.builder;


import com.hippocp.mycommand.mapping.CommandSource;
import com.hippocp.mycommand.mapping.ParameterMapping;
import com.hippocp.mycommand.parsing.GenericTokenParser;
import com.hippocp.mycommand.parsing.TokenHandler;
import com.hippocp.mycommand.reflection.MetaClass;
import com.hippocp.mycommand.reflection.MetaObject;
import com.hippocp.mycommand.scripting.ScriptingException;
import com.hippocp.mycommand.scripting.xmltags.DynamicContext;
import com.hippocp.mycommand.scripting.xmltags.OgnlCache;
import com.hippocp.mycommand.session.Configuration;
import com.hippocp.mycommand.type.JdbcType;
import com.hippocp.mycommand.type.SimpleTypeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 *
 */
public class SqlSourceBuilder extends BaseBuilder {

    private static final String PARAMETER_PROPERTIES = "javaType,jdbcType,mode,numericScale,resultMap,typeHandler,jdbcTypeName";

    public SqlSourceBuilder(Configuration configuration) {
        super(configuration);
    }

    public CommandSource parse(String originalSql, Class<?> parameterType, Map<String, Object> additionalParameters) {
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler(configuration, parameterType, additionalParameters);
        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
        String sql;
        if (configuration.isShrinkWhitespacesInSql()) {
            sql = parser.parse(removeExtraWhitespaces(originalSql));
        } else {
            sql = parser.parse(originalSql);
        }
        return new StaticCommandSource(configuration, sql, handler.getParameterMappings());
    }

    public CommandSource parse(String originalSql, DynamicContext context) {
        handleQuotingTokenParser handler = new handleQuotingTokenParser(context, null);
        GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
        String sql;
        if (configuration.isShrinkWhitespacesInSql()) {
            sql = parser.parse(removeExtraWhitespaces(originalSql));
        } else {
            sql = parser.parse(originalSql);
        }
        return new StaticCommandSource(configuration, sql, new ArrayList<>());
    }

    public static String removeExtraWhitespaces(String original) {
        StringTokenizer tokenizer = new StringTokenizer(original);
        StringBuilder builder = new StringBuilder();
        boolean hasMoreTokens = tokenizer.hasMoreTokens();
        while (hasMoreTokens) {
            builder.append(tokenizer.nextToken());
            hasMoreTokens = tokenizer.hasMoreTokens();
            if (hasMoreTokens) {
                builder.append(' ');
            }
        }
        return builder.toString();
    }

    private static class handleQuotingTokenParser implements TokenHandler {

        private DynamicContext context;
        private Pattern injectionFilter;

        public handleQuotingTokenParser(DynamicContext context, Pattern injectionFilter) {
            this.context = context;
            this.injectionFilter = injectionFilter;
        }

        @Override
        public String handleToken(String content) {
            Object parameter = context.getBindings().get("_parameter");
            if (parameter == null) {
                context.getBindings().put("value", null);
            } else if (SimpleTypeRegistry.isSimpleType(parameter.getClass())) {
                context.getBindings().put("value", parameter);
            }
            Object value = OgnlCache.getValue(content, context.getBindings());
            // issue #274 return "" instead of "null"
            // 此处修改源码处理文件路径引用
            String srtValue = value == null ? "" : "\"" + value + "\"";
            checkInjection(srtValue);
            return srtValue;
        }

        private void checkInjection(String value) {
            if (injectionFilter != null && !injectionFilter.matcher(value).matches()) {
                throw new ScriptingException("Invalid input. Please conform to regex" + injectionFilter.pattern());
            }
        }
    }

    private static class ParameterMappingTokenHandler extends BaseBuilder implements TokenHandler {

        private final List<ParameterMapping> parameterMappings = new ArrayList<>();
        private final Class<?> parameterType;
        private final MetaObject metaParameters;

        public ParameterMappingTokenHandler(Configuration configuration, Class<?> parameterType, Map<String, Object> additionalParameters) {
            super(configuration);
            this.parameterType = parameterType;
            this.metaParameters = configuration.newMetaObject(additionalParameters);
        }

        public List<ParameterMapping> getParameterMappings() {
            return parameterMappings;
        }

        @Override
        public String handleToken(String content) {
            parameterMappings.add(buildParameterMapping(content));
            return "?";
        }

        private ParameterMapping buildParameterMapping(String content) {
            Map<String, String> propertiesMap = parseParameterMapping(content);
            String property = propertiesMap.get("property");
            Class<?> propertyType;
            if (metaParameters.hasGetter(property)) { // issue #448 get type from additional params
                propertyType = metaParameters.getGetterType(property);
            } else if (typeHandlerRegistry.hasTypeHandler(parameterType)) {
                propertyType = parameterType;
            } else if (JdbcType.CURSOR.name().equals(propertiesMap.get("jdbcType"))) {
                propertyType = java.sql.ResultSet.class;
            } else if (property == null || Map.class.isAssignableFrom(parameterType)) {
                propertyType = Object.class;
            } else {
                MetaClass metaClass = MetaClass.forClass(parameterType, configuration.getReflectorFactory());
                if (metaClass.hasGetter(property)) {
                    propertyType = metaClass.getGetterType(property);
                } else {
                    propertyType = Object.class;
                }
            }
            ParameterMapping.Builder builder = new ParameterMapping.Builder(configuration, property, propertyType);
            Class<?> javaType = propertyType;
            String typeHandlerAlias = null;
            for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();
                if ("javaType".equals(name)) {
                    javaType = resolveClass(value);
                    builder.javaType(javaType);
                } else if ("jdbcType".equals(name)) {
                    builder.jdbcType(resolveJdbcType(value));
                } else if ("mode".equals(name)) {
                    builder.mode(resolveParameterMode(value));
                } else if ("numericScale".equals(name)) {
                    builder.numericScale(Integer.valueOf(value));
                } else if ("resultMap".equals(name)) {
                    builder.resultMapId(value);
                } else if ("typeHandler".equals(name)) {
                    typeHandlerAlias = value;
                } else if ("jdbcTypeName".equals(name)) {
                    builder.jdbcTypeName(value);
                } else if ("property".equals(name)) {
                    // Do Nothing
                } else if ("expression".equals(name)) {
                    throw new BuilderException("Expression based parameters are not supported yet");
                } else {
                    throw new BuilderException("An invalid property '" + name + "' was found in mapping #{" + content + "}.  Valid properties are " + PARAMETER_PROPERTIES);
                }
            }
            if (typeHandlerAlias != null) {
                builder.typeHandler(resolveTypeHandler(javaType, typeHandlerAlias));
            }
            return builder.build();
        }

        private Map<String, String> parseParameterMapping(String content) {
            try {
                return new ParameterExpression(content);
            } catch (BuilderException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new BuilderException("Parsing error was found in mapping #{" + content + "}.  Check syntax #{property|(expression), var1=value1, var2=value2, ...} ", ex);
            }
        }
    }

}
