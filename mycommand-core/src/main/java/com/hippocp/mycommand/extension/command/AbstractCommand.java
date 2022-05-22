package com.hippocp.mycommand.extension.command;

import com.hippocp.mycommand.customize.CommandFileCustomize;
import com.hippocp.mycommand.session.*;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ProcessDestroyer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 抽象命令
 *
 * @author ZhouYifan
 * @date 2022/1/18 10:23
 */
public abstract class AbstractCommand<Children extends AbstractCommand<Children>> {

    private static final Logger log = LoggerFactory.getLogger(AbstractCommand.class);

    /**
     * 命令行对象
     */
    protected CommandLine commandLine;

    /**
     * 占位符
     */
    protected final Children typedThis = (Children) this;


    /**
     * {@link CommandLine}命令行对象构建器<br>
     */
    protected final CommandLineBuilder<Children> commandLineBuilder;


    public AbstractCommand() {
        // 通过构造函数为 commandLine 属性赋值
        this.setCommandLine();
        this.commandLineBuilder = new CommandLineBuilder<>(commandLine, this.typedThis);
    }


    /**
     * 获取命令行对象
     *
     * @return {@link CommandLine}命令行对象
     */
    public CommandLine getCommandLine() {
        return commandLine;
    }

    /**
     * 设置命令行对象<br>
     * 将命令可执行文件的设置过程延迟到子类完成
     */
    protected abstract void setCommandLine();

    /**
     * 组装被聚合的各个选项，默认处理引用
     */
    public void assemble() {
        assemble(true);
    }

    /**
     * 组装被聚合的各个选项
     *
     * @param handleQuoting 处理引用
     */
    public abstract void assemble(boolean handleQuoting);


    /**
     * 添加命令
     *
     * @param arg 参数
     * @return {@link Children}
     */
    public Children addCommand(String arg) {
        return this.addCommand(true, arg);
    }

    /**
     * 添加命令
     *
     * @param arg           参数
     * @param handleQuoting 处理引用
     * @return {@link Children}
     */
    public Children addCommand(String arg, boolean handleQuoting) {
        return this.addCommand(true, arg, handleQuoting);
    }

    /**
     * 添加命令
     *
     * @param condition 执行条件
     * @param arg       参数
     * @return {@link Children}
     */
    public Children addCommand(boolean condition, String arg) {
        return this.addCommand(condition, arg, true);
    }

    /**
     * 添加命令
     *
     * @param condition     执行条件
     * @param arg           参数
     * @param handleQuoting 处理引用
     * @return {@link Children}
     */
    public Children addCommand(boolean condition, String arg, boolean handleQuoting) {
        return commandLineBuilder.append(condition, arg, handleQuoting);
    }

    /**
     * 添加命令
     *
     * @param arg 参数
     * @param val 值
     * @return {@link Children}
     */
    public Children addCommand(String arg, String val) {
        return this.addCommand(true, arg, val, true);
    }

    /**
     * 添加命令
     *
     * @param arg           参数
     * @param val           值
     * @param handleQuoting 处理引用
     * @return {@link Children}
     */
    public Children addCommand(String arg, String val, boolean handleQuoting) {
        return this.addCommand(true, arg, val, handleQuoting);
    }

    /**
     * 添加命令
     *
     * @param condition 执行条件
     * @param arg       参数
     * @param val       值
     * @return {@link Children}
     */
    public Children addCommand(boolean condition, String arg, String val) {
        return this.addCommand(condition, arg, val, true);
    }

    /**
     * 添加命令
     *
     * @param condition     执行条件
     * @param arg           参数
     * @param val           值
     * @param handleQuoting 处理引用
     * @return {@link Children}
     */
    public Children addCommand(boolean condition, String arg, String val, boolean handleQuoting) {
        String[] args = new String[]{arg, val};
        return commandLineBuilder.append(condition, args, handleQuoting);
    }

