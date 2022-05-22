package com.hippocp.mycommand.mapping;

import java.util.Properties;

/**
 * @author ZhouYifan
 * @date 2022/3/12
 */
public interface PlatformIdProvider {

    default void setProperties(Properties p) {
        // NOP
    }

    String getPlatformId();

}
