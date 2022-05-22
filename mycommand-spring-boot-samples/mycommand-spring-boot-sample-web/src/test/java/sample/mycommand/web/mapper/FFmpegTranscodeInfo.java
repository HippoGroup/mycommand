package sample.mycommand.web.mapper;

import org.apache.commons.exec.DefaultExecuteResultHandler;

import java.util.Objects;

/**
 * 转码信息
 *
 * @author ZhouYifan
 * @date 2022/3/4
 */
public class FFmpegTranscodeInfo {

    /**
     * 转码命令
     */
    private String command;

    /**
     * ffmpeg 转码动态信息
     */
    private FFmpegTranscodeDynamicInfo dynamicInfo;

    /**
     * 是否异步执行
     */
    private boolean isAsync;

    /**
     * 仅在异步执行是不为 null
     * 用于异步进程处理的“ExecuteResultHandler”的默认实现。
     */
    private DefaultExecuteResultHandler resultHandler;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FFmpegTranscodeInfo that = (FFmpegTranscodeInfo) o;
        return isAsync == that.isAsync && Objects.equals(command, that.command) && Objects.equals(dynamicInfo, that.dynamicInfo) && Objects.equals(resultHandler, that.resultHandler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, dynamicInfo, isAsync, resultHandler);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FFmpegTranscodeInfo{");
        sb.append("command='").append(command).append('\'');
        sb.append(", dynamicInfo=").append(dynamicInfo);
        sb.append(", isAsync=").append(isAsync);
        sb.append(", resultHandler=").append(resultHandler);
        sb.append('}');
        return sb.toString();
    }

    public DefaultExecuteResultHandler getResultHandler() {
        return resultHandler;
    }

    public void setResultHandler(DefaultExecuteResultHandler resultHandler) {
        this.resultHandler = resultHandler;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public FFmpegTranscodeDynamicInfo getDynamicInfo() {
        return dynamicInfo;
    }

    public void setDynamicInfo(FFmpegTranscodeDynamicInfo dynamicInfo) {
        this.dynamicInfo = dynamicInfo;
    }

    public boolean isAsync() {
        return isAsync;
    }

    public void setAsync(boolean async) {
        isAsync = async;
    }

    public FFmpegTranscodeInfo() {
    }

    public FFmpegTranscodeInfo(String command, FFmpegTranscodeDynamicInfo dynamicInfo) {
        this.command = command;
        this.dynamicInfo = dynamicInfo;
    }

    public FFmpegTranscodeInfo(String command, FFmpegTranscodeDynamicInfo dynamicInfo, DefaultExecuteResultHandler resultHandler) {
        this.command = command;
        this.dynamicInfo = dynamicInfo;
        this.resultHandler = resultHandler;
    }

    public FFmpegTranscodeInfo(String command, FFmpegTranscodeDynamicInfo dynamicInfo, boolean isAsync, DefaultExecuteResultHandler resultHandler) {
        this.command = command;
        this.dynamicInfo = dynamicInfo;
        this.isAsync = isAsync;
        this.resultHandler = resultHandler;
    }
}
