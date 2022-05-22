package com.hippocp.mycommand.extension.ffmpeg.argument.value.video;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 视频编码 buffer
 *
 * @author ZhouYifan
 * @date 2021/12/31 11:07
 */
@Getter
@ToString
@AllArgsConstructor
public enum VideoBufSizeEnum {

    /**
     * 50k
     */
    B50(FFmpegArgument.BUF_SIZE, "50k", "50k"),
    /**
     * 100k
     */
    B100(FFmpegArgument.BUF_SIZE, "100k", "100k"),
    /**
     * 150k
     */
    B150(FFmpegArgument.BUF_SIZE, "150k", "150k"),
    /**
     * 300k
     */
    B300(FFmpegArgument.BUF_SIZE, "300k", "300k"),
    /**
     * 350k
     */
    B350(FFmpegArgument.BUF_SIZE, "350k", "350k"),
    /**
     * 400k
     */
    B400(FFmpegArgument.BUF_SIZE, "400k", "400k"),
    /**
     * 450k
     */
    B450(FFmpegArgument.BUF_SIZE, "450k", "450k"),
    /**
     * 544k
     */
    B544(FFmpegArgument.BUF_SIZE, "544k", "544k"),
    /**
     * 620k
     */
    B620(FFmpegArgument.BUF_SIZE, "620k", "620k"),
    /**
     * 800k
     */
    B800(FFmpegArgument.BUF_SIZE, "800k", "800k"),
    /**
     * 1200k
     */
    B1200(FFmpegArgument.BUF_SIZE, "1200k", "1200k"),
    /**
     * 1400k
     */
    B1400(FFmpegArgument.BUF_SIZE, "1400k", "1400k"),
    /**
     * 1560k
     */
    B1560(FFmpegArgument.BUF_SIZE, "1560k", "1560k"),
    /**
     * 1600k
     */
    B1600(FFmpegArgument.BUF_SIZE, "1600k", "1600k"),
    /**
     * 1800k
     */
    B1800(FFmpegArgument.BUF_SIZE, "1800k", "1800k");

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
