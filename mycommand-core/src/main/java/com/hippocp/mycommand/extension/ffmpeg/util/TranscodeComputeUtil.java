package com.hippocp.mycommand.extension.ffmpeg.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.VideoBitrateEnum;

/**
 * 转码计算工具类
 *
 * @author ZhouYifan
 * @date 2022/2/12 17:49
 */
public class TranscodeComputeUtil {

    /**
     * 计算muxRate
     *
     * @param videoBitrateEnum 视频码率枚举
     * @return muxRate值
     */
    public static String computeMuxRate(VideoBitrateEnum videoBitrateEnum) {
        String videoBitrate = videoBitrateEnum.getVal();
        return computeMuxRate(videoBitrate);
    }

    /**
     * 计算muxRate
     *
     * @param videoBitrate 视频码率值
     * @return muxRate值
     */
    public static String computeMuxRate(String videoBitrate) {
        // 倍率
        float val = 1.1005F;
        // 类型转换
        Integer vb = Convert.toInt(videoBitrate);
        // 技术结构
        int result = (int) (vb * val);
        // 差值
        int difference = result - vb;
        final int benchmark = 700;
        // 获取单位，如：k
        String unit = StrUtil.sub(videoBitrate, -1, videoBitrate.length());
        String muxRate;
        // 差值小于基准
        if (difference < benchmark) {
            // 视频码率加上基准
            int newResult = vb + benchmark;
            muxRate = newResult + unit;
        } else {
            // 差值大于基准
            muxRate = result + unit;
        }
        return muxRate;
    }

    /**
     * 计算bufSize
     *
     * @param videoBitrateEnum 视频码率枚举
     * @return bufSize值
     */
    public static String computeBufSize(VideoBitrateEnum videoBitrateEnum) {
        String videoBitrate = videoBitrateEnum.getVal();
        return computeBufSize(videoBitrate);
    }

    /**
     * 计算bufSize
     *
     * @param videoBitrate 视频码率值
     * @return bufSize值
     */
    public static String computeBufSize(String videoBitrate) {
        Integer vb = Convert.toInt(videoBitrate);
        int result = vb / 10 * 2;
        String unit = StrUtil.sub(videoBitrate, -1, videoBitrate.length());
        return result + unit;
    }

}
