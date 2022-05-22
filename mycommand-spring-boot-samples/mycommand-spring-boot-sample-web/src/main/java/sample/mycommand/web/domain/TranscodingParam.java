package sample.mycommand.web.domain;

import java.io.Serializable;
import java.util.List;


/**
 * 转码参数
 */

public class TranscodingParam implements Serializable {
    /**
     * 输入路径<br>
     * 绝对路径： https:    nas://
     */
    private String inputPath;

    /**
     * 输出路径<br>
     * 绝对路径带上文件名 nas://192.
     */
    private String outputPath;

    /**
     * ffmpeg日志级别
     */
    protected String loglevel;

    /**
     * 如有同名文件是否覆盖输出
     */
    protected String overwrite;

    protected String pcmSign;

    protected String pcmSampleSize;

    protected String pcmByteOrdering;

    protected String commandParam;

    protected String clipStartTime;

    protected String clipEndTime;

    protected String outputFolder;

    /**
     * 视频编码格式 例如：h264 h265
     */
    protected String videoEncodeFormat;

    /**
     * "video", "audio"
     * 输出为视频或音频
     */
    protected List<String> outputEnable;

    /**
     * 视频帧率 例如：25
     */
    protected String videoFrame;

    /**
     * 视频码率控制模式 例如：cbr vbr
     */
    protected String controlRate;

    /**
     * 视频码率
     */
    protected String videoRate;

    /**
     * 视频最小码率
     */
    protected String minRate;

    /**
     * 视频最大码率
     */
    protected String maxRate;

    /**
     * 总体码率
     */
    protected String totalRate;

    /**
     * 视频分辨率宽度 例如：1920
     */
    protected String videoWidth;

    /**
     * 视频分辨率高度 例如：1080
     */
    protected String videoHeight;

    protected String videoSize;

    /**
     * 视频宽高比 例如：16:9
     */
    protected String videoRatio;

    protected String fillingMode;

    /**
     * 是否开启硬件加速
     */
    protected String hwAccel;

    protected String pictureOptimization;

    protected String superScaleTranscodeType;

    protected String superScaleNoise;

    protected String superScaleDeinterlace;

    protected String superScaleDetailEnhancement;

    protected String autoDelBlackEdge;

    protected String autoTailoring;

    /**
     * 音频编码格式 例如：mp2 mp3 aac
     */
    protected String audioEncodeFormat;

    /**
     * 音频码率 例如：192k 224k
     */
    protected String audioRate;

    /**
     * 音频采样率 例如：48000
     */
    protected String audioSample;

    /**
     * 音频声道 例如：stereo 或者 2
     */
    protected String audioTrack;

    protected String audioGain;

    protected String audioNormalize;

    protected String aacLatmFix;

    protected Integer multiaudioCheck;

    /**
     * 封装格式 例如：ts
     */
    protected String packageFormat;

    protected String serviceName;

    protected String muxName;

    protected String pmtPid;

    protected String pcrPid;

    protected String audioPid;

    protected String videoPid;

    protected String programPid;

    protected String segment;

    /**
     * 视频档次 例如：high main
     */
    protected String profile;

    /**
     * 视频像素格式 例如：yuv420
     */
    protected String pixelFormat;

    protected String hdr;

    protected String colorPrimaries;

    protected String colorTransferCharacteristics;

    protected String colorspace;

    protected String colorRange;

    protected String chromaSampleLocation;

    /**
     * 视频级别 例如：auto 4.0 5.0
     */
    protected String level;

    /**
     * 帧扫描方式 例如：逐行扫描 隔行扫描
     */
    protected String frameScan;

    /**
     * 场选择 例如：顶场优先（奇数场优先） 底场优先（偶数场优先）
     */
    protected String fieldSelect;

    /**
     * 熵编码
     */
    protected String entropyCoding;

    protected String partitionMacroBlock;

    protected String bitDepth;

    /**
     * GOP类型 close GOP（闭合GOP） 或者 open GOP（开放GOP）
     */
    protected String gop;