    /**
     * 添加命令
     *
     * @param args 参数数组
     * @return {@link Children}
     */
    public Children addCommand(List<String> args) {
        return this.addCommand(true, args);
    }

    /**
     * 添加命令
     *
     * @param args          参数数组
     * @param handleQuoting 处理引用
     * @return {@link Children}
     */
    public Children addCommand(List<String> args, boolean handleQuoting) {
        return this.addCommand(true, args, handleQuoting);
    }

    /**
     * 添加命令
     *
     * @param condition 执行条件
     * @param args      参数数组
     * @return {@link Children}
     */
    public Children addCommand(boolean condition, List<String> args) {
        return this.addCommand(condition, args, true);
    }

    /**
     * 添加命令
     *
     * @param condition     执行条件
     * @param args          参数数组
     * @param handleQuoting 处理引用
     * @return {@link Children}
     */
    public Children addCommand(boolean condition, List<String> args, boolean handleQuoting) {
        String[] argArray = args.toArray(new String[]{});
        return commandLineBuilder.append(condition, argArray, handleQuoting);
    }

    /**
     * 添加命令
     *
     * @param args 参数数组
     * @return {@link Children}
     */
    public Children addCommand(String[] args) {
        return this.addCommand(true, args);
    }

    /**
     * 添加命令
     *
     * @param condition 执行条件
     * @param args      参数数组
     * @return {@link Children}
     */
    public Children addCommand(boolean condition, String[] args) {
        return this.addCommand(condition, args, true);
    }

    /**
     * 添加命令
     *
     * @param condition     执行条件
     * @param args          参数数组
     * @param handleQuoting 处理引用
     * @return {@link Children}
     */
    public Children addCommand(boolean condition, String[] args, boolean handleQuoting) {
        return commandLineBuilder.append(condition, args, handleQuoting);
    }

    public void executeAsync(
            int exitValueNumber,
            int timeoutSeconds,
            ResultHandlerStream stream
    ) {
        executeAsync(exitValueNumber, timeoutSeconds, stream, null, null);
    }

    public void executeAsync(
            int exitValueNumber,
            int timeoutSeconds,
            ResultHandlerStream stream,
            CommandFileCustomize customize
    ) {
        executeAsync(exitValueNumber, timeoutSeconds, stream, customize, null);
    }

    public void executeAsync(
            int exitValueNumber,
            int timeoutSeconds,
            ResultHandlerStream stream,
            CommandFileCustomize customize,
            ProcessDestroyer destroyer
    ) {
        CommandSessionFactory factory = new CommandSessionFactoryBuilder().build(new Configuration());
        CommandSession session = factory.openSession();
        session.executeAsync(this, exitValueNumber, timeoutSeconds, stream, customize, destroyer);
    }

    public int executeSync(
            int exitValueNumber,
            int timeoutSeconds,
            ResultHandlerStream stream
    ) {
        return executeSync(exitValueNumber, timeoutSeconds, stream, null, null);
    }

    public int executeSync(
            int exitValueNumber,
            int timeoutSeconds,
            ResultHandlerStream stream,
            CommandFileCustomize customize
    ) {
        return executeSync(exitValueNumber, timeoutSeconds, stream, customize, null);
    }

    /**
     * 同步执行命令方法
     *
     * @param exitValueNumber 命令执行成功时的返回码
     * @param timeoutSeconds  超时强制退出时间 单位：秒
     * @param stream          {@link ResultHandlerStream}
     * @param customize       {@link CommandFileCustomize}
     * @param destroyer       {@link ProcessDestroyer}
     */
    public int executeSync(
            int exitValueNumber,
            int timeoutSeconds,
            ResultHandlerStream stream,
            CommandFileCustomize customize,
            ProcessDestroyer destroyer
    ) {
        CommandSessionFactory factory = new CommandSessionFactoryBuilder().build(new Configuration());
        CommandSession session = factory.openSession();
        return session.executeSync(this, exitValueNumber, timeoutSeconds, stream, customize, destroyer);
    }


}
