package com.hippocp.mycommand.session;

import cn.hutool.core.convert.Convert;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 显卡监控日志
 *
 * @author ZhouYifan
 * @date 2022/3/8
 */
@Getter
@ToString
@EqualsAndHashCode
public class NvidiaMonitorLog {
    /**
     * GPU索引 例如 0 1
     */
    private String gpuIndexText;

    /**
     * GPU索引 例如 0 1
     */
    private Integer gpuIndex;

    /**
     * GPU功耗
     */
    private String pwrText;

    /**
     * GPU功耗
     */
    private Integer pwr;

    /**
     * GPU温度
     */
    private String gtempText;

    /**
     * GPU温度
     */
    private Integer gtemp;

    /**
     * 显存温度
     */
    private String mtempText;

    /**
     * 显存温度
     */
    private Integer mtemp;

    /**
     * 流处理器使用率
     */
    private String smText;

    /**
     * 流处理器使用率
     */
    private Integer sm;

    /**
     * 显存使用率
     */
    private String memText;

    /**
     * 显存使用率
     */
    private Integer mem;

    /**
     * 编码引擎使用率
     */
    private String encText;

    /**
     * 编码引擎使用率
     */
    private Integer enc;

    /**
     * 解码引擎使用率
     */
    private String decText;

    /**
     * 解码引擎使用率
     */
    private Integer dec;

    /**
     * 显存频率
     */
    private String mclkText;

    /**
     * 显存频率
     */
    private Integer mclk;

    /**
     * 处理器频率
     */
    private String pclkText;

    /**
     * 处理器频率
     */
    private Integer pclk;

    /**
     * 初始化数据，并执行类型转换
     *
     * @param gpuIndexText GPU索引
     * @param pwrText      GPU功耗
     * @param gtempText    GPU温度
     * @param mtempText    显存温度
     * @param smText       流处理器使用率
     * @param memText      显存使用率
     * @param encText      编码引擎使用率
     * @param decText      解码引擎使用率
     * @param mclkText     显存频率
     * @param pclkText     处理器频率
     */
    public void init(
            String gpuIndexText,
            String pwrText,
            String gtempText,
            String mtempText,
            String smText,
            String memText,
            String encText,
            String decText,
            String mclkText,
            String pclkText
    ) {
        this.gpuIndexText = gpuIndexText;
        this.pwrText = pwrText;
        this.gtempText = gtempText;
        this.mtempText = mtempText;
        this.smText = smText;
        this.memText = memText;
        this.encText = encText;
        this.decText = decText;
        this.mclkText = mclkText;
        this.pclkText = pclkText;
        typeConvert();
    }

    /**
     * 类型转换
     */
    public void typeConvert() {
        this.gpuIndex = Convert.toInt(gpuIndexText);
        this.pwr = Convert.toInt(pwrText, null);
        this.gtemp = Convert.toInt(gtempText);
        this.mtemp = Convert.toInt(mtempText, null);
        this.sm = Convert.toInt(smText);
        this.mem = Convert.toInt(memText);
        this.enc = Convert.toInt(encText);
        this.dec = Convert.toInt(decText);
        this.mclk = Convert.toInt(mclkText);
        this.pclk = Convert.toInt(pclkText);
    }

}
