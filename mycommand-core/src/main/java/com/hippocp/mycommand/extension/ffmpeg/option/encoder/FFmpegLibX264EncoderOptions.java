package com.hippocp.mycommand.extension.ffmpeg.option.encoder;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import com.hippocp.mycommand.extension.ffmpeg.argument.constant.LibX264Argument;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.LibX264VideoBPyramidEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.LibX264VideoBitrateModelEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.LibX264VideoProfileEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.X264OptsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * libx264 编码器选项
 *
 * @author ZhouYifan
 * @date 2022/1/22 11:09
 */
public class FFmpegLibX264EncoderOptions extends FFmpegH264EncoderOptions<FFmpegLibX264EncoderOptions> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegLibX264EncoderOptions.class);

    public interface Argument {
        String B_PYRAMID = "-b-pyramid";
        String RC_LOOKAHEAD = "-rc-lookahead";
    }

    /**
     * 设置场景切换不强行插入关键帧
     */
    private static final String SC_THRESHOLD = "-sc_threshold";

    /**
     * 设置视频码率模式
     *
     * @param videoBitrateEnum 视频码率枚举
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions videoBitrateModel(LibX264VideoBitrateModelEnum videoBitrateEnum) {
        return videoBitrateModel(videoBitrateEnum.getVal());
    }

    /**
     * 设置视频码率模式
     *
     * @param condition        执行条件
     * @param videoBitrateEnum 视频码率枚举
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions videoBitrateModel(boolean condition, LibX264VideoBitrateModelEnum videoBitrateEnum) {
        return videoBitrateModel(condition, videoBitrateEnum.getVal());
    }

    /**
     * 设置H.264的编码HRD信号形式
     *
     * @param val 值 例如：vbr cbr
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions videoBitrateModel(String val) {
        return videoBitrateModel(true, val);
    }

    /**
     * 设置H.264的编码HRD信号形式
     *
     * @param condition 执行条件
     * @param val       值 例如：vbr cbr
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions videoBitrateModel(boolean condition, String val) {
        return this.addCommandSegment(condition, LibX264Argument.VIDEO_BITRATE_MODEL, val);
    }

    /**
     * 设置x264专有参数
     *
     * @param x264OptsEnum 枚举值 例如："bframes=10:b-adapt=0"
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions x264opts(X264OptsEnum x264OptsEnum) {
        return x264opts(x264OptsEnum.getVal());
    }

    /**
     * 设置x264专有参数
     *
     * @param condition    执行条件
     * @param x264OptsEnum 枚举值 例如："bframes=10:b-adapt=0"
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions x264opts(boolean condition, X264OptsEnum x264OptsEnum) {
        return x264opts(condition, x264OptsEnum.getVal());
    }

    /**
     * 设置x264专有参数
     *
     * @param val 值 例如："bframes=10:b-adapt=0"
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions x264opts(String val) {
        return x264opts(true, val);
    }


    /**
     * 设置x264专有参数
     *
     * @param condition 执行条件
     * @param val       值 例如："bframes=10:b-adapt=0"
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions x264opts(boolean condition, String val) {
        return this.addCommandSegment(condition, LibX264Argument.X264OPTS, val);
    }

    /**
     * 设置场景切换强行插入关键帧的个数，0表示不插入
     *
     * @param intVal int值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions scThreshold(int intVal) {
        return scThreshold(String.valueOf(intVal));
    }

    /**
     * 设置场景切换强行插入关键帧的个数，0表示不插入
     *
     * @param intVal int值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions scThreshold(boolean condition, int intVal) {
        return scThreshold(condition, String.valueOf(intVal));
    }

    /**
     * 设置场景切换强行插入关键帧的个数，0表示不插入
     *
     * @param val 值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions scThreshold(String val) {
        return scThreshold(true, val);
    }


    /**
     * 设置场景切换强行插入关键帧的个数，0表示不插入
     *
     * @param condition 执行条件
     * @param val       值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions scThreshold(boolean condition, String val) {
        return this.addCommandSegment(condition, SC_THRESHOLD, val);
    }

    /**
     * 设置视频档次
     *
     * @param videoProfileEnum H264Nvenc视频档次枚举值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions profile(LibX264VideoProfileEnum videoProfileEnum) {
        return profile(true, videoProfileEnum.getVal());
    }

    /**
     * 设置视频档次
     *
     * @param condition        执行条件
     * @param videoProfileEnum H264Nvenc视频档次枚举值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions profile(boolean condition, LibX264VideoProfileEnum videoProfileEnum) {
        return profile(condition, videoProfileEnum.getVal());
    }

    /**
     * 设置视频档次
     *
     * @param videoProfile H264Nvenc视频档次值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions profile(String videoProfile) {
        return profile(true, videoProfile);
    }

    /**
     * 设置视频档次
     *
     * @param condition    执行条件
     * @param videoProfile 值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions profile(boolean condition, String videoProfile) {
        return this.addCommandSegment(condition, FFmpegArgument.PROFILE, videoProfile);
    }

    /**
     * b帧金字塔，确保参考帧数量不偏移
     *
     * @param bPyramidEnum 枚举值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions bPyramid(LibX264VideoBPyramidEnum bPyramidEnum) {
        return this.bPyramid(true, bPyramidEnum.getVal());
    }

    /**
     * b帧金字塔，确保参考帧数量不偏移
     *
     * @param condition    执行条件
     * @param bPyramidEnum 枚举值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions bPyramid(boolean condition, LibX264VideoBPyramidEnum bPyramidEnum) {
        return this.bPyramid(condition, bPyramidEnum.getVal());
    }

    /**
     * b帧金字塔，确保参考帧数量不偏移
     *
     * @param val 值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions bPyramid(String val) {
        return this.bPyramid(true, val);
    }

    /**
     * b帧金字塔，确保参考帧数量不偏移
     *
     * @param condition 执行条件
     * @param val       值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions bPyramid(boolean condition, String val) {
        return this.addCommandSegment(condition, Argument.B_PYRAMID, val);
    }

    /**
     * 向前预测
     *
     * @param intVal int值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions lookahead(int intVal) {
        return this.lookahead(true, String.valueOf(intVal));
    }

    /**
     * 向前预测
     *
     * @param condition 执行条件
     * @param intVal    int值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions lookahead(boolean condition, int intVal) {
        return this.lookahead(condition, String.valueOf(intVal));
    }

    /**
     * 向前预测
     *
     * @param val int值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions lookahead(String val) {
        return this.lookahead(true, val);
    }

    /**
     * 向前预测
     *
     * @param condition 执行条件
     * @param val       int值
     * @return {@link FFmpegLibX264EncoderOptions}
     */
    public FFmpegLibX264EncoderOptions lookahead(boolean condition, String val) {
        return this.addCommandSegment(condition, Argument.RC_LOOKAHEAD, val);
    }

}
