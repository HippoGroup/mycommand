package com.hippocp.mycommand.extension.command;

import com.hippocp.mycommand.extension.ffmpeg.option.CommandSegmentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象命令片段
 *
 * @author ZhouYifan
 * @date 2022/1/18 10:23
 */
public abstract class AbstractCommandSegment<Children extends AbstractCommandSegment<Children>> {

    private static final Logger log = LoggerFactory.getLogger(AbstractCommandSegment.class);

    protected List<String> commandSegments = new ArrayList<>();

    /**
     * 占位符
     */
    protected final Children typedThis = (Children) this;

    /**
     * 命令片段构建器<br>
     */
    protected final CommandSegmentBuilder<Children> commandSegmentBuilder =
            new CommandSegmentBuilder<>(commandSegments, this.typedThis);

    /**
     * 获取所有命令片段
     *
     * @return 命令片段列表
     */
    public List<String> getCommandSegments() {
        return commandSegments;
    }

    /**
     * 添加命令
     *
     * @param arg 参数
     * @return {@link Children}
     */
    public Children addCommandSegment(String arg) {
        return addCommandSegment(true, arg);
    }

    /**
     * 添加命令
     *
     * @param condition 执行条件
     * @param arg       参数
     * @return {@link Children}
     */
    public Children addCommandSegment(boolean condition, String arg) {
        return commandSegmentBuilder.append(condition, arg);
    }


    /**
     * 添加命令
     *
     * @param arg 参数
     * @param val 值
     * @return {@link Children}
     */
    public Children addCommandSegment(String arg, String val) {
        return addCommandSegment(true, arg, val);
    }

    /**
     * 添加命令
     *
     * @param condition 执行条件
     * @param arg       参数
     * @param val       值
     * @return {@link Children}
     */
    public Children addCommandSegment(boolean condition, String arg, String val) {
        return commandSegmentBuilder.append(condition, arg, val);
    }


    /**
     * 添加命令
     *
     * @param args 参数数组
     * @return {@link Children}
     */
    public Children addCommandSegment(String[] args) {
        return addCommandSegment(true, args);
    }

    /**
     * 添加命令
     *
     * @param condition 执行条件
     * @param args      参数数组
     * @return {@link Children}
     */
    public Children addCommandSegment(boolean condition, String[] args) {
        return commandSegmentBuilder.append(condition, args);
    }

    /**
     * 组装被聚合的各个命令片段，将各个命令片段中的命令片段放入，Children commandSegment 类的属性 commandSegments 中
     *
     * @param commandSegment 继承自抽象命令片段的类
     */
    public void assemble(Children commandSegment) {
        CommandSegmentUtil.assemble(commandSegment);
    }


}