    /**
     * IDR 帧之间的最小间隔。<br>
     * minimum interval between IDR-frames.
     */
    protected String minGop;

    /**
     * 图片组 (GOP) 大小。<br>
     * group of picture (GOP) size.
     */
    protected String maxGop;

    /**
     * 最大B帧数量
     */
    protected String maxBFrame;

    /**
     * 参考帧数量
     */
    protected String referenceFrames;

    protected String bFrameAsReference;

    protected String bFramWp;

    protected String pFrameWeightedPrediction;

    protected String sceneMonitoring;

    protected String motionPredictionModel;

    protected String motionSearchRange;

    protected String subpixelMotionEstimation;

    protected String motionDetectionMode;

    protected String rfConstantValue;

    protected String qpValue;

    protected String subtitleOverlayMode;

    protected String subtitleOverlayEnable;

    protected List<?> multisubtitle;

    protected List<?> multidrawtext;

    protected String drawtextOverlayEnable;

    protected String logoOverlayMode;

    protected String logoOverlayEnable;

    protected String logoLocal;

    protected String backgroundVideoWidth;

    protected String backgroundVideoHeight;

    protected String backgroundVideoX;

    protected String backgroundVideoY;

    protected List<?> multilogo;

    protected List<?> multiblur;

    protected String brightness;

    protected String ratio;

    protected String saturation;

    protected String audioDelay;

    protected String drawingFrameEnable;

    protected String drawingFrameWidth;

    protected String drawingFrameHeight;

    protected Integer drawingFrameInterval;

    protected String drawingFrameSendUrl;

    protected String customName;

    protected String denoiseEnable;

    protected String luma;

    protected String sharpEnable;

    protected String lumaX;

    protected String lumaY;

    protected String chromaX;

    protected String chromaY;

    protected String lumaAmount;

    protected String chromaAmount;

    public String getLoglevel() {
        return loglevel;
    }

