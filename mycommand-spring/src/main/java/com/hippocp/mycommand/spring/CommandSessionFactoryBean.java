/*
 * Copyright 2010-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hippocp.mycommand.spring;

import com.hippocp.mycommand.builder.xml.XMLMapperBuilder;
import com.hippocp.mycommand.executor.ErrorContext;
import com.hippocp.mycommand.io.Resources;
import com.hippocp.mycommand.io.VFS;
import com.hippocp.mycommand.logging.Logger;
import com.hippocp.mycommand.logging.LoggerFactory;
import com.hippocp.mycommand.reflection.factory.ObjectFactory;
import com.hippocp.mycommand.reflection.wrapper.ObjectWrapperFactory;
import com.hippocp.mycommand.session.CommandSessionFactory;
import com.hippocp.mycommand.session.CommandSessionFactoryBuilder;
import com.hippocp.mycommand.session.Configuration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.Assert.state;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.tokenizeToStringArray;

/**
 * {@code FactoryBean} that creates a MyBatis {@code SqlSessionFactory}. This is the usual way to set up a shared
 * MyBatis {@code SqlSessionFactory} in a Spring application context; the SqlSessionFactory can then be passed to
 * MyBatis-based DAOs via dependency injection.
 * <p>
 * Either {@code DataSourceTransactionManager} or {@code JtaTransactionManager} can be used for transaction demarcation
 * in combination with a {@code SqlSessionFactory}. JTA should be used for transactions which span multiple databases or
 * when container managed transactions (CMT) are being used.
 *
 * @see #setConfigLocation
 */
public class CommandSessionFactoryBean
        implements FactoryBean<CommandSessionFactory>, InitializingBean, ApplicationListener<ApplicationEvent> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CommandSessionFactoryBean.class);

  private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();
  private static final MetadataReaderFactory METADATA_READER_FACTORY = new CachingMetadataReaderFactory();

  private Resource configLocation;

  private Configuration configuration;

  private Resource[] mapperLocations;

  private CommandSessionFactoryBuilder commandSessionFactoryBuilder = new CommandSessionFactoryBuilder();

  private CommandSessionFactory commandSessionFactory;

  // EnvironmentAware requires spring 3.1
  private String environment = CommandSessionFactoryBean.class.getSimpleName();

  private boolean failFast;

  private Class<?>[] typeAliases;

  private String typeAliasesPackage;

  private Class<?> typeAliasesSuperType;

  // issue #19. No default provider.
//  private PlatformIdProvider platformIdProvider;

  private Class<? extends VFS> vfs;

  private ObjectFactory objectFactory;

  private ObjectWrapperFactory objectWrapperFactory;

  /**
   * Sets the ObjectFactory.
   *
   * @param objectFactory a custom ObjectFactory
   * @since 1.1.2
   */
  public void setObjectFactory(ObjectFactory objectFactory) {
    this.objectFactory = objectFactory;
  }

  /**
   * Sets the ObjectWrapperFactory.
   *
   * @param objectWrapperFactory a specified ObjectWrapperFactory
   * @since 1.1.2
   */
  public void setObjectWrapperFactory(ObjectWrapperFactory objectWrapperFactory) {
    this.objectWrapperFactory = objectWrapperFactory;
  }

  /**
   * Gets the DatabaseIdProvider
   *
   * @since 1.1.0
   * @return a specified DatabaseIdProvider
   */
//  public PlatformIdProvider getPlatformIdProvider() {
//    return platformIdProvider;
//  }

  /**
   * Sets the platformIdProvider. As of version 1.2.2 this variable is not initialized by default.
   *
   * @since 1.1.0
   * @param platformIdProvider
   *          a platformIdProvider
   */
