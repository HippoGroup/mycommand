package com.hippocp.mycommand.customize;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.CharsetUtil;

import java.io.File;
import java.util.Objects;

/**
 * 命令文件定制器
 *
 * @author ZhouYifan
 * @date 2022/3/19
 */
public abstract class CommandFileCustomize {
    /**
     * 命令文件
     */
    protected File commandFile;

    /**
     * 文件写入器
     */
    protected FileWriter writer;

    public File getCommandFile() {
        return commandFile;
    }

    public void setCommandFile(File commandFile) {
        this.commandFile = commandFile;
    }

    /**
     * 创建命令文件
     *
     * @return 命令文件
     */
    public abstract File createCommandFile();

    /**
     * 以追加形式写内容到文件
     * 子类重写该方法可以自定义写入文件时所使用的字符集
     *
     * @param content 需要写入的内容
     * @param file    被写入的文件
     */
    public void writeCommandFile(String content, File file) {
        if (writer != null) {
            // 将内容追加到文件
            writer.append(content);

            return;

        }

        writer = new FileWriter(file, CharsetUtil.systemCharset());
        // 将内容追加到文件
        writer.append(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandFileCustomize)) {
            return false;
        }
        CommandFileCustomize that = (CommandFileCustomize) o;
        return Objects.equals(commandFile, that.commandFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandFile);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CommandFileCustomize{");
        sb.append("commandFile=").append(commandFile);
        sb.append('}');
        return sb.toString();
    }
}
