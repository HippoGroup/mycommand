package com.hippocp.mycommand.extension.nvidia.option;

import com.hippocp.mycommand.extension.command.AbstractCommandSegment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Nvidia选项
 *
 * @author ZhouYifan
 * @date 2022/3/8
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class NvidiaOptions extends AbstractCommandSegment<NvidiaOptions> {

    private NvidiaDeviceMonitorOptions deviceMonitorOptions;

}
