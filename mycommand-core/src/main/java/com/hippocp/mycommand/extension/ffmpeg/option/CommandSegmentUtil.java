package com.hippocp.mycommand.extension.ffmpeg.option;

import cn.hutool.core.util.StrUtil;
import com.hippocp.mycommand.extension.command.AbstractCommandSegment;
import com.hippocp.mycommand.extension.exception.CommandSegmentAssembleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 命令片段工具类
 *
 * @author ZhouYifan
 * @date 2022/2/3
 */
public class CommandSegmentUtil {

    private static final Logger log = LoggerFactory.getLogger(CommandSegmentUtil.class);

    /**
     * 组装被聚合的各个命令片段，将各个命令片段中的命令片段放入，Children commandSegment 类的属性 commandSegments 中
     *
     * @param commandSegment 继承自抽象命令片段的类
     * @param <Children>     {@link AbstractCommandSegment}子类
     */
    public static <Children extends AbstractCommandSegment<Children>> void assemble(Children commandSegment) {
        // 获取继承自抽象命令片段的类 Class
        Class<? extends AbstractCommandSegment> clazz = commandSegment.getClass();
        // 获取类中所有属性
        Field[] fields = clazz.getDeclaredFields();
        // 遍历
        for (Field field : fields) {
            // 获取属性类型
            Class<?> fieldType = field.getType();
            // 排除非 AbstractCommandSegment 类型属性
            boolean isNotAssignableFrom = !AbstractCommandSegment.class.isAssignableFrom(fieldType);
            if (isNotAssignableFrom) {
                // AbstractCommandSegment 类型属性，则结束当前循环，进入下次循环
                continue;
            }

            // 类属性、成员名称
            String fieldName = field.getName();
            // 拼接get方法名
            String methodName = jointGetMethodName(fieldName);
            // 取得get方法
            Method method;
            try {
                method = clazz.getMethod(methodName);
            } catch (NoSuchMethodException e) {
                String msg = StrUtil.format("在类 {} 中，找不到 {} 方法", clazz.getName(), methodName);
                throw new CommandSegmentAssembleException(msg, e);
            }
            // 调用get方法
            Object fieldValue;
            try {
                fieldValue = method.invoke(commandSegment);
            } catch (IllegalAccessException e) {
                throw new CommandSegmentAssembleException("反射创建实例时发生异常", e);
            } catch (InvocationTargetException e) {
                throw new CommandSegmentAssembleException("反射调用的方法或构造函数内部发生异常", e);
            }

            boolean isNotInstance = !(fieldValue instanceof AbstractCommandSegment);
            if (isNotInstance) {
                // 类属性（成员）无法实例化为 AbstractCommandSegment 或者为空，则结束当前循环，进入下次循环
                continue;
            }

            // 强转为 AbstractCommandSegment
            AbstractCommandSegment options = (AbstractCommandSegment) fieldValue;
            // 各个options中的List不可能为null
            // 因为父类 AbstractCommandSegment 中 commandSegments 属性有初值 new ArrayList<>();
            List<String> optionsCommandSegments = options.getCommandSegments();
            // 合并至该类 commandSegments 属性中
            List<String> commandSegments = commandSegment.getCommandSegments();
            commandSegments.addAll(optionsCommandSegments);

        }

    }

    /**
     * 拼接类 get 方法名称
     *
     * @param fieldName 属性名称
     * @return get方法名称
     */
    protected static String jointGetMethodName(String fieldName) {
        final String get = "get";
        String initial = fieldName.substring(0, 1);
        String uppercaseInitial = initial.toUpperCase();
        String surplusLetter = fieldName.substring(1);
        // 方法名
        return StrUtil.format("{}{}{}", get, uppercaseInitial, surplusLetter);
    }

    private CommandSegmentUtil() {
    }

}
