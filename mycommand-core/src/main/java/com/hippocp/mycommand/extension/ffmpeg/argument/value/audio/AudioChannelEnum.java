package com.hippocp.mycommand.extension.ffmpeg.argument.value.audio;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 音频声道
 *
 * @author ZhouYifan
 * @date 2021/12/31 11:07
 */
@Getter
@ToString
@AllArgsConstructor
public enum AudioChannelEnum {

    /**
     * 双声道 立体声
     */
    STEREO_CHANNEL(FFmpegArgument.AUDIO_CHANNEL, "2", "双声道 立体声"),
    /**
     * 5.1 声道
     */
    FIVE_CHANNEL(FFmpegArgument.AUDIO_CHANNEL, "5", "5.1 声道"),
    /**
     * 7.1 声道
     */
    SEVEN_CHANNEL(FFmpegArgument.AUDIO_CHANNEL, "7", "7.1 声道");

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
