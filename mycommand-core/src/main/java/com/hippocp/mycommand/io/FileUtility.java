package com.hippocp.mycommand.io;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.RandomUtil;
import com.hippocp.mycommand.exceptions.ExceptionFactory;
import com.hippocp.mycommand.executor.ErrorContext;
import com.hippocp.mycommand.logging.Log;
import com.hippocp.mycommand.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author ZhouYifan
 * @date 2022/3/19
 */
public class FileUtility {

    protected static final String SLASH = "/";

    private static final Log log = LogFactory.getLog(FileUtility.class);

    /**
     * 双策略创建文件，优先在类加载根目录创建，若失败则在当前系统临时目录创建，仍然失败返回null
     *
     * @param filePath       类加载根目录创建文件路径，如：MyCommand/log.txt
     * @param tempFilePrefix 系统临时目录创建的文件名，如：log
     * @param tempFileSuffix 系统临时目录创建的文件名后缀，如：.txt
     * @return 文件
     */
    public static File createFile(String filePath, String tempFilePrefix, String tempFileSuffix) {
        // 创建日志文件
        File logFile = null;

        // 在类加载根目录创建文件
        try {
            logFile = createFileInClassLoadRootDir(filePath);
        } catch (IORuntimeException e) {
            log.error("在类加载根目录创建文件失败，开始尝试在当前系统临时目录创建文件。", e);
        }

        // 在当前系统临时目录创建文件
        if (logFile == null) {
            logFile = createFileInTempDir(tempFilePrefix, tempFileSuffix);
        }

        return logFile;

    }

    /**
     * 在类加载根路径创建文件
     *
     * @param filePath 自定义的路径路径，不以路径分隔符开头，如：MyCommand/log.txt
     * @return 文件
     */
    public static File createFileInClassLoadRootDir(String filePath) {
        String classLoadRootPath = FileUtility.class.getResource(SLASH).getPath();
        String absolutePath = classLoadRootPath + SLASH + filePath;
        return FileUtil.touch(absolutePath);
    }


    /**
     * 在当前系统临时目录创建文件
     *
     * @return 文件
     */
    public static File createFileInTempDir(String prefix, String suffix) {
        // 创建临时文件
        File logFile;
        try {
            logFile = File.createTempFile(prefix, suffix);
        } catch (IOException e) {
            throw ExceptionFactory.wrapException("在当前系统临时目录创建文件失败。原因：" + e, e);
        } finally {
            ErrorContext.instance().reset();
        }
        return logFile;
    }


    /**
     * 拼接文件名前缀
     *
     * @return 绝对路径
     */
    public static String joinFileNamePrefix(String name) {
        // 生成时间戳
        String timeStr = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        // 生成随机字符串
        String randomStr = RandomUtil.randomNumbers(6);
        StringBuffer str = new StringBuffer();
        // 拼接日志路径
        str.append(name).append("-").append(timeStr).append(randomStr);
        return str.toString();
    }


    /**
     * 拼接文件路径
     *
     * @param repositoryPath 存放路径
     * @return 文件路径
     */
    public static String joinFilePath(String repositoryPath, String fileFullName) {
        // 生成时间戳
        String timeStr = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        // 生成随机字符串
        String randomStr = RandomUtil.randomNumbers(6);
        StringBuffer str = new StringBuffer();
        // 拼接文件路径
        str.append(repositoryPath).append(timeStr).append(randomStr).append("/").append(fileFullName);
        return str.toString();
    }

    private FileUtility() {
    }

}
