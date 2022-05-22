package sample.mycommand.web.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 显卡监控动态信息
 *
 * @author ZhouYifan
 * @date 2022/3/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NvidiaMonitorDynamicInfo {

    /**
     * 显卡监控日志信息
     */
    private NvidiaMonitorLog logData;

}
