package com.hippocp.mycommand.session;

import cn.hutool.core.util.StrUtil;
import com.hippocp.mycommand.binding.MapperRegistry;
import com.hippocp.mycommand.builder.xml.XMLStatementBuilder;
import com.hippocp.mycommand.cache.decorators.SoftCache;
import com.hippocp.mycommand.cache.decorators.WeakCache;
import com.hippocp.mycommand.exceptions.MyCommandException;
import com.hippocp.mycommand.executor.CommandExecutor;
import com.hippocp.mycommand.executor.ExecutorType;
import com.hippocp.mycommand.executor.SimpleCommandExecutor;
import com.hippocp.mycommand.logging.commons.JakartaCommonsLoggingImpl;
import com.hippocp.mycommand.logging.jdk14.Jdk14LoggingImpl;
import com.hippocp.mycommand.logging.log4j2.Log4j2Impl;
import com.hippocp.mycommand.logging.nologging.NoLoggingImpl;
import com.hippocp.mycommand.logging.slf4j.Slf4jImpl;
import com.hippocp.mycommand.logging.stdout.StdOutImpl;
import com.hippocp.mycommand.mapping.MappedStatement;
import com.hippocp.mycommand.mapping.PlatformIdProvider;
import com.hippocp.mycommand.mapping.VendorPlatformIdProvider;
import com.hippocp.mycommand.parsing.XNode;
import com.hippocp.mycommand.reflection.DefaultReflectorFactory;
import com.hippocp.mycommand.reflection.MetaObject;
import com.hippocp.mycommand.reflection.ReflectorFactory;
import com.hippocp.mycommand.reflection.factory.DefaultObjectFactory;
import com.hippocp.mycommand.reflection.factory.ObjectFactory;
import com.hippocp.mycommand.reflection.wrapper.DefaultObjectWrapperFactory;
import com.hippocp.mycommand.reflection.wrapper.ObjectWrapperFactory;
import com.hippocp.mycommand.scripting.LanguageDriver;
import com.hippocp.mycommand.scripting.LanguageDriverRegistry;
import com.hippocp.mycommand.scripting.defaults.RawLanguageDriver;
import com.hippocp.mycommand.scripting.xmltags.XMLLanguageDriver;
import com.hippocp.mycommand.type.JdbcType;
import com.hippocp.mycommand.type.TypeAliasRegistry;
import com.hippocp.mycommand.type.TypeHandlerRegistry;

import java.util.*;
import java.util.function.BiFunction;

/**
 * @author ZhouYifan
 * @date 2022/3/2
 */
public class Configuration {

    protected boolean useActualParamName = true;

    protected boolean useColumnLabel = true;

    protected JdbcType jdbcTypeForNull = JdbcType.OTHER;

    protected boolean shrinkWhitespacesInSql;

    protected boolean nullableOnForEach;

    protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry(this);

    protected final MapperRegistry mapperRegistry = new MapperRegistry(this);

    protected final LanguageDriverRegistry languageRegistry = new LanguageDriverRegistry();

    protected final Set<String> loadedResources = new HashSet<>();

    protected Properties variables = new Properties();

    protected final Collection<XMLStatementBuilder> incompleteStatements = new LinkedList<>();

    protected final Map<String, XNode> sqlFragments = new StrictMap<>("XML fragments parsed from previous mappers");

    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    protected final Map<String, MappedStatement> mappedStatements = new StrictMap<MappedStatement>("Mapped Statements collection")
            .conflictMessageProducer((savedValue, targetValue) ->
                    ". please check " + savedValue.getResource() + " and " + targetValue.getResource());

    protected String logPrefix;

    protected ReflectorFactory reflectorFactory = new DefaultReflectorFactory();

    protected ObjectFactory objectFactory = new DefaultObjectFactory();

    protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

    protected String databaseId;

    protected String platformId;

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getDatabaseId() {
        return databaseId;
    }