//  public void setPlatformIdProvider(PlatformIdProvider platformIdProvider) {
//    this.platformIdProvider = platformIdProvider;
//  }

  /**
   * Gets the VFS.
   *
   * @return a specified VFS
   */
  public Class<? extends VFS> getVfs() {
    return this.vfs;
  }

  /**
   * Sets the VFS.
   *
   * @param vfs a VFS
   */
  public void setVfs(Class<? extends VFS> vfs) {
    this.vfs = vfs;
  }


  /**
   * Packages to search for type aliases.
   *
   * <p>
   * Since 2.0.1, allow to specify a wildcard such as {@code com.example.*.model}.
   *
   * @param typeAliasesPackage package to scan for domain objects
   * @since 1.0.1
   */
  public void setTypeAliasesPackage(String typeAliasesPackage) {
    this.typeAliasesPackage = typeAliasesPackage;
  }

  /**
   * Super class which domain objects have to extend to have a type alias created. No effect if there is no package to
   * scan configured.
   *
   * @param typeAliasesSuperType super class for domain objects
   * @since 1.1.2
   */
  public void setTypeAliasesSuperType(Class<?> typeAliasesSuperType) {
    this.typeAliasesSuperType = typeAliasesSuperType;
  }


  /**
   * List of type aliases to register. They can be annotated with {@code Alias}
   *
   * @param typeAliases Type aliases list
   * @since 1.0.1
   */
  public void setTypeAliases(Class<?>... typeAliases) {
    this.typeAliases = typeAliases;
  }

  /**
   * If true, a final check is done on Configuration to assure that all mapped statements are fully loaded and there is
   * no one still pending to resolve includes. Defaults to false.
   *
   * @param failFast enable failFast
   * @since 1.0.1
   */
  public void setFailFast(boolean failFast) {
    this.failFast = failFast;
  }

  /**
   * Set the location of the MyBatis {@code SqlSessionFactory} config file. A typical value is
   * "WEB-INF/mybatis-configuration.xml".
   *
   * @param configLocation a location the MyBatis config file
   */
  public void setConfigLocation(Resource configLocation) {
    this.configLocation = configLocation;
  }

  /**
   * Set a customized MyBatis configuration.
   *
   * @param configuration MyBatis configuration
   * @since 1.3.0
   */
  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  /**
   * Set locations of MyBatis mapper files that are going to be merged into the {@code SqlSessionFactory} configuration
   * at runtime.
   * <p>
   * This is an alternative to specifying "&lt;sqlmapper&gt;" entries in an MyBatis config file. This property being
   * based on Spring's resource abstraction also allows for specifying resource patterns here: e.g.
   * "classpath*:sqlmap/*-mapper.xml".
   *
   * @param mapperLocations location of MyBatis mapper files
   */
  public void setMapperLocations(Resource... mapperLocations) {
    this.mapperLocations = mapperLocations;
  }


  /**
   * <b>NOTE:</b> This class <em>overrides</em> any {@code Environment} you have set in the MyBatis config file. This is
   * used only as a placeholder name. The default value is {@code SqlSessionFactoryBean.class.getSimpleName()}.
   *
   * @param environment the environment name
   */
  public void setEnvironment(String environment) {
    this.environment = environment;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    notNull(commandSessionFactoryBuilder, "Property 'commandSessionFactoryBuilder' is required");
    state((configuration == null && configLocation == null) || !(configuration != null && configLocation != null),
            "Property 'configuration' and 'configLocation' can not specified with together");

    this.commandSessionFactory = buildSqlSessionFactory();
  }

  /**
   * Build a {@code SqlSessionFactory} instance.
   * <p>
   * The default implementation uses the standard MyBatis {@code XMLConfigBuilder} API to build a
   * {@code SqlSessionFactory} instance based on a Reader. Since 1.3.0, it can be specified a {@link Configuration}
   * instance directly(without config file).
   *
   * @return SqlSessionFactory
   * @throws Exception if configuration is failed
   */
  protected CommandSessionFactory buildSqlSessionFactory() throws Exception {

    final Configuration targetConfiguration;

    if (this.configuration != null) {
      targetConfiguration = this.configuration;
//      if (targetConfiguration.getVariables() == null) {
//        targetConfiguration.setVariables(this.configurationProperties);
//      } else if (this.configurationProperties != null) {
//        targetConfiguration.getVariables().putAll(this.configurationProperties);
//      }
    } else {
      targetConfiguration = new Configuration();
    }
//    else if (this.configLocation != null) {
//      xmlConfigBuilder = new XMLConfigBuilder(this.configLocation.getInputStream(), null, this.configurationProperties);
//      targetConfiguration = xmlConfigBuilder.getConfiguration();
//    } else {
//      LOGGER.debug(
//          () -> "Property 'configuration' or 'configLocation' not specified, using default MyBatis Configuration");
//      targetConfiguration = new Configuration();
//      Optional.ofNullable(this.configurationProperties).ifPresent(targetConfiguration::setVariables);
//    }

//    Optional.ofNullable(this.objectFactory).ifPresent(targetConfiguration::setObjectFactory);
//    Optional.ofNullable(this.objectWrapperFactory).ifPresent(targetConfiguration::setObjectWrapperFactory);
//    Optional.ofNullable(this.vfs).ifPresent(targetConfiguration::setVfsImpl);

    if (hasLength(this.typeAliasesPackage)) {
      scanClasses(this.typeAliasesPackage, this.typeAliasesSuperType).stream()
              .filter(clazz -> !clazz.isAnonymousClass()).filter(clazz -> !clazz.isInterface())
              .filter(clazz -> !clazz.isMemberClass()).forEach(targetConfiguration.getTypeAliasRegistry()::registerAlias);
    }

    if (!isEmpty(this.typeAliases)) {
      Stream.of(this.typeAliases).forEach(typeAlias -> {
        targetConfiguration.getTypeAliasRegistry().registerAlias(typeAlias);
        LOGGER.debug(() -> "Registered type alias: '" + typeAlias + "'");
      });
    }

//    if (this.platformIdProvider != null) {// fix #64 set databaseId before parse mapper xmls
//      try {
//        targetConfiguration.setPlatformId(this.platformIdProvider.getPlatformId());
//      } catch (SQLException e) {
//        throw new NestedIOException("Failed getting a databaseId", e);
//      }
//    }

//    Optional.ofNullable(this.cache).ifPresent(targetConfiguration::addCache);
//
//    if (xmlConfigBuilder != null) {
//      try {
//        xmlConfigBuilder.parse();
//        LOGGER.debug(() -> "Parsed configuration file: '" + this.configLocation + "'");
//      } catch (Exception ex) {
//        throw new NestedIOException("Failed to parse config resource: " + this.configLocation, ex);
//      } finally {
//        ErrorContext.instance().reset();
//      }
//    }

//    targetConfiguration.setEnvironment(new Environment(this.environment,
//        this.transactionFactory == null ? new SpringManagedTransactionFactory() : this.transactionFactory,
//        this.dataSource));

    if (this.mapperLocations != null) {
      if (this.mapperLocations.length == 0) {
        LOGGER.warn(() -> "Property 'mapperLocations' was specified but matching resources are not found.");
      } else {
        for (Resource mapperLocation : this.mapperLocations) {
          if (mapperLocation == null) {
            continue;
          }
          try {
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperLocation.getInputStream(),
                    targetConfiguration, mapperLocation.toString(), targetConfiguration.getSqlFragments());
            xmlMapperBuilder.parse();
          } catch (Exception e) {
            throw new NestedIOException("Failed to parse mapping resource: '" + mapperLocation + "'", e);
          } finally {
            ErrorContext.instance().reset();
          }
          LOGGER.debug(() -> "Parsed mapper file: '" + mapperLocation + "'");
        }
      }
    } else {
      LOGGER.debug(() -> "Property 'mapperLocations' was not specified.");
    }

    return this.commandSessionFactoryBuilder.build(targetConfiguration);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CommandSessionFactory getObject() throws Exception {
    if (this.commandSessionFactory == null) {
      afterPropertiesSet();
    }

    return this.commandSessionFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<? extends CommandSessionFactory> getObjectType() {
    return this.commandSessionFactory == null ? CommandSessionFactory.class : this.commandSessionFactory.getClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSingleton() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    if (failFast && event instanceof ContextRefreshedEvent) {
      // fail-fast -> check all statements are completed
      this.commandSessionFactory.getConfiguration().getMappedStatementNames();
    }
  }

  private Set<Class<?>> scanClasses(String packagePatterns, Class<?> assignableType) throws IOException {
    Set<Class<?>> classes = new HashSet<>();
    String[] packagePatternArray = tokenizeToStringArray(packagePatterns,
            ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
    for (String packagePattern : packagePatternArray) {
      Resource[] resources = RESOURCE_PATTERN_RESOLVER.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
              + ClassUtils.convertClassNameToResourcePath(packagePattern) + "/**/*.class");
      for (Resource resource : resources) {
        try {
          ClassMetadata classMetadata = METADATA_READER_FACTORY.getMetadataReader(resource).getClassMetadata();
          Class<?> clazz = Resources.classForName(classMetadata.getClassName());
          if (assignableType == null || assignableType.isAssignableFrom(clazz)) {
            classes.add(clazz);
          }
        } catch (Throwable e) {
          LOGGER.warn(() -> "Cannot load the '" + resource + "'. Cause by " + e.toString());
        }
      }
    }
    return classes;
  }

}
