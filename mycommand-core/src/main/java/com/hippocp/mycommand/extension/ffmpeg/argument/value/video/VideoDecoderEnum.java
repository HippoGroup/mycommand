package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * ffmpeg 视频解码器
 *
 * @author ZhouYifan
 * @date 2022/1/2 1:13
 */
@Getter
@ToString
@AllArgsConstructor
public enum VideoDecoderEnum {

    /**
     * Decoder h264_cuvid [Nvidia CUVID H264 decoder]:<br>
     * General capabilities: delay avoidprobe hardware<br>
     * Threading capabilities: none<br>
     * Supported hardware devices: cuda<br>
     * Supported pixel formats: cuda nv12 p010le p016le<br>
     */
    H264_CUVID(FFmpegArgument.VIDEO_ENCODER, "h264_cuvid", "h264_cuvid 一种h264视频解码器"),
    /**
     * Decoder mpeg2_cuvid [Nvidia CUVID MPEG2VIDEO decoder]:<br>
     * General capabilities: delay avoidprobe hardware<br>
     * Threading capabilities: none<br>
     * Supported hardware devices: cuda<br>
     * Supported pixel formats: cuda nv12 p010le p016le<br>
     */
    MPEG2_CUVID(FFmpegArgument.VIDEO_ENCODER, "mpeg2_cuvid", "mpeg2_cuvid 一种mpeg2视频解码器"),
    /**
     * Decoder hevc_cuvid [Nvidia CUVID HEVC decoder]:<br>
     * General capabilities: delay avoidprobe hardware<br>
     * Threading capabilities: none<br>
     * Supported hardware devices: cuda<br>
     * Supported pixel formats: cuda nv12 p010le p016le<br>
     */
    HEVC_CUVID(FFmpegArgument.VIDEO_ENCODER, "hevc_cuvid", "hevc_cuvid 一种h265视频解码器");

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
