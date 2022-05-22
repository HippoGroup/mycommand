package com.hippocp.mycommand.extension.ffmpeg.option.composition;

import com.hippocp.mycommand.extension.ffmpeg.option.FFmpegInputOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffmpeg 命令输入选项和输入文件路径
 *
 * @author ZhouYifan
 * @date 2022/1/22 11:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FFmpegInputOptionsAndFile {

    private static final Logger log = LoggerFactory.getLogger(FFmpegInputOptionsAndFile.class);

    /**
     * 输入选项
     */
    private FFmpegInputOptions inputOption;

    /**
     * 输入文件路径
     */
    private String inputFilePath;

}
