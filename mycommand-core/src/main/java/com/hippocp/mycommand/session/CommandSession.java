package com.hippocp.mycommand.session;


import com.hippocp.mycommand.customize.CommandFileCustomize;
import com.hippocp.mycommand.extension.command.AbstractCommand;
import org.apache.commons.exec.ProcessDestroyer;

/**
 * @author ZhouYifan
 * @date 2022/3/9
 */
public interface CommandSession {

    int executeSync(String statement, Object parameter);


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
     */
    int executeSync(AbstractCommand command,
                    Integer exitValueNumber,
                    Integer timeoutSeconds,
                    ResultHandlerStream stream,
                    CommandFileCustomize customize,
                    ProcessDestroyer destroyer);


    void executeAsync(String statement, Object parameter);

    /**
     * 异步执行子进程
     *
     * @param command         {@link AbstractCommand}
     * @param exitValueNumber 指定子进程正常退出值
     * @param timeoutSeconds  指定子进程执行超时时间（秒）
     * @param stream          {@link ResultHandlerStream}
     * @param customize       {@link CommandFileCustomize}
     * @param destroyer       {@link ProcessDestroyer}
     */
    void executeAsync(AbstractCommand command,
                      Integer exitValueNumber,
                      Integer timeoutSeconds,
                      ResultHandlerStream stream,
                      CommandFileCustomize customize,
                      ProcessDestroyer destroyer);


    /**
     * Retrieves current configuration.
     *
     * @return Configuration
     */
    Configuration getConfiguration();

    /**
     * Retrieves a mapper.
     *
     * @param <T>  the mapper type
     * @param type Mapper interface class
     * @return a mapper bound to this SqlSession
     */
    <T> T getMapper(Class<T> type);
}
