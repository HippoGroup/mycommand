package com.hippocp.mycommand.session;

import cn.hutool.core.io.FileUtil;
import com.hippocp.mycommand.builder.TranscodeMapper;
import com.hippocp.mycommand.builder.xml.XMLMapperBuilder;
import com.hippocp.mycommand.executor.SimpleCommandExecutor;
import com.hippocp.mycommand.io.Resources;
import com.hippocp.mycommand.template.TranscodingParam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ZhouYifan
 * @date 2022/3/11
 */
public class CommandSessionTest {

    protected TranscodeMapper init() throws IOException {
        Configuration configuration = new Configuration();
        String resource = "command-mapper/TranscodeMapper.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
            XMLMapperBuilder builder = new XMLMapperBuilder(inputStream, configuration, resource, configuration.getSqlFragments());
            builder.parse();
        }
        SimpleCommandExecutor executor = new SimpleCommandExecutor();
        DefaultCommandSession session = new DefaultCommandSession(configuration, executor);
        return session.getMapper(TranscodeMapper.class);
    }

    @Test
    public void asyncHardwareTranscodingTest() throws Exception {
        TranscodeMapper mapper = init();
        TranscodingParam param = new TranscodingParam();
        param.setOverwrite("1");
        param.setInputPath("F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\bbmdzhpbxsn01.mp4");
        param.setOutputPath("F:\\ffmpeg-test\\output\\测试视频\\MyCommand异步执行测试01.ts");
        mapper.asyncHardwareTranscoding(param);
    }

    @Test
    public void syncHardwareTranscodingTest() throws Exception {
        TranscodeMapper mapper = init();
        TranscodingParam param = new TranscodingParam();
        param.setOverwrite("1");
        param.setInputPath("F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\bbmdzhpbxsn01.mp4");
        param.setOutputPath("F:\\ffmpeg-test\\output\\测试视频\\MyCommand同步执行测试01.ts");
        int exitValue = mapper.syncHardwareTranscoding(param);
        Assertions.assertEquals(0, exitValue);
    }

    @Test
    public void syncHardwareTranscodingReturnStreamTest() throws Exception {
        TranscodeMapper mapper = init();
        TranscodingParam param = new TranscodingParam();
        param.setOverwrite("1");
        param.setInputPath("F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\bbmdzhpbxsn01.mp4");
        param.setOutputPath("F:\\ffmpeg-test\\output\\测试视频\\MyCommand同步执行测试02.ts");
        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch("F:/transcode/" + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream logOutPutAndErrorStream = new FFmpegLogOutPutAndErrorStream(logFile);
        mapper.syncHardwareTranscodingReturnStream(param, logOutPutAndErrorStream);
    }

    @Test
    public void asyncHardwareTranscodingReturnStreamTest() throws Exception {
        TranscodeMapper mapper = init();
        TranscodingParam param = new TranscodingParam();
        param.setOverwrite("1");
        param.setInputPath("F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\bbmdzhpbxsn01.mp4");
        param.setOutputPath("F:\\ffmpeg-test\\output\\测试视频\\MyCommand异步执行测试02.ts");
        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch("F:/transcode/" + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream logOutPutAndErrorStream = new FFmpegLogOutPutAndErrorStream(logFile);
        mapper.asyncHardwareTranscodingReturnStream(param, logOutPutAndErrorStream);
    }


}
