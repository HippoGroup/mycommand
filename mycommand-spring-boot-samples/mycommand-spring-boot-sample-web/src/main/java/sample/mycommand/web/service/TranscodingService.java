package sample.mycommand.web.service;

import com.hippocp.mycommand.session.ResultHandlerStream;
import org.springframework.stereotype.Service;
import sample.mycommand.web.domain.TranscodingParam;
import sample.mycommand.web.mapper.TranscodingCommandMapper;

import javax.annotation.Resource;

/**
 * @author ZhouYifan
 * @date 2022/3/15
 */
@Service
public class TranscodingService {

    @Resource
    private TranscodingCommandMapper tscMapper;

    public int syncHardwareTranscoding(TranscodingParam param) {
        return tscMapper.syncHardwareTranscoding(param);
    }

    public int syncHardwareTranscodingReturnStream(TranscodingParam param, ResultHandlerStream stream) {
        return tscMapper.syncHardwareTranscodingReturnStream(param, stream);
    }

    public void asyncHardwareTranscoding(TranscodingParam param) {
        tscMapper.syncHardwareTranscoding(param);
    }

    public void asyncHardwareTranscodingReturnStream(TranscodingParam param, ResultHandlerStream stream) {
        tscMapper.asyncHardwareTranscodingReturnStream(param, stream);
    }
}
