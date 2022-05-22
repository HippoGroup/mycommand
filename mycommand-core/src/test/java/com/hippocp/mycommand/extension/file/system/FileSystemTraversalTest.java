package com.hippocp.mycommand.extension.file.system;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 文件系统遍历测试
 *
 * @author ZhouYifan
 * @date 2022/2/8
 */
@Slf4j
public class FileSystemTraversalTest {

    @Test
    public void findRootsTest() {
        FilePath[] filePathRoots = FileSystemTraversalUtil.findRoots();
        for (FilePath filePathRoot : filePathRoots) {
            log.info("根目录目录子文件或路径：{}", filePathRoot);
        }
    }

    @Test
    public void findFileSystemByPathTest() {
        FilePath[] filePathRoots = FileSystemTraversalUtil.findRoots();

        // 是路径而非文件
        if (filePathRoots[1].getDirectory()) {
            String absolutePath = filePathRoots[1].getAbsolutePath();
            FilePath[] filePaths = FileSystemTraversalUtil.findFileSystemByPath(absolutePath);

            for (FilePath filePath : filePaths) {
                log.info("当前目录子文件或路径：{}", filePath);
            }

        }

    }


}
