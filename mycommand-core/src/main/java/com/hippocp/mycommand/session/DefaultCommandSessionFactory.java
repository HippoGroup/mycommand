package com.hippocp.mycommand.session;

import com.hippocp.mycommand.executor.CommandExecutor;
import com.hippocp.mycommand.executor.ExecutorType;

/**
 * @author ZhouYifan
 * @date 2022/3/14
 */
public class DefaultCommandSessionFactory implements CommandSessionFactory {

    private final Configuration configuration;

    public DefaultCommandSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public CommandSession openSession() {
        CommandExecutor executor = this.configuration.buildExecutor(null);
        return new DefaultCommandSession(this.configuration, executor);
    }

    @Override
    public CommandSession openSession(ExecutorType execType) {
        CommandExecutor executor = this.configuration.buildExecutor(execType);
        return new DefaultCommandSession(this.configuration, executor);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }
}