    public void setLoglevel(String loglevel) {
        this.loglevel = loglevel;
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(String totalRate) {
        this.totalRate = totalRate;
    }

    public String getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(String overwrite) {
        this.overwrite = overwrite;
    }

    public String getPcmSign() {
        return pcmSign;
    }

    public void setPcmSign(String pcmSign) {
        this.pcmSign = pcmSign;
    }

    public String getPcmSampleSize() {
        return pcmSampleSize;
    }

    public void setPcmSampleSize(String pcmSampleSize) {
        this.pcmSampleSize = pcmSampleSize;
    }

    public String getPcmByteOrdering() {
        return pcmByteOrdering;
    }

    public void setPcmByteOrdering(String pcmByteOrdering) {
        this.pcmByteOrdering = pcmByteOrdering;
    }

    public String getCommandParam() {
        return commandParam;
    }

    public void setCommandParam(String commandParam) {
        this.commandParam = commandParam;
    }

    public String getClipStartTime() {
        return clipStartTime;
    }

    public void setClipStartTime(String clipStartTime) {
        this.clipStartTime = clipStartTime;
    }

    public String getClipEndTime() {
        return clipEndTime;
    }

    public void setClipEndTime(String clipEndTime) {
        this.clipEndTime = clipEndTime;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public String getVideoEncodeFormat() {
        return videoEncodeFormat;
    }

    public void setVideoEncodeFormat(String videoEncodeFormat) {
        this.videoEncodeFormat = videoEncodeFormat;
    }

    public List<String> getOutputEnable() {
        return outputEnable;
    }

    public void setOutputEnable(List<String> outputEnable) {
        this.outputEnable = outputEnable;
    }

    public String getVideoFrame() {
        return videoFrame;
    }

    public void setVideoFrame(String videoFrame) {
        this.videoFrame = videoFrame;
    }

    public String getControlRate() {
        return controlRate;
    }

    public void setControlRate(String controlRate) {
        this.controlRate = controlRate;
    }

    public String getVideoRate() {
        return videoRate;
    }

    public void setVideoRate(String videoRate) {
        this.videoRate = videoRate;
    }

    public String getMinRate() {
        return minRate;
    }

    public void setMinRate(String minRate) {
        this.minRate = minRate;
    }

    public String getMaxRate() {
        return maxRate;
    }

    public void setMaxRate(String maxRate) {
        this.maxRate = maxRate;
    }

    public String getVideoWidth() {
        return videoWidth;
    }

    public void setVideoWidth(String videoWidth) {
        this.videoWidth = videoWidth;
    }

    public String getVideoHeight() {
        return videoHeight;
    }

    public void setVideoHeight(String videoHeight) {
        this.videoHeight = videoHeight;
    }

    public String getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(String videoSize) {
        this.videoSize = videoSize;
    }

    public String getVideoRatio() {
        return videoRatio;
    }

    public void setVideoRatio(String videoRatio) {
        this.videoRatio = videoRatio;
    }

    public String getFillingMode() {
        return fillingMode;
    }

    public void setFillingMode(String fillingMode) {
        this.fillingMode = fillingMode;
    }

    public String getHwAccel() {
        return hwAccel;
    }

    public void setHwAccel(String hwAccel) {
        this.hwAccel = hwAccel;
    }

    public String getPictureOptimization() {
        return pictureOptimization;
    }

    public void setPictureOptimization(String pictureOptimization) {
        this.pictureOptimization = pictureOptimization;
    }

    public String getSuperScaleTranscodeType() {
        return superScaleTranscodeType;
    }

    public void setSuperScaleTranscodeType(String superScaleTranscodeType) {
        this.superScaleTranscodeType = superScaleTranscodeType;
    }

    public String getSuperScaleNoise() {
        return superScaleNoise;
    }

    public void setSuperScaleNoise(String superScaleNoise) {
        this.superScaleNoise = superScaleNoise;
    }

    public String getSuperScaleDeinterlace() {
        return superScaleDeinterlace;
    }

    public void setSuperScaleDeinterlace(String superScaleDeinterlace) {
        this.superScaleDeinterlace = superScaleDeinterlace;
    }

    public String getSuperScaleDetailEnhancement() {
        return superScaleDetailEnhancement;
    }

    public void setSuperScaleDetailEnhancement(String superScaleDetailEnhancement) {
        this.superScaleDetailEnhancement = superScaleDetailEnhancement;
    }

    public String getAutoDelBlackEdge() {
        return autoDelBlackEdge;
    }

    public void setAutoDelBlackEdge(String autoDelBlackEdge) {
        this.autoDelBlackEdge = autoDelBlackEdge;
    }

    public String getAutoTailoring() {
        return autoTailoring;
    }

    public void setAutoTailoring(String autoTailoring) {
        this.autoTailoring = autoTailoring;
    }

    public String getAudioEncodeFormat() {
        return audioEncodeFormat;
    }

    public void setAudioEncodeFormat(String audioEncodeFormat) {
        this.audioEncodeFormat = audioEncodeFormat;
    }

    public String getAudioRate() {
        return audioRate;
    }

    public void setAudioRate(String audioRate) {
        this.audioRate = audioRate;
    }

    public String getAudioSample() {
        return audioSample;
    }

    public void setAudioSample(String audioSample) {
        this.audioSample = audioSample;
    }

    public String getAudioTrack() {
        return audioTrack;
    }

    public void setAudioTrack(String audioTrack) {
        this.audioTrack = audioTrack;
    }

    public String getAudioGain() {
        return audioGain;
    }

    public void setAudioGain(String audioGain) {
        this.audioGain = audioGain;
    }

    public String getAudioNormalize() {
        return audioNormalize;
    }

    public void setAudioNormalize(String audioNormalize) {
        this.audioNormalize = audioNormalize;
    }

    public String getAacLatmFix() {
        return aacLatmFix;
    }

    public void setAacLatmFix(String aacLatmFix) {
        this.aacLatmFix = aacLatmFix;
    }

    public Integer getMultiaudioCheck() {
        return multiaudioCheck;
    }

    public void setMultiaudioCheck(Integer multiaudioCheck) {
        this.multiaudioCheck = multiaudioCheck;
    }

    public String getPackageFormat() {
        return packageFormat;
    }

    public void setPackageFormat(String packageFormat) {
        this.packageFormat = packageFormat;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMuxName() {
        return muxName;
    }

    public void setMuxName(String muxName) {
        this.muxName = muxName;
    }

    public String getPmtPid() {
        return pmtPid;
    }

    public void setPmtPid(String pmtPid) {
        this.pmtPid = pmtPid;
    }

    public String getPcrPid() {
        return pcrPid;
    }

    public void setPcrPid(String pcrPid) {
        this.pcrPid = pcrPid;
    }

    public String getAudioPid() {
        return audioPid;
    }

    public void setAudioPid(String audioPid) {
        this.audioPid = audioPid;
    }

    public String getVideoPid() {
        return videoPid;
    }

    public void setVideoPid(String videoPid) {
        this.videoPid = videoPid;
    }

    public String getProgramPid() {
        return programPid;
    }

    public void setProgramPid(String programPid) {
        this.programPid = programPid;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPixelFormat() {
        return pixelFormat;
    }

    public void setPixelFormat(String pixelFormat) {
        this.pixelFormat = pixelFormat;
    }

    public String getHdr() {
        return hdr;
    }

    public void setHdr(String hdr) {
        this.hdr = hdr;
    }

    public String getColorPrimaries() {
        return colorPrimaries;
    }

    public void setColorPrimaries(String colorPrimaries) {
        this.colorPrimaries = colorPrimaries;
    }

    public String getColorTransferCharacteristics() {
        return colorTransferCharacteristics;
    }

    public void setColorTransferCharacteristics(String colorTransferCharacteristics) {
        this.colorTransferCharacteristics = colorTransferCharacteristics;
    }

    public String getColorspace() {
        return colorspace;
    }

    public void setColorspace(String colorspace) {
        this.colorspace = colorspace;
    }

    public String getColorRange() {
        return colorRange;
    }

    public void setColorRange(String colorRange) {
        this.colorRange = colorRange;
    }

    public String getChromaSampleLocation() {
        return chromaSampleLocation;
    }

    public void setChromaSampleLocation(String chromaSampleLocation) {
        this.chromaSampleLocation = chromaSampleLocation;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFrameScan() {
        return frameScan;
    }

    public void setFrameScan(String frameScan) {
        this.frameScan = frameScan;
    }

    public String getFieldSelect() {
        return fieldSelect;
    }

    public void setFieldSelect(String fieldSelect) {
        this.fieldSelect = fieldSelect;
    }

    public String getEntropyCoding() {
        return entropyCoding;
    }

    public void setEntropyCoding(String entropyCoding) {
        this.entropyCoding = entropyCoding;
    }

    public String getPartitionMacroBlock() {
        return partitionMacroBlock;
    }

    public void setPartitionMacroBlock(String partitionMacroBlock) {
        this.partitionMacroBlock = partitionMacroBlock;
    }

    public String getBitDepth() {
        return bitDepth;
    }

    public void setBitDepth(String bitDepth) {
        this.bitDepth = bitDepth;
    }

    public String getGop() {
        return gop;
    }

    public void setGop(String gop) {
        this.gop = gop;
    }

    public String getMinGop() {
        return minGop;
    }

    public void setMinGop(String minGop) {
        this.minGop = minGop;
    }

    public String getMaxGop() {
        return maxGop;
    }

    public void setMaxGop(String maxGop) {
        this.maxGop = maxGop;
    }

    public String getMaxBFrame() {
        return maxBFrame;
    }

    public void setMaxBFrame(String maxBFrame) {
        this.maxBFrame = maxBFrame;
    }

    public String getReferenceFrames() {
        return referenceFrames;
    }

    public void setReferenceFrames(String referenceFrames) {
        this.referenceFrames = referenceFrames;
    }

    public String getbFrameAsReference() {
        return bFrameAsReference;
    }

    public void setbFrameAsReference(String bFrameAsReference) {
        this.bFrameAsReference = bFrameAsReference;
    }

    public String getbFramWp() {
        return bFramWp;
    }

    public void setbFramWp(String bFramWp) {
        this.bFramWp = bFramWp;
    }

    public String getpFrameWeightedPrediction() {
        return pFrameWeightedPrediction;
    }

    public void setpFrameWeightedPrediction(String pFrameWeightedPrediction) {
        this.pFrameWeightedPrediction = pFrameWeightedPrediction;
    }

    public String getSceneMonitoring() {
        return sceneMonitoring;
    }

    public void setSceneMonitoring(String sceneMonitoring) {
        this.sceneMonitoring = sceneMonitoring;
    }

    public String getMotionPredictionModel() {
        return motionPredictionModel;
    }

    public void setMotionPredictionModel(String motionPredictionModel) {
        this.motionPredictionModel = motionPredictionModel;
    }

    public String getMotionSearchRange() {
        return motionSearchRange;
    }

    public void setMotionSearchRange(String motionSearchRange) {
        this.motionSearchRange = motionSearchRange;
    }

    public String getSubpixelMotionEstimation() {
        return subpixelMotionEstimation;
    }

    public void setSubpixelMotionEstimation(String subpixelMotionEstimation) {
        this.subpixelMotionEstimation = subpixelMotionEstimation;
    }

    public String getMotionDetectionMode() {
        return motionDetectionMode;
    }

    public void setMotionDetectionMode(String motionDetectionMode) {
        this.motionDetectionMode = motionDetectionMode;
    }

    public String getRfConstantValue() {
        return rfConstantValue;
    }

    public void setRfConstantValue(String rfConstantValue) {
        this.rfConstantValue = rfConstantValue;
    }

    public String getQpValue() {
        return qpValue;
    }

    public void setQpValue(String qpValue) {
        this.qpValue = qpValue;
    }

    public String getSubtitleOverlayMode() {
        return subtitleOverlayMode;
    }

    public void setSubtitleOverlayMode(String subtitleOverlayMode) {
        this.subtitleOverlayMode = subtitleOverlayMode;
    }

    public String getSubtitleOverlayEnable() {
        return subtitleOverlayEnable;
    }

    public void setSubtitleOverlayEnable(String subtitleOverlayEnable) {
        this.subtitleOverlayEnable = subtitleOverlayEnable;
    }

    public List<?> getMultisubtitle() {
        return multisubtitle;
    }

    public void setMultisubtitle(List<?> multisubtitle) {
        this.multisubtitle = multisubtitle;
    }

    public List<?> getMultidrawtext() {
        return multidrawtext;
    }

    public void setMultidrawtext(List<?> multidrawtext) {
        this.multidrawtext = multidrawtext;
    }

    public String getDrawtextOverlayEnable() {
        return drawtextOverlayEnable;
    }

    public void setDrawtextOverlayEnable(String drawtextOverlayEnable) {
        this.drawtextOverlayEnable = drawtextOverlayEnable;
    }

    public String getLogoOverlayMode() {
        return logoOverlayMode;
    }

    public void setLogoOverlayMode(String logoOverlayMode) {
        this.logoOverlayMode = logoOverlayMode;
    }

    public String getLogoOverlayEnable() {
        return logoOverlayEnable;
    }

    public void setLogoOverlayEnable(String logoOverlayEnable) {
        this.logoOverlayEnable = logoOverlayEnable;
    }

    public String getLogoLocal() {
        return logoLocal;
    }

    public void setLogoLocal(String logoLocal) {
        this.logoLocal = logoLocal;
    }

    public String getBackgroundVideoWidth() {
        return backgroundVideoWidth;
    }

    public void setBackgroundVideoWidth(String backgroundVideoWidth) {
        this.backgroundVideoWidth = backgroundVideoWidth;
    }

    public String getBackgroundVideoHeight() {
        return backgroundVideoHeight;
    }

    public void setBackgroundVideoHeight(String backgroundVideoHeight) {
        this.backgroundVideoHeight = backgroundVideoHeight;
    }

    public String getBackgroundVideoX() {
        return backgroundVideoX;
    }

    public void setBackgroundVideoX(String backgroundVideoX) {
        this.backgroundVideoX = backgroundVideoX;
    }

    public String getBackgroundVideoY() {
        return backgroundVideoY;
    }

    public void setBackgroundVideoY(String backgroundVideoY) {
        this.backgroundVideoY = backgroundVideoY;
    }

    public List<?> getMultilogo() {
        return multilogo;
    }

    public void setMultilogo(List<?> multilogo) {
        this.multilogo = multilogo;
    }

    public List<?> getMultiblur() {
        return multiblur;
    }

    public void setMultiblur(List<?> multiblur) {
        this.multiblur = multiblur;
    }

    public String getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getSaturation() {
        return saturation;
    }

    public void setSaturation(String saturation) {
        this.saturation = saturation;
    }

    public String getAudioDelay() {
        return audioDelay;
    }

    public void setAudioDelay(String audioDelay) {
        this.audioDelay = audioDelay;
    }

    public String getDrawingFrameEnable() {
        return drawingFrameEnable;
    }

    public void setDrawingFrameEnable(String drawingFrameEnable) {
        this.drawingFrameEnable = drawingFrameEnable;
    }

    public String getDrawingFrameWidth() {
        return drawingFrameWidth;
    }

    public void setDrawingFrameWidth(String drawingFrameWidth) {
        this.drawingFrameWidth = drawingFrameWidth;
    }

    public String getDrawingFrameHeight() {
        return drawingFrameHeight;
    }

    public void setDrawingFrameHeight(String drawingFrameHeight) {
        this.drawingFrameHeight = drawingFrameHeight;
    }

    public Integer getDrawingFrameInterval() {
        return drawingFrameInterval;
    }

    public void setDrawingFrameInterval(Integer drawingFrameInterval) {
        this.drawingFrameInterval = drawingFrameInterval;
    }

    public String getDrawingFrameSendUrl() {
        return drawingFrameSendUrl;
    }

    public void setDrawingFrameSendUrl(String drawingFrameSendUrl) {
        this.drawingFrameSendUrl = drawingFrameSendUrl;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getDenoiseEnable() {
        return denoiseEnable;
    }

    public void setDenoiseEnable(String denoiseEnable) {
        this.denoiseEnable = denoiseEnable;
    }

    public String getLuma() {
        return luma;
    }

    public void setLuma(String luma) {
        this.luma = luma;
    }

    public String getSharpEnable() {
        return sharpEnable;
    }

    public void setSharpEnable(String sharpEnable) {
        this.sharpEnable = sharpEnable;
    }

    public String getLumaX() {
        return lumaX;
    }

    public void setLumaX(String lumaX) {
        this.lumaX = lumaX;
    }

    public String getLumaY() {
        return lumaY;
    }

    public void setLumaY(String lumaY) {
        this.lumaY = lumaY;
    }

    public String getChromaX() {
        return chromaX;
    }

    public void setChromaX(String chromaX) {
        this.chromaX = chromaX;
    }

    public String getChromaY() {
        return chromaY;
    }

    public void setChromaY(String chromaY) {
        this.chromaY = chromaY;
    }

    public String getLumaAmount() {
        return lumaAmount;
    }

    public void setLumaAmount(String lumaAmount) {
        this.lumaAmount = lumaAmount;
    }

    public String getChromaAmount() {
        return chromaAmount;
    }

    public void setChromaAmount(String chromaAmount) {
        this.chromaAmount = chromaAmount;
    }
}
