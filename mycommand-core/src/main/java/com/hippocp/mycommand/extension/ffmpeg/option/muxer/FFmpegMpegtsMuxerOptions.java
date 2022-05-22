package com.hippocp.mycommand.extension.ffmpeg.option.muxer;

import com.hippocp.mycommand.extension.command.AbstractCommandSegment;
import com.hippocp.mycommand.extension.ffmpeg.argument.constant.MpegtsArgument;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.VideoBitrateEnum;

/**
 * ffmpeg Mpegts Muxer Options
 *
 * @author ZhouYifan
 * @date 2022/1/28 9:54
 */
public class FFmpegMpegtsMuxerOptions extends AbstractCommandSegment<FFmpegMpegtsMuxerOptions> {

    /**
     * 设置一个恒定的复用率，用于稳定码率
     *
     * @param videoBitrateEnum 视频码率枚举
     * @return {@link FFmpegMpegtsMuxerOptions}
     */
    public FFmpegMpegtsMuxerOptions muxRate(VideoBitrateEnum videoBitrateEnum) {
        return muxRate(videoBitrateEnum.getVal());
    }

    /**
     * 设置一个恒定的复用率，用于稳定码率
     *
     * @param condition        执行条件
     * @param videoBitrateEnum 视频码率枚举
     * @return {@link FFmpegMpegtsMuxerOptions}
     */
    public FFmpegMpegtsMuxerOptions muxRate(boolean condition, VideoBitrateEnum videoBitrateEnum) {
        return muxRate(condition, videoBitrateEnum.getVal());
    }

    /**
     * 设置一个恒定的复用率，用于稳定码率
     *
     * @param val 视频码率
     * @return {@link FFmpegMpegtsMuxerOptions}
     */
    public FFmpegMpegtsMuxerOptions muxRate(String val) {
        return muxRate(true, val);
    }

    /**
     * 设置一个恒定的复用率，用于稳定码率
     *
     * @param condition 执行条件
     * @param val       视频码率
     * @return {@link FFmpegMpegtsMuxerOptions}
     */
    public FFmpegMpegtsMuxerOptions muxRate(boolean condition, String val) {
        return addCommandSegment(condition, MpegtsArgument.MUX_RATE, val);
    }

}
