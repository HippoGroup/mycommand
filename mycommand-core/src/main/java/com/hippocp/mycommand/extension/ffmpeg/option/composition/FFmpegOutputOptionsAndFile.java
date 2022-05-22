package com.hippocp.mycommand.extension.ffmpeg.option.composition;

import com.hippocp.mycommand.extension.ffmpeg.option.FFmpegOutputOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffmpeg 命令输出选项和输出文件路径
 *
 * @author ZhouYifan
 * @date 2022/1/22 11:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FFmpegOutputOptionsAndFile {

    private static final Logger log = LoggerFactory.getLogger(FFmpegOutputOptionsAndFile.class);

    /**
     * 输出选项
     */
    private FFmpegOutputOptions outputOption;

    /**
     * 输出文件路径
     */
    private String outputFilePath;

}
