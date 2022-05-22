package sample.mycommand.web.mapper;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * FFMpeg 转码日志数据对象
 *
 * @author ZhouYifan
 * @date 2022/2/4
 */
@Getter
@ToString
@EqualsAndHashCode
public class FFmpegTranscodeLog {

    private static final Logger log = LoggerFactory.getLogger(FFmpegTranscodeLog.class);

    // frame=    1 fps=0.0 q=0.0 size=       0kB time=00:00:00.12 bitrate=   0.0kbits/s speed=5.34x
    // frame=  335 fps=327 q=24.0 size=    7424kB time=00:00:18.81 bitrate=3232.2kbits/s speed=18.4x
    // frame= 1449 fps=411 q=30.0 size=   33024kB time=00:01:20.68 bitrate=3353.0kbits/s speed=22.9x

    /**
     * 计量单位常量
     */
    public static final String KB = "kB";
    public static final String MB = "mB";
    public static final String GB = "gB";

    /**
     * 计量单位常量
     */
    public static final String KBS = "kbits/s";
    public static final String MBS = "mbits/s";
    public static final String GBS = "gbits/s";

    /**
     * 标志常量
     */
    public static final String FRAME_FLAG = "frame=";
    public static final String FPS_FLAG = "fps=";
    public static final String Q_FLAG = "q=";
    public static final String SIZE_FLAG = "size=";
    public static final String TIME_FLAG = "time=";
    public static final String BITRATE_FLAG = "bitrate=";
    public static final String SPEED_FLAG = "speed=";


    /**
     * 用于替换字符串的正则常量
     */
    public static final String SPACE_REGEX = "|\\s";
    public static final String FRAME_FILTER_REGEX = FRAME_FLAG;
    public static final String FPS_FILTER_REGEX = FPS_FLAG;
    public static final String Q_FILTER_REGEX = Q_FLAG;
    public static final String SIZE_FILTER_REGEX = SIZE_FLAG + "|" + KB + "|" + MB + "|" + GB;
    public static final String TIME_FILTER_REGEX = TIME_FLAG;
    public static final String BITRATE_FILTER_REGEX = BITRATE_FLAG + "|" + KBS + "|" + MBS + "|" + GBS;
    public static final String SPEED_FILTER_REGEX = SPEED_FLAG + "|x|X";

    /**
     * 换算值
     */
    public static final int CONVERT_VALUE = 1024;

    private String frameText;

    private Long frame;

    private String fpsText;

    private BigDecimal fps;

    private String qText;

    private BigDecimal q;

    /**
     * 输出文件大小文本
     * 例子 33024kB
     */
    private String sizeText;

    /**
     * 输出文件大小，单位（KB）
     * 例子 33024
     */
    private BigDecimal size;

    /**
     * 已处理时长文本
     * 例子 00:01:20.68
     */
    private String timeText;

    /**
     * 已处理时长，单位（秒）
     * 例子 80
     */
    private Integer time;
    /**
     * 比特率文本 单位（kbits/s）例子 3353.0kbits/s
     */
    private String bitrateText;

    /**
     * 比特率 单位（kbits/s） 例子 3353.0
     */
    private BigDecimal bitrate;

    /**
     * 处理速度文本 例子 22.9x
     */
    private String speedText;

    /**
     * 处理速度 例子 22.9
     */
    private BigDecimal speed;

    /**
     * 初始化部分属性，然后进行类型转换、单位换算
     *
     * @param frameText   frame文本
     * @param fpsText     fps文本
     * @param qText       q文本
     * @param sizeText    输出文件大小文本
     * @param timeText    已处理时长文本
     * @param bitrateText 比特率文本
     * @param speedText   速度文本
     */
    public void init(String frameText, String fpsText, String qText, String sizeText, String timeText, String bitrateText, String speedText) {
        this.frameText = frameText;
        this.fpsText = fpsText;
        this.qText = qText;
        this.sizeText = sizeText;
        this.timeText = timeText;
        this.bitrateText = bitrateText;
        this.speedText = speedText;
        typeConvert();
        unitConvert();
    }

    /**
     * 类型转换
     */
    public void typeConvert() {
        // frame= 1449 fps=411 q=30.0 size=   33024kB time=00:01:20.68 bitrate=3353.0kbits/s speed=22.9x

//        String Str = Text.replaceAll(, "").trim();
        String frameStr = frameText.replaceAll(FRAME_FILTER_REGEX, "").trim();
        this.frame = Convert.toLong(frameStr);

        String fpsStr = fpsText.replaceAll(FPS_FILTER_REGEX, "").trim();
        this.fps = Convert.toBigDecimal(fpsStr);

        String qStr = qText.replaceAll(Q_FILTER_REGEX, "").trim();
        this.q = Convert.toBigDecimal(qStr);

        String sizeStr = sizeText.replaceAll(SIZE_FILTER_REGEX, "").trim();
        this.size = Convert.toBigDecimal(sizeStr);

        String timeStr = timeText.replaceAll(TIME_FILTER_REGEX, "").trim();
        String trimMillisecondTimeStr = StrUtil.sub(timeStr, 0, timeStr.indexOf("."));
        this.time = DateUtil.timeToSecond(trimMillisecondTimeStr);

        String bitrateStr = bitrateText.replaceAll(BITRATE_FILTER_REGEX, "").trim();
        this.bitrate = Convert.toBigDecimal(bitrateStr);

        String speedStr = speedText.replaceAll(SPEED_FILTER_REGEX, "").trim();
        this.speed = Convert.toBigDecimal(speedStr);
    }

    /**
     * 单位换算
     */
    public void unitConvert() {
        // sizeText 进行计量单位识别，kB为默认单位不进行换算
        boolean isMB = StrUtil.containsIgnoreCase(sizeText, FFmpegTranscodeLog.MB);
        if (isMB) {
            this.size = size.divide(BigDecimal.valueOf(CONVERT_VALUE), RoundingMode.HALF_UP);
        }

        boolean isGB = StrUtil.containsIgnoreCase(sizeText, FFmpegTranscodeLog.GB);
        if (isGB) {
            this.size = size.divide(BigDecimal.valueOf(CONVERT_VALUE * CONVERT_VALUE), RoundingMode.HALF_UP);
        }

        // bitrateText 进行计量单位识别、换算，kbits/s为默认单位不进行换算
        boolean isMBS = StrUtil.containsIgnoreCase(bitrateText, FFmpegTranscodeLog.MBS);
        if (isMBS) {
            this.bitrate = bitrate.divide(BigDecimal.valueOf(CONVERT_VALUE), RoundingMode.HALF_UP);
        }

        boolean isGBS = StrUtil.containsIgnoreCase(bitrateText, FFmpegTranscodeLog.GBS);
        if (isGBS) {
            this.bitrate = bitrate.divide(BigDecimal.valueOf(CONVERT_VALUE * CONVERT_VALUE), RoundingMode.HALF_UP);
        }

    }

    public void clear() {
        this.frameText = null;
        this.frame = 0L;
        this.fpsText = null;
        this.fps = null;
        this.qText = null;
        this.q = null;
        this.sizeText = null;
        this.size = null;
        this.timeText = null;
        this.time = 0;
        this.bitrateText = null;
        this.bitrate = null;
        this.speedText = null;
        this.speed = null;
    }

}
