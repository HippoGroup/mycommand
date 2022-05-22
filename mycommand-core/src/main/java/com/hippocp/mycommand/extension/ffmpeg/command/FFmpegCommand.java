package com.hippocp.mycommand.extension.ffmpeg.command;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.hippocp.mycommand.extension.command.AbstractSingleCommand;
import com.hippocp.mycommand.extension.exception.FFmpegCommandBuildException;
import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import com.hippocp.mycommand.extension.ffmpeg.option.FFmpegInputOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.FFmpegOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.FFmpegOutputOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.composition.FFmpegInputOptionsAndFile;
import com.hippocp.mycommand.extension.ffmpeg.option.composition.FFmpegOutputOptionsAndFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.exec.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ffmpeg 命令
 *
 * @author ZhouYifan
 * @date 2022/1/17 12:29
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class FFmpegCommand extends AbstractSingleCommand<FFmpegCommand> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegCommand.class);

    private final FFmpegOptions options;

    private final List<FFmpegInputOptionsAndFile> inputOptionsAndFileList;

    private final List<FFmpegOutputOptionsAndFile> outputOptionsAndFileList;

    public FFmpegCommand() {
        this.options = null;
        this.inputOptionsAndFileList = null;
        this.outputOptionsAndFileList = null;
    }

    /**
     * 组装被聚合的各个选项
     *
     * @param handleQuoting 处理引用
     */
    @Override
    public void assemble(boolean handleQuoting) {
        // 组装
        // [options]
        options.assemble(options);
        List<String> optionCommandSegments = options.getCommandSegments();
        this.addCommand(optionCommandSegments);

        // 组装
        // [[infile options] -i infile]...
        String inputFilePath = "null";
        for (FFmpegInputOptionsAndFile inputOptionsAndFile : inputOptionsAndFileList) {
            FFmpegInputOptions inputOptions = inputOptionsAndFile.getInputOption();
            inputFilePath = inputOptionsAndFile.getInputFilePath();
            if (inputOptions == null) {
                continue;
            }
            // 调用 FFmpegInputOptions#assemble 方法
            inputOptions.assemble(inputOptions);
            // 获取 inputOptions 所有命令片段
            List<String> inputOptionsCommandSegments = inputOptions.getCommandSegments();
            // 合并至自身的 commandLine 属性中
            this.addCommand(inputOptionsCommandSegments, handleQuoting);
            this.addCommand(FFmpegArgument.INPUT, inputFilePath, handleQuoting);
        }

        // 组装
        // {[outfile options] outfile}...
        String outputFilePath = "null";
        for (FFmpegOutputOptionsAndFile outputOptionsAndFile : outputOptionsAndFileList) {
            FFmpegOutputOptions outputOptions = outputOptionsAndFile.getOutputOption();
            outputFilePath = outputOptionsAndFile.getOutputFilePath();
            // 调用 FFmpegOutputOptions#assemble 方法
            outputOptions.assemble(outputOptions);
//            CommandSegmentUtil.assemble(outputOptions);
            // 获取 outputOptions 所有命令片段
            List<String> outputOptionsCommandSegments = outputOptions.getCommandSegments();
            // 合并至自身的 commandLine 属性中
            this.addCommand(outputOptionsCommandSegments);
            this.addCommand(outputFilePath);
        }

        // FFmpeg 命令组装完成

    }

    /**
     * 校验
     *
     * @param filePath 需要执行分拣的文件夹路径
     */
    protected void validate(String filePath) {
        // 未指定 ffmpeg 命令行工具路径抛异常
        if (StrUtil.isBlank(filePath)) {
            String msg = "请务必输入 ffmpeg 命令行工具文件路径";
            log.warn(msg);
            throw new FFmpegCommandBuildException(msg);
        }
        // 不是文件
        boolean isNotFile = !FileUtil.isFile(filePath);
        // 需要文件，而不是文件夹
        if (isNotFile) {
            String msg = "请输入 ffmpeg 命令行工具文件路径，不要输入文件夹路径";
            log.warn(msg);
            throw new FFmpegCommandBuildException(msg);
        }
    }

    @Override
    protected void setCommandLine() {
        this.commandLine = new CommandLine(FFmpegArgument.FFMPEG);
    }

}
