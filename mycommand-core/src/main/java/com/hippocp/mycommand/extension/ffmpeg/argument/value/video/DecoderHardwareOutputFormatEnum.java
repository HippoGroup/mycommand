package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;


import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 将解码帧保存在 GPU 内存中
 *
 * @author ZhouYifan
 * @date 2022/1/22 9:55
 */
@Getter
@ToString
@AllArgsConstructor
public enum DecoderHardwareOutputFormatEnum {
    /**
     * 将解码帧保存在 GPU 内存中 cuda
     */
    CUDA_DECODER_HARDWARE_OUTPUT_FORMAT(FFmpegArgument.DECODER_HARDWARE_OUTPUT_FORMAT, "cuda", "将解码帧保存在 GPU 内存中 cuda");

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
