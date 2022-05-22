package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * ffmpeg 视频过滤器
 *
 * @author ZhouYifan
 * @date 2022/1/2 1:13
 */
@Getter
@ToString
@AllArgsConstructor
public enum VideoFilterEnum {

    /**
     * 设置分辨率 1280x720<br>
     * 设置隔行扫描，交错顶部和底部字段（别忘记使用x264专有参数 -x264opts force-cfr=1:tff）<br>
     * interleave_top  4            ..FV....... interleave top and bottom fields
     */
    HD720_INTERLEAVE_TOP(FFmpegArgument.VIDEO_FILTER, "scale=1280x720[out],[out]tinterlace=mode=interleave_top", "隔行扫描，交错顶部和底部字段"),
    /**
     * 设置分辨率 1920x1080<br>
     * 设置隔行扫描，交错顶部和底部字段（别忘记使用x264专有参数 -x264opts force-cfr=1:tff）<br>
     * interleave_top  4            ..FV....... interleave top and bottom fields
     */
    HD1080_INTERLEAVE_TOP(FFmpegArgument.VIDEO_FILTER, "scale=1920x1080[out],[out]tinterlace=mode=interleave_top", "隔行扫描，交错顶部和底部字段"),
    /**
     * 设置隔行扫描，交错底部和顶部字段
     * interleave_bottom 5            ..FV....... interleave bottom and top fields
     */
    INTERLEAVE_BOTTOM(FFmpegArgument.VIDEO_FILTER, "tinterlace=mode=interleave_bottom", "隔行扫描，交错底部和顶部字段"),
    /**
     * 设置逐行扫描，每帧发送一帧
     * send one frame for each frame
     */
    SEND_FRAME(FFmpegArgument.VIDEO_FILTER, "yadif=mode=send_frame", "逐行扫描，每帧输出一帧");

    private String arg;

    private String val;

    private String description;


}
