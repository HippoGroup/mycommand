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
import com.hippocp.mycommand.extension.ffmpeg.option.encoder.FFmpegLibX264EncoderOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.global.FFmpegGlobalOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.muxer.FFmpegMpegtsMuxerOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.pre.file.FFmpegPerFileMainOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.video.FFmpegAdvancedVideoOptions;
import com.hippocp.mycommand.extension.ffmpeg.option.video.FFmpegVideoOptions;
import com.hippocp.mycommand.extension.ffmpeg.util.TranscodeComputeUtil;
import com.hippocp.mycommand.session.FFmpegLogOutPutAndErrorStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ffmpeg 命令构建测试，提示也是转码测试
 *
 * @author ZhouYifan
 * @date 2022/1/1 22:50
 */
@Slf4j
public class FFmpegBuilderTest {

    //    String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\djczj01.mp4";
    String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\bbmdzhpbxsn01.mp4";
    //    String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\kjyslkzsnwj01.mpg";
    //    String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\江苏\\kxcrlmzjxbqyj01.mp4";
    String globalPath = "F:\\ffmpeg-test\\output\\测试视频\\";
    String fileName = "\\测试31.ts";
//    String fileName = "\\测试mpg解码.ts";

    public String fileOutputPath() {
        // 生成时间戳
        String timeStr = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        // 生成随机字符串
        String randomStr = RandomUtil.randomNumbers(6);

        return "F:/transcode/" + timeStr + randomStr + "/";
    }

    @Test
    public void guangXiYouXianTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\djczj01.mp4";
        String outputFilePath = globalPath + "广西有线" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD1080)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.MP2),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB7000)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB192),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB7000)
                                .minRate(VideoBitrateEnum.VB7000)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB7000)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB7000))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();
        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void shanDongDianXinTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\djczj01.mp4";
        String outputFilePath = globalPath + "山东电信" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD1080)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.MP2),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB8000)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB224),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB8000)
                                .minRate(VideoBitrateEnum.VB8000)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB8000)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB8000))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void jiangSuYouXianTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\江苏\\bbmdzhphymx01.mp4";
        String outputFilePath = globalPath + "江苏有线" + fileName;
        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD1080)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
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

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void tianJinYouXianTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\djczj01.mp4";
        String outputFilePath = globalPath + "天津有线" + fileName;
        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD1080)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.MP2),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB7800)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
//                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB192),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB7800)
                                .minRate(VideoBitrateEnum.VB7800)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB7800)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB7800))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void jiangSuYiDongTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\江苏\\sbxfd01.mp4";
        String outputFilePath = globalPath + "江苏移动" + fileName;
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\江苏\\bbmdzhphymx01.mp4";
//        String outputFilePath = "F:/ffmpeg-test/output/测试视频/江苏移动/bbmdzhphymx01.ts";

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .overwrite()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD1080)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.AAC),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB4000)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB192),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB4000)
                                .minRate(VideoBitrateEnum.VB4000)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB4000)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB4000))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);
    }

    @Test
    public void anHuiYiDongTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\安徽移动\\bcldmx01.mp4";
        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\安徽移动\\zyyx01.mp4";
//        String outputFilePath = "F:/ffmpeg-test/output/测试视频/安徽移动/bcldmx01.ts";
        String outputFilePath = "F:/ffmpeg-test/output/测试视频/安徽移动/zyyx01.ts";

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        new FFmpegAdvancedVideoOptions()
                                .decoderHardware(DecoderHardwareEnum.CUDA_DECODER_HARDWARE)
                                .decoderHardwareOutputFormat(DecoderHardwareOutputFormatEnum.CUDA_DECODER_HARDWARE_OUTPUT_FORMAT),
                        new FFmpegH264CuvidDecoderOptions()
                                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                                .resize(VideoResolutionEnum.HD1080)
                                .dropSecondField(true));

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.AAC),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB4000)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB192),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB4000)
                                .minRate(VideoBitrateEnum.VB4000)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB4000)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB4000))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);
    }

    @Test
    public void jiangSuYouXianJiaoYuTest() {
        String outputFilePath = globalPath + "江苏有线学霸宝盒" + fileName;
        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD1080)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.AAC),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB3100)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB192),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB3100)
                                .minRate(VideoBitrateEnum.VB3100)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB3100)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB3100))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);
    }

    @Test
    public void fuJianYiDongJiaoYuTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\福建移动教育\\ywydyxz1njc01.ts";
        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\福建移动教育\\ywydyxz1njh01.ts";
