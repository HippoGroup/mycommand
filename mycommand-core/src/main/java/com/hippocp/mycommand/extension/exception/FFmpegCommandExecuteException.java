package com.hippocp.mycommand.extension.exception;

/**
 * ffmpeg命令行执行异常
 *
 * @author ZhouYifan
 * @date 2022/1/1 23:21
 */
public class FFmpegCommandExecuteException extends RuntimeException {

    public FFmpegCommandExecuteException(String message) {
        super(message);
    }

}
