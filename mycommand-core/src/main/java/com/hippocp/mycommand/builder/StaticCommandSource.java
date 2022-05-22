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


import com.hippocp.mycommand.mapping.BoundCommand;
import com.hippocp.mycommand.mapping.CommandSource;
import com.hippocp.mycommand.mapping.ParameterMapping;
import com.hippocp.mycommand.session.Configuration;

import java.util.List;

/**
 *
 */
public class StaticCommandSource implements CommandSource {

    private final String sql;
    private final List<ParameterMapping> parameterMappings;
    private final Configuration configuration;

    public StaticCommandSource(Configuration configuration, String sql) {
        this(configuration, sql, null);
    }

    public StaticCommandSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }

    @Override
    public BoundCommand getBoundSql(Object parameterObject) {
        return new BoundCommand(configuration, sql, parameterMappings, parameterObject);
    }

}
