package com.hippocp.mycommand.extension.ffmpeg.argument.constant;

/**
 * libx264 编码器参数
 *
 * @author ZhouYifan
 * @date 2022/1/18 17:50
 */
public interface LibX264Argument {
    /**
     * 视频码率模式
     * Signal HRD information (requires vbv-bufsize; cbr not allowed in .mp4) (from -1 to INT_MAX) (default -1)
     */
    String VIDEO_BITRATE_MODEL = "-nal-hrd";
    /**
     * 设置x264专有参数
     * 例如："bframes=10:b-adapt=0"
     */
    String X264OPTS = "-x264opts";
}
