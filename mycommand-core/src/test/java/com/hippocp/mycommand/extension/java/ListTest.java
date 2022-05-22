package com.hippocp.mycommand.extension.java;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ZhouYifan
 * @date 2022/3/8
 */
public class ListTest {

    @Test
    public void removeIfTest() {
        List<String> splits = new ArrayList<>();
        splits.add(" ");
        splits.add(" 1");
        splits.add("\t");
        splits.add("2 ");
        splits.add("\n");
        splits.add("3");
        splits.add(" ");

        // 移除空白字符元素
        splits.removeIf(StrUtil::isBlank);

        boolean isNotBlankElement = splits.stream().allMatch(StrUtil::isNotBlank);
        splits.forEach(System.out::println);
        Assertions.assertTrue(isNotBlankElement);
    }

    @Test
    public void filterTest() {
        List<String> splits = new ArrayList<>();
        splits.add(" ");
        splits.add(" 1");
        splits.add("\t");
        splits.add("2 ");
        splits.add("\n");
        splits.add("3");
        splits.add(" ");

        // 过滤流中不符合predicate的元素
        List<String> strings = splits.stream().filter(StrUtil::isNotBlank).collect(Collectors.<String>toList());

        boolean isNotBlankElement = strings.stream().allMatch(StrUtil::isNotBlank);
        strings.forEach(System.out::println);
        Assertions.assertTrue(isNotBlankElement);
    }

    @Test
    public void removeBlankStrTest() {
        String line = "    0     -    39     -     1    46     0     0   298   141";
        String trimLine = line.trim();
        List<String> strings = StrUtil.split(trimLine, " ");
        // 移除所有空白字符元素
        strings.removeIf(StrUtil::isBlank);
        // 去除所有元素左右空白字符
        strings.forEach(StrUtil::trim);
        System.out.println(strings);
    }

}
