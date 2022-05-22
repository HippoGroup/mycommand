package sample.mycommand.web.mapper;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.hippocp.easy.code.util.string.StringUtil;
import com.hippocp.mycommand.session.ResultHandlerStream;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ZhouYifan
 * @date 2022/2/4
 */
@ToString(callSuper = true)
public class FFmpegLogOutPutAndErrorStream extends ResultHandlerStream {

    private static final Logger log = LoggerFactory.getLogger(FFmpegLogOutPutAndErrorStream.class);

    public final String[] flags = new String[]{
            FFmpegTranscodeLog.FRAME_FLAG,
            FFmpegTranscodeLog.FPS_FLAG,
            FFmpegTranscodeLog.Q_FLAG,
            FFmpegTranscodeLog.SIZE_FLAG,
            FFmpegTranscodeLog.TIME_FLAG,
            FFmpegTranscodeLog.BITRATE_FLAG,
            FFmpegTranscodeLog.SPEED_FLAG
    };

    public static final String DURATION_FLAG = "Duration:";

    public static final String DURATION_REPLACE = "Duration:|\\s";

    /**
     * ffmpeg 转码日志
     */
    private final FFmpegTranscodeLog logData = new FFmpegTranscodeLog();

    /**
     * ffmpeg 转码动态信息
     */
    private final FFmpegTranscodeDynamicInfo dynamicInfo = new FFmpegTranscodeDynamicInfo(logData);

    private final File logFile;

    public FFmpegLogOutPutAndErrorStream(File logFile) {
        this.logFile = logFile;
    }

    /**
     * 将缓冲区转换为字符串并将其发送到 processLine 方法
     *
     * @param cc 要记录的数据（字节）。
     */
    @Override
    public void write(final int cc) {
        super.processBuffer();
    }

    @Override
    protected void processLine(String line, int logLevel) {
        // frame=    1 fps=0.0 q=0.0 size=       0kB time=00:00:00.12 bitrate=   0.0kbits/s speed=5.34x

        // 当前行内容为空白
        if (StrUtil.isBlank(line)) {
            // 结束方法，什么都不做
            return;
        }

        // 每行子进程信息都输出日志
        if (log.isDebugEnabled()) {
            log.debug(line);
        }

        // 输出到日志文件
        try {
            writeLogFile(line);
        } catch (Exception e) {
            log.error("写日志到文件失败", e);
        }

        Integer durationCount = dynamicInfo.getDurationCount();
        boolean isNullDuration = durationCount == null;
        // 未抓取到总时长
        if (isNullDuration) {
            try {
                // 抓取时长信息
                int durationFlagIndexOf = line.indexOf(DURATION_FLAG);
                boolean isFindDurationFlag = durationFlagIndexOf > -1;
                if (isFindDurationFlag) {

                    String durationFullStr = line.substring(durationFlagIndexOf, line.indexOf(","));
                    String durationStr = durationFullStr.replaceAll(DURATION_REPLACE, "");

                    if (log.isDebugEnabled()) {
                        log.debug("抓取到的时长字符串：{}", durationStr);
                    }

                    String trimMillisecondDurationStr = StrUtil.sub(durationStr, 0, durationStr.indexOf("."));
                    durationCount = DateUtil.timeToSecond(trimMillisecondDurationStr);
                    dynamicInfo.setDurationCount(durationCount);

                    if (log.isDebugEnabled()) {
                        log.debug("抓取到的时长：{}", durationCount);
                    }

                }

            } catch (Exception e) {
                log.error("抓取时长信息失败", e);
            }
        }

        // 总时长不为空时执行
        if (!isNullDuration) {
            // 抓取转码动态实时信息
            try {

                boolean isTranscodeDynamicInfoLine = findTranscodeDynamicInfoLine(line);

                if (log.isDebugEnabled() && isTranscodeDynamicInfoLine) {
                    log.debug("速度：{}", logData.getSpeed());
                    BigDecimal needSecondTime = dynamicInfo.getNeedSecondTime();

                    if (needSecondTime == null) {
                        log.debug("所需时间：无法计算");
                    } else {
                        int needSecondIntValue = needSecondTime.intValue();
                        String needTime = DateUtil.secondToTime(needSecondIntValue);
                        log.debug("所需时间：{}", needTime);
                        log.debug("所需时间（秒）：{}", needSecondTime);
                    }

                    Date predictCompleteDate = dynamicInfo.getPredictCompleteDate();
                    if (predictCompleteDate == null) {
                        log.debug("预计完成时间：无法计算");
                    } else {
                        String formatDate = DateUtil.formatDateTime(predictCompleteDate);
                        log.debug("预计完成时间：{}", formatDate);
                    }

                }

            } catch (Exception e) {
                log.error("抓取转码动态实时信息失败", e);
            }
        }

    }

