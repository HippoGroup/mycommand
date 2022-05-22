package com.hippocp.mycommand.extension.ffmpeg.option;

import com.hippocp.mycommand.extension.command.AbstractCommandSegment;
import com.hippocp.mycommand.extension.ffmpeg.option.decoder.FFmpegDecoderOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.pre.file.FFmpegPerFileMainOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.video.FFmpegAdvancedVideoOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffmpeg命令 [infile options] 输入文件选项
 *
 * @author ZhouYifan
 * @date 2022/1/22 11:09
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class FFmpegInputOptions extends AbstractCommandSegment<FFmpegInputOptions> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegInputOptions.class);

    private FFmpegPerFileMainOptions perFileMainOptions;

    private FFmpegAdvancedVideoOptions advancedVideoOptions;

    private FFmpegDecoderOptions decoderOptions;

    public FFmpegInputOptions(FFmpegPerFileMainOptions perFileMainOptions, FFmpegDecoderOptions decoderOptions) {
        this.perFileMainOptions = perFileMainOptions;
        this.decoderOptions = decoderOptions;
    }

    public FFmpegInputOptions() {
    }

    /**
     * 组装被聚合的各个options，将各个options中的命令片段放入，该类 commandSegments 属性中
     */
//    public void assemble() {
//        // 各个options中的List不可能为null，因为父类 AbstractCommandSegment 中 commandSegments属性有初值 new ArrayList<>()
//        // 合并至该类 commandSegments 属性中
//        if (perFileMainOptions != null) {
//            List<String> perFileMainOptionsCommandSegments = perFileMainOptions.getCommandSegments();
//            this.commandSegments.addAll(perFileMainOptionsCommandSegments);
//        }
//
//        if (advancedVideoOptions != null) {
//            List<String> advancedVideoOptionsCommandSegments = advancedVideoOptions.getCommandSegments();
//            this.commandSegments.addAll(advancedVideoOptionsCommandSegments);
//        }
//
//        if (h264CuvidDecoderOptions != null) {
//            List<String> h264CuvidDecoderOptionsCommandSegments = h264CuvidDecoderOptions.getCommandSegments();
//            this.commandSegments.addAll(h264CuvidDecoderOptionsCommandSegments);
//        }
//
//    }


}
