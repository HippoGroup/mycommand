package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.H264NvencArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * H264Nvenc视频码率控制
 *
 * @author ZhouYifan
 * @date 2021/12/31 11:07
 */
@Getter
@ToString
@AllArgsConstructor
public enum H264NvencVideoBitrateModelEnum {
    /**
     * 恒定码率（cbr）
     */
    CONSTANT_RATE(H264NvencArgument.VIDEO_BITRATE_MODEL, "cbr", "恒定码率（cbr）"),
    /**
     * 可变码率（vbr）
     */
    VARIABLE_RATE(H264NvencArgument.VIDEO_BITRATE_MODEL, "vbr", "可变码率（vbr）");

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
