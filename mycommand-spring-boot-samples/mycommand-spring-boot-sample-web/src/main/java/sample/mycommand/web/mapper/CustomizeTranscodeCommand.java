package sample.mycommand.web.mapper;

import com.hippocp.mycommand.annotations.CommandMapper;
import com.hippocp.mycommand.annotations.CommandParam;
import com.hippocp.mycommand.session.ResultHandlerStream;

/**
 * 自定义转码命令Mapper
 *
 * @author ZhouYifan
 * @date 2022/3/23
 */
@CommandMapper
public interface CustomizeTranscodeCommand {

    void asyncCustomizeTranscode(@CommandParam("input") String input,
                                 @CommandParam("output") String output,
                                 @CommandParam("customizeCommand") String customizeCommand,
                                 ResultHandlerStream stream);

}
