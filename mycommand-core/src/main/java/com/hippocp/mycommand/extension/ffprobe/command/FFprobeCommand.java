package com.hippocp.mycommand.extension.ffprobe.command;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.hippocp.mycommand.extension.command.AbstractSingleCommand;
import com.hippocp.mycommand.extension.exception.FFprobeCommandBuildException;
import com.hippocp.mycommand.extension.ffprobe.argument.FFprobeArgument;
import com.hippocp.mycommand.extension.ffprobe.argument.value.PrintFormatEnum;
import org.apache.commons.exec.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffprobe 命令
 *
 * @author ZhouYifan
 * @date 2022/1/18 10:18
 */
public class FFprobeCommand extends AbstractSingleCommand<FFprobeCommand> {

    private static final Logger log = LoggerFactory.getLogger(FFprobeCommand.class);

    /**
     * 输出打印格式设置为默认
     *
     * @return {@link FFprobeCommand}
     */
    public FFprobeCommand printFormat() {
        return printFormat(PrintFormatEnum.DEFAULT);
    }

    /**
     * 输出打印格式设置为默认
     *
     * @param condition 执行条件
     * @return {@link FFprobeCommand}
     */
    public FFprobeCommand printFormat(boolean condition) {
        return printFormat(condition, PrintFormatEnum.DEFAULT);
    }

    /**
     * 设置输出打印格式(可用的格式有 default, compact, csv, flat, ini, json, xml)
     *
     * @param printFormatEnum 输出打印格式枚举
     * @return {@link FFprobeCommand}
     */
    public FFprobeCommand printFormat(PrintFormatEnum printFormatEnum) {
        return printFormat(true, printFormatEnum.getVal());
    }

    /**
     * 设置输出打印格式(可用的格式有 default, compact, csv, flat, ini, json, xml)
     *
     * @param printFormatEnum 输出打印格式枚举
     * @return {@link FFprobeCommand}
     */
    public FFprobeCommand printFormat(boolean condition, PrintFormatEnum printFormatEnum) {
        return printFormat(condition, printFormatEnum.getVal());
    }

    /**
     * @param condition 执行条件
     * @param format    输出打印格式
     * @return {@link FFprobeCommand}
     */
    public FFprobeCommand printFormat(boolean condition, String format) {
        return addCommand(true, FFprobeArgument.PRINT_FORMAT, format);
    }

    /**
     * 显示所有流视频流、音频流、字幕流、图片流、数据流
     *
     * @return {@link FFprobeCommand}
     */
    public FFprobeCommand showStreams() {
        return showStreams(true);
    }

    /**
     * 显示所有流视频流、音频流、字幕流、图片流、数据流
     *
     * @param condition 执行条件
     * @return {@link FFprobeCommand}
     */
    public FFprobeCommand showStreams(boolean condition) {
        return addCommand(true, FFprobeArgument.SHOW_STREAMS);
    }


    /**
     * 校验
     *
     * @param filePath 需要执行校验的文件夹路径
     */
    protected void validate(String filePath) {
        // 未指定 ffprobe 命令行工具路径抛异常
        if (StrUtil.isBlank(filePath)) {
            String msg = "请务必输入 ffprobe 命令行工具可执行文件路径";
            log.warn(msg);
            throw new FFprobeCommandBuildException(msg);
        }
        // 不是文件
        boolean isNotFile = !FileUtil.isFile(filePath);
        // 需要文件，而不是文件夹
        if (isNotFile) {
            String msg = "请输入 ffprobe 命令行工具可执行文件路径，不要输入文件夹路径";
            log.warn(msg);
            throw new FFprobeCommandBuildException(msg);
        }
    }

    @Override
    protected void setCommandLine() {
        this.commandLine = new CommandLine(FFprobeArgument.FFPROBE);
    }

    @Override
    public void assemble(boolean handleQuoting) {
        // todo 待完善的空方法
    }
}
