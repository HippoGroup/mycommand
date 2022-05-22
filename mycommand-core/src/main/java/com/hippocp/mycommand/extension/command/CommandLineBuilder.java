package com.hippocp.mycommand.extension.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.exec.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link CommandLine}命令行对象构建器<br>
 * 封装{@link CommandLine}命令行对象操作方法
 *
 * @author ZhouYifan
 * @date 2022/1/27 10:43
 */
@Data
@AllArgsConstructor
public class CommandLineBuilder<T> {

    private static final Logger log = LoggerFactory.getLogger(CommandLineBuilder.class);

    /**
     * 命令行对象
     */
    private CommandLine cmdLine;

    /**
     * 一个对象，代表操作哪个对象中命令行对象
     */
    private T type;

    /**
     * 添加命令
     *
     * @param arg 参数
     * @return {@link T}
     */
    public T append(String arg) {
        return this.maybeDo(true, () -> cmdLine.addArgument(arg));
    }

    /**
     * 添加命令
     *
     * @param condition 执行条件
     * @param arg       参数
     * @return {@link T}
     */
    public T append(boolean condition, String arg) {
        return this.maybeDo(condition, () -> cmdLine.addArgument(arg));
    }

    /**
     * 添加命令
     *
     * @param condition     执行条件
     * @param arg           参数
     * @param handleQuoting 处理引用
     * @return {@link T}
     */
    public T append(boolean condition, String arg, boolean handleQuoting) {
        return this.maybeDo(condition, () -> cmdLine.addArgument(arg, handleQuoting));
    }

    /**
     * 添加命令
     *
     * @param arg 参数
     * @param val 值
     * @return {@link T}
     */
    public T append(String arg, String val) {
        return this.append(true, arg, val, true);
    }

    /**
     * 添加命令
     *
     * @param condition 执行条件
     * @param arg       参数
     * @param val       值
     * @return {@link T}
     */
    public T append(boolean condition, String arg, String val) {
        return this.append(condition, arg, val, true);
    }

    /**
     * 添加命令
     *
     * @param condition     执行条件
     * @param arg           参数
     * @param val           值
     * @param handleQuoting 处理引用
     * @return {@link T}
     */
    public T append(boolean condition, String arg, String val, boolean handleQuoting) {
        return this.maybeDo(condition, () -> cmdLine.addArgument(arg, handleQuoting).addArguments(val, handleQuoting));
    }

    /**
     * 添加命令
     *
     * @param args 参数数组
     * @return {@link T}
     */
    public T append(String[] args) {
        return this.maybeDo(true, () -> cmdLine.addArguments(args));
    }

    /**
     * 添加命令
     *
     * @param condition 执行条件
     * @param args      参数数组
     * @return {@link T}
     */
    public T append(boolean condition, String[] args) {
        return this.maybeDo(condition, () -> cmdLine.addArguments(args));
    }

    /**
     * 添加命令
     *
     * @param condition     执行条件
     * @param args          参数数组
     * @param handleQuoting 处理引用
     * @return {@link T}
     */
    public T append(boolean condition, String[] args, boolean handleQuoting) {
        return this.maybeDo(condition, () -> cmdLine.addArguments(args, handleQuoting));
    }

    /**
     * 函数化的做事
     *
     * @param condition 做不做
     * @param something 做什么
     * @return T
     */
    private T maybeDo(boolean condition, DoSomething something) {
        if (condition) {
            something.doIt();
        }
        return this.type;
    }


}