//        String outputFilePath = "F:/ffmpeg-test/output/测试视频/福建移动教育/ywydyxz1njc01.ts";
        String outputFilePath = "F:/ffmpeg-test/output/测试视频/福建移动教育/ywydyxz1njh01.ts";

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD1080)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.AAC),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB3100)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB192),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB3100)
                                .minRate(VideoBitrateEnum.VB3100)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB3100)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB3100))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);
    }

    @Test
    public void heBeiTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\djczj01.mp4";
        String outputFilePath = globalPath + "河北" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD1080)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.AAC),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB8000)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
//                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB64),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB8000)
                                .minRate(VideoBitrateEnum.VB8000)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB8000)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB8000))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void guangDongYiDongJiaoYuTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\djczj01.mp4";
        String outputFilePath = globalPath + "广东移动" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD1080)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.AAC),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB8000)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB192),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB8000)
                                .minRate(VideoBitrateEnum.VB8000)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB8000)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB8000))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void siChuanDianXinTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\djczj01.mp4";
        String outputFilePath = globalPath + "四川电信" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.AAC),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB9000)
//                                .videoFrameRate(VideoFrameRateEnum.FPS25)
//                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB256),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.VARIABLE_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB12000)
                                .minRate(VideoBitrateEnum.VB6000)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB9000)),
                        null
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void heNanTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\djczj01.mp4";
        String outputFilePath = globalPath + "河南" + fileName;
        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD1080)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.MP2),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB8000)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB96),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB8000)
                                .minRate(VideoBitrateEnum.VB8000)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB8000)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB8000))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void tianJinXinMeiTiTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\djczj01.mp4";
        String outputFilePath = globalPath + "天津新媒体" + fileName;
        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD1080)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        new FFmpegAdvancedVideoOptions()
                                .hwaccelDevice(0)
                                .decoderHardware(DecoderHardwareEnum.CUDA_DECODER_HARDWARE)
                                .decoderHardwareOutputFormat(DecoderHardwareOutputFormatEnum.CUDA_DECODER_HARDWARE_OUTPUT_FORMAT),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.AAC),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB8000)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB128),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE)
                                .profile(H264NvencVideoProfileEnum.HIGH)
                                .level(H264VideoLevelEnum.L4)
                                .lookahead(15)
                                .dpbSize(3),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB8000)
                                .minRate(VideoBitrateEnum.VB8000)
                                .gop(25)
                                .maxBFrame(3)
                                .flags("+cgop")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB8000)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(VideoBitrateEnum.VB9000)
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);
    }

    @Test
    public void fuJianGuangDianTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\福建广电\\asj01.mp4";
        String outputFilePath = globalPath + "福建广电" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions = new FFmpegInputOptions();

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.LIBX264)
                                .audioEncoder(AudioEncoderEnum.MP2),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB7000)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB192)
                                .videoFilter(VideoFilterEnum.HD1080_INTERLEAVE_TOP),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegLibX264EncoderOptions()
                                .videoBitrateModel(LibX264VideoBitrateModelEnum.CONSTANT_RATE)
                                .x264opts(X264OptsEnum.TFF),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB7000)
                                .minRate(VideoBitrateEnum.VB7000)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB7000)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB7000))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void zheJiangDianXinTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\浙江电信\\djczj01.mp4";
        String outputFilePath = globalPath + "浙江电信" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD720)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.MP2),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB6000)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB224),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB6000)
                                .minRate(VideoBitrateEnum.VB6000)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB6000)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB6000))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void networkStreamTranscodingTest() {
        String inputFilePath =
                "https://njxjxhb.oss-cn-hangzhou.aliyuncs.com/nanchuaginput/%E8%B6%85%E8%83%BD%E7%8E%A9%E5%85%B7%E7%99%BD%E7%99%BD%E4%BE%A0%E7%AC%AC%E5%85%AD%E5%AD%A3/cnwjbbxdlj219.mp4";
        String outputFilePath =
                "F:\\ffmpeg-test\\output\\流转码\\测试10.ts";
        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD1080)
                .dropSecondField(true);

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        cuvidDecoderOptions);

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.MP2),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB7800)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
//                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB192),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB7800)
                                .minRate(VideoBitrateEnum.VB7800)
                                .gop("25")
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB7800)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB7800))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void anHuiDianXinTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\安徽移动\\bcldmx01.mp4";
        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\安徽移动\\zyyx01.mp4";
