package com.hippocp.mycommand.extension.ffprobe.argument.value;

import com.hippocp.mycommand.extension.ffprobe.argument.FFprobeArgument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author ZhouYifan
 * @date 2022/1/26 9:58
 */
@Getter
@ToString
@AllArgsConstructor
public enum PrintFormatEnum {
    /**
     * 默认格式
     */
    DEFAULT(FFprobeArgument.PRINT_FORMAT, "default", "默认格式"),
    /**
     *
     */
    COMPACT(FFprobeArgument.PRINT_FORMAT, "compact", ""),
    /**
     * csv 格式
     */
    CSV(FFprobeArgument.PRINT_FORMAT, "csv", "csv 格式"),
    /**
     *
     */
    FLAT(FFprobeArgument.PRINT_FORMAT, "flat", ""),
    /**
     * ini 格式
     */
    INI(FFprobeArgument.PRINT_FORMAT, "ini", "ini 格式"),
    /**
     * json 格式
     */
    JSON(FFprobeArgument.PRINT_FORMAT, "json", "json 格式"),
    /**
     * xml 格式
     */
    XML(FFprobeArgument.PRINT_FORMAT, "xml", "xml 格式");

    /**
     * 参数名
     */
    private String arg;
    /**
     * 参数值
     */
    private String val;
    /**
     * 描述
     */
    private String description;

}
