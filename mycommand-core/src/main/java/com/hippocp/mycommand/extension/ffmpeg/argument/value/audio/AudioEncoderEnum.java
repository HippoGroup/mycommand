package com.hippocp.mycommand.extension.ffmpeg.argument.value.audio;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * ffmpeg 音频编码器
 *
 * @author ZhouYifan
 * @date 2022/1/2 1:13
 */
@Getter
@ToString
@AllArgsConstructor
public enum AudioEncoderEnum {

    /**
     * Encoder mp2 [MP2 (MPEG audio layer 2)]:<br>
     * General capabilities: none<br>
     * Threading capabilities: none<br>
     * Supported sample rates: 44100 48000 32000 22050 24000 16000<br>
     * Supported sample formats: s16<br>
     * Supported channel layouts: mono stereo<br>
     */
    MP2(FFmpegArgument.AUDIO_ENCODER, "mp2", "mp2音频编码器"),
    /**
     * Encoder aac [AAC (Advanced Audio Coding)]:<br>
     * General capabilities: delay small<br>
     * Threading capabilities: none<br>
     * Supported sample rates: 96000 88200 64000 48000 44100 32000 24000 22050 16000 12000 11025 8000 7350<br>
     * Supported sample formats: fltp<br>
     */
    AAC(FFmpegArgument.AUDIO_ENCODER, "aac", "aac音频编码器"),
    /**
     * Encoder libmp3lame [libmp3lame MP3 (MPEG audio layer 3)]:<br>
     * General capabilities: delay small<br>
     * Threading capabilities: none<br>
     * Supported sample rates: 44100 48000 32000 22050 24000 16000 11025 12000 8000<br>
     * Supported sample formats: s32p fltp s16p<br>
     * Supported channel layouts: mono stereo<br>
     */
    LIBMP3LAME(FFmpegArgument.AUDIO_ENCODER, "libmp3lame", "libmp3lame一种mp3音频编码器");

    private String arg;

    private String val;

    private String description;


}
