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


import com.hippocp.mycommand.executor.ErrorContext;
import com.hippocp.mycommand.mapping.CommandSource;
import com.hippocp.mycommand.mapping.CommandType;
import com.hippocp.mycommand.mapping.MappedStatement;
import com.hippocp.mycommand.mapping.StatementType;
import com.hippocp.mycommand.scripting.LanguageDriver;
import com.hippocp.mycommand.session.Configuration;

/**
 *
 */
public class MapperBuilderAssistant extends BaseBuilder {


    private String currentNamespace;
    private final String resource;

    public String getCurrentNamespace() {
        return currentNamespace;
    }


    public MapperBuilderAssistant(Configuration configuration, String resource) {
        super(configuration);
        ErrorContext.instance().resource(resource);
        this.resource = resource;
    }

    public void setCurrentNamespace(String currentNamespace) {
        if (currentNamespace == null) {
            throw new BuilderException("The mapper element requires a namespace attribute to be specified.");
        }

        if (this.currentNamespace != null && !this.currentNamespace.equals(currentNamespace)) {
            throw new BuilderException("Wrong namespace. Expected '"
                    + this.currentNamespace + "' but found '" + currentNamespace + "'.");
        }

        this.currentNamespace = currentNamespace;
    }

    public String applyCurrentNamespace(String base, boolean isReference) {
        if (base == null) {
            return null;
        }
        if (isReference) {
            // is it qualified with any namespace yet?
            if (base.contains(".")) {
                return base;
            }
        } else {
            // is it qualified with this namespace yet?
            if (base.startsWith(currentNamespace + ".")) {
                return base;
            }
            if (base.contains(".")) {
                throw new BuilderException("Dots are not allowed in element names, please remove it from " + base);
            }
        }
        return currentNamespace + "." + base;
    }

    public MappedStatement addMappedStatement(
            String id,
            CommandSource commandSource,
            StatementType statementType,
            CommandType commandType,
//            Integer fetchSize,
            Integer timeout,
//            String parameterMap,
            Class<?> parameterType,
//            String resultMap,
//            Class<?> resultType,
//            ResultSetType resultSetType,
//            boolean flushCache,
//            boolean useCache,
//            boolean resultOrdered,
//            KeyGenerator keyGenerator,
//            String keyProperty,
//            String keyColumn,
            String databaseId,
            LanguageDriver lang,
            String platformId,
            Integer exitValueNumber,
            String commandRepositoryPath,
            String logRepositoryPath
//            String resultSets
    ) {

        /*if (unresolvedCacheRef) {
            throw new IncompleteElementException("Cache-ref not yet resolved");
        }*/

        id = applyCurrentNamespace(id, false);
//        boolean isSelect = commandType == CommandType.SELECT;

        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, id, commandSource, commandType)
                .resource(resource)
//                .fetchSize(fetchSize)
                .timeout(timeout)
                .statementType(statementType)
//                .keyGenerator(keyGenerator)
//                .keyProperty(keyProperty)
//                .keyColumn(keyColumn)
                .databaseId(databaseId)
                .lang(lang)
                .platformId(platformId)
                .exitValueNumber(exitValueNumber)
                .commandRepositoryPath(commandRepositoryPath)
                .logRepositoryPath(logRepositoryPath)
//                .resultOrdered(resultOrdered)
//                .resultSets(resultSets)
//                .resultMaps(getStatementResultMaps(resultMap, resultType, id))
//                .resultSetType(resultSetType)
//                .flushCacheRequired(valueOrDefault(flushCache, !isSelect))
//                .useCache(valueOrDefault(useCache, isSelect))
//                .cache(currentCache)
                ;

        /*ParameterMap statementParameterMap = getStatementParameterMap(parameterMap, parameterType, id);
        if (statementParameterMap != null) {
            statementBuilder.parameterMap(statementParameterMap);
        }*/

        MappedStatement statement = statementBuilder.build();
        configuration.addMappedStatement(statement);
        return statement;
    }

}
