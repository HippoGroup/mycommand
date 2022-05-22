package com.hippocp.mycommand.extension.ffmpeg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ffmpeg 命令文件路径，包含输入文件路径、输出文件路径
 *
 * @author ZhouYifan
 * @date 2022/1/28 15:49
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FFmpegInputOutputFilePath {
    /**
     * 输入文件路径
     */
    String inputFilePath;

    /**
     * 输出文件路径
     */
    String outputFilePath;

}
