package com.hippocp.mycommand.customize;

import cn.hutool.core.io.FileUtil;
import com.hippocp.mycommand.io.FileUtility;

import java.io.File;

/**
 * 默认命令文件
 *
 * @author ZhouYifan
 * @date 2022/3/19
 */
public class DefaultCommandFile extends CommandFileCustomize {

    /**
     * MyCommand路径
     */
    protected static final String MY_COMMAND_PATH = "MyCommand/";

    /**
     * 创建命令文件
     *
     * @return 命令文件
     */
    @Override
    public File createCommandFile() {
        // 命令文件后缀名，区分平台
        final String fileSuffix;
        if (FileUtil.isWindows()) {
            fileSuffix = ".bat";
        } else {
            fileSuffix = ".sh";
        }

        final String command = "command";
        final String commandFileName = command + fileSuffix;
        String filePath = FileUtility.joinFilePath(MY_COMMAND_PATH, commandFileName);
        String filePrefix = FileUtility.joinFileNamePrefix(command);
        // 创建命令文件
        return FileUtility.createFile(filePath, filePrefix, fileSuffix);
    }

}
