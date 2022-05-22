package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 视频分辨率
 *
 * @author ZhouYifan
 * @date 2021/12/31 11:07
 */
@Getter
@ToString
@AllArgsConstructor
public enum VideoResolutionEnum {
    /**
     * 720p 1280x720
     */
    HD720(FFmpegArgument.VIDEO_RESOLUTION, "1280x720", "16:9 720p 1280x720"),
    /**
     * 1080p 1920x1080
     */
    HD1080(FFmpegArgument.VIDEO_RESOLUTION, "1920x1080", "16:9 1080p 1920x1080"),
    /**
     * 2k 2560x1440
     */
    VR2K(FFmpegArgument.VIDEO_RESOLUTION, "2560x1440", "16:9 2K 2560x1440"),
    /**
     * 4k 3840x2160
     */
    UHD2160(FFmpegArgument.VIDEO_RESOLUTION, "3840x2160", "16:9 4K 3840x2160"),
    /**
     * 4k 4096x2160
     */
    VR4K(FFmpegArgument.VIDEO_RESOLUTION, "4096x2160", "16:9 4K 4096x2160"),
    /**
     * 8k 7680x4320
     */
    UHD4320(FFmpegArgument.VIDEO_RESOLUTION, "7680x4320", "16:9 8K 7680x4320");

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
