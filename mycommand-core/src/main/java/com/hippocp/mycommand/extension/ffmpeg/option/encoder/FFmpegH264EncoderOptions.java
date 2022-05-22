package com.hippocp.mycommand.extension.ffmpeg.option.encoder;

import com.hippocp.mycommand.extension.ffmpeg.argument.constant.FFmpegArgument;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.H264VideoLevelEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffmpeg 编码器选项
 *
 * @author ZhouYifan
 * @date 2022/2/12 11:35
 */
public class FFmpegH264EncoderOptions<Children extends FFmpegH264EncoderOptions<Children>>
        extends FFmpegEncoderOptions<Children> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegH264EncoderOptions.class);

    /**
     * 设置等级
     *
     * @param h264VideoLevelEnum 视频等级枚举
     * @return {@link Children}
     */
    public Children level(H264VideoLevelEnum h264VideoLevelEnum) {
        return level(h264VideoLevelEnum.getVal());
    }

    /**
     * 设置等级
     *
     * @param condition          执行条件
     * @param h264VideoLevelEnum 视频等级枚举
     * @return {@link Children}
     */
    public Children level(boolean condition, H264VideoLevelEnum h264VideoLevelEnum) {
        return level(condition, h264VideoLevelEnum.getVal());
    }

    /**
     * 设置等级
     *
     * @param level 视频等级值
     * @return {@link Children}
     */
    public Children level(String level) {
        return level(true, level);
    }

    /**
     * 设置等级
     *
     * @param condition 执行条件
     * @param level     值
     * @return {@link Children}
     */
    public Children level(boolean condition, String level) {
        return this.addCommandSegment(condition, FFmpegArgument.LEVEL, level);
    }

}
