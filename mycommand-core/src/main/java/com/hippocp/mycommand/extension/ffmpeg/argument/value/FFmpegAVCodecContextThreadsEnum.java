package com.hippocp.mycommand.extension.ffmpeg.argument.value;

import com.hippocp.mycommand.extension.ffmpeg.option.av.FFmpegAVCodecContextAVOptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * AVCodecContext threads 参数值枚举
 *
 * @author ZhouYifan
 * @date 2021/12/31 11:07
 */
@Getter
@ToString
@AllArgsConstructor
public enum FFmpegAVCodecContextThreadsEnum {
    /**
     * 自动检测要使用的合适数量的线程
     * autodetect a suitable number of threads to use
     */
    THREADS_AUTO(FFmpegAVCodecContextAVOptions.Argument.THREADS, "auto", "自动检测要使用的合适数量的线程 autodetect a suitable number of threads to use");

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
