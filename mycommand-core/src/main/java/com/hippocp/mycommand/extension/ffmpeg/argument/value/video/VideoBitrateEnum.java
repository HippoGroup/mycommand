package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 视频码率
 *
 * @author ZhouYifan
 * @date 2021/12/31 11:07
 */
@Getter
@ToString
@AllArgsConstructor
public enum VideoBitrateEnum {
    /**
     * 2720kbps
     */
    VB2720(FFmpegArgument.VIDEO_AVG_BITRATE, "2720k", "2720kbps"),
    /**
     * 3100kbps
     */
    VB3100(FFmpegArgument.VIDEO_AVG_BITRATE, "3100k", "3100kbps"),
    /**
     * 3220kbps
     */
    VB3220(FFmpegArgument.VIDEO_AVG_BITRATE, "3220k", "3220kbps"),
    /**
     * 3420kbps
     */
    VB3420(FFmpegArgument.VIDEO_AVG_BITRATE, "3420k", "3420kbps"),
    /**
     * 4000kbps
     */
    VB4000(FFmpegArgument.VIDEO_AVG_BITRATE, "4000k", "4000kbps"),
    /**
     * 4500kbps
     */
    VB4500(FFmpegArgument.VIDEO_AVG_BITRATE, "4500k", "4500kbps"),
    /**
     * 6000kbps
     */
    VB6000(FFmpegArgument.VIDEO_AVG_BITRATE, "6000k", "6000kbps"),
    /**
     * 6500kbps
     */
    VB6500(FFmpegArgument.VIDEO_AVG_BITRATE, "6500k", "6500kbps"),
    /**
     * 7000kbps
     */
    VB7000(FFmpegArgument.VIDEO_AVG_BITRATE, "7000k", "7000kbps"),
    /**
     * 7800kbps
     */
    VB7800(FFmpegArgument.VIDEO_AVG_BITRATE, "7800k", "7800kbps"),
    /**
     * 8000kbps
     */
    VB8000(FFmpegArgument.VIDEO_AVG_BITRATE, "8000k", "8000kbps"),
    /**
     * 8500kbps
     */
    VB8500(FFmpegArgument.VIDEO_AVG_BITRATE, "8500k", "8500kbps"),
    /**
     * 9000kbps
     */
    VB9000(FFmpegArgument.VIDEO_AVG_BITRATE, "9000k", "9000kbps"),
    /**
     * 9500kbps
     */
    VB9500(FFmpegArgument.VIDEO_AVG_BITRATE, "9500k", "9500kbps"),
    /**
     * 12000kbps
     */
    VB12000(FFmpegArgument.VIDEO_AVG_BITRATE, "12000k", "12000kbps");

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
