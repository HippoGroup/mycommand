package com.hippocp.mycommand.extension.ffmpeg.option.audio;

import com.hippocp.mycommand.extension.command.AbstractCommandSegment;
import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.audio.AudioChannelEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.audio.AudioSampleRateEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffmpeg Audio Options
 *
 * @author ZhouYifan
 * @date 2022/1/22 11:09
 */
public class FFmpegAudioOptions extends AbstractCommandSegment<FFmpegAudioOptions> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegAudioOptions.class);

    /**
     * 设置音频采样率
     *
     * @param audioSampleRateEnum 音频采样率枚举
     * @return {@link FFmpegAudioOptions}
     */
    public FFmpegAudioOptions audioSampleRate(AudioSampleRateEnum audioSampleRateEnum) {
        return audioSampleRate(audioSampleRateEnum.getVal());
    }

    /**
     * 设置音频采样率
     *
     * @param condition           执行条件
     * @param audioSampleRateEnum 音频采样率枚举
     * @return {@link FFmpegAudioOptions}
     */
    public FFmpegAudioOptions audioSampleRate(boolean condition, AudioSampleRateEnum audioSampleRateEnum) {
        return audioSampleRate(condition, audioSampleRateEnum.getVal());
    }

    /**
     * 设置音频采样率
     *
     * @param audioSampleRate 音频采样率
     * @return {@link FFmpegAudioOptions}
     */
    public FFmpegAudioOptions audioSampleRate(String audioSampleRate) {
        return audioSampleRate(true, audioSampleRate);
    }

    /**
     * 设置音频采样率
     *
     * @param condition       执行条件
     * @param audioSampleRate 音频采样率
     * @return {@link FFmpegAudioOptions}
     */
    public FFmpegAudioOptions audioSampleRate(boolean condition, String audioSampleRate) {
        return addCommandSegment(condition, FFmpegArgument.AUDIO_SAMPLE_RATE, audioSampleRate);
    }

    /**
     * 设置音频声道
     *
     * @param audioChannelEnum 音频声道枚举
     * @return {@link FFmpegAudioOptions}
     */
    public FFmpegAudioOptions audioChannel(AudioChannelEnum audioChannelEnum) {
        return audioChannel(audioChannelEnum.getVal());
    }

    /**
     * 设置音频声道
     *
     * @param condition        执行条件
     * @param audioChannelEnum 音频声道枚举
     * @return {@link FFmpegAudioOptions}
     */
    public FFmpegAudioOptions audioChannel(boolean condition, AudioChannelEnum audioChannelEnum) {
        return audioChannel(condition, audioChannelEnum.getVal());
    }

    /**
     * 设置音频声道
     *
     * @param audioChannel 音频声道 例子：2
     * @return {@link FFmpegAudioOptions}
     */
    public FFmpegAudioOptions audioChannel(String audioChannel) {
        return audioChannel(true, audioChannel);
    }

    /**
     * 设置音频声道
     *
     * @param condition    执行条件
     * @param audioChannel 音频声道 例子：2
     * @return {@link FFmpegAudioOptions}
     */
    public FFmpegAudioOptions audioChannel(boolean condition, String audioChannel) {
        return addCommandSegment(condition, FFmpegArgument.AUDIO_CHANNEL, audioChannel);
    }

}
