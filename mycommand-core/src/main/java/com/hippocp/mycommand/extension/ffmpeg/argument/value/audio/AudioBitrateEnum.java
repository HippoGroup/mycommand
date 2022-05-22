package com.hippocp.mycommand.extension.ffmpeg.argument.value.audio;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 音频码率
 *
 * @author ZhouYifan
 * @date 2021/12/31 11:07
 */
@Getter
@ToString
@AllArgsConstructor
public enum AudioBitrateEnum {
    /**
     * 64kbps
     */
    AB64(FFmpegArgument.AUDIO_BITRATE, "64k", "64kbps"),
    /**
     * 96kbps
     */
    AB96(FFmpegArgument.AUDIO_BITRATE, "96k", "96kbps"),
    /**
     * 128kbps
     */
    AB128(FFmpegArgument.AUDIO_BITRATE, "128k", "128kbps"),
    /**
     * 192kbps
     */
    AB192(FFmpegArgument.AUDIO_BITRATE, "192k", "192kbps"),
    /**
     * 224kbps
     */
    AB224(FFmpegArgument.AUDIO_BITRATE, "224k", "224kbps"),
    /**
     * 256kbps
     */
    AB256(FFmpegArgument.AUDIO_BITRATE, "256k", "256kbps");

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
