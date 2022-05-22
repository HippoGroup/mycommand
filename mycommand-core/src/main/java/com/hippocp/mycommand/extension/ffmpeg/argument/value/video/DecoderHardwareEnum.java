package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;


import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 选择合适的硬件解码加速器
 *
 * @author ZhouYifan
 * @date 2022/1/22 9:55
 */
@Getter
@ToString
@AllArgsConstructor
public enum DecoderHardwareEnum {
    /**
     * 选择硬件加速解码器 cuda
     */
    CUDA_DECODER_HARDWARE(FFmpegArgument.DECODER_HARDWARE, "cuda", "选择硬件加速解码器 cuda"),
    /**
     * 自动选择硬件加速解码器
     */
    AUTO_DECODER_HARDWARE(FFmpegArgument.DECODER_HARDWARE, "auto", "自动选择硬件加速解码器"),
    /**
     * 不选择硬件加速解码器
     */
    NONE_DECODER_HARDWARE(FFmpegArgument.DECODER_HARDWARE, "none", "不选择硬件加速解码器");

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
