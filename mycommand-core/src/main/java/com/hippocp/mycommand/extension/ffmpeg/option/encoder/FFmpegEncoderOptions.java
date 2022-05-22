package com.hippocp.mycommand.extension.ffmpeg.option.encoder;

import com.hippocp.mycommand.extension.command.AbstractCommandSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffmpeg 编码器选项
 *
 * @author ZhouYifan
 * @date 2022/2/12 11:35
 */
public class FFmpegEncoderOptions<Children extends FFmpegEncoderOptions<Children>>
        extends AbstractCommandSegment<Children> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegEncoderOptions.class);


}
