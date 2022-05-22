package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 视频等级
 *
 * @author ZhouYifan
 * @date 2021/12/31 11:07
 */
@Getter
@ToString
@AllArgsConstructor
public enum H264VideoLevelEnum {
    /**
     * 1.0
     */
    L1(FFmpegArgument.LEVEL, "1.0", "1.0"),
    /**
     * 1.3
     */
    L1D3(FFmpegArgument.LEVEL, "1.3", "1.3"),
    /**
     * 2.0
     */
    L2(FFmpegArgument.LEVEL, "2.0", "2.0"),
    /**
     * 3.0
     */
    L3(FFmpegArgument.LEVEL, "3.0", "3.0"),
    /**
     * 4.0
     */
    L4(FFmpegArgument.LEVEL, "4.0", "4.0"),
    /**
     * 5.0
     */
    L5(FFmpegArgument.LEVEL, "5.0", "5.0"),
    /**
     * 6.0
     */
    L6(FFmpegArgument.LEVEL, "6.0", "6.0"),
    /**
     * 2.2
     */
    L2D2(FFmpegArgument.LEVEL, "2.2", "2.2"),
    /**
     * 3.2
     */
    L3D2(FFmpegArgument.LEVEL, "3.2", "3.2"),
    /**
     * 4.2
     */
    L4D2(FFmpegArgument.LEVEL, "4.2", "4.2");

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
