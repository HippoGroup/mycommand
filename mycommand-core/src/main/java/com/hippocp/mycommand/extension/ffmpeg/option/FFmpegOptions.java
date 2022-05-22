package com.hippocp.mycommand.extension.ffmpeg.option;

import com.hippocp.mycommand.extension.command.AbstractCommandSegment;
import com.hippocp.mycommand.extension.ffmpeg.option.global.FFmpegAdvancedGlobalOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.global.FFmpegGlobalOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffmpeg命令 [options] 选项
 *
 * @author ZhouYifan
 * @date 2022/1/24 9:34
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class FFmpegOptions extends AbstractCommandSegment<FFmpegOptions> {

    private static final Logger log = LoggerFactory.getLogger(FFmpegOptions.class);

    private FFmpegGlobalOptions globalOptions;

    private FFmpegAdvancedGlobalOptions advancedGlobalOptions;

    public FFmpegOptions(FFmpegGlobalOptions globalOptions) {
        this.globalOptions = globalOptions;
    }

    public FFmpegOptions(FFmpegAdvancedGlobalOptions advancedGlobalOptions) {
        this.advancedGlobalOptions = advancedGlobalOptions;
    }

    /**
     * 组装被聚合的各个options，将各个options中的命令片段放入，该类 commandSegments 属性中
     */
//    public void assemble() {
//        if (globalOptions != null) {
//            List<String> globalOptionsCommandSegments = globalOptions.getCommandSegments();
//            this.commandSegments.addAll(globalOptionsCommandSegments);
//
//        }
//
//        if (advancedGlobalOptions != null) {
//            List<String> advancedGlobalOptionsCommandSegments = advancedGlobalOptions.getCommandSegments();
//            this.commandSegments.addAll(advancedGlobalOptionsCommandSegments);
//        }
//    }

}
