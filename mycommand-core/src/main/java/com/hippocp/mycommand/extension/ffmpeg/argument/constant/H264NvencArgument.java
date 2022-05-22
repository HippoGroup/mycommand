package com.hippocp.mycommand.extension.ffmpeg.argument.constant;

/**
 * h264_nvenc 编码器参数
 *
 * @author ZhouYifan
 * @date 2022/1/18 17:50
 */
public interface H264NvencArgument {
    /**
     * 视频码率模式<br>
     * Override the preset rate-control (from -1 to INT_MAX) (default -1)
     */
    String VIDEO_BITRATE_MODEL = "-rc";
    /**
     * int type<br>
     * Number of frames to look ahead for rate-control (from 0 to INT_MAX) (default 0)
     */
    String LOOKAHEAD = "-rc-lookahead";
    /**
     * boolean type<br>
     * When lookahead is enabled, set this to 1 to disable adaptive I-frame insertion at scene cuts (default false)
     */
    String NO_SCENE_CUT = "-no-scenecut";
    /**
     * 参考帧数量<br>
     * int type<br>
     * Specifies the DPB size used for encoding (0 means automatic) (from 0 to INT_MAX) (default 0)
     */
    String DPB_SIZE = "-dpb_size";
}
