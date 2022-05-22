package com.hippocp.mycommand.session;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.RandomUtil;
import com.hippocp.easy.code.util.string.StringUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.LogOutputStream;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * 结果处理流<br>
 * 若需要自行处理子进程的每一行输出信息，请重写processLine方法。<br>
 * 此类提供多个方法，以支持将子进程日志输出到文件，可在子类重写的processLine方法中调用。
 *
 * @author ZhouYifan
 * @date 2022/3/10
 */
@Slf4j
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public abstract class ResultHandlerStream extends LogOutputStream {
    /**
     * 框架执行的命令
     */
    protected String command;
    /**
     * 异步执行结果处理器接口，用于保存自定义实现
     */
    protected ExecuteResultHandler resultHandler;
    /**
     * 默认的异步执行结果处理器
     */
    protected DefaultExecuteResultHandler defaultExecuteResultHandler;
    /**
     * 文件内命令
     */
    protected String inFileCommand;
    /**
     * 子进程日志的文件写入器
     */
    protected FileWriter writer;
    /**
     * MyCommand路径
     */
    protected static final String MY_COMMAND_PATH = "/MyCommand/";


    /**
     * 将缓冲区转换为字符串并将其发送到 processLine 方法
     *
     * @param cc 要记录的数据（字节）。
     */
    @Override
    public void write(final int cc) {
        super.processBuffer();
    }


    /**
     * 创建日志文件，双策略（在类加载根目录创建或在当前系统临时目录创建）
     *
     * @return 日志文件
     */
    protected File createLogFile() {
        // 创建日志文件
        File logFile = null;

        // 在类加载根目录创建文件
        try {
            logFile = createLogFileInClassLoadRootDir();
        } catch (IORuntimeException e) {
            log.error("在类加载根目录创建文件失败，开始尝试在当前系统临时目录创建文件。", e);
        }

        // 在当前系统临时目录创建文件
        if (logFile == null) {
            logFile = createLogFileInTempDir();
        }

        return logFile;

    }


    /**
     * 写日志文件
     *
     * @param line 单行内容
     */
    protected void writeLogFile(String line, File logFile, Charset charset) {
        // 将每一行数据写入text文件
        String lineBreak = StringUtil.presentOsLineBreak();
        if (writer == null) {
            writer = new FileWriter(logFile, charset);
        }
        // 内容
        String content = line + lineBreak;
        // 追加到文件
        writer.append(content);
    }


    /**
     * 在类加载根路径创建日志文件
     *
     * @return 日志文件
     */
    protected File createLogFileInClassLoadRootDir() {
        String classLoadRootPath = this.getClass().getResource("/").getPath();
        String logRepositoryPath = classLoadRootPath + MY_COMMAND_PATH;
        String logFilePath = getLogFilePath(logRepositoryPath);
        return FileUtil.touch(logFilePath);
    }


    /**
     * 获取日志文件绝对路径
     *
     * @param logRepositoryPath 日志存放路径
     * @return 绝对路径
     */
    public String getLogFilePath(String logRepositoryPath) {
        // 生成时间戳
        String timeStr = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        // 生成随机字符串
        String randomStr = RandomUtil.randomNumbers(6);
        StringBuffer str = new StringBuffer();
        // 拼接日志路径
        str.append(logRepositoryPath).append(timeStr).append(randomStr).append("/").append("log.txt");
        return str.toString();
    }


    /**
     * 在当前系统临时目录创建日志文件
     *
     * @return 日志文件
     */
    protected File createLogFileInTempDir() {
        String logFilePrefix = getLogFilePrefix();
        // 创建临时文件
        File logFile = null;
        try {
            logFile = File.createTempFile(logFilePrefix, ".txt");
        } catch (IOException e) {
            log.error("在当前系统临时目录创建文件失败。", e);
        }
        return logFile;
    }


    /**
     * 获取日志文件前缀名
     *
     * @return 绝对路径
     */
    protected String getLogFilePrefix() {
        final String name = "log-";
        // 生成时间戳
        String timeStr = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        // 生成随机字符串
        String randomStr = RandomUtil.randomNumbers(6);
        StringBuffer str = new StringBuffer();
        // 拼接日志路径
        str.append(name).append(timeStr).append(randomStr);
        return str.toString();
    }

}
