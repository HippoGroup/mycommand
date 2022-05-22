package com.hippocp.mycommand.extension.nvidia.command;

import com.hippocp.mycommand.extension.command.AbstractSingleCommand;
import com.hippocp.mycommand.extension.nvidia.option.NvidiaOptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;

import java.util.List;

/**
 * Nvidia命令
 *
 * @author ZhouYifan
 * @date 2022/3/8
 */
@Slf4j
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class NvidiaCommand extends AbstractSingleCommand<NvidiaCommand> {

    public static final String EXECUTABLE_FILE = "nvidia-smi";

    private NvidiaOptions options;

    @Override
    protected void setCommandLine() {
        this.commandLine = new CommandLine(EXECUTABLE_FILE);
    }

    /**
     * 组装被聚合的各个选项
     *
     * @param handleQuoting 处理引用
     */
    @Override
    public void assemble(boolean handleQuoting) {
        // 组装
        // [options]
        options.assemble(options);
        List<String> optionCommandSegments = options.getCommandSegments();
        this.addCommand(optionCommandSegments);
    }

}