    public Configuration() {
        platformIdProviderElement(this.platformId);
//        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
//        typeAliasRegistry.registerAlias("MANAGED", ManagedTransactionFactory.class);
//
//        typeAliasRegistry.registerAlias("JNDI", JndiDataSourceFactory.class);
//        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);
//        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
//
//        typeAliasRegistry.registerAlias("PERPETUAL", PerpetualCache.class);
//        typeAliasRegistry.registerAlias("FIFO", FifoCache.class);
//        typeAliasRegistry.registerAlias("LRU", LruCache.class);
        typeAliasRegistry.registerAlias("SOFT", SoftCache.class);
        typeAliasRegistry.registerAlias("WEAK", WeakCache.class);

        typeAliasRegistry.registerAlias("PLATFORM_VENDOR", VendorPlatformIdProvider.class);

        typeAliasRegistry.registerAlias("XML", XMLLanguageDriver.class);
        typeAliasRegistry.registerAlias("RAW", RawLanguageDriver.class);

        typeAliasRegistry.registerAlias("SLF4J", Slf4jImpl.class);
        typeAliasRegistry.registerAlias("COMMONS_LOGGING", JakartaCommonsLoggingImpl.class);
//        typeAliasRegistry.registerAlias("LOG4J", Log4jImpl.class);
        typeAliasRegistry.registerAlias("LOG4J2", Log4j2Impl.class);
        typeAliasRegistry.registerAlias("JDK_LOGGING", Jdk14LoggingImpl.class);
        typeAliasRegistry.registerAlias("STDOUT_LOGGING", StdOutImpl.class);
        typeAliasRegistry.registerAlias("NO_LOGGING", NoLoggingImpl.class);

//        typeAliasRegistry.registerAlias("CGLIB", CglibProxyFactory.class);
//        typeAliasRegistry.registerAlias("JAVASSIST", JavassistProxyFactory.class);

        // 设置默认LanguageDriver以修复XMLStatementBuilder类运行时NPE
        languageRegistry.setDefaultDriverClass(XMLLanguageDriver.class);
        languageRegistry.register(RawLanguageDriver.class);
    }

    public boolean isUseActualParamName() {
        return useActualParamName;
    }

    public void setUseActualParamName(boolean useActualParamName) {
        this.useActualParamName = useActualParamName;
    }

    public boolean isUseColumnLabel() {
        return useColumnLabel;
    }

    public void setUseColumnLabel(boolean useColumnLabel) {
        this.useColumnLabel = useColumnLabel;
    }

    public JdbcType getJdbcTypeForNull() {
        return jdbcTypeForNull;
    }

    public ReflectorFactory getReflectorFactory() {
        return reflectorFactory;
    }

    public void setReflectorFactory(ReflectorFactory reflectorFactory) {
        this.reflectorFactory = reflectorFactory;
    }

    public boolean isShrinkWhitespacesInSql() {
        return shrinkWhitespacesInSql;
    }

    public void setShrinkWhitespacesInSql(boolean shrinkWhitespacesInSql) {
        this.shrinkWhitespacesInSql = shrinkWhitespacesInSql;
    }

    /**
     * 构建执行器
     *
     * @param executorType 执行器类型
     * @return {@link CommandExecutor}
     */
    public CommandExecutor buildExecutor(ExecutorType executorType) {

        if (executorType == null) {
            executorType = ExecutorType.SIMPLE;
        }

        CommandExecutor executor;
        if (ExecutorType.SIMPLE == executorType) {
            executor = new SimpleCommandExecutor();
        } else {
            throw new MyCommandException("未指定执行器类型");
        }

        return executor;
    }

    private void platformIdProviderElement(String platformId) {
        if (StrUtil.isNotBlank(platformId)) {
            return;
        }
        PlatformIdProvider platformIdProvider = new VendorPlatformIdProvider();
        String providerPlatformId = platformIdProvider.getPlatformId();
        setPlatformId(providerPlatformId);
    }

    /**
     * Sets the default value of 'nullable' attribute on 'foreach' tag.
     *
     * @param nullableOnForEach If nullable, set to {@code true}
     * @since 3.5.9
     */
    public void setNullableOnForEach(boolean nullableOnForEach) {
        this.nullableOnForEach = nullableOnForEach;
    }

