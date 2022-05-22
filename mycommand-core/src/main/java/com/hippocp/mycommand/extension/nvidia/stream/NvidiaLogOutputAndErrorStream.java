package com.hippocp.mycommand.extension.nvidia.stream;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import com.hippocp.easy.code.util.string.StringUtil;
import lombok.ToString;
import org.apache.commons.exec.LogOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author ZhouYifan
 * @date 2022/3/8
 */
@ToString(callSuper = true)
public class NvidiaLogOutputAndErrorStream extends LogOutputStream {

    private static final Logger log = LoggerFactory.getLogger(NvidiaLogOutputAndErrorStream.class);

    /**
     * 预期第一行内容
     */
    private final String expectedFirstRowContent = "# gpu   pwr gtemp mtemp    sm   mem   enc   dec  mclk  pclk";
    private final int one = 1;
    private final int two = 2;

    /**
     * 预期第二行内容
     */
    private final String expectedSecondRowContent = "# Idx     W     C     C     %     %     %     %   MHz   MHz";

    /**
     * 不是预期内容
     */
    private boolean isNotExpectedContent = true;

    /**
     * 是预期第一行内容
     */
    private boolean isExpectedFirstRowContent = false;

    /**
     * 是预期第二行内容
     */
    private boolean isExpectedSecondRowContent = false;

    /**
     * 行号从1开始
     */
    private int rowNum = 0;

    /**
     * 日志文件
     */
    private final File logFile;

    /**
     * 显卡监控日志对象
     */
    private final NvidiaMonitorLog logData;

    /**
     * 显卡监控动态信息
     */
    private final NvidiaMonitorDynamicInfo dynamicInfo;

    public NvidiaLogOutputAndErrorStream(File logFile, NvidiaMonitorLog nvidiaMonitorLog) {
        // 属性初始化
        this.logFile = logFile;

        if (nvidiaMonitorLog != null) {
            this.logData = nvidiaMonitorLog;
        } else {
            this.logData = new NvidiaMonitorLog();
        }

        this.dynamicInfo = new NvidiaMonitorDynamicInfo(this.logData);
    }

    public NvidiaMonitorDynamicInfo getDynamicInfo() {
        return dynamicInfo;
    }

    /**
     * 检查是否不是预期内容
     *
     * @return 不是预期内容-true 是预期内容-false
     */
    protected boolean checkIsNotExpectedContent() {
        isNotExpectedContent = !(isExpectedFirstRowContent && isExpectedSecondRowContent);
        return isNotExpectedContent;
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
        // 当前行内容为空白
        if (StrUtil.isBlank(line)) {
            // 结束方法，什么都不做
            return;
        }

        // 行号加1，空白行不加行号
        rowNum++;

        // 每行子进程信息都输出日志
        if (log.isDebugEnabled()) {
            log.debug(line);
        }

        // 写日志到文件
        try {
            writeLogFile(line);
        } catch (Exception e) {
            log.error("写日志到文件失败", e);
        }

        // 给当前行内容去除左右空格
        String trimLine = line.trim();
        // 是第一行内容
        boolean isFirstRowContent = rowNum == one;
        if (isFirstRowContent) {
            isExpectedFirstRowContent = expectedFirstRowContent.equals(trimLine);
            checkIsNotExpectedContent();
        }

        // 是第二行内容
        boolean isSecondRowContent = rowNum == two;
        if (isSecondRowContent) {
            isExpectedSecondRowContent = expectedSecondRowContent.equals(trimLine);
            checkIsNotExpectedContent();
        }

        // 不是预期内容
        if (isNotExpectedContent) {
            return;
        }

        // 是第二行之后内容
        try {

            boolean isSecondRowAfterContent = rowNum > two;
            if (isSecondRowAfterContent) {
                List<String> strings = StrUtil.split(trimLine, " ");
                // 移除所有空白字符元素
                strings.removeIf(StrUtil::isBlank);
                // 去除所有元素左右空白字符
                strings.forEach(StrUtil::trim);
                logData.init(
                        strings.get(0),
                        strings.get(1),
                        strings.get(2),
                        strings.get(3),
                        strings.get(4),
                        strings.get(5),
                        strings.get(6),
                        strings.get(7),
                        strings.get(8),
                        strings.get(9)
                );
            }

            if (log.isDebugEnabled()) {
                Integer gpuIndex = logData.getGpuIndex();
                Integer pwr = logData.getPwr();
                Integer gtemp = logData.getGtemp();
                Integer mtemp = logData.getMtemp();
                Integer sm = logData.getSm();
                Integer mem = logData.getMem();
                Integer enc = logData.getEnc();
                Integer dec = logData.getDec();
                Integer mclk = logData.getMclk();
                Integer pclk = logData.getPclk();
                final String logStr = "GPU索引：{}, GPU功耗：{}，GPU温度：{}，显存温度：{}，流处理器使用率：{}%，" +
                        "显存使用率：{}%，编码引擎使用率：{}%，解码引擎使用率：{}%，显存频率：{}MHz，处理器频率：{}MHz";
                log.debug(logStr, gpuIndex, pwr, gtemp, mtemp, sm, mem, enc, dec, mclk, pclk);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    /**
     * 写日志文件
     *
     * @param line 单行内容
     */
    public void writeLogFile(String line) {
        if (logFile == null) {
            return;
        }
        // 将每一行数据写入text文件
        String lineBreak = StringUtil.presentOsLineBreak();
        FileWriter writer = new FileWriter(logFile, StandardCharsets.UTF_8);
        // 内容
        String content = line + lineBreak;
        // 追加到文件
        writer.append(content);
    }

}
