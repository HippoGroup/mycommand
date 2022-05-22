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
package com.hippocp.mycommand.scripting.xmltags;


import com.hippocp.mycommand.builder.SqlSourceBuilder;
import com.hippocp.mycommand.mapping.BoundCommand;
import com.hippocp.mycommand.mapping.CommandSource;
import com.hippocp.mycommand.session.Configuration;

/**
 *
 */
public class DynamicCommandSource implements CommandSource {

    private final Configuration configuration;
    private final SqlNode rootSqlNode;

    public DynamicCommandSource(Configuration configuration, SqlNode rootSqlNode) {
        this.configuration = configuration;
        this.rootSqlNode = rootSqlNode;
    }

    @Override
    public BoundCommand getBoundSql(Object parameterObject) {
        DynamicContext context = new DynamicContext(configuration, parameterObject);
        rootSqlNode.apply(context);
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
        CommandSource commandSource = sqlSourceParser.parse(context.getSql(), context);
        BoundCommand boundCommand = commandSource.getBoundSql(parameterObject);
        context.getBindings().forEach(boundCommand::setAdditionalParameter);
        return boundCommand;
    }

//  @Override
//  public BoundCommand getBoundSql(Object parameterObject) {
//    DynamicContext context = new DynamicContext(configuration, parameterObject);
//    rootSqlNode.apply(context);
//    SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
//    Class<?> parameterType = parameterObject == null ? Object.class : parameterObject.getClass();
//    CommandSource sqlSource = sqlSourceParser.parse(context.getSql(), parameterType, context.getBindings());
//    BoundCommand boundSql = sqlSource.getBoundSql(parameterObject);
//    context.getBindings().forEach(boundSql::setAdditionalParameter);
//    return boundSql;
//  }

}
