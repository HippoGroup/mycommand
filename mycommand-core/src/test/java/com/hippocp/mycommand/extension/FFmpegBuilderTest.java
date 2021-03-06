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
 * ffmpeg ?????????????????????????????????????????????
 *
 * @author ZhouYifan
 * @date 2022/1/1 22:50
 */
@Slf4j
public class FFmpegBuilderTest {

    //    String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\djczj01.mp4";
    String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\bbmdzhpbxsn01.mp4";
    //    String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\kjyslkzsnwj01.mpg";
    //    String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\??????\\kxcrlmzjxbqyj01.mp4";
    String globalPath = "F:\\ffmpeg-test\\output\\????????????\\";
    String fileName = "\\??????31.ts";
//    String fileName = "\\??????mpg??????.ts";

    public String fileOutputPath() {
        // ???????????????
        String timeStr = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
        // ?????????????????????
        String randomStr = RandomUtil.randomNumbers(6);

        return "F:/transcode/" + timeStr + randomStr + "/";
    }

    @Test
    public void guangXiYouXianTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\djczj01.mp4";
        String outputFilePath = globalPath + "????????????" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
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
        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void shanDongDianXinTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\djczj01.mp4";
        String outputFilePath = globalPath + "????????????" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void jiangSuYouXianTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\??????\\bbmdzhphymx01.mp4";
        String outputFilePath = globalPath + "????????????" + fileName;
        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
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

        String fileOutputPath = fileOutputPath();

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void tianJinYouXianTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\djczj01.mp4";
        String outputFilePath = globalPath + "????????????" + fileName;
        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void jiangSuYiDongTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\??????\\sbxfd01.mp4";
        String outputFilePath = globalPath + "????????????" + fileName;
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\??????\\bbmdzhphymx01.mp4";
//        String outputFilePath = "F:/ffmpeg-test/output/????????????/????????????/bbmdzhphymx01.ts";

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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);
    }

    @Test
    public void anHuiYiDongTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\bcldmx01.mp4";
        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\zyyx01.mp4";
//        String outputFilePath = "F:/ffmpeg-test/output/????????????/????????????/bcldmx01.ts";
        String outputFilePath = "F:/ffmpeg-test/output/????????????/????????????/zyyx01.ts";

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // ?????? FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        // ?????????????????????????????????
        // ??????????????????
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

        // ?????? FFmpegOutputOptionsAndFile
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);
    }

    @Test
    public void jiangSuYouXianJiaoYuTest() {
        String outputFilePath = globalPath + "????????????????????????" + fileName;
        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);
    }

    @Test
    public void fuJianYiDongJiaoYuTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\??????????????????\\ywydyxz1njc01.ts";
        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\??????????????????\\ywydyxz1njh01.ts";
//        String outputFilePath = "F:/ffmpeg-test/output/????????????/??????????????????/ywydyxz1njc01.ts";
        String outputFilePath = "F:/ffmpeg-test/output/????????????/??????????????????/ywydyxz1njh01.ts";

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);
    }

    @Test
    public void heBeiTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\djczj01.mp4";
        String outputFilePath = globalPath + "??????" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void guangDongYiDongJiaoYuTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\djczj01.mp4";
        String outputFilePath = globalPath + "????????????" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void siChuanDianXinTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\djczj01.mp4";
        String outputFilePath = globalPath + "????????????" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // ?????? FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void heNanTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\djczj01.mp4";
        String outputFilePath = globalPath + "??????" + fileName;
        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void tianJinXinMeiTiTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\djczj01.mp4";
        String outputFilePath = globalPath + "???????????????" + fileName;
        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
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
                        new FFmpegAdvancedVideoOptions()
                                .hwaccelDevice(0)
                                .decoderHardware(DecoderHardwareEnum.CUDA_DECODER_HARDWARE)
                                .decoderHardwareOutputFormat(DecoderHardwareOutputFormatEnum.CUDA_DECODER_HARDWARE_OUTPUT_FORMAT),
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);
    }

    @Test
    public void fuJianGuangDianTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\asj01.mp4";
        String outputFilePath = globalPath + "????????????" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // ?????? FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        // ?????????????????????????????????
        // ??????????????????
        FFmpegInputOptions inputOptions = new FFmpegInputOptions();

        FFmpegInputOptionsAndFile inputOptionsAndFile =
                new FFmpegInputOptionsAndFile(inputOptions, inputFilePath);

        inputOptionsAndFileList.add(inputOptionsAndFile);

        // ?????? FFmpegOutputOptionsAndFile
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void zheJiangDianXinTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\djczj01.mp4";
        String outputFilePath = globalPath + "????????????" + fileName;

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // ?????? FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        FFmpegH264CuvidDecoderOptions cuvidDecoderOptions = new FFmpegH264CuvidDecoderOptions()
                .deinterlace(H264CuvidDeinterlaceEnum.ADAPTIVE)
                .resize(VideoResolutionEnum.HD720)
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void networkStreamTranscodingTest() {
        String inputFilePath =
                "https://njxjxhb.oss-cn-hangzhou.aliyuncs.com/nanchuaginput/%E8%B6%85%E8%83%BD%E7%8E%A9%E5%85%B7%E7%99%BD%E7%99%BD%E4%BE%A0%E7%AC%AC%E5%85%AD%E5%AD%A3/cnwjbbxdlj219.mp4";
        String outputFilePath =
                "F:\\ffmpeg-test\\output\\?????????\\??????10.ts";
        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);

    }

    @Test
    public void anHuiDianXinTranscodingTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\bcldmx01.mp4";
        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\zyyx01.mp4";
//        String outputFilePath = "F:/ffmpeg-test/output/????????????/????????????/bcldmx01.ts";
        String outputFilePath = "F:/ffmpeg-test/output/????????????/????????????/zyyx01.ts";

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // ?????? FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        // ?????????????????????????????????
        // ??????????????????
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

        // ?????? FFmpegOutputOptionsAndFile
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeAsync(0, 24 * 3600, stream, null);
    }

    @Test
    public void noSuchFileOrDirectoryBugTest() {
//        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\????????????\\bcldmx01.mp4";
        String inputFilePath = "F:\\ffmpeg-test\\test\\????????????\\???0-12????????? ?????????????????????\\zyyx01.mp4";
//        String outputFilePath = "F:/ffmpeg-test/output/????????????/????????????/bcldmx01.ts";
        String outputFilePath = "F:/ffmpeg-test/output/????????????/???????????????????????????/zyyx01.ts";

        // FFmpegOptions
        FFmpegOptions options = new FFmpegOptions(
                new FFmpegGlobalOptions()
                        .logLevel(FFmpegLogLevelEnum.INFO)
                        .notOverwriteAndQuit()
        );

        // ?????? FFmpegInputOptionsAndFile
        List<FFmpegInputOptionsAndFile> inputOptionsAndFileList = new ArrayList<>();

        // ?????????????????????????????????
        // ??????????????????
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

        // ?????? FFmpegOutputOptionsAndFile
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

        // ????????????
        final String logFileName = "log.txt";
        File logFile = FileUtil.touch(fileOutputPath + logFileName);

        // ????????????
        FFmpegLogOutPutAndErrorStream stream = new FFmpegLogOutPutAndErrorStream(logFile);

        command.executeSync(0, 24 * 3600, stream, null);
    }

}
