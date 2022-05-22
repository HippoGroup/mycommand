package com.hippocp.mycommand.extension.nvidia;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.hippocp.mycommand.extension.nvidia.command.NvidiaCommand;
import com.hippocp.mycommand.extension.nvidia.option.NvidiaDeviceMonitorOptions;
import com.hippocp.mycommand.extension.nvidia.option.NvidiaOptions;
import com.hippocp.mycommand.session.NvidiaLogOutputAndErrorStream;
import com.hippocp.mycommand.session.NvidiaMonitorDynamicInfo;
import com.hippocp.mycommand.session.NvidiaMonitorLog;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author ZhouYifan
 * @date 2022/3/8
 */
@Slf4j
public class NvidiaCommandBuilderTest {

    public String fileOutputPath() {
        // 生成时间戳
        String timeStr = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        // 生成随机字符串
        String randomStr = RandomUtil.randomNumbers(6);

        String fileOutputPath = "F:/GPU/" + timeStr + randomStr + "/";

        return fileOutputPath;
    }

    @Test
    public void monitor() throws IOException {
        NvidiaOptions options = new NvidiaOptions(
                new NvidiaDeviceMonitorOptions()
                        .monitor()
                        .count(10)
        );
        NvidiaCommand command = new NvidiaCommand(options);
        String fileOutputPath = fileOutputPath();
        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);
        NvidiaMonitorLog nvidiaMonitorLog = new NvidiaMonitorLog();
        NvidiaLogOutputAndErrorStream stream = new NvidiaLogOutputAndErrorStream(logFile, nvidiaMonitorLog);
        int value = command.executeSync(0, 3600, stream);
    }

    @Test
    public void monitorAsync() throws IOException {
        NvidiaOptions options = new NvidiaOptions(
                new NvidiaDeviceMonitorOptions()
                        .monitor()
                        .count(10)
        );
        NvidiaCommand command = new NvidiaCommand(options);
        String fileOutputPath = fileOutputPath();
        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);
        NvidiaMonitorLog nvidiaMonitorLog = new NvidiaMonitorLog();
        NvidiaLogOutputAndErrorStream stream = new NvidiaLogOutputAndErrorStream(logFile, nvidiaMonitorLog);
        command.executeAsync(0, 3600, stream);
        NvidiaMonitorDynamicInfo dynamicInfo = stream.getDynamicInfo();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info(dynamicInfo.toString());
    }

    @Test
    public void monitorSyncTest() throws IOException {
        NvidiaOptions options = new NvidiaOptions(
                new NvidiaDeviceMonitorOptions()
                        .monitor()
                        .count(10)
        );
        NvidiaCommand command = new NvidiaCommand(options);
        String fileOutputPath = fileOutputPath();
        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);
        NvidiaMonitorLog nvidiaMonitorLog = new NvidiaMonitorLog();
        NvidiaLogOutputAndErrorStream stream = new NvidiaLogOutputAndErrorStream(logFile, nvidiaMonitorLog);
        command.executeSync(0, 3600, stream);
        String cmd = stream.getCommand();
        log.info(cmd);
        String inFileCommand = stream.getInFileCommand();
        log.info(inFileCommand);

    }

}