//        String outputFilePath = "F:/ffmpeg-test/output/测试视频/安徽移动/bcldmx01.ts";
        String outputFilePath = "F:/ffmpeg-test/output/测试视频/安徽电信/zyyx01.ts";

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        new FFmpegAdvancedVideoOptions()
                                .decoderHardware(DecoderHardwareEnum.CUDA_DECODER_HARDWARE)
                                .decoderHardwareOutputFormat(DecoderHardwareOutputFormatEnum.CUDA_DECODER_HARDWARE_OUTPUT_FORMAT),
                        new FFmpegH264CuvidDecoderOptions()
                                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                                .resize(VideoResolutionEnum.HD1080)
                                .dropSecondField(true));

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.MP2),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB8000)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB192),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB8000)
                                .minRate(VideoBitrateEnum.VB8000)
                                .gop(32)
                                .maxBFrame(3)
                                .flags(FFmpegAVCodecContextAVOptions.FlagsArgumentValue.CLOSE_GOP)
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB8000)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB8000))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeAsync(0, 24 * 3600, stream, null);
    }

    @Test
    public void noSuchFileOrDirectoryBugTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\安徽移动\\bcldmx01.mp4";
        String inputFilePath = "F:\\ffmpeg-test\\test\\测试视频\\（0-12个月） 宝宝喂养很重要\\zyyx01.mp4";
//        String outputFilePath = "F:/ffmpeg-test/output/测试视频/安徽移动/bcldmx01.ts";
        String outputFilePath = "F:/ffmpeg-test/output/测试视频/不是文件或路径问题/zyyx01.ts";

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // 多个 FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        // 输入选项和输入文件路径
        // 输入文件选项
        FFmpegInputOptions inputOptions =
                new FFmpegInputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoDecoder(VideoDecoderEnum.H264_CUVID),
                        new FFmpegAdvancedVideoOptions()
                                .decoderHardware(DecoderHardwareEnum.CUDA_DECODER_HARDWARE)
                                .decoderHardwareOutputFormat(DecoderHardwareOutputFormatEnum.CUDA_DECODER_HARDWARE_OUTPUT_FORMAT),
                        new FFmpegH264CuvidDecoderOptions()
                                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                                .resize(VideoResolutionEnum.HD1080)
                                .dropSecondField(true));

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // 多个 FFmpegOutputOptionsAndFile
        ArrayList<FFmpegOutputOptionsAndFile> outputOptionsAndFileList = new ArrayList<>();

        FFmpegOutputOptions outputOptions =
                new FFmpegOutputOptions(
                        new FFmpegPerFileMainOptions()
                                .videoEncoder(VideoEncoderEnum.H264_NVENC)
                                .audioEncoder(AudioEncoderEnum.MP2),
                        new FFmpegVideoOptions()
                                .videoVagBitrate(VideoBitrateEnum.VB8000)
                                .videoFrameRate(VideoFrameRateEnum.FPS25)
                                .videoAspectRatio(VideoAspectRatioEnum.SIXTEEN_TO_NINE)
                                .audioBitrate(AudioBitrateEnum.AB192),
                        new FFmpegAudioOptions()
                                .audioSampleRate(AudioSampleRateEnum.ASR48000)
                                .audioChannel(AudioChannelEnum.STEREO_CHANNEL),
                        new FFmpegH264NvencEncoderOptions()
                                .videoBitrateModel(H264NvencVideoBitrateModelEnum.CONSTANT_RATE),
                        new FFmpegAVCodecContextAVOptions()
                                .maxRate(VideoBitrateEnum.VB8000)
                                .minRate(VideoBitrateEnum.VB8000)
                                .gop(32)
                                .maxBFrame(3)
                                .flags(FFmpegAVCodecContextAVOptions.FlagsArgumentValue.CLOSE_GOP)
                                .bufSize(TranscodeComputeUtil.computeBufSize(VideoBitrateEnum.VB8000)),
                        new FFmpegMpegtsMuxerOptions()
                                .muxRate(TranscodeComputeUtil.computeMuxRate(VideoBitrateEnum.VB8000))
                );

        FFmpegOutputOptionsAndFile outputOptionsAndFile =
                new FFmpegOutputOptionsAndFile(outputOptions, outputFilePath);

        outputOptionsAndFileList.add(outputOptionsAndFile);

        FFmpegCommand command =
                new FFmpegCommand(options, inputOptionsAndFileList, outputOptionsAndFileList);

        String fileOutputPath = fileOutputPath();

        // 创建文件
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // 写入文件
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);
    }

}
