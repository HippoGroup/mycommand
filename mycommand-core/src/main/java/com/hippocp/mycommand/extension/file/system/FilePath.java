package com.hippocp.mycommand.extension.file.system;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.File;

/**
 * 文件路径对象，描述一个文件或者路径
 *
 * @author ZhouYifan
 * @date 2022/2/5
 */
@Getter
@ToString
@EqualsAndHashCode
public class FilePath {

    /**
     * 输入文件，即原文件
     */
    private final File inputFile;

    /**
     * 名称
     */
    private String name;

    /**
     * 绝对路径
     */
    private String absolutePath;

    /**
     * 子文件或者路径
     */
    private File[] subFilePaths;

    /**
     * 有子文件或者路径
     */
    private Boolean hasSubFilePath;

    /**
     * 是路径
     */
    private Boolean directory;

    /**
     * 是文件
     */
    private Boolean file;

    /**
     * 存在
     */
    private Boolean exist;

    /**
     * 根据给定文件或路径，构造 FilePath 对象
     *
     * @param inputFile 给定文件或路径
     */
    public FilePath(File inputFile) {
        this.inputFile = inputFile;
        init();
    }

    /**
     * 初始化，生成类属性
     * <pre>
     * name 名称
     * absolutePath 绝对路径
     * subFilePaths 子文件或者路径
     * hasSubFilePath 有子文件或者路径
     * directory 是路径
     * file 是文件
     * exist 存在
     * </pre>
     */
    public void init() {

        if (inputFile == null) {
            return;
        }

        String name = inputFile.getName();
        boolean isBlankName = StrUtil.isBlank(name);
        if (isBlankName) {
            this.name = inputFile.getPath();
        } else {
            this.name = name;
        }

        absolutePath = inputFile.getAbsolutePath();
        directory = inputFile.isDirectory();
        // 是目录执行
        if (directory) {
            subFilePaths = FileUtil.ls(absolutePath);
        }

        hasSubFilePath = ArrayUtil.isNotEmpty(subFilePaths);
        file = inputFile.isFile();
        exist = inputFile.exists();

    }


}
