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


import com.hippocp.mycommand.builder.xml.XMLMapperEntityResolver;
import com.hippocp.mycommand.executor.parameter.ParameterHandler;
import com.hippocp.mycommand.mapping.BoundCommand;
import com.hippocp.mycommand.mapping.CommandSource;
import com.hippocp.mycommand.mapping.MappedStatement;
import com.hippocp.mycommand.parsing.PropertyParser;
import com.hippocp.mycommand.parsing.XNode;
import com.hippocp.mycommand.parsing.XPathParser;
import com.hippocp.mycommand.scripting.LanguageDriver;
import com.hippocp.mycommand.scripting.defaults.DefaultParameterHandler;
import com.hippocp.mycommand.scripting.defaults.RawCommandSource;
import com.hippocp.mycommand.session.Configuration;

/**
 *
 */
public class XMLLanguageDriver implements LanguageDriver {

    @Override
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundCommand boundCommand) {
        return new DefaultParameterHandler(mappedStatement, parameterObject, boundCommand);
    }

    @Override
    public CommandSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }

    @Override
    public CommandSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        // issue #3
        if (script.startsWith("<script>")) {
            XPathParser parser = new XPathParser(script, false, configuration.getVariables(), new XMLMapperEntityResolver());
            return createSqlSource(configuration, parser.evalNode("/script"), parameterType);
        } else {
            // issue #127
            script = PropertyParser.parse(script, configuration.getVariables());
            TextSqlNode textSqlNode = new TextSqlNode(script);
            if (textSqlNode.isDynamic()) {
                return new DynamicCommandSource(configuration, textSqlNode);
            } else {
                return new RawCommandSource(configuration, script, parameterType);
            }
        }
    }

}
