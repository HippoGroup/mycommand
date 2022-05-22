package com.hippocp.mycommand.extension.ffmpeg.argument.value;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * ffmpeg 日志级别枚举类
 *
 * @author ZhouYifan
 * @date 2022/1/26 9:02
 */
@Getter
@ToString
@AllArgsConstructor
public enum FFmpegLogLevelEnum {
    /**
     * 只显示致命错误。这些是错误，之后该过程绝对无法继续。
     * Only show fatal errors. These are errors after which the process absolutely cannot continue.
     */
    FATAL(FFmpegArgument.LOG_LEVEL, "fatal", "日志级别说明：只显示致命错误。这些是错误，之后该过程绝对无法继续。"),
    /**
     * 显示所有错误，包括可以从中恢复的错误。
     * Show all errors, including ones which can be recovered from.
     */
    ERROR(FFmpegArgument.LOG_LEVEL, "error", "日志级别说明：显示所有错误，包括可以从中恢复的错误。"),
    /**
     * 显示所有警告和错误。将显示与可能不正确或意外事件相关的任何消息。
     * Show all warnings and errors. Any message related to possibly incorrect or unexpected events will be shown.
     */
    WARNING(FFmpegArgument.LOG_LEVEL, "warning", "日志级别说明：显示所有警告和错误。将显示与可能不正确或意外事件相关的任何消息。"),
    /**
     * 在处理过程中显示信息性消息。这是对警告和错误的补充。这是默认值。
     * Show informative messages during processing. This is in addition to warnings and errors. This is the default value.
     */
    INFO(FFmpegArgument.LOG_LEVEL, "info", "日志级别说明：在处理过程中显示信息性消息。这是对警告和错误的补充。这是默认值。"),
    /**
     * 与 info 相同，但更详细。
     * Same as info, except more verbose.
     */
    VERBOSE(FFmpegArgument.LOG_LEVEL, "verbose", "日志级别说明：与 info 相同，但更详细。"),
    /**
     * 显示所有内容，包括调试信息。
     * Show everything, including debugging information.
     */
    DEBUG(FFmpegArgument.LOG_LEVEL, "debug", "日志级别说明：显示所有内容，包括调试信息。");


    /**
     * 参数名
     */
    private String arg;
    /**
     * 参数值
     */
    private String val;
    /**
     * 描述
     */
    private String description;

}
