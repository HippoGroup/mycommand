package com.hippocp.mycommand.extension.ffmpeg.option.pre.file;

import com.hippocp.mycommand.extension.command.AbstractCommandSegment;
import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.audio.AudioEncoderEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.VideoDecoderEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.VideoEncoderEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffmpeg Per-File MainOptions
 *
 * @author ZhouYifan
 * @date 2022/1/22 11:09
 */
public class FFmpegPerFileMainOptions extends AbstractCommandSegment<FFmpegPerFileMainOptions> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegPerFileMainOptions.class);

    /**
     * 指定视频编码器
     *
     * @param videoEncoder 视频编码器
     * @return {@link FFmpegPerFileMainOptions} 子类
     */
    public FFmpegPerFileMainOptions videoEncoder(VideoEncoderEnum videoEncoder) {
        return videoEncoder(videoEncoder.getVal());
    }

    /**
     * 指定视频编码器
     *
     * @param condition    执行条件
     * @param videoEncoder 视频编码器
     * @return {@link FFmpegPerFileMainOptions} 子类
     */
    public FFmpegPerFileMainOptions videoEncoder(boolean condition, VideoEncoderEnum videoEncoder) {
        return videoEncoder(condition, videoEncoder.getVal());
    }

    /**
     * 指定视频编码器
     *
     * @param videoEncoder 视频编码器
     * @return {@link FFmpegPerFileMainOptions} 子类
     */
    public FFmpegPerFileMainOptions videoEncoder(String videoEncoder) {
        return videoEncoder(true, videoEncoder);
    }

    /**
     * 指定视频编码器
     *
     * @param condition    执行条件
     * @param videoEncoder 视频编码器
     * @return {@link FFmpegPerFileMainOptions} 子类
     */
    public FFmpegPerFileMainOptions videoEncoder(boolean condition, String videoEncoder) {
        return addCommandSegment(condition, FFmpegArgument.VIDEO_ENCODER, videoEncoder);
    }

    /**
     * 强制输入或输出文件格式。通常会自动检测输入文件的格式，并根据输出文件的文件扩展名猜测格式，因此在大多数情况下不需要此选项。
     *
     * @param format 输出格式
     * @return {@link FFmpegPerFileMainOptions}
     */
    public FFmpegPerFileMainOptions format(String format) {
        return format(true, format);
    }

    /**
     * 强制输入或输出文件格式。通常会自动检测输入文件的格式，并根据输出文件的文件扩展名猜测格式，因此在大多数情况下不需要此选项。
     *
     * @param format 输出格式
     * @return {@link FFmpegPerFileMainOptions}
     */
    public FFmpegPerFileMainOptions format(boolean condition, String format) {
        return addCommandSegment(condition, FFmpegArgument.FORCE_OUTPUT_FORMAT, format);
    }

    /**
     * 指定视频编码器
     *
     * @param videoDecoder 视频编码器
     * @return {@link FFmpegPerFileMainOptions} 子类
     */
    public FFmpegPerFileMainOptions videoDecoder(VideoDecoderEnum videoDecoder) {
        return videoDecoder(videoDecoder.getVal());
    }

    /**
     * 指定视频编码器
     *
     * @param condition    执行条件
     * @param videoDecoder 视频编码器
     * @return {@link FFmpegPerFileMainOptions} 子类
     */
    public FFmpegPerFileMainOptions videoDecoder(boolean condition, VideoDecoderEnum videoDecoder) {
        return videoDecoder(condition, videoDecoder.getVal());
    }

    /**
     * 指定视频编码器
     *
     * @param videoDecoder 视频编码器
     * @return {@link FFmpegPerFileMainOptions} 子类
     */
    public FFmpegPerFileMainOptions videoDecoder(String videoDecoder) {
        return videoDecoder(true, videoDecoder);
    }

    /**
     * 指定视频编码器
     *
     * @param condition    执行条件
     * @param videoDecoder 视频编码器
     * @return {@link FFmpegPerFileMainOptions} 子类
     */
    public FFmpegPerFileMainOptions videoDecoder(boolean condition, String videoDecoder) {
        return addCommandSegment(condition, FFmpegArgument.VIDEO_DECODER, videoDecoder);
    }

    /**
     * 设置录制或转码音频/视频的“持续时间”单位秒，即限制从输入文件读取数据的持续时间。
     *
     * @param duration 形如 0.001 表示0.001秒，hh:mm:ss[.xxx]格式的记录时间也支持
     * @return {@link FFmpegPerFileMainOptions}
     */
    public FFmpegPerFileMainOptions duration(String duration) {
        return duration(true, duration);
    }

    /**
     * 设置录制或转码音频/视频的“持续时间”单位秒，即限制从输入文件读取数据的持续时间。
     *
     * @param condition 执行条件
     * @param duration  形如 0.001 表示0.001秒，hh:mm:ss[.xxx]格式的记录时间也支持
     * @return {@link FFmpegPerFileMainOptions}
     */
    public FFmpegPerFileMainOptions duration(boolean condition, String duration) {
        return addCommandSegment(condition, FFmpegArgument.VIDEO_DURATION, duration);
    }

    /**
     * 指定音频编码器
     *
     * @param audioEncoderEnum 音频编码器枚举
     * @return {@link FFmpegPerFileMainOptions}
     */
    public FFmpegPerFileMainOptions audioEncoder(AudioEncoderEnum audioEncoderEnum) {
        return audioEncoder(audioEncoderEnum.getVal());
    }

    /**
     * 指定音频编码器
     *
     * @param condition        执行条件
     * @param audioEncoderEnum 音频编码器枚举
     * @return {@link FFmpegPerFileMainOptions}
     */
    public FFmpegPerFileMainOptions audioEncoder(boolean condition, AudioEncoderEnum audioEncoderEnum) {
        return audioEncoder(condition, audioEncoderEnum.getVal());
    }

    /**
     * 指定音频编码器
     *
     * @param audioEncoder 音频编码器
     * @return {@link FFmpegPerFileMainOptions}
     */
    public FFmpegPerFileMainOptions audioEncoder(String audioEncoder) {
        return audioEncoder(true, audioEncoder);
    }

    /**
     * 指定音频编码器
     *
     * @param condition    执行条件
     * @param audioEncoder 音频编码器
     * @return {@link FFmpegPerFileMainOptions}
     */
    public FFmpegPerFileMainOptions audioEncoder(boolean condition, String audioEncoder) {
        return addCommandSegment(condition, FFmpegArgument.AUDIO_ENCODER, audioEncoder);
    }

    /**
     * 设置开始时间偏移量
     *
     * @param position 形如 17 表示17秒，[-]hh:mm:ss[.xxx]的格式也支持
     * @return {@link FFmpegPerFileMainOptions}
     */
    public FFmpegPerFileMainOptions position(String position) {
        return position(true, position);
    }

    /**
     * 设置开始时间偏移量
     *
     * @param condition 执行条件
     * @param position  形如 17 表示17秒，[-]hh:mm:ss[.xxx]的格式也支持
     * @return {@link FFmpegPerFileMainOptions}
     */
    public FFmpegPerFileMainOptions position(boolean condition, String position) {
        return addCommandSegment(condition, FFmpegArgument.POSITION, position);
    }


}
