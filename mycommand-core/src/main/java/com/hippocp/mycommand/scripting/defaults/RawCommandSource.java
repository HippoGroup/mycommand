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
package com.hippocp.mycommand.scripting.defaults;


import com.hippocp.mycommand.builder.SqlSourceBuilder;
import com.hippocp.mycommand.mapping.BoundCommand;
import com.hippocp.mycommand.mapping.CommandSource;
import com.hippocp.mycommand.scripting.xmltags.DynamicCommandSource;
import com.hippocp.mycommand.scripting.xmltags.DynamicContext;
import com.hippocp.mycommand.scripting.xmltags.SqlNode;
import com.hippocp.mycommand.session.Configuration;

import java.util.HashMap;

/**
 * Static CommandSource. It is faster than {@link DynamicCommandSource} because mappings are
 * calculated during startup.
 *
 * @since 3.2.0
 */
public class RawCommandSource implements CommandSource {

    private final CommandSource commandSource;

    public RawCommandSource(Configuration configuration, SqlNode rootSqlNode, Class<?> parameterType) {
        this(configuration, getSql(configuration, rootSqlNode), parameterType);
    }

    public RawCommandSource(Configuration configuration, String sql, Class<?> parameterType) {
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> clazz = parameterType == null ? Object.class : parameterType;
        commandSource = sqlSourceParser.parse(sql, clazz, new HashMap<>());
    }

    private static String getSql(Configuration configuration, SqlNode rootSqlNode) {
        DynamicContext context = new DynamicContext(configuration, null);
        rootSqlNode.apply(context);
        return context.getSql();
    }

    @Override
    public BoundCommand getBoundSql(Object parameterObject) {
        return commandSource.getBoundSql(parameterObject);
    }

}
