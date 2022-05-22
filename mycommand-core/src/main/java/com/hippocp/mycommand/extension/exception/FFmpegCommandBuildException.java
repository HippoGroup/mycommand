package com.hippocp.mycommand.extension.exception;

/**
 * ffmpeg命令行构建异常
 *
 * @author ZhouYifan
 * @date 2022/1/1 23:21
 */
public class FFmpegCommandBuildException extends CommandBuildException {

    public FFmpegCommandBuildException(String message) {
        super(message);
    }

}
