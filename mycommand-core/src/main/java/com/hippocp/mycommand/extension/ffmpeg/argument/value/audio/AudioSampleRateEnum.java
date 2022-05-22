package com.hippocp.mycommand.extension.ffmpeg.argument.value.audio;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 音频采样率
 *
 * @author ZhouYifan
 * @date 2021/12/31 11:07
 */
@Getter
@ToString
@AllArgsConstructor
public enum AudioSampleRateEnum {

    /**
     * 音频采样率 48000
     */
    ASR48000(FFmpegArgument.AUDIO_SAMPLE_RATE, "48000", "音频采样率 48000"),
    /**
     * 音频采样率 44100
     */
    ASR44100(FFmpegArgument.AUDIO_SAMPLE_RATE, "44100", "音频采样率 44100"),
    /**
     * 音频采样率 32000
     */
    ASR32000(FFmpegArgument.AUDIO_SAMPLE_RATE, "32000", "音频采样率 32000"),
    /**
     * 音频采样率 22050
     */
    ASR22050(FFmpegArgument.AUDIO_SAMPLE_RATE, "22050", "音频采样率 22050"),
    /**
     * 音频采样率 24000
     */
    ASR24000(FFmpegArgument.AUDIO_SAMPLE_RATE, "24000", "音频采样率 24000"),
    /**
     * 音频采样率 16000
     */
    ASR16000(FFmpegArgument.AUDIO_SAMPLE_RATE, "16000", "音频采样率 16000");

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
