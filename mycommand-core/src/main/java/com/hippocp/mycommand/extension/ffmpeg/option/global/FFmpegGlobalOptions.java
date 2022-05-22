package com.hippocp.mycommand.extension.ffmpeg.option.global;

import com.hippocp.mycommand.extension.command.AbstractCommandSegment;
import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.FFmpegLogLevelEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffmpeg Global Options
 *
 * @author ZhouYifan
 * @date 2022/1/24 9:34
 */
public class FFmpegGlobalOptions extends AbstractCommandSegment<FFmpegGlobalOptions> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegGlobalOptions.class);


    /**
     * 设置日志级别
     *
     * @param logLevelEnum 日志级别枚举
     * @return {@link FFmpegGlobalOptions}
     */
    public FFmpegGlobalOptions logLevel(FFmpegLogLevelEnum logLevelEnum) {
        return logLevel(logLevelEnum.getVal());
    }

    /**
     * 设置日志级别
     *
     * @param condition    执行条件
     * @param logLevelEnum 日志级别枚举
     * @return {@link FFmpegGlobalOptions}
     */
    public FFmpegGlobalOptions logLevel(boolean condition, FFmpegLogLevelEnum logLevelEnum) {
        return logLevel(condition, logLevelEnum.getVal());
    }

    /**
     * 设置日志级别
     *
     * @param logLevel 日志级别
     * @return {@link FFmpegGlobalOptions}
     */
    public FFmpegGlobalOptions logLevel(String logLevel) {
        return logLevel(true, logLevel);
    }

    /**
     * 设置日志级别
     *
     * @param condition 执行条件
     * @param logLevel  日志级别
     * @return {@link FFmpegGlobalOptions}
     */

    public FFmpegGlobalOptions logLevel(boolean condition, String logLevel) {
        return addCommandSegment(condition, FFmpegArgument.LOG_LEVEL, logLevel);
    }

    /**
     * 如有同名文件是否覆盖输出
     *
     * @param val 是否覆盖输出
     * @return {@link FFmpegGlobalOptions}
     */
    public FFmpegGlobalOptions overwriteOrNotOverwriteAndQuit(boolean val) {
        if (val) {
            // true 执行覆盖输出
            return overwrite();
        } else {
            // false 不覆盖输出并且退出进程
            return notOverwriteAndQuit();
        }
    }

    /**
     * 指定无需询问即可覆盖输出文件
     *
     * @return {@link FFmpegGlobalOptions}
     */
    public FFmpegGlobalOptions overwrite() {
        return overwrite(true);
    }

    /**
     * 指定无需询问即可覆盖输出文件
     *
     * @param condition 执行条件
     * @return {@link FFmpegGlobalOptions}
     */
    public FFmpegGlobalOptions overwrite(boolean condition) {
        return addCommandSegment(condition, FFmpegArgument.OVERWRITE);
    }

    /**
     * 指定不要覆盖输出文件，如果指定的输出文件已经存在，则立即退出。
     *
     * @return {@link FFmpegGlobalOptions}
     */
    public FFmpegGlobalOptions notOverwriteAndQuit() {
        return notOverwriteAndQuit(true);
    }

    /**
     * 指定不要覆盖输出文件，如果指定的输出文件已经存在，则立即退出。
     *
     * @param condition 执行条件
     * @return {@link FFmpegGlobalOptions}
     */
    public FFmpegGlobalOptions notOverwriteAndQuit(boolean condition) {
        return addCommandSegment(condition, FFmpegArgument.NOT_OVERWRITE);
    }


}
