package com.hippocp.mycommand.extension.ffmpeg.option.video;

import com.hippocp.mycommand.extension.command.AbstractCommandSegment;
import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.audio.AudioBitrateEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffmpeg Video Options
 *
 * @author ZhouYifan
 * @date 2022/1/22 11:09
 */
public class FFmpegVideoOptions extends AbstractCommandSegment<FFmpegVideoOptions> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegVideoOptions.class);

    /**
     * 设置视频过滤
     *
     * @param videoFilterEnum 视频过滤器枚举
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoFilter(VideoFilterEnum videoFilterEnum) {
        return videoFilter(videoFilterEnum.getVal());
    }

    /**
     * 设置视频过滤
     *
     * @param condition       查询条件
     * @param videoFilterEnum 视频过滤器枚举
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoFilter(boolean condition, VideoFilterEnum videoFilterEnum) {
        return videoFilter(condition, videoFilterEnum.getVal());
    }

    /**
     * 设置视频过滤
     *
     * @param val 值
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoFilter(String val) {
        return videoFilter(true, val);
    }

    /**
     * 设置视频过滤
     *
     * @param condition 查询条件
     * @param val       值
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoFilter(boolean condition, String val) {
        return addCommandSegment(condition, FFmpegArgument.VIDEO_FILTER, val);
    }

    /**
     * 设置视频视频平均码率
     *
     * @param videoBitrateEnum 视频码率枚举
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoVagBitrate(VideoBitrateEnum videoBitrateEnum) {
        return videoVagBitrate(videoBitrateEnum.getVal());
    }

    /**
     * 设置视频视频平均码率
     *
     * @param condition        执行条件
     * @param videoBitrateEnum 视频码率枚举
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoVagBitrate(boolean condition, VideoBitrateEnum videoBitrateEnum) {
        return videoVagBitrate(condition, videoBitrateEnum.getVal());
    }

    /**
     * 设置视频视频平均码率
     *
     * @param videoConstantBitrate 视频平均码率
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoVagBitrate(String videoConstantBitrate) {
        return videoVagBitrate(true, videoConstantBitrate);
    }

    /**
     * 设置视频视频平均码率
     *
     * @param condition            执行条件
     * @param videoConstantBitrate 视频平均码率
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoVagBitrate(boolean condition, String videoConstantBitrate) {
        return addCommandSegment(condition, FFmpegArgument.VIDEO_AVG_BITRATE, videoConstantBitrate);
    }

    /**
     * 设置视频帧率FPS
     *
     * @param videoFrameRateEnum 视频帧率FPS枚举
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoFrameRate(VideoFrameRateEnum videoFrameRateEnum) {
        return videoFrameRate(videoFrameRateEnum.getVal());
    }

    /**
     * 设置视频帧率FPS
     *
     * @param condition          执行条件
     * @param videoFrameRateEnum 视频帧率FPS枚举
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoFrameRate(boolean condition, VideoFrameRateEnum videoFrameRateEnum) {
        return videoFrameRate(condition, videoFrameRateEnum.getVal());
    }

    /**
     * 设置视频帧率FPS
     *
     * @param videoFrameRate 视频帧率FPS
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoFrameRate(String videoFrameRate) {
        return videoFrameRate(true, videoFrameRate);
    }

    /**
     * 设置视频帧率FPS
     *
     * @param condition      执行条件
     * @param videoFrameRate 视频帧率FPS
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoFrameRate(boolean condition, String videoFrameRate) {
        return addCommandSegment(condition, FFmpegArgument.FRAME_RATE, videoFrameRate);
    }

    /**
     * 设置分辨率，当需求为隔行扫描时不推荐使用，会导致视频拖影
     *
     * @param videoResolutionEnum 视频分辨率枚举
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions size(VideoResolutionEnum videoResolutionEnum) {
        return size(videoResolutionEnum.getVal());
    }

    /**
     * 设置分辨率，当需求为隔行扫描时不推荐使用，会导致视频拖影
     *
     * @param condition           执行条件
     * @param videoResolutionEnum 视频分辨率枚举
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions size(boolean condition, VideoResolutionEnum videoResolutionEnum) {
        return size(condition, videoResolutionEnum.getVal());
    }

    /**
     * 设置分辨率，当需求为隔行扫描时不推荐使用，会导致视频拖影
     *
     * @param size 例子 1920*1080<br>
     *             例子 1280x720
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions size(String size) {
        return size(true, size);
    }

    /**
     * 设置分辨率，当需求为隔行扫描时不推荐使用，会导致视频拖影
     *
     * @param condition 执行条件
     * @param size      例子 1920*1080<br>
     *                  例子 1280x720
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions size(boolean condition, String size) {
        return addCommandSegment(condition, FFmpegArgument.VIDEO_RESOLUTION, size);
    }

    /**
     * 设置视频宽高比
     *
     * @param videoAspectRatioEnum 视频宽高比枚举
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoAspectRatio(VideoAspectRatioEnum videoAspectRatioEnum) {
        return videoAspectRatio(videoAspectRatioEnum.getVal());
    }

    /**
     * 设置视频宽高比
     *
     * @param condition            执行条件
     * @param videoAspectRatioEnum 视频宽高比枚举
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoAspectRatio(boolean condition, VideoAspectRatioEnum videoAspectRatioEnum) {
        return videoAspectRatio(condition, videoAspectRatioEnum.getVal());
    }

    /**
     * 设置视频宽高比
     *
     * @param videoAspectRatio 视频宽高比
     * @return {@link FFmpegVideoOptions}
     */

    public FFmpegVideoOptions videoAspectRatio(String videoAspectRatio) {
        return videoAspectRatio(true, videoAspectRatio);
    }

    /**
     * 设置视频宽高比
     *
     * @param condition        执行条件
     * @param videoAspectRatio 视频宽高比
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions videoAspectRatio(boolean condition, String videoAspectRatio) {
        return addCommandSegment(condition, FFmpegArgument.VIDEO_ASPECT_RATIO, videoAspectRatio);
    }

    /**
     * 设置音频码率
     *
     * @param audioBitrateEnum 音频码率枚举
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions audioBitrate(AudioBitrateEnum audioBitrateEnum) {
        return audioBitrate(audioBitrateEnum.getVal());
    }

    /**
     * 设置音频码率
     *
     * @param condition        执行条件
     * @param audioBitrateEnum 音频码率枚举
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions audioBitrate(boolean condition, AudioBitrateEnum audioBitrateEnum) {
        return audioBitrate(condition, audioBitrateEnum.getVal());
    }

    /**
     * 设置音频码率
     *
     * @param audioBitrate 音频码率，单位k或m
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions audioBitrate(String audioBitrate) {
        return audioBitrate(true, audioBitrate);
    }

    /**
     * 设置音频码率
     *
     * @param condition    执行条件
     * @param audioBitrate 音频码率，单位k或m
     * @return {@link FFmpegVideoOptions}
     */
    public FFmpegVideoOptions audioBitrate(boolean condition, String audioBitrate) {
        return addCommandSegment(condition, FFmpegArgument.AUDIO_BITRATE, audioBitrate);
    }


}
