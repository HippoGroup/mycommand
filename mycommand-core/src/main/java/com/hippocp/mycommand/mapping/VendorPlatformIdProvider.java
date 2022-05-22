package com.hippocp.mycommand.mapping;

import cn.hutool.core.io.FileUtil;

/**
 * @author ZhouYifan
 * @date 2022/3/12
 */
public class VendorPlatformIdProvider implements PlatformIdProvider {

    @Override
    public String getPlatformId() {
        final String windows = "windows";
        final String unix = "unix";
        boolean isWindows = FileUtil.isWindows();
        if (isWindows) {
            return windows;
        } else {
            return unix;
        }
    }

}
