package com.hippocp.mycommand.extension.ffmpeg.option.video;

import com.hippocp.mycommand.extension.command.AbstractCommandSegment;
import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.DecoderHardwareEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.DecoderHardwareOutputFormatEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffmpeg AdvancedVideo Options
 *
 * @author ZhouYifan
 * @date 2022/1/22 11:09
 */
public class FFmpegAdvancedVideoOptions extends AbstractCommandSegment<FFmpegAdvancedVideoOptions> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegAdvancedVideoOptions.class);

    /**
     * 选择硬件加速设备
     */
    private static final String HWACCEL_DEVICE = "-hwaccel_device";

    /**
     * 设置像素格式
     */
    private static final String PIX_FORMAT = "-pix_fmt";

    /**
     * 将解码帧保存在 GPU 内存中
     *
     * @param outputFormatEnum 枚举值值 例如：cuda
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions decoderHardwareOutputFormat(DecoderHardwareOutputFormatEnum outputFormatEnum) {
        return decoderHardwareOutputFormat(outputFormatEnum.getVal());
    }

    /**
     * 将解码帧保存在 GPU 内存中
     *
     * @param condition        执行条件
     * @param outputFormatEnum 枚举值值 例如：cuda
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions decoderHardwareOutputFormat(boolean condition, DecoderHardwareOutputFormatEnum outputFormatEnum) {
        return decoderHardwareOutputFormat(condition, outputFormatEnum.getVal());
    }

    /**
     * 将解码帧保存在 GPU 内存中
     *
     * @param val 值 例如：cuda
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions decoderHardwareOutputFormat(String val) {
        return decoderHardwareOutputFormat(true, val);
    }

    /**
     * 将解码帧保存在 GPU 内存中
     *
     * @param condition 执行条件
     * @param val       值 例如：cuda
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions decoderHardwareOutputFormat(boolean condition, String val) {
        return addCommandSegment(condition, FFmpegArgument.DECODER_HARDWARE_OUTPUT_FORMAT, val);
    }

    /**
     * 选择合适的硬件解码加速器
     *
     * @param decoderHardwareEnum 枚举值
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions decoderHardware(DecoderHardwareEnum decoderHardwareEnum) {
        return decoderHardware(decoderHardwareEnum.getVal());
    }

    /**
     * 选择合适的硬件解码加速器
     *
     * @param condition           执行条件
     * @param decoderHardwareEnum 枚举值
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions decoderHardware(boolean condition, DecoderHardwareEnum decoderHardwareEnum) {
        return decoderHardware(condition, decoderHardwareEnum.getVal());
    }

    /**
     * 选择合适的硬件解码加速器
     *
     * @param val 值
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions decoderHardware(String val) {
        return decoderHardware(true, val);
    }

    /**
     * 选择合适的硬件解码加速器
     *
     * @param condition 执行条件
     * @param val       值
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions decoderHardware(boolean condition, String val) {
        return addCommandSegment(condition, FFmpegArgument.DECODER_HARDWARE, val);
    }

    /**
     * 选择硬件加速设备
     *
     * @param intVal int值
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions hwaccelDevice(int intVal) {
        return hwaccelDevice(String.valueOf(intVal));
    }

    /**
     * 选择硬件加速设备
     *
     * @param condition 执行条件
     * @param intVal    int值
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions hwaccelDevice(boolean condition, int intVal) {
        return hwaccelDevice(condition, String.valueOf(intVal));
    }

    /**
     * 选择硬件加速设备
     *
     * @param val 值
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions hwaccelDevice(String val) {
        return hwaccelDevice(true, val);
    }

    /**
     * 选择硬件加速设备
     *
     * @param condition 执行条件
     * @param val       值
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions hwaccelDevice(boolean condition, String val) {
        return addCommandSegment(condition, HWACCEL_DEVICE, val);
    }

    /**
     * 设置像素格式
     *
     * @param val 值
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions pixelFormat(String val) {
        return pixelFormat(true, val);
    }

    /**
     * 设置像素格式
     *
     * @param condition 执行条件
     * @param val       值
     * @return {@link FFmpegAdvancedVideoOptions}
     */
    public FFmpegAdvancedVideoOptions pixelFormat(boolean condition, String val) {
        return addCommandSegment(condition, PIX_FORMAT, val);
    }

}
