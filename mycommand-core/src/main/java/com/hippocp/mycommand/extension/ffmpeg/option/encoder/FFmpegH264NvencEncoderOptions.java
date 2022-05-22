package com.hippocp.mycommand.extension.ffmpeg.option.encoder;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import com.hippocp.mycommand.extension.ffmpeg.argument.constant.H264NvencArgument;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.H264NvencVideoBitrateModelEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.H264NvencVideoProfileEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * h264_nvenc 编码器选项
 *
 * @author ZhouYifan
 * @date 2022/1/22 11:09
 */
public class FFmpegH264NvencEncoderOptions extends FFmpegH264EncoderOptions<FFmpegH264NvencEncoderOptions> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegH264NvencEncoderOptions.class);

    /**
     * 设置视频码率模式
     *
     * @param videoBitrateEnum 视频码率枚举
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions videoBitrateModel(H264NvencVideoBitrateModelEnum videoBitrateEnum) {
        return videoBitrateModel(videoBitrateEnum.getVal());
    }

    /**
     * 设置视频码率模式
     *
     * @param condition        执行条件
     * @param videoBitrateEnum 视频码率枚举
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions videoBitrateModel(boolean condition, H264NvencVideoBitrateModelEnum videoBitrateEnum) {
        return videoBitrateModel(condition, videoBitrateEnum.getVal());
    }

    /**
     * 设置视频码率模式
     *
     * @param videoBitrateModel 视频码率模式
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions videoBitrateModel(String videoBitrateModel) {
        return videoBitrateModel(true, videoBitrateModel);
    }

    /**
     * 设置视频码率模式
     *
     * @param condition         执行条件
     * @param videoBitrateModel 视频码率模式
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions videoBitrateModel(boolean condition, String videoBitrateModel) {
        return this.addCommandSegment(condition, H264NvencArgument.VIDEO_BITRATE_MODEL, videoBitrateModel);
    }

    /**
     * 设置视频档次
     *
     * @param videoProfileEnum H264Nvenc视频档次枚举值
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions profile(H264NvencVideoProfileEnum videoProfileEnum) {
        return profile(true, videoProfileEnum.getVal());
    }

    /**
     * 设置视频档次
     *
     * @param condition        执行条件
     * @param videoProfileEnum H264Nvenc视频档次枚举值
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions profile(boolean condition, H264NvencVideoProfileEnum videoProfileEnum) {
        return profile(condition, videoProfileEnum.getVal());
    }

    /**
     * 设置视频档次
     *
     * @param videoProfile H264Nvenc视频档次值
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions profile(String videoProfile) {
        return profile(true, videoProfile);
    }

    /**
     * 设置视频档次
     *
     * @param condition    执行条件
     * @param videoProfile 值
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions profile(boolean condition, String videoProfile) {
        return this.addCommandSegment(condition, FFmpegArgument.PROFILE, videoProfile);
    }

    /**
     * 设置向前预测帧数
     *
     * @param intVal int值
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions lookahead(int intVal) {
        return this.lookahead(String.valueOf(intVal));
    }

    /**
     * 设置向前预测帧数
     *
     * @param condition 执行条件
     * @param intVal    int值
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions lookahead(boolean condition, int intVal) {
        return this.lookahead(condition, String.valueOf(intVal));
    }

    /**
     * 设置向前预测帧数
     *
     * @param intVal int值
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions lookahead(String intVal) {
        return this.lookahead(true, intVal);
    }

    /**
     * 设置向前预测帧数
     *
     * @param condition 执行条件
     * @param intVal    int值
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions lookahead(boolean condition, String intVal) {
        return this.addCommandSegment(condition, H264NvencArgument.LOOKAHEAD, intVal);
    }

    /**
     * lookahead启用时设置不强行插入关键帧（I帧）
     *
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions noSceneCut() {
        return this.noSceneCut(true, "true");
    }

    /**
     * lookahead启用时设置不强行插入关键帧（I帧）
     *
     * @param condition  执行条件
     * @param booleanVal 布尔值，字符串类型
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions noSceneCut(boolean condition, String booleanVal) {
        return this.addCommandSegment(condition, H264NvencArgument.NO_SCENE_CUT, booleanVal);
    }

    /**
     * 设置参考帧数量
     *
     * @param intVal int值
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions dpbSize(int intVal) {
        return this.dpbSize(String.valueOf(intVal));
    }

    /**
     * 设置参考帧数量
     *
     * @param condition 执行条件
     * @param intVal    int值
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions dpbSize(boolean condition, int intVal) {
        return this.dpbSize(condition, String.valueOf(intVal));
    }

    /**
     * 设置参考帧数量
     *
     * @param intVal int值，字符串类型
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions dpbSize(String intVal) {
        return this.dpbSize(true, intVal);
    }

    /**
     * 设置参考帧数量
     *
     * @param condition 执行条件
     * @param intVal    int值，字符串类型
     * @return {@link FFmpegH264NvencEncoderOptions}
     */
    public FFmpegH264NvencEncoderOptions dpbSize(boolean condition, String intVal) {
        return this.addCommandSegment(condition, H264NvencArgument.DPB_SIZE, intVal);
    }
}
