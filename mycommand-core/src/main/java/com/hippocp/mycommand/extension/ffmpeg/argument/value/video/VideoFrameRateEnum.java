package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 视频帧率
 *
 * @author ZhouYifan
 * @date 2021/12/31 11:06
 */
@Getter
@ToString
@AllArgsConstructor
public enum VideoFrameRateEnum {

    /**
     * 24fps
     */
    FPS24(FFmpegArgument.FRAME_RATE, "24", "24fps"),
    /**
     * 25fps
     */
    FPS25(FFmpegArgument.FRAME_RATE, "25", "25fps"),
    /**
     * 30fps
     */
    FPS30(FFmpegArgument.FRAME_RATE, "30", "30fps"),
    /**
     * 50fps
     */
    FPS50(FFmpegArgument.FRAME_RATE, "50", "50fps"),
    /**
     * 60fps
     */
    FPS60(FFmpegArgument.FRAME_RATE, "60", "60fps");

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
