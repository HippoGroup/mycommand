package com.hippocp.mycommand.extension.ffmpeg.option;

import com.hippocp.mycommand.extension.command.AbstractCommandSegment;
import com.hippocp.mycommand.extension.ffmpeg.option.audio.FFmpegAudioOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.av.FFmpegAVCodecContextAVOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.encoder.FFmpegEncoderOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.muxer.FFmpegMpegtsMuxerOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.pre.file.FFmpegPerFileMainOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.video.FFmpegAdvancedVideoOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.video.FFmpegVideoOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffmpeg命令 [outfile options] 输出文件选项
 *
 * @author ZhouYifan
 * @date 2022/1/22 11:09
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class FFmpegOutputOptions extends AbstractCommandSegment<FFmpegOutputOptions> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegOutputOptions.class);

    private FFmpegPerFileMainOptions perFileMainOptions;

    private FFmpegVideoOptions videoOptions;

    private FFmpegAdvancedVideoOptions advancedVideoOptions;

    private FFmpegAudioOptions audioOptions;

    private FFmpegEncoderOptions encoderOptions;

    private FFmpegAVCodecContextAVOptions avCodecContextAVOptions;

    private FFmpegMpegtsMuxerOptions mpegtsMuxerOptions;


    public FFmpegOutputOptions() {
    }

    public FFmpegOutputOptions(FFmpegPerFileMainOptions perFileMainOptions) {
        this.perFileMainOptions = perFileMainOptions;
    }

    public FFmpegOutputOptions(FFmpegPerFileMainOptions perFileMainOptions, FFmpegVideoOptions videoOptions, FFmpegAudioOptions audioOptions, FFmpegEncoderOptions encoderOptions, FFmpegAVCodecContextAVOptions avCodecContextAVOptions, FFmpegMpegtsMuxerOptions mpegtsMuxerOptions) {
        this.perFileMainOptions = perFileMainOptions;
        this.videoOptions = videoOptions;
        this.audioOptions = audioOptions;
        this.encoderOptions = encoderOptions;
        this.avCodecContextAVOptions = avCodecContextAVOptions;
        this.mpegtsMuxerOptions = mpegtsMuxerOptions;
    }
}
