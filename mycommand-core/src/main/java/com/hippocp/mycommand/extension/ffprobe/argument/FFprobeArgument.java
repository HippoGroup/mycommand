package com.hippocp.mycommand.extension.ffprobe.argument;

/**
 * ffprobe参数
 *
 * @author ZhouYifan
 * @date 2022/1/26 9:20
 */
public interface FFprobeArgument {
    /**
     * ffprobe
     */
    String FFPROBE = "ffprobe";
    /**
     * 设置输出打印格式(可用的格式有 default, compact, csv, flat, ini, json, xml)
     */
    String PRINT_FORMAT = "-of";
    /**
     * 显示所有流视频流、音频流、字幕流、图片流、数据流
     */
    String SHOW_STREAMS = "-show_streams";

}
