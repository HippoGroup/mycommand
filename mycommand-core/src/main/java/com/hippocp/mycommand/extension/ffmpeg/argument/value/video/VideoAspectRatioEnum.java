package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 视频宽高比
 *
 * @author ZhouYifan
 * @date 2021/12/31 11:07
 */
@Getter
@ToString
@AllArgsConstructor
public enum VideoAspectRatioEnum {

    /**
     * 16:9
     */
    SIXTEEN_TO_NINE(FFmpegArgument.VIDEO_ASPECT_RATIO, "16:9", "16:9"),
    /**
     * 16:10
     */
    SIXTEEN_TO_TEN(FFmpegArgument.VIDEO_ASPECT_RATIO, "16:10", "16:10"),
    /**
     * 3:2
     */
    THREE_TO_TWO(FFmpegArgument.VIDEO_ASPECT_RATIO, "3:2", "3:2"),
    /**
     * 3:4
     */
    FOUR_TO_THREE(FFmpegArgument.VIDEO_ASPECT_RATIO, "4:3", "4:3");

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
