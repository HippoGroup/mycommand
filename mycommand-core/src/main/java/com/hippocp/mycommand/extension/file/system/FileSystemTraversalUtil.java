package com.hippocp.mycommand.extension.file.system;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;

/**
 * 文件系统遍历工具类
 *
 * @author ZhouYifan
 * @date 2022/2/8
 */
@Slf4j
public class FileSystemTraversalUtil {

    /**
     * 寻找当前系统根目录
     *
     * @return {@link FilePath} 文件路径对象数组
     */
    public static FilePath[] findRoots() {

        // 获取当前系统根目录，例如，Windows 平台为每个活动驱动器都有一个根目录； UNIX 平台有一个根目录，即"/" 。
        File[] listRoots = File.listRoots();
        log.info("当前系统文件根目录：{}", Arrays.toString(listRoots));

        // 生成文件路径对象数组
        FilePath[] filePathRoots = new FilePath[listRoots.length];
        // 将File转换为FilePath
        for (int i = 0; i < listRoots.length; i++) {
            FilePath filePath = new FilePath(listRoots[i]);
            filePathRoots[i] = filePath;
        }

        return filePathRoots;

    }

    /**
     * 根据指定路径查询文件系统
     *
     * @param path 路径
     * @return {@link FilePath} 文件路径对象数组
     */
    public static FilePath[] findFileSystemByPath(String path) {

        // 列出指定路径下的目录和文件，给定的绝对路径不能是压缩包中的路径
        File[] files = FileUtil.ls(path);
        // 生成文件路径对象数组
        FilePath[] filePaths = new FilePath[files.length];
        // 将File转换为FilePath
        for (int i = 0; i < files.length; i++) {
            FilePath filePath = new FilePath(files[i]);
            filePaths[i] = filePath;
        }

        return filePaths;

    }

    private FileSystemTraversalUtil() {
    }
}
