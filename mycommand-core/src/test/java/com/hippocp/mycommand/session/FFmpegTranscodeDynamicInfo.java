package com.hippocp.mycommand.session;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * ffmpeg 转码动态信息
 *
 * @author ZhouYifan
 * @date 2022/2/11 11:35
 */
public class FFmpegTranscodeDynamicInfo {

    /**
     * 总时长 单位（秒）
     */
    private Integer durationCount;

    /**
     * 所需时间
     */
    private BigDecimal needSecondTime;

    /**
     * 预计完成时间
     */
    private Date predictCompleteDate;

    /**
     * 转码日志数据
     */
    private FFmpegTranscodeLog logData;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FFmpegTranscodeDynamicInfo{");
        sb.append("durationCount=").append(durationCount);
        sb.append(", needSecondTime=").append(needSecondTime);
        sb.append(", predictCompleteDate=").append(predictCompleteDate);
        sb.append(", logData=").append(logData);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FFmpegTranscodeDynamicInfo that = (FFmpegTranscodeDynamicInfo) o;
        return Objects.equals(durationCount, that.durationCount) && Objects.equals(needSecondTime, that.needSecondTime) && Objects.equals(predictCompleteDate, that.predictCompleteDate) && Objects.equals(logData, that.logData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(durationCount, needSecondTime, predictCompleteDate, logData);
    }

    public Integer getDurationCount() {
        return durationCount;
    }

    public void setDurationCount(Integer durationCount) {
        this.durationCount = durationCount;
    }

    public BigDecimal getNeedSecondTime() {
        return needSecondTime;
    }

    public void setNeedSecondTime(BigDecimal needSecondTime) {
        this.needSecondTime = needSecondTime;
    }

    public Date getPredictCompleteDate() {
        return predictCompleteDate;
    }

    public void setPredictCompleteDate(Date predictCompleteDate) {
        this.predictCompleteDate = predictCompleteDate;
    }

    public FFmpegTranscodeLog getLogData() {
        return logData;
    }

    public void setLogData(FFmpegTranscodeLog logData) {
        this.logData = logData;
    }

    public FFmpegTranscodeDynamicInfo() {
    }

    public FFmpegTranscodeDynamicInfo(FFmpegTranscodeLog logData) {
        this.logData = logData;
    }

    public FFmpegTranscodeDynamicInfo(Integer durationCount, BigDecimal needSecondTime, Date predictCompleteDate, FFmpegTranscodeLog logData) {
        this.durationCount = durationCount;
        this.needSecondTime = needSecondTime;
        this.predictCompleteDate = predictCompleteDate;
        this.logData = logData;
    }
}
