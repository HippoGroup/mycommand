/*
 *    Copyright 2015-2021 the original author or authors.
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
package com.hippocp.mycommand.autoconfigure;

import com.hippocp.mycommand.session.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Configuration properties for MyBatis.
 *
 * @author Eddú Meléndez
 * @author Kazuki Shimizu
 */
@ConfigurationProperties(prefix = MyCommandProperties.MYCOMMAND_PREFIX)
public class MyCommandProperties {

    public static final String MYCOMMAND_PREFIX = "mycommand";

    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    /**
     * Location of MyBatis xml config file.
     */
    private String configLocation;

    /**
     * Locations of MyBatis mapper files.
     */
    private String[] mapperLocations;

    /**
     * Packages to search type aliases. (Package delimiters are ",; \t\n")
     */
    private String typeAliasesPackage;

    /**
     * The super class for filtering type alias. If this not specifies, the MyBatis deal as type alias all classes that
     * searched from typeAliasesPackage.
     */
    private Class<?> typeAliasesSuperType;

    /**
     * Indicates whether perform presence check of the MyBatis xml config file.
     */
    private boolean checkConfigLocation = false;

    /**
     * A Configuration object for customize default settings. If {@link #configLocation} is specified, this property is
     * not used.
     */
    @NestedConfigurationProperty
    private Configuration configuration;

    /**
     * @since 1.1.0
     */
    public String getConfigLocation() {
        return this.configLocation;
    }

    /**
     * @since 1.1.0
     */
    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

    public String[] getMapperLocations() {
        return this.mapperLocations;
    }

    public void setMapperLocations(String[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getTypeAliasesPackage() {
        return this.typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    /**
     * @since 1.3.3
     */
    public Class<?> getTypeAliasesSuperType() {
        return typeAliasesSuperType;
    }

    /**
     * @since 1.3.3
     */
    public void setTypeAliasesSuperType(Class<?> typeAliasesSuperType) {
        this.typeAliasesSuperType = typeAliasesSuperType;
    }

    public boolean isCheckConfigLocation() {
        return this.checkConfigLocation;
    }

    public void setCheckConfigLocation(boolean checkConfigLocation) {
        this.checkConfigLocation = checkConfigLocation;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Resource[] resolveMapperLocations() {
        return Stream.of(Optional.ofNullable(this.mapperLocations).orElse(new String[0]))
                .flatMap(location -> Stream.of(getResources(location))).toArray(Resource[]::new);
    }

    private Resource[] getResources(String location) {
        try {
            return resourceResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }

}
