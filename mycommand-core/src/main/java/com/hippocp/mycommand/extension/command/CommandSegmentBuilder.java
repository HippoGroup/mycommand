package com.hippocp.mycommand.extension.command;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.exec.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * {@link CommandLine}命令行对象构建器<br>
 * 封装{@link CommandLine}命令行对象操作方法
 *
 * @author ZhouYifan
 * @date 2022/1/27 10:43
 */
@Data
@AllArgsConstructor
public class CommandSegmentBuilder<T> {

    private static final Logger log = LoggerFactory.getLogger(CommandSegmentBuilder.class);

    /**
     * 命令行对象
     */
    private List<String> commandSegments;

    /**
     * 一个对象，代表操作哪个对象中命令行对象
     */
    private T type;

    /**
     * 添加命令片段
     *
     * @param arg 参数
     * @return {@link T}
     */
    public T append(String arg) {
        return this.maybeDo(true, () -> commandSegments.add(arg));
    }

    /**
     * 添加命令片段
     *
     * @param condition 执行条件
     * @param arg       参数
     * @return {@link T}
     */
    public T append(boolean condition, String arg) {
        return this.maybeDo(condition, () -> commandSegments.add(arg));
    }


    /**
     * 添加命令片段
     *
     * @param arg 参数
     * @param val 值
     * @return {@link T}
     */
    public T append(String arg, String val) {
        return this.append(true, arg, val);
    }

    /**
     * 添加命令片段
     *
     * @param condition 执行条件
     * @param arg       参数
     * @param val       值
     * @return {@link T}
     */
    public T append(boolean condition, String arg, String val) {
        return this.maybeDo(condition, () -> {
            commandSegments.add(arg);
            commandSegments.add(val);
        });
    }

    /**
     * 添加命令片段
     *
     * @param args 参数数组
     * @return {@link T}
     */
    public T append(String[] args) {
        return this.append(true, args);
    }

    /**
     * 添加命令片段
     *
     * @param condition 执行条件
     * @param args      参数数组
     * @return {@link T}
     */
    public T append(boolean condition, String[] args) {
        if (ArrayUtil.isEmpty(args)) {
            return type;
        }
        List<String> asList = Arrays.asList(args);
        return this.maybeDo(condition, () -> commandSegments.addAll(asList));
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
