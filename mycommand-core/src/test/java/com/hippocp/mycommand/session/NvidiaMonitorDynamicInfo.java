package com.hippocp.mycommand.session;

import lombok.Data;

/**
 * 显卡监控动态信息
 *
 * @author ZhouYifan
 * @date 2022/3/8
 */
@Data
public class NvidiaMonitorDynamicInfo {

    /**
     * 执行的命令
     */
    private String command;

    /**
     * 显卡监控日志信息
     */
    private NvidiaMonitorLog logData;

    protected NvidiaMonitorDynamicInfo() {
    }

    public NvidiaMonitorDynamicInfo(NvidiaMonitorLog logData) {
        this.logData = logData;
    }
}
