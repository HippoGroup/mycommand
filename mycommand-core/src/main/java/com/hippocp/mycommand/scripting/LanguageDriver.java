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
package com.hippocp.mycommand.scripting;

import com.hippocp.mycommand.executor.parameter.ParameterHandler;
import com.hippocp.mycommand.mapping.BoundCommand;
import com.hippocp.mycommand.mapping.CommandSource;
import com.hippocp.mycommand.mapping.MappedStatement;
import com.hippocp.mycommand.parsing.XNode;
import com.hippocp.mycommand.scripting.defaults.DefaultParameterHandler;
import com.hippocp.mycommand.session.Configuration;

public interface LanguageDriver {

    /**
     * Creates a {@link ParameterHandler} that passes the actual parameters to the the JDBC statement.
     *
     * @param mappedStatement The mapped statement that is being executed
     * @param parameterObject The input parameter object (can be null)
     * @param boundCommand    The resulting SQL once the dynamic language has been executed.
     * @return the parameter handler
     * @see DefaultParameterHandler
     */
    ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundCommand boundCommand);

    /**
     * Creates an {@link CommandSource} that will hold the statement read from a mapper xml file.
     * It is called during startup, when the mapped statement is read from a class or an xml file.
     *
     * @param configuration The MyBatis configuration
     * @param script        XNode parsed from a XML file
     * @param parameterType input parameter type got from a mapper method or specified in the parameterType xml attribute. Can be null.
     * @return the sql source
     */
    CommandSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType);

    /**
     * Creates an {@link CommandSource} that will hold the statement read from an annotation.
     * It is called during startup, when the mapped statement is read from a class or an xml file.
     *
     * @param configuration The MyBatis configuration
     * @param script        The content of the annotation
     * @param parameterType input parameter type got from a mapper method or specified in the parameterType xml attribute. Can be null.
     * @return the sql source
     */
    CommandSource createSqlSource(Configuration configuration, String script, Class<?> parameterType);

}
