package com.hippocp.mycommand.extension.exception;

/**
 * 命令构建异常
 *
 * @author ZhouYifan
 * @date 2022/1/1 23:21
 */
public class CommandSegmentAssembleException extends RuntimeException {

    public CommandSegmentAssembleException(String message) {
        super(message);
    }

    public CommandSegmentAssembleException(String message, Throwable cause) {
        super(message, cause);
    }
}
