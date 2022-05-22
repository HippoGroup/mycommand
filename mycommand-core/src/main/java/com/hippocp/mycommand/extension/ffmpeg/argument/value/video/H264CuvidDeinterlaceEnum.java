package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.H264CuvidArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * H264Cuvid反交错参数值
 *
 * @author ZhouYifan
 * @date 2022/1/25 18:27
 */
@Getter
@ToString
@AllArgsConstructor
public enum H264CuvidDeinterlaceEnum {

    /**
     * 编织去隔行（什么都不做）
     */
    WEAVE(H264CuvidArgument.DEINTERLACE, "weave", "编织去隔行（什么都不做）"),
    /**
     * Bob去隔行
     */
    BOB(H264CuvidArgument.DEINTERLACE, "bob", "Bob去隔行"),
    /**
     * 自适应去隔行
     */
    ADAPTIVE(H264CuvidArgument.DEINTERLACE, "adaptive", "自适应去隔行");

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
