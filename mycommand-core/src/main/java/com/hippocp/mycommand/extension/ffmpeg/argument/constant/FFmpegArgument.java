package com.hippocp.mycommand.extension.ffmpeg.argument.constant;

/**
 * FFmpeg 公共参数常量
 *
 * @author ZhouYifan
 * @date 2022/1/18 17:13
 */
public interface FFmpegArgument {
    /**
     * ffmpeg
     */
    String FFMPEG = "ffmpeg";
    /**
     * 视频过滤
     */
    String VIDEO_FILTER = "-vf";
    /**
     * GOP(Group of picture)
     */
    String GOP = "-g";
    /**
     * 输入文件
     * 即需要转码的文件
     */
    String INPUT = "-i";
    /**
     * 视频编码器参数，指定视频编码器
     */
    String VIDEO_ENCODER = "-c:v";
    /**
     * 音频编码器参数
     */
    String AUDIO_ENCODER = "-c:a";
    /**
     * 指定视频解码器
     */
    String VIDEO_DECODER = "-c:v";
    /**
     * 视频平均码率
     */
    String VIDEO_AVG_BITRATE = "-b:v";
    /**
     * 逐行扫描
     */
    String DEINTERLACE = "-deinterlace";
    /**
     * 帧率FPS
     */
    String FRAME_RATE = "-r";
    /**
     * 分辨率
     */
    String VIDEO_RESOLUTION = "-s";
    /**
     * 宽高比
     */
    String VIDEO_ASPECT_RATIO = "-aspect";
    /**
     * 音频采样率
     */
    String AUDIO_SAMPLE_RATE = "-ar";
    /**
     * 声道
     */
    String AUDIO_CHANNEL = "-ac";
    /**
     * 音频码率，单位k
     */
    String AUDIO_BITRATE = "-b:a";
    /**
     * 指定无需询问即可覆盖输出文件
     */
    String OVERWRITE = "-y";
    /**
     * 指定不要覆盖输出文件，如果指定的输出文件已经存在，则立即退出。
     */
    String NOT_OVERWRITE = "-n";
    /**
     * 视频总长度，设置录制/转码的时长
     */
    String VIDEO_DURATION = "-t";
    /**
     * 切片定位起始时间点
     */
    String POSITION = "-ss";
    /**
     * 最大码率<br>
     * 例子：1000k
     */
    String MAX_RATE = "-maxrate";
    /**
     * 最小码率<br>
     * 例子：1000k
     */
    String MIN_RATE = "-minrate";
    /**
     * 设置编码 buffer 大小<br>
     * 例如：码率 1M/s bufSize 50k
     */
    String BUF_SIZE = "-bufsize";
    /**
     * 视频档次
     */
    String PROFILE = "-profile:v";
    /**
     * 视频等级
     */
    String LEVEL = "-level";
    /**
     * 选择硬件加速解码器
     */
    String DECODER_HARDWARE = "-hwaccel";
    /**
     * 将解码帧保存在 GPU 内存中
     */
    String DECODER_HARDWARE_OUTPUT_FORMAT = "-hwaccel_output_format";

    String THREADS = "-threads";
    /**
     * 非B帧之间的最大B帧数
     */
    String BF = "-bf";
    /**
     * 最小 GOP 大小
     */
    String KEYINT_MIN = "-keyint_min";
    /**
     * 参考帧
     */
    String REFS = "-refs";
    /**
     * 日志级别
     */
    String LOG_LEVEL = "-v";
    /**
     * 强制输出格式
     */
    String FORCE_OUTPUT_FORMAT = "-f";


}
