package com.hippocp.mycommand.builder;

import com.hippocp.mycommand.annotations.CommandParam;
import com.hippocp.mycommand.session.ResultHandlerStream;
import com.hippocp.mycommand.template.TranscodingParam;

/**
 * @author ZhouYifan
 * @date 2022/3/7
 */
public interface TranscodeMapper {

    int syncHardwareTranscoding(TranscodingParam param);

    int syncHardwareTranscodingReturnStream(@CommandParam("param") TranscodingParam param, ResultHandlerStream stream);

    void asyncHardwareTranscoding(TranscodingParam param);

    void asyncHardwareTranscodingReturnStream(@CommandParam("param") TranscodingParam param, ResultHandlerStream stream);

}
