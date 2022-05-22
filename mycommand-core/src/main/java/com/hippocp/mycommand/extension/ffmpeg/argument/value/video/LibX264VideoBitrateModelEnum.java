package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.LibX264Argument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * LibX264视频码率控制
 *
 * @author ZhouYifan
 * @date 2021/12/31 11:07
 */
@Getter
@ToString
@AllArgsConstructor
public enum LibX264VideoBitrateModelEnum {
    /**
     * 恒定码率（cbr）
     */
    CONSTANT_RATE(LibX264Argument.VIDEO_BITRATE_MODEL, "cbr", "恒定码率（cbr）"),
    /**
     * 可变码率（vbr）
     */
    VARIABLE_RATE(LibX264Argument.VIDEO_BITRATE_MODEL, "vbr", "可变码率（vbr）");

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
