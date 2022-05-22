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


import com.hippocp.mycommand.builder.BuilderException;
import com.hippocp.mycommand.mapping.CommandSource;
import com.hippocp.mycommand.parsing.XNode;
import com.hippocp.mycommand.scripting.xmltags.XMLLanguageDriver;
import com.hippocp.mycommand.session.Configuration;

/**
 * As of 3.2.4 the default XML language is able to identify static statements
 * and create a {@link RawCommandSource}. So there is no need to use RAW unless you
 * want to make sure that there is not any dynamic tag for any reason.
 *
 * @since 3.2.0
 */
public class RawLanguageDriver extends XMLLanguageDriver {

    @Override
    public CommandSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
        CommandSource source = super.createSqlSource(configuration, script, parameterType);
        checkIsNotDynamic(source);
        return source;
    }

    @Override
    public CommandSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        CommandSource source = super.createSqlSource(configuration, script, parameterType);
        checkIsNotDynamic(source);
        return source;
    }

    private void checkIsNotDynamic(CommandSource source) {
        if (!RawCommandSource.class.equals(source.getClass())) {
            throw new BuilderException("Dynamic content is not allowed when using RAW language");
        }
    }

}
