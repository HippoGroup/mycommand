package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * H264Nvenc视频档次
 *
 * @author ZhouYifan
 * @date 2021/12/31 11:07
 */
@Getter
@ToString
@AllArgsConstructor
public enum H264NvencVideoProfileEnum {
    /**
     * baseline
     */
    BASELINE(FFmpegArgument.PROFILE, "baseline", "profile baseline"),
    /**
     * main
     */
    MAIN(FFmpegArgument.PROFILE, "main", "profile main"),
    /**
     * high
     */
    HIGH(FFmpegArgument.PROFILE, "high", "profile high"),
    /**
     * high444p
     */
    HIGH444P(FFmpegArgument.PROFILE, "high444p", "profile high444p");

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
