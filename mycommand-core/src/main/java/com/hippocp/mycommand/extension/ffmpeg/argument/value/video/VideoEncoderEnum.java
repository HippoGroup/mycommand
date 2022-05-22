package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * ffmpeg 视频编码器
 *
 * @author ZhouYifan
 * @date 2022/1/2 1:13
 */
@Getter
@ToString
@AllArgsConstructor
public enum VideoEncoderEnum {

    /**
     * h264_nvenc<br>
     * Encoder h264_nvenc [NVIDIA NVENC H.264 encoder]:<br>
     * General capabilities: dr1 delay hardware<br>
     * Threading capabilities: none<br>
     * Supported hardware devices: cuda cuda d3d11va d3d11va<br>
     * Supported pixel formats: yuv420p nv12 p010le yuv444p p016le yuv444p16le bgr0 rgb0 cuda d3d11<br>
     */
    H264_NVENC(FFmpegArgument.VIDEO_ENCODER, "h264_nvenc", "h264_nvenc编码器一种h264视频编码器"),
    /**
     * libx264<br>
     * Encoder libx264 [libx264 H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10]:<br>
     * General capabilities: delay threads<br>
     * Threading capabilities: other<br>
     * Supported pixel formats: yuv420p yuvj420p yuv422p yuvj422p yuv444p yuvj444p nv12 nv16 nv21 yuv420p10le<br>
     * yuv422p10le yuv444p10le nv20le gray gray10le<br>
     */
    LIBX264(FFmpegArgument.VIDEO_ENCODER, "libx264", "libx264视频编码器"),
    /**
     * libx265<br>
     * Encoder libx265 [libx265 H.265 / HEVC]:<br>
     * General capabilities: delay threads<br>
     * Threading capabilities: other<br>
     * Supported pixel formats: yuv420p yuvj420p yuv422p yuvj422p yuv444p yuvj444p gbrp yuv420p10le yuv422p10le<br>
     * yuv444p10le gbrp10le yuv420p12le yuv422p12le yuv444p12le gbrp12le gray gray10le gray12le<br>
     */
    LIBX265(FFmpegArgument.VIDEO_ENCODER, "libx265", "libx265视频编码器"),
    /**
     * hevc_nvenc<br>
     * h265编码器<br>
     * Encoder hevc_nvenc [NVIDIA NVENC hevc encoder]:<br>
     * General capabilities: dr1 delay hardware<br>
     * Threading capabilities: none<br>
     * Supported hardware devices: cuda cuda d3d11va d3d11va<br>
     * Supported pixel formats: yuv420p nv12 p010le yuv444p p016le yuv444p16le bgr0 rgb0 cuda d3d11<br>
     */
    HEVC_NVENC(FFmpegArgument.VIDEO_ENCODER, "hevc_nvenc", "hevc_nvenc一种h265视频编码器");

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
