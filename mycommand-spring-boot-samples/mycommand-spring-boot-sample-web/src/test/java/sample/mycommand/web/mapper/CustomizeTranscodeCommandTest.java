package sample.mycommand.web.mapper;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author ZhouYifan
 * @date 2022/3/15
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomizeTranscodeCommandTest {

    @Resource
    private CustomizeTranscodeCommand command;

    @Test
    public void loopExecute() {
        String inputPrefix = "F:\\ffmpeg-test\\test\\测试视频\\安徽电信\\3-4岁无基础：小天鹅启蒙课\\";
        String outputPrefix = "F:\\ffmpeg-test\\output\\测试视频\\安徽电信\\3-4岁无基础：小天鹅启蒙课\\";
        for (int i = 1; i <= 10; i++) {
//        for (int i = 1; i <= 1; i++) {
            String fileName = "wjcxteqmk";
            if (i < 10) {
                fileName = fileName + "0" + i;
            } else {
                fileName = fileName + i;
            }

            String input = inputPrefix + fileName + ".mp4";
            String path = outputPrefix + fileName;
            FileUtil.mkdir(path);
            String commandSegment = "ffmpeg -v info -n -c:v h264_cuvid -hwaccel cuda -hwaccel_output_format cuda -deint adaptive -resize 1920x1080 -drop_second_field true -i #{input} -c:v h264_nvenc -c:a mp2 -b:v 8000k -r 25 -aspect 16:9 -b:a 192k -ar 48000 -ac 2 -rc cbr -maxrate 8000k -minrate 8000k -g 32 -bf 3 -flags +cgop -bufsize 1600k -muxrate 8804k -hls_time 2 -hls_list_size 0 -hls_segment_filename ";
            String output = path + "\\" + fileName + ".m3u8";
            String command = commandSegment + "\"" + outputPrefix + fileName + "\\" + fileName + "_%%03d.ts" + "\" " + " #{output}";
            asyncCustomizeTranscodeTest(input, output, command);
            ZipUtil.zip(path, path + ".zip", true);
        }

    }

    void asyncCustomizeTranscodeTest(String inputPath, String outputPath, String command) {
        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch("F:/transcode/" + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);
        this.command.asyncCustomizeTranscode(inputPath, outputPath, command, stream);
        DefaultExecuteResultHandler resultHandler = stream.getDefaultExecuteResultHandler();
        log.info("命令执行中......");
        while (!resultHandler.hasResult()) {

        }
    }

    @Test
    public void asyncCustomizeTranscodeTest() {

        String inputPath = "F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\djczj01.mp4";
        String outputPath = "F:\\ffmpeg-test\\output\\测试视频\\MyCommand自定义转码命令测试01.ts";
        String command = "ffmpeg -v info -y -hwaccel_device 0 -hwaccel cuda -hwaccel_output_format cuda -c:v h264_cuvid -deint adaptive -resize 1920x1080 -drop_second_field true -i #{input} -c:v h264_nvenc -c:a mp2 -b:v 8000k -r 25 -aspect 16:9 -b:a 224k -ar 48000 -ac 2 -rc cbr -maxrate 8000k -minrate 8000k -g 25 -bufsize 1600k -muxrate 8804k #{output}";

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch("F:/transcode/" + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);
        this.command.asyncCustomizeTranscode(inputPath, outputPath, command, stream);
        DefaultExecuteResultHandler resultHandler = stream.getDefaultExecuteResultHandler();
        log.info("命令执行中......");
        while (!resultHandler.hasResult()) {

        }
    }

}
