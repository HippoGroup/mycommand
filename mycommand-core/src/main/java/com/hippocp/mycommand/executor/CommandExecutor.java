package com.hippocp.mycommand.executor;

import com.hippocp.mycommand.customize.CommandFileCustomize;
import com.hippocp.mycommand.extension.command.AbstractCommand;
import com.hippocp.mycommand.mapping.MappedStatement;
import com.hippocp.mycommand.session.ResultHandlerStream;
import org.apache.commons.exec.ProcessDestroyer;

import java.io.IOException;

/**
 * 命令执行器
 *
 * @author ZhouYifan
 * @date 2022/3/10
 */
public interface CommandExecutor {
    /**
     * 设置执行器，非底层执行器
     *
     * @param commandExecutor {@link CommandExecutor}
     */
    void setExecutorWrapper(CommandExecutor commandExecutor);

    /**
     * 同步执行子进程
     *
     * @param ms              {@link MappedStatement}
     * @param parameterObject 参数
     * @return 子进程退出值
     * @throws IOException 子进程执行异常
     */
    int executeSync(MappedStatement ms, Object parameterObject) throws IOException;

    /**
     * 同步执行子进程
     *
     * @param command         {@link AbstractCommand}
     * @param exitValueNumber 指定子进程正常退出值
     * @param timeoutSeconds  指定子进程执行超时时间（秒）
     * @param stream          {@link ResultHandlerStream}
     * @param customize       {@link CommandFileCustomize}
     * @param destroyer       {@link ProcessDestroyer}
     * @return 子进程退出值
     * @throws IOException 子进程执行异常
     */
    int executeSync(
            AbstractCommand command,
            Integer exitValueNumber,
            Integer timeoutSeconds,
            ResultHandlerStream stream,
            CommandFileCustomize customize,
            ProcessDestroyer destroyer
    ) throws IOException;

    /**
     * 异步执行子进程
     *
     * @param ms              {@link MappedStatement}
     * @param parameterObject 参数
     * @throws IOException 子进程执行异常
     */
    void executeAsync(MappedStatement ms, Object parameterObject) throws IOException;

    /**
     * 异步执行子进程
     *
     * @param command         {@link AbstractCommand}
     * @param exitValueNumber 指定子进程正常退出值
     * @param timeoutSeconds  指定子进程执行超时时间（秒）
     * @param stream          {@link ResultHandlerStream}
     * @param customize       {@link CommandFileCustomize}
     * @param destroyer       {@link ProcessDestroyer}
     * @throws IOException 子进程执行异常
     */
    void executeAsync(
            AbstractCommand command,
            Integer exitValueNumber,
            Integer timeoutSeconds,
            ResultHandlerStream stream,
            CommandFileCustomize customize,
            ProcessDestroyer destroyer
    ) throws IOException;


}
