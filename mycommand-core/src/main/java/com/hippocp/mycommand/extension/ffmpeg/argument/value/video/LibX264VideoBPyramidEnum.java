package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.option.encoder.FFmpegLibX264EncoderOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * LibX264视频BPyramid
 *
 * @author ZhouYifan
 * @date 2022/3/4
 */
@Getter
@ToString
@AllArgsConstructor
public enum LibX264VideoBPyramidEnum {
    /**
     * strict
     */
    STRICT(FFmpegLibX264EncoderOptions.Argument.B_PYRAMID, "strict", "strict");

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
