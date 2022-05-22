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
package com.hippocp.mycommand.builder.xml;


import com.hippocp.mycommand.builder.BaseBuilder;
import com.hippocp.mycommand.builder.MapperBuilderAssistant;
import com.hippocp.mycommand.mapping.CommandSource;
import com.hippocp.mycommand.mapping.CommandType;
import com.hippocp.mycommand.mapping.MappedStatement;
import com.hippocp.mycommand.mapping.StatementType;
import com.hippocp.mycommand.parsing.XNode;
import com.hippocp.mycommand.scripting.LanguageDriver;
import com.hippocp.mycommand.session.Configuration;

import java.util.Locale;

/**
 *
 */
public class XMLStatementBuilder extends BaseBuilder {

    private final MapperBuilderAssistant builderAssistant;
    private final XNode context;
    private final String requiredDatabaseId;

    public XMLStatementBuilder(Configuration configuration, MapperBuilderAssistant builderAssistant, XNode context) {
        this(configuration, builderAssistant, context, null);
    }

    public XMLStatementBuilder(Configuration configuration, MapperBuilderAssistant builderAssistant, XNode context, String databaseId) {
        super(configuration);
        this.builderAssistant = builderAssistant;
        this.context = context;
        this.requiredDatabaseId = databaseId;
    }

    public void parseStatementNode() {
        String id = context.getStringAttribute("id");
        String databaseId = context.getStringAttribute("databaseId");
        String platformId = context.getStringAttribute("platformId");
        String commandRepositoryPath = context.getStringAttribute("commandRepositoryPath");
        String logRepositoryPath = context.getStringAttribute("logRepositoryPath");

        if (!databaseIdMatchesCurrent(id, databaseId, this.requiredDatabaseId)) {
            return;
        }

        // todo platformId区分平台命令执行逻辑
//        if (!databaseIdMatchesCurrent(id, databaseId, this.requiredDatabaseId)) {
//            return;
//        }

        String nodeName = context.getNode().getNodeName();
        CommandType commandType = CommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
//        boolean isSelect = commandType == CommandType.SELECT;
//        boolean flushCache = context.getBooleanAttribute("flushCache", !isSelect);
//        boolean useCache = context.getBooleanAttribute("useCache", isSelect);
//        boolean resultOrdered = context.getBooleanAttribute("resultOrdered", false);

        // Include Fragments before parsing
        XMLIncludeTransformer includeParser = new XMLIncludeTransformer(configuration, builderAssistant);
        includeParser.applyIncludes(context.getNode());

        String parameterType = context.getStringAttribute("parameterType");
        Class<?> parameterTypeClass = resolveClass(parameterType);

        String lang = context.getStringAttribute("lang");
//        LanguageDriver langDriver = getLanguageDriver(lang);
        LanguageDriver langDriver = getLanguageDriver(lang);
/*
        // Parse selectKey after includes and remove them.
        processSelectKeyNodes(id, parameterTypeClass, langDriver);*/

        // Parse the SQL (pre: <selectKey> and <include> were parsed and removed)
        /*KeyGenerator keyGenerator;
        String keyStatementId = id + SelectKeyGenerator.SELECT_KEY_SUFFIX;
        keyStatementId = builderAssistant.applyCurrentNamespace(keyStatementId, true);
        if (configuration.hasKeyGenerator(keyStatementId)) {
            keyGenerator = configuration.getKeyGenerator(keyStatementId);
        } else {
            keyGenerator = context.getBooleanAttribute("useGeneratedKeys",
                    configuration.isUseGeneratedKeys() && CommandType.INSERT.equals(commandType))
                    ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE;
        }*/

        CommandSource commandSource = langDriver.createSqlSource(configuration, context, parameterTypeClass);
//        CommandSource commandSource = null;
        StatementType statementType = StatementType.valueOf(context.getStringAttribute("statementType", StatementType.PREPARED.toString()));
//        Integer fetchSize = context.getIntAttribute("fetchSize");
        Integer timeout = context.getIntAttribute("timeout");
        Integer exitValueNumber = context.getIntAttribute("exitValueNumber");
//        String parameterMap = context.getStringAttribute("parameterMap");
//        String resultType = context.getStringAttribute("resultType");
//        Class<?> resultTypeClass = resolveClass(resultType);
//        String resultMap = context.getStringAttribute("resultMap");
//        String resultSetType = context.getStringAttribute("resultSetType");
        /*ResultSetType resultSetTypeEnum = resolveResultSetType(resultSetType);
        if (resultSetTypeEnum == null) {
            resultSetTypeEnum = configuration.getDefaultResultSetType();
        }*/
//        String keyProperty = context.getStringAttribute("keyProperty");
//        String keyColumn = context.getStringAttribute("keyColumn");
//        String resultSets = context.getStringAttribute("resultSets");

        /*builderAssistant.addMappedStatement(id, commandSource, statementType, commandType,
                fetchSize, timeout, parameterMap, parameterTypeClass, resultMap, resultTypeClass,
                resultSetTypeEnum, flushCache, useCache, resultOrdered,
                keyGenerator, keyProperty, keyColumn, databaseId, langDriver, resultSets);*/

        builderAssistant.addMappedStatement(id, commandSource, statementType, commandType,
                timeout, parameterTypeClass, databaseId, langDriver,
                platformId, exitValueNumber, commandRepositoryPath, logRepositoryPath);
    }

    private boolean databaseIdMatchesCurrent(String id, String databaseId, String requiredDatabaseId) {
        if (requiredDatabaseId != null) {
            return requiredDatabaseId.equals(databaseId);
        }
        if (databaseId != null) {
            return false;
        }
        id = builderAssistant.applyCurrentNamespace(id, false);
        if (!this.configuration.hasStatement(id, false)) {
            return true;
        }
        // skip this statement if there is a previous one with a not null databaseId
        MappedStatement previous = this.configuration.getMappedStatement(id, false); // issue #2
        return previous.getDatabaseId() == null;
    }

    private LanguageDriver getLanguageDriver(String lang) {
        Class<? extends LanguageDriver> langClass = null;
        if (lang != null) {
            langClass = resolveClass(lang);
        }
        return configuration.getLanguageDriver(langClass);
    }

}
