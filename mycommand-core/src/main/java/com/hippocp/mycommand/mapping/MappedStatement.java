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
package com.hippocp.mycommand.mapping;


import com.hippocp.mycommand.logging.Log;
import com.hippocp.mycommand.logging.LogFactory;
import com.hippocp.mycommand.scripting.LanguageDriver;
import com.hippocp.mycommand.session.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public final class MappedStatement {

    private String resource;
    private Configuration configuration;
    private String id;
    private Integer fetchSize;
    private Integer timeout;
    private StatementType statementType;
    private CommandSource commandSource;
    private ParameterMap parameterMap;
    private CommandType commandType;
    private String databaseId;
    private Log statementLog;
    private LanguageDriver lang;
    private String platformId;
    private Integer exitValueNumber;
    private String commandRepositoryPath;
    private String logRepositoryPath;

    MappedStatement() {
        // constructor disabled
    }

    public static class Builder {
        private MappedStatement mappedStatement = new MappedStatement();

        public Builder(Configuration configuration, String id, CommandSource commandSource, CommandType commandType) {
            mappedStatement.configuration = configuration;
            mappedStatement.id = id;
            mappedStatement.commandSource = commandSource;
            mappedStatement.statementType = StatementType.PREPARED;
            mappedStatement.parameterMap = new ParameterMap.Builder(configuration, "defaultParameterMap", null, new ArrayList<>()).build();
            mappedStatement.commandType = commandType;
            String logId = id;
            if (configuration.getLogPrefix() != null) {
                logId = configuration.getLogPrefix() + id;
            }
            mappedStatement.statementLog = LogFactory.getLog(logId);
            mappedStatement.lang = configuration.getDefaultScriptingLanguageInstance();
        }

        public Builder parameterMap(ParameterMap parameterMap) {
            mappedStatement.parameterMap = parameterMap;
            return this;
        }

        public Builder resource(String resource) {
            mappedStatement.resource = resource;
            return this;
        }

        public String id() {
            return mappedStatement.id;
        }

        public Builder fetchSize(Integer fetchSize) {
            mappedStatement.fetchSize = fetchSize;
            return this;
        }

        public Builder timeout(Integer timeout) {
            mappedStatement.timeout = timeout;
            return this;
        }

        public Builder statementType(StatementType statementType) {
            mappedStatement.statementType = statementType;
            return this;
        }

        public Builder databaseId(String databaseId) {
            mappedStatement.databaseId = databaseId;
            return this;
        }

        public Builder lang(LanguageDriver driver) {
            mappedStatement.lang = driver;
            return this;
        }

        public Builder platformId(String platformId) {
            mappedStatement.platformId = platformId;
            return this;
        }

        public Builder exitValueNumber(Integer exitValueNumber) {
            mappedStatement.exitValueNumber = exitValueNumber;
            return this;
        }

        public Builder commandRepositoryPath(String commandRepositoryPath) {
            mappedStatement.commandRepositoryPath = commandRepositoryPath;
            return this;
        }

        public Builder logRepositoryPath(String logRepositoryPath) {
            mappedStatement.logRepositoryPath = logRepositoryPath;
            return this;
        }

        public MappedStatement build() {
            assert mappedStatement.configuration != null;
            assert mappedStatement.id != null;
            assert mappedStatement.commandSource != null;
            assert mappedStatement.lang != null;
            return mappedStatement;
        }
    }

    public ParameterMap getParameterMap() {
        return parameterMap;
    }

    public CommandType getSqlCommandType() {
        return commandType;
    }

    public String getResource() {
        return resource;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getId() {
        return id;
    }

    public Integer getFetchSize() {
        return fetchSize;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public StatementType getStatementType() {
        return statementType;
    }

    public CommandSource getSqlSource() {
        return commandSource;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public Log getStatementLog() {
        return statementLog;
    }

    public LanguageDriver getLang() {
        return lang;
    }

    public String getPlatformId() {
        return platformId;
    }

    public Integer getExitValueNumber() {
        return exitValueNumber;
    }

    public String getCommandRepositoryPath() {
        return commandRepositoryPath;
    }

    public String getLogRepositoryPath() {
        return logRepositoryPath;
    }

    public BoundCommand getBoundSql(Object parameterObject) {
        BoundCommand boundCommand = commandSource.getBoundSql(parameterObject);
        List<ParameterMapping> parameterMappings = boundCommand.getParameterMappings();
        if (parameterMappings == null || parameterMappings.isEmpty()) {
            boundCommand = new BoundCommand(configuration, boundCommand.getSql(),
                    parameterMap.getParameterMappings(),
                    parameterObject);
        }

        return boundCommand;
    }

    private static String[] delimitedStringToArray(String in) {
        if (in == null || in.trim().length() == 0) {
            return null;
        } else {
            return in.split(",");
        }
    }

}
