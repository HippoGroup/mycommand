package com.hippocp.mycommand.session;

import com.hippocp.mycommand.customize.CommandFileCustomize;
import com.hippocp.mycommand.exceptions.ExceptionFactory;
import com.hippocp.mycommand.executor.CommandExecutor;
import com.hippocp.mycommand.executor.ErrorContext;
import com.hippocp.mycommand.extension.command.AbstractCommand;
import com.hippocp.mycommand.mapping.MappedStatement;
import com.hippocp.mycommand.reflection.ParamNameResolver;
import org.apache.commons.exec.ProcessDestroyer;

/**
 * @author ZhouYifan
 * @date 2022/3/10
 */
public class DefaultCommandSession implements CommandSession {

    private final Configuration configuration;
    private final CommandExecutor commandExecutor;

    public DefaultCommandSession(Configuration configuration, CommandExecutor commandExecutor) {
        this.configuration = configuration;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public int executeSync(String statement, Object parameter) {
        try {
            MappedStatement ms = configuration.getMappedStatement(statement);
            Object wrapCollection = wrapCollection(parameter);
            return commandExecutor.executeSync(ms, wrapCollection);
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error execute command.  Cause: " + e, e);
        } finally {
            ErrorContext.instance().reset();
        }
    }

    @Override
    public int executeSync(AbstractCommand command, Integer exitValueNumber, Integer timeoutSeconds, ResultHandlerStream stream, CommandFileCustomize customize, ProcessDestroyer destroyer) {
        try {
            return commandExecutor.executeSync(command, exitValueNumber, timeoutSeconds, stream, customize, destroyer);
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error execute command.  Cause: " + e, e);
        } finally {
            ErrorContext.instance().reset();
        }
    }

    private Object wrapCollection(final Object object) {
        return ParamNameResolver.wrapToMapIfCollection(object, null);
    }

    @Override
    public void executeAsync(String statement, Object parameter) {
        try {
            MappedStatement ms = configuration.getMappedStatement(statement);
            Object wrapCollection = wrapCollection(parameter);
            commandExecutor.executeAsync(ms, wrapCollection);
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error execute command.  Cause: " + e, e);
        } finally {
            ErrorContext.instance().reset();
        }
    }

    @Override
    public void executeAsync(AbstractCommand command, Integer exitValueNumber, Integer timeoutSeconds, ResultHandlerStream stream, CommandFileCustomize customize, ProcessDestroyer destroyer) {
        try {
            commandExecutor.executeAsync(command, exitValueNumber, timeoutSeconds, stream, customize, destroyer);
        } catch (Exception e) {
            throw ExceptionFactory.wrapException("Error execute command.  Cause: " + e, e);
        } finally {
            ErrorContext.instance().reset();
        }
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return getConfiguration().getMapper(type, this);
    }
}
