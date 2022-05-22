package com.hippocp.mycommand.extension.exception;

/**
 * ffprobe命令构建异常
 *
 * @author ZhouYifan
 * @date 2022/1/1 23:21
 */
public class FFprobeCommandExecuteException extends CommandBuildException {

    public FFprobeCommandExecuteException(String message) {
        super(message);
    }

}
