package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.LibX264Argument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author ZhouYifan
 * @date 2022/1/21 14:48
 */
@Getter
@ToString
@AllArgsConstructor
public enum X264OptsEnum {

    /**
     * x264专有参数 隔行扫描 顶场优先 TFF
     */
    TFF(LibX264Argument.X264OPTS, "force-cfr=1:tff", "x264专有参数 隔行扫描 顶场优先"),
    /**
     * 设置B帧个数，两个P帧之间包含10个B帧
     */
    B10(LibX264Argument.X264OPTS, "bframes=10:b-adapt=0", "设置B帧个数，两个P帧之间包含10个B帧");

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