    /**
     * Returns the default value of 'nullable' attribute on 'foreach' tag.
     *
     * <p>Default is {@code false}.
     *
     * @return If nullable, set to {@code true}
     * @since 3.5.9
     */
    public boolean isNullableOnForEach() {
        return nullableOnForEach;
    }

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String id) {
        return this.getMappedStatement(id, true);
    }

    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }

    public MappedStatement getMappedStatement(String id, boolean validateIncompleteStatements) {
//        if (validateIncompleteStatements) {
//            buildAllStatements();
//        }
        return mappedStatements.get(id);
    }

    public Collection<String> getMappedStatementNames() {
//        buildAllStatements();
        return mappedStatements.keySet();
    }

    public Collection<MappedStatement> getMappedStatements() {
//        buildAllStatements();
        return mappedStatements.values();
    }

    public LanguageDriver getDefaultScriptingLanguageInstance() {
        return languageRegistry.getDefaultDriver();
    }

    public MetaObject newMetaObject(Object object) {
        return MetaObject.forObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
    }

    public String getLogPrefix() {
        return logPrefix;
    }

    public boolean hasStatement(String statementName) {
        return hasStatement(statementName, true);
    }

    public boolean hasStatement(String statementName, boolean validateIncompleteStatements) {
//        if (validateIncompleteStatements) {
//            buildAllStatements();
//        }
        return mappedStatements.containsKey(statementName);
    }

    public LanguageDriver getLanguageDriver(Class<? extends LanguageDriver> langClass) {
        if (langClass == null) {
            return languageRegistry.getDefaultDriver();
        }
        languageRegistry.register(langClass);
        return languageRegistry.getDriver(langClass);
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }

//    public void setDatabaseId(String databaseId) {
//        this.databaseId = databaseId;
//    }

    public <T> T getMapper(Class<T> type, CommandSession commandSession) {
        return mapperRegistry.getMapper(type, commandSession);
    }

    public boolean hasMapper(Class<?> type) {
        return mapperRegistry.hasMapper(type);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public Map<String, XNode> getSqlFragments() {
        return sqlFragments;
    }

    public Properties getVariables() {
        return variables;
    }

    public Collection<XMLStatementBuilder> getIncompleteStatements() {
        return incompleteStatements;
    }

    public void setVariables(Properties variables) {
        this.variables = variables;
    }

    public boolean isResourceLoaded(String resource) {
        return loadedResources.contains(resource);
    }

    public void addLoadedResource(String resource) {
        loadedResources.add(resource);
    }

    public void addIncompleteStatement(XMLStatementBuilder incompleteStatement) {
        incompleteStatements.add(incompleteStatement);
    }

    protected static class StrictMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -4950446264854982944L;
        private final String name;
        private BiFunction<V, V, String> conflictMessageProducer;

        public StrictMap(String name, int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
            this.name = name;
        }

        public StrictMap(String name, int initialCapacity) {
            super(initialCapacity);
            this.name = name;
        }

        public StrictMap(String name) {
            super();
            this.name = name;
        }

        public StrictMap(String name, Map<String, ? extends V> m) {
            super(m);
            this.name = name;
        }

        /**
         * Assign a function for producing a conflict error message when contains value with the same key.
         * <p>
         * function arguments are 1st is saved value and 2nd is target value.
         *
         * @param conflictMessageProducer A function for producing a conflict error message
         * @return a conflict error message
         * @since 3.5.0
         */
        public StrictMap<V> conflictMessageProducer(BiFunction<V, V, String> conflictMessageProducer) {
            this.conflictMessageProducer = conflictMessageProducer;
            return this;
        }

        @Override
        @SuppressWarnings("unchecked")
        public V put(String key, V value) {
            if (containsKey(key)) {
                throw new IllegalArgumentException(name + " already contains value for " + key
                        + (conflictMessageProducer == null ? "" : conflictMessageProducer.apply(super.get(key), value)));
            }
            if (key.contains(".")) {
                final String shortKey = getShortName(key);
                if (super.get(shortKey) == null) {
                    super.put(shortKey, value);
                } else {
                    super.put(shortKey, (V) new Ambiguity(shortKey));
                }
            }
            return super.put(key, value);
        }

        @Override
        public V get(Object key) {
            V value = super.get(key);
            if (value == null) {
                throw new IllegalArgumentException(name + " does not contain value for " + key);
            }
            if (value instanceof Ambiguity) {
                throw new IllegalArgumentException(((Ambiguity) value).getSubject() + " is ambiguous in " + name
                        + " (try using the full name including the namespace, or rename one of the entries)");
            }
            return value;
        }

        protected static class Ambiguity {
            private final String subject;

            public Ambiguity(String subject) {
                this.subject = subject;
            }

            public String getSubject() {
                return subject;
            }
        }

        private String getShortName(String key) {
            final String[] keyParts = key.split("\\.");
            return keyParts[keyParts.length - 1];
        }
    }

}
