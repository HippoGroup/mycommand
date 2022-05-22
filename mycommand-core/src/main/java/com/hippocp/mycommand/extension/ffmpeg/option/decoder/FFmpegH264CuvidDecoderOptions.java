package com.hippocp.mycommand.extension.ffmpeg.option.decoder;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.H264CuvidArgument;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.H264CuvidDeinterlaceEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.VideoResolutionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * h264_cuvid解码器选项
 *
 * @author ZhouYifan
 * @date 2022/1/22 11:09
 */
public class FFmpegH264CuvidDecoderOptions extends FFmpegDecoderOptions<FFmpegH264CuvidDecoderOptions> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegH264CuvidDecoderOptions.class);

    /**
     * h264_cuvid解码器设置视频缩放
     *
     * @param videoResolutionEnum 视频分辨率枚举
     * @return {@link FFmpegH264CuvidDecoderOptions}
     */
    public FFmpegH264CuvidDecoderOptions resize(VideoResolutionEnum videoResolutionEnum) {
        return resize(videoResolutionEnum.getVal());
    }

    /**
     * h264_cuvid解码器设置视频缩放
     *
     * @param condition           执行条件
     * @param videoResolutionEnum 视频分辨率枚举
     * @return {@link FFmpegH264CuvidDecoderOptions}
     */
    public FFmpegH264CuvidDecoderOptions resize(boolean condition, VideoResolutionEnum videoResolutionEnum) {
        return resize(condition, videoResolutionEnum.getVal());
    }

    /**
     * h264_cuvid解码器设置视频缩放
     *
     * @return {@link FFmpegH264CuvidDecoderOptions}
     */
    public FFmpegH264CuvidDecoderOptions resize(String size) {
        return resize(true, size);
    }

    /**
     * h264_cuvid解码器设置视频缩放
     *
     * @param condition 执行条件
     * @param size      视频分辨率枚举
     * @return {@link FFmpegH264CuvidDecoderOptions}
     */
    public FFmpegH264CuvidDecoderOptions resize(boolean condition, String size) {
        return addCommandSegment(condition, H264CuvidArgument.RESIZE, size);
    }

    /**
     * h264_cuvid解码器设置放弃第二场
     *
     * @return {@link FFmpegH264CuvidDecoderOptions}
     */
    public FFmpegH264CuvidDecoderOptions dropSecondField(boolean val) {
        return dropSecondField(true, val);
    }

    /**
     * h264_cuvid解码器设置放弃第二场
     *
     * @param condition 执行条件
     * @return {@link FFmpegH264CuvidDecoderOptions}
     */
    public FFmpegH264CuvidDecoderOptions dropSecondField(boolean condition, boolean val) {
        return addCommandSegment(condition, H264CuvidArgument.DROP_SECOND_FIELD, String.valueOf(val));
    }

    /**
     * h264_cuvid解码器选择工作 GPU
     *
     * @param index GPU索引
     * @return {@link FFmpegH264CuvidDecoderOptions}
     */
    public FFmpegH264CuvidDecoderOptions chooseGPU(String index) {
        return chooseGPU(true, index);
    }

    /**
     * h264_cuvid解码器选择工作 GPU
     *
     * @param condition 执行条件
     * @param index     GPU索引
     * @return {@link FFmpegH264CuvidDecoderOptions}
     */
    public FFmpegH264CuvidDecoderOptions chooseGPU(boolean condition, String index) {
        return addCommandSegment(condition, H264CuvidArgument.GPU, index);
    }

    /**
     * 设置h264_cuvid解码器反交错模式
     *
     * @param h264CuvidDeinterlaceEnum H264Cuvid反交错模式参数值枚举
     * @return {@link FFmpegH264CuvidDecoderOptions}
     */
    public FFmpegH264CuvidDecoderOptions deinterlace(H264CuvidDeinterlaceEnum h264CuvidDeinterlaceEnum) {
        return deinterlace(h264CuvidDeinterlaceEnum.getVal());
    }

    /**
     * 设置h264_cuvid解码器反交错模式
     *
     * @param condition                执行条件
     * @param h264CuvidDeinterlaceEnum H264Cuvid反交错模式参数值枚举
     * @return {@link FFmpegH264CuvidDecoderOptions}
     */
    public FFmpegH264CuvidDecoderOptions deinterlace(boolean condition, H264CuvidDeinterlaceEnum h264CuvidDeinterlaceEnum) {
        return deinterlace(condition, h264CuvidDeinterlaceEnum.getVal());
    }

    /**
     * 设置h264_cuvid解码器反交错模式
     *
     * @param mode 反交错模式
     * @return {@link FFmpegH264CuvidDecoderOptions}
     */
    public FFmpegH264CuvidDecoderOptions deinterlace(String mode) {
        return deinterlace(true, mode);
    }

    /**
     * 设置h264_cuvid解码器反交错模式
     *
     * @param condition 执行条件
     * @param mode      反交错模式
     * @return {@link FFmpegH264CuvidDecoderOptions}
     */
    public FFmpegH264CuvidDecoderOptions deinterlace(boolean condition, String mode) {
        return addCommandSegment(condition, H264CuvidArgument.DEINTERLACE, mode);
    }

}
