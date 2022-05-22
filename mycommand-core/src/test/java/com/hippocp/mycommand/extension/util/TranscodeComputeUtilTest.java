package com.hippocp.mycommand.extension.util;

import com.hippocp.mycommand.extension.ffmpeg.util.TranscodeComputeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author ZhouYifan
 * @date 2022/2/12 17:50
 */
public class TranscodeComputeUtilTest {

    @Test
    public void computeMuxRateTest() {
        String muxRate = TranscodeComputeUtil.computeMuxRate("8000k");
        Assertions.assertEquals("8804k", muxRate);
    }

    @Test
    public void computeMuxRateTest02() {
        String muxRate = TranscodeComputeUtil.computeMuxRate("2720k");
        Assertions.assertEquals("3420k", muxRate);
    }

    @Test
    public void computeBufSizeTest() {
        String bufSize = TranscodeComputeUtil.computeBufSize("8000k");
        Assertions.assertEquals("1600k", bufSize);
    }

}