    /**
     * 写日志文件
     *
     * @param line 单行内容
     */
    public void writeLogFile(String line) {
        // 将每一行数据写入text文件
        String lineBreak = StringUtil.presentOsLineBreak();
        FileWriter writer = new FileWriter(logFile, CharsetUtil.systemCharset());
        // 内容
        String content = line + lineBreak;
        // 追加到文件
        writer.append(content);
    }


    /**
     * 寻找转码动态信息行，并且将数据存入 FFmpegTranscodeLog logData 对象中
     *
     * @param line 行内容
     * @return 布尔值 true-是，false-否
     */
    public boolean findTranscodeDynamicInfoLine(String line) {
        // frame=    1 fps=0.0 q=0.0 size=       0kB time=00:00:00.12 bitrate=   0.0kbits/s speed=5.34x
        // 行内容为空非目标行
        if (StrUtil.isBlank(line)) {
            return false;
        }

        // 索引列表
        List<Integer> indexList = new ArrayList<>();
        // 是否匹配标志列表
        List<Boolean> matchList = new ArrayList<>();

        // 遍历标志样本，校验是否匹配当前行内容
        for (String flag : flags) {

            try {
                // 找下标，找不到返回 -1
                int flagIndexOf = line.indexOf(flag);
                // 是否找到标志
                boolean isFindFlag = flagIndexOf > -1;
                // 各个标志索引存入列表
                indexList.add(flagIndexOf);
                // ture/false 存入列表
                matchList.add(isFindFlag);
            } catch (Exception e) {
                log.error("遍历标志样本，校验是否匹配当前行内容时出错", e);
            }

        }

        // 遍历是否匹配标志列表
        for (Boolean flag : matchList) {
            // 一个标志不匹配，立即返回null
            if (flag.equals(false)) {
                return false;
            }
        }

        // 标志文本列表
        List<String> flagTextList = new ArrayList<>();

        // 索引列表大小
        int indexListSize = indexList.size();
        // 最后一个索引值
        int lastIndex = indexListSize - 1;
        // 标志完全匹配情况下，遍历索引列表，将行内容映射至对象
        for (int i = 0; i < indexListSize; i++) {
            // 当前标志索引
            int index = indexList.get(i);

            // 到达最后一个索引
            if (i == lastIndex) {
                // 标志文本内容
                String flagText = StrUtil.sub(line, index, -1);
                // 加入标志文本列表
                flagTextList.add(flagText);
                // 结束当次循环，进入下次循环，预期不应该有下次循环，已到达最后一个索引
                continue;
            }

            // 下一个标志索引
            int nextIndex = indexList.get(i + 1);
            // 非最后一个索引
            // 标志文本内容
            String flagText = StrUtil.sub(line, index, nextIndex);
            // 加入标志文本列表
            flagTextList.add(flagText);

        }

        // 解析出的数据存入 FFmpegTranscodeLog logData 对象中
        logData.init(
                flagTextList.get(0),
                flagTextList.get(1),
                flagTextList.get(2),
                flagTextList.get(3),
                flagTextList.get(4),
                flagTextList.get(5),
                flagTextList.get(6)
        );

        // 计算属性
        Integer durationCount = dynamicInfo.getDurationCount();
        computed(durationCount);

        return true;
    }

    /**
     * 计算属性
     *
     * @param durationCount 总时长
     */
    public void computed(int durationCount) {

        // 总时长 - 已处理时长 = 剩余未处理时长
        int surplusDuration = durationCount - logData.getTime();

        // 剩余未处理时长（秒） / 速度 = 处理所需时间
        BigDecimal d = new BigDecimal(surplusDuration);
        BigDecimal s = logData.getSpeed();

        // 防止除零异常
        // 如果速度为0则不进行计算
        boolean isZero = s.equals(BigDecimal.ZERO);
        if (isZero) {
            return;
        }

        // 计算结果四舍五入
        BigDecimal needSecondTime = d.divide(s, RoundingMode.HALF_UP);
        dynamicInfo.setNeedSecondTime(needSecondTime);

        if (log.isDebugEnabled()) {
            log.debug("所需总时间（秒）：{}", needSecondTime);
        }

        Date predictCompleteDate = DateUtil.offsetSecond(new Date(), needSecondTime.intValue());
        dynamicInfo.setPredictCompleteDate(predictCompleteDate);
        String dateFormat = DateUtil.format(predictCompleteDate, DatePattern.NORM_DATETIME_MS_FORMAT);
        if (log.isDebugEnabled()) {
            log.debug("预计完成时间：{}", dateFormat);
        }

    }

    public FFmpegTranscodeDynamicInfo getDynamicInfo() {
        return dynamicInfo;
    }

}
