package com.hippocp.mycommand.extension.ffmpeg.option.av;

import com.hippocp.mycommand.extension.command.AbstractCommandSegment;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.FFmpegAVCodecContextThreadsEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.VideoBitrateEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.VideoBufSizeEnum;

/**
 * ffmpeg AVCodecContext AVOptions
 *
 * @author ZhouYifan
 * @date 2022/1/28 9:50
 */
public class FFmpegAVCodecContextAVOptions extends AbstractCommandSegment<FFmpegAVCodecContextAVOptions> {

    /**
     * 设置使用线程数量
     *
     * @param codecContextEnum threads 参数值枚举
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions threads(FFmpegAVCodecContextThreadsEnum codecContextEnum) {
        return threads(true, codecContextEnum.getVal());
    }

    /**
     * 设置使用线程数量
     *
     * @param condition        执行条件
     * @param codecContextEnum threads 参数值枚举
     * @return {@link FFmpegAVCodecContextAVOptions
     */
    public FFmpegAVCodecContextAVOptions threads(boolean condition, FFmpegAVCodecContextThreadsEnum codecContextEnum) {
        return threads(condition, codecContextEnum.getVal());
    }

    /**
     * 设置使用线程数量
     *
     * @param val 值
     * @return {@link FFmpegAVCodecContextAVOptions
     */
    public FFmpegAVCodecContextAVOptions threads(String val) {
        return threads(true, val);
    }

    /**
     * 设置使用线程数量
     *
     * @param condition 执行条件
     * @param val       值
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions threads(boolean condition, String val) {
        return addCommandSegment(condition, Argument.THREADS, val);
    }

    /**
     * 设置最大码率
     *
     * @param videoBitrateEnum 视频码率枚举
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions maxRate(VideoBitrateEnum videoBitrateEnum) {
        return maxRate(videoBitrateEnum.getVal());
    }

    /**
     * 设置最大码率
     *
     * @param condition        执行条件
     * @param videoBitrateEnum 视频码率枚举
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions maxRate(boolean condition, VideoBitrateEnum videoBitrateEnum) {
        return maxRate(condition, videoBitrateEnum.getVal());
    }

    /**
     * 设置最大码率
     *
     * @param maxRate 值
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions maxRate(String maxRate) {
        return maxRate(true, maxRate);
    }

    /**
     * 设置最大码率
     *
     * @param condition 执行条件
     * @param maxRate   值
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions maxRate(boolean condition, String maxRate) {
        return addCommandSegment(condition, Argument.MAX_RATE, maxRate);
    }

    /**
     * 设置最小码率
     *
     * @param videoBitrateEnum 视频码率枚举
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions minRate(VideoBitrateEnum videoBitrateEnum) {
        return minRate(videoBitrateEnum.getVal());
    }

    /**
     * 设置最小码率
     *
     * @param condition        执行条件
     * @param videoBitrateEnum 视频码率枚举
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions minRate(boolean condition, VideoBitrateEnum videoBitrateEnum) {
        return minRate(condition, videoBitrateEnum.getVal());
    }

    /**
     * 设置最小码率
     *
     * @param minRate 值
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions minRate(String minRate) {
        return minRate(true, minRate);
    }


    /**
     * 设置最小码率
     *
     * @param condition 执行条件
     * @param minRate   值
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions minRate(boolean condition, String minRate) {
        return addCommandSegment(condition, Argument.MIN_RATE, minRate);
    }

    /**
     * 设置每多少帧一个GOP
     *
     * @param val int值
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions gop(int val) {
        return gop(true, String.valueOf(val));
    }

    /**
     * 设置每多少帧一个GOP
     *
     * @param condition 执行条件
     * @param val       值
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions gop(boolean condition, int val) {
        return gop(condition, String.valueOf(val));
    }

    /**
     * 设置每多少帧一个GOP
     *
     * @param val 值
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions gop(String val) {
        return gop(true, val);
    }

    /**
     * 设置每多少帧一个GOP
     *
     * @param condition 执行条件
     * @param val       值
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions gop(boolean condition, String val) {
        return addCommandSegment(condition, Argument.GOP, val);
    }

    /**
     * 设置编码 buffer 大小
     *
     * @param videoBufSizeEnum 视频编码 buffer 枚举
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions bufSize(VideoBufSizeEnum videoBufSizeEnum) {
        return bufSize(videoBufSizeEnum.getVal());
    }

    /**
     * 设置编码 buffer 大小
     *
     * @param condition        执行条件
     * @param videoBufSizeEnum 视频编码 buffer 枚举
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions bufSize(boolean condition, VideoBufSizeEnum videoBufSizeEnum) {
        return bufSize(condition, videoBufSizeEnum.getVal());
    }

    /**
     * 设置编码 buffer 大小
     *
     * @param bufSize 值，例如：码率 1M/s bufSize 50k
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions bufSize(String bufSize) {
        return bufSize(true, bufSize);
    }

    /**
     * 设置编码 buffer 大小
     *
     * @param condition 执行条件
     * @param bufSize   值，例如：码率 1M/s bufSize 50k
     * @return {@link FFmpegAVCodecContextAVOptions}
     */
    public FFmpegAVCodecContextAVOptions bufSize(boolean condition, String bufSize) {
        return addCommandSegment(condition, Argument.BUF_SIZE, bufSize);
    }

    public FFmpegAVCodecContextAVOptions maxBFrame(int intVal) {
        return maxBFrame(String.valueOf(intVal));
    }

    public FFmpegAVCodecContextAVOptions maxBFrame(String val) {
        return maxBFrame(true, val);
    }

    public FFmpegAVCodecContextAVOptions maxBFrame(boolean condition, String val) {
        return addCommandSegment(condition, Argument.BF, val);
    }

    public FFmpegAVCodecContextAVOptions referenceFrame(String val) {
        return referenceFrame(true, val);
    }

    public FFmpegAVCodecContextAVOptions referenceFrame(boolean condition, String val) {
        return addCommandSegment(condition, Argument.REFS, val);
    }

    public FFmpegAVCodecContextAVOptions flags(String val) {
        return flags(true, val);
    }

    public FFmpegAVCodecContextAVOptions flags(boolean condition, String val) {
        return addCommandSegment(condition, Argument.FLAGS, val);
    }

    public interface Argument {
        /**
         * GOP(Group of picture)
         */
        String GOP = "-g";

        /**
         * 设置编码 buffer 大小<br>
         * 例如：码率 1M/s bufSize 50k
         */
        String BUF_SIZE = "-bufsize";

        /**
         * 最小码率<br>
         * 例子：1000k
         */
        String MIN_RATE = "-minrate";

        /**
         * 最大码率<br>
         * 例子：1000k
         */
        String MAX_RATE = "-maxrate";

        /**
         * 非B帧之间的最大B帧数
         */
        String BF = "-bf";

        /**
         * 参考帧
         */
        String REFS = "-refs";

        /**
         * 设置线程数
         */
        String THREADS = "-threads";

        /**
         * 设置 IDR 帧之间的最小间隔
         */
        String KEYINT_MIN = "-keyint_min";

        /**
         * 设置通用标志
         */
        String FLAGS = "-flags";
    }

    public interface FlagsArgumentValue {
        /**
         * 闭合 GOP
         */
        String CLOSE_GOP = "+cgop";
    }

}
