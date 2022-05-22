package com.hippocp.mycommand.extension.nvidia.option;

import com.hippocp.mycommand.extension.command.AbstractCommandSegment;

/**
 * Nvidia设备监控选项
 *
 * @author ZhouYifan
 * @date 2022/3/8
 */
public class NvidiaDeviceMonitorOptions extends AbstractCommandSegment<NvidiaDeviceMonitorOptions> {

    public static final String DMON = "dmon";

    public interface Argument {
        /**
         * Collection delay/interval in seconds [default=1sec]
         */
        String DELAY = "-d";

        /**
         * Collect specified number of samples and exit
         */
        String COUNT = "-c";
    }

    public NvidiaDeviceMonitorOptions monitor() {
        return this.monitor(true);
    }

    /**
     * 显卡监控
     *
     * @param condition 执行条件
     * @return {@link NvidiaDeviceMonitorOptions}
     */
    public NvidiaDeviceMonitorOptions monitor(boolean condition) {
        return this.addCommandSegment(condition, DMON);
    }

    /**
     * 设置收集指定数量的样本并退出<br>
     * Set collect specified number of samples and exit
     *
     * @param count 样本数量
     * @return {@link NvidiaDeviceMonitorOptions}
     */
    public NvidiaDeviceMonitorOptions count(int count) {
        return this.count(true, count);
    }

    /**
     * 设置收集指定数量的样本并退出 <br>
     * Set collect specified number of samples and exit
     *
     * @param condition 执行条件
     * @param count     样本数量
     * @return {@link NvidiaDeviceMonitorOptions}
     */
    public NvidiaDeviceMonitorOptions count(boolean condition, int count) {
        return this.addCommandSegment(condition, Argument.COUNT, String.valueOf(count));
    }

    /**
     * 收集延迟/间隔(秒)[默认=1sec]<br>
     * Collection delay/interval in seconds [default=1sec]
     *
     * @param seconds 秒
     * @return {@link NvidiaDeviceMonitorOptions}
     */
    public NvidiaDeviceMonitorOptions delay(int seconds) {
        return this.delay(true, seconds);
    }

    /**
     * 收集延迟/间隔(秒)[默认=1sec]<br>
     * Collection delay/interval in seconds [default=1sec]
     *
     * @param condition 执行条件
     * @param seconds   秒
     * @return {@link NvidiaDeviceMonitorOptions}
     */
    public NvidiaDeviceMonitorOptions delay(boolean condition, int seconds) {
        return this.addCommandSegment(condition, Argument.DELAY, String.valueOf(seconds));
    }

}
