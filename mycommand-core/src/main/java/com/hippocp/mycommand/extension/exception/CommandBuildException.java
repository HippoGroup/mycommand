package com.hippocp.mycommand.extension.exception;

/**
 * 命令构建异常
 *
 * @author ZhouYifan
 * @date 2022/1/1 23:21
 */
public class CommandBuildException extends RuntimeException {

    public CommandBuildException(String message) {
        super(message);
    }

}
