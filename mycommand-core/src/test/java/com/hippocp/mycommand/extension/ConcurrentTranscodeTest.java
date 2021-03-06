package com.hippocp.mycommand.extension;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.FFmpegLogLevelEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.audio.AudioBitrateEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.audio.AudioChannelEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.audio.AudioEncoderEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.audio.AudioSampleRateEnum;
import com.hippocp.mycommand.extension.ffmpeg.argument.value.video.*;
import com.hippocp.mycommand.extension.ffmpeg.command.FFmpegCommand;
import com.hippocp.mycommand.extension.ffmpeg.option.FFmpegInputOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.FFmpegOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.FFmpegOutputOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.audio.FFmpegAudioOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.av.FFmpegAVCodecContextAVOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.composition.FFmpegInputOptionsAndFile;
import com.hippocp.mycommand.extension.ffmpeg.option.composition.FFmpegOutputOptionsAndFile;
import com.hippocp.mycommand.extension.ffmpeg.option.decoder.FFmpegH264CuvidDecoderOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.encoder.FFmpegH264NvencEncoderOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.global.FFmpegGlobalOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.muxer.FFmpegMpegtsMuxerOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.pre.file.FFmpegPerFileMainOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.video.FFmpegVideoOptions;
import com.hippocp.mycommand.extension.ffmpeg.util.TranscodeComputeUtil;
import com.hippocp.mycommand.session.FFmpegLogOutPutAndErrorStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ??????????????????
 *
 * @author ZhouYifan
 * @date 2022/2/14
 */
@Slf4j
public class ConcurrentTranscodeTest {

    public String logFileOutputPath() {
        // ???????????????
        String timeStr = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        // ?????????????????????
        String randomStr = RandomUtil.randomNumbers(6);
        String fileOutputPath = "F:\\ffmpeg-test\\ConcurrentTranscode\\log\\" + timeStr + randomStr + "\\";
        return fileOutputPath;
    }

    String inputFilePathPrefix = "F:\\ffmpeg-test\\test\\????????????\\";
    String inputFilePath01 = inputFilePathPrefix + "??????\\kxcrlmzjxbqyj01.mp4";
    String inputFilePath02 = inputFilePathPrefix + "??????\\bbmdzhphymx01.mp4";
    String inputFilePath03 = inputFilePathPrefix + "??????\\sbxfd01.mp4";
    String inputFilePath04 = inputFilePathPrefix + "????????????\\asj01.mp4";
    String inputFilePath05 = inputFilePathPrefix + "????????????\\RunFoxhlkpd3j01.mp4";
    String inputFilePath06 = inputFilePathPrefix + "????????????\\bbmdzhpbxsn01.mp4";
    String[] inputFilePaths = new String[]{inputFilePath01, inputFilePath02, inputFilePath03,
            inputFilePath04, inputFilePath05, inputFilePath06};

    String outputFilePathPrefix = "F:\\ffmpeg-test\\ConcurrentTranscode\\";
    String outputFilePath01 = outputFilePathPrefix + "kxcrlmzjxbqyj01.ts";
    String outputFilePath02 = outputFilePathPrefix + "bbmdzhphymx01.ts";
    String outputFilePath03 = outputFilePathPrefix + "sbxfd01.ts";
    String outputFilePath04 = outputFilePathPrefix + "asj01.ts";
    String outputFilePath05 = outputFilePathPrefix + "RunFoxhlkpd3j01.ts";
    String outputFilePath06 = outputFilePathPrefix + "bbmdzhpbxsn01.ts";
    String[] outputFilePaths = new String[]{outputFilePath01, outputFilePath02, outputFilePath03,
            outputFilePath04, outputFilePath05, outputFilePath06};

    @Test
    public void test() {

        for (int i = 0; i < inputFilePaths.length; i++) {
            try {
                transcode(inputFilePaths[i], outputFilePaths[i]);

                int times = i + 1;
                log.info("???" + times + "??????????????????");

                Thread.sleep(2000);
            } catch (IOException e) {
                log.error("????????????", e);
            } catch (InterruptedException e) {
                log.error("??????????????????", e);
            }
        }

    }

    public void transcode(String inputFilePath, String outputFilePath) throws IOException {
        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .overwrite()
        );

        // ?????? FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD1080)
                .dropSecondField(true);

        // ?????????????????????????????????
        // ??????????????????
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // ?????? FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.AAC),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB2720)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB192),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB2720)
                                .minRate(VideoBitrateEnum.VB2720)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB2720)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB2720))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String logFileOutputPath = logFileOutputPath();
        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(logFileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeAsync(0, 24 * 3600, stream, null);
    }

}
