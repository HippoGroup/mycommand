package com.hippocp.mycommand.extension.hutool;

import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author ZhouYifan
 * @date 2022/3/4
 */
class FileUtilTest {

    @Test
    void getFileExtNameTest() {
        String extName = FileUtil.extName("F:\\ffmpeg-test\\output\\参考帧硬编测试\\参考帧硬编测试205-1.ts");
        Assertions.assertEquals("ts", extName);
    }

}
