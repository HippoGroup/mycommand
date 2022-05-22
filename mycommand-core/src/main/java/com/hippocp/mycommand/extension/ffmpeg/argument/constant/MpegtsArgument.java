package com.hippocp.mycommand.extension.ffmpeg.argument.constant;

/**
 * mpegts 封装格式参数
 *
 * @author ZhouYifan
 * @date 2022/1/21 15:55
 */
public interface MpegtsArgument {

    /**
     * mpegts 封装格式特有参数<br>
     * 设置一个恒定的复用率。默认为 VBR<br>
     * 用于稳定码率 计算公式 平均码率加 500<br>
     * 例如平均码率7000K + 500k = 7500k<br>
     */
    String MUX_RATE = "-muxrate";

}
