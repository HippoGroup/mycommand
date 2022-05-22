package sample.mycommand.web.mapper;

import cn.hutool.core.io.FileUtil;
import com.hippocp.mycommand.customize.CommandFileCustomize;
import com.hippocp.mycommand.io.FileUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sample.mycommand.web.domain.TranscodingParam;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author ZhouYifan
 * @date 2022/3/15
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TranscodingCommandMapperTest {

    @Resource
    private TranscodingCommandMapper mapper;

    @Test
    public void asyncHardwareTranscodingReturnStreamTest() {
        TranscodingParam param = new TranscodingParam();
        param.setOverwrite("1");
        param.setInputPath("F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\bbmdzhpbxsn01.mp4");
        param.setOutputPath("F:\\ffmpeg-test\\output\\测试视频\\MyCommand异步执行测试02.ts");
        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch("F:/transcode/" + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);
        mapper.asyncHardwareTranscodingReturnStream(param, stream);
        DefaultExecuteResultHandler resultHandler = stream.getDefaultExecuteResultHandler();
        log.info("命令执行中......");
        while (!resultHandler.hasResult()) {

        }
    }

    @Test
    public void hardwareTranscodingAsync() {
        TranscodingParam param = new TranscodingParam();
        param.setOverwrite("1");
        param.setInputPath("K:\\ffmpeg-test\\test\\测试视频\\179.mp4");
        param.setOutputPath("K:\\ffmpeg-test\\output\\测试视频\\MyCommand异步执行测试06.ts");
        // 创建文件
        final String logFileName = "log-06.txt";
        File logFile = FileUtil.touch("K:/transcode/" + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);
        mapper.hardwareTranscodingAsync(param, stream, new CommandFileCustomize() {
            @Override
            public File createCommandFile() {
                String filePath = FileUtility.joinFilePath("K:/transcode/", "command-06.bat");
                return FileUtil.touch(filePath);
            }
        });
        DefaultExecuteResultHandler resultHandler = stream.getDefaultExecuteResultHandler();
        log.info("命令执行中......");
        while (!resultHandler.hasResult()) {

        }
    }

}
