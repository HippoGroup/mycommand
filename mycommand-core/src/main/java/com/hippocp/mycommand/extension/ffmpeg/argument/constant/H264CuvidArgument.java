package com.hippocp.mycommand.extension.ffmpeg.argument.constant;

/**
 * h264_cuvid 解码器参数
 *
 * @author ZhouYifan
 * @date 2022/1/25 18:18
 */
public interface H264CuvidArgument {
    /**
     * 设置反交错模式<br>
     * -deint             <int>        .D.V....... Set deinterlacing mode (from 0 to 2) (default weave)<br>
     * weave           0            .D.V....... Weave deinterlacing (do nothing)<br>
     * bob             1            .D.V....... Bob deinterlacing<br>
     * adaptive        2            .D.V....... Adaptive deinterlacing<br>
     */
    String DEINTERLACE = "-deint";
    /**
     * 选择用于解码的GPU
     * GPU to be used for decoding
     */
    String GPU = "-gpu";
    /**
     * Drop second field when deinterlacing (default false)
     */
    String DROP_SECOND_FIELD = "-drop_second_field";
    /**
     * Resize (width)x(height)
     */
    String RESIZE = "-resize";
}
