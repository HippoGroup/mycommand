package sample.mycommand.web.mapper;

import com.hippocp.mycommand.annotations.CommandParam;
import com.hippocp.mycommand.customize.CommandFileCustomize;
import com.hippocp.mycommand.session.ResultHandlerStream;
import sample.mycommand.web.domain.TranscodingParam;

/**
 * @author ZhouYifan
 * @date 2022/3/15
 */
//@CommandMapper
public interface TranscodingCommandMapper {

    int syncHardwareTranscoding(TranscodingParam param);

    int syncHardwareTranscodingReturnStream(@CommandParam("param") TranscodingParam param, ResultHandlerStream stream);

    void asyncHardwareTranscoding(TranscodingParam param);

    void asyncHardwareTranscodingReturnStream(@CommandParam("param") TranscodingParam param, ResultHandlerStream stream);

    void hardwareTranscodingAsync(@CommandParam("param") TranscodingParam param, ResultHandlerStream stream, CommandFileCustomize wrapper);

}
