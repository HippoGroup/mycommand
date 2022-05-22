/*
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.hippocp.mycommand.reflection;


import com.hippocp.mycommand.annotations.CommandParam;
import com.hippocp.mycommand.binding.MapperMethod;
import com.hippocp.mycommand.session.Configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class ParamNameResolver {

    public static final String GENERIC_NAME_PREFIX = "param";

    private final boolean useActualParamName;

    /**
     * <p>
     * The key is the index and the value is the name of the parameter.<br />
     * The name is obtained from {@link CommandParam} if specified. When {@link CommandParam} is not specified,
     * the parameter index is used. Note that this index could be different from the actual index
     * when the method has special parameters (i.e. {@link RowBounds} or {@link ResultHandler}).
     * </p>
     * <ul>
     * <li>aMethod(@CommandParam("M") int a, @CommandParam("N") int b) -&gt; {{0, "M"}, {1, "N"}}</li>
     * <li>aMethod(int a, int b) -&gt; {{0, "0"}, {1, "1"}}</li>
     * <li>aMethod(int a, RowBounds rb, int b) -&gt; {{0, "0"}, {2, "1"}}</li>
     * </ul>
     */
    private final SortedMap<Integer, String> names;

    private boolean hasParamAnnotation;

    public ParamNameResolver(Configuration config, Method method) {
        this.useActualParamName = config.isUseActualParamName();
        final Class<?>[] paramTypes = method.getParameterTypes();
        final Annotation[][] paramAnnotations = method.getParameterAnnotations();
        final SortedMap<Integer, String> map = new TreeMap<>();
        int paramCount = paramAnnotations.length;
        // get names from @CommandParam annotations
        for (int paramIndex = 0; paramIndex < paramCount; paramIndex++) {
            // todo 参数列表没有 ResultHandlerStream 代表不获取子进程输入输出流
            // todo 返回值不是Integer 或 int 或 Long 或 long 则抛出异常，应在XML构建并且扫描注册Mapper接口时就检查如不符合则报错
//      if (isSpecialParameter(paramTypes[paramIndex])) {
//        // skip special parameters
//        continue;
//      }
            String name = null;
            for (Annotation annotation : paramAnnotations[paramIndex]) {
                if (annotation instanceof CommandParam) {
                    hasParamAnnotation = true;
                    name = ((CommandParam) annotation).value();
                    break;
                }
            }
            if (name == null) {
                // @CommandParam was not specified.
                if (useActualParamName) {
                    name = getActualParamName(method, paramIndex);
                }
                if (name == null) {
                    // use the parameter index as the name ("0", "1", ...)
                    // gcode issue #71
                    name = String.valueOf(map.size());
                }
            }
            map.put(paramIndex, name);
        }
        names = Collections.unmodifiableSortedMap(map);
    }

    private String getActualParamName(Method method, int paramIndex) {
        return ParamNameUtil.getParamNames(method).get(paramIndex);
    }

//  private static boolean isSpecialParameter(Class<?> clazz) {
//    return RowBounds.class.isAssignableFrom(clazz) || ResultHandler.class.isAssignableFrom(clazz);
//  }

    /**
     * Returns parameter names referenced by SQL providers.
     *
     * @return the names
     */
    public String[] getNames() {
        return names.values().toArray(new String[0]);
    }

    /**
     * <p>
     * A single non-special parameter is returned without a name.
     * Multiple parameters are named using the naming rule.
     * In addition to the default names, this method also adds the generic names (param1, param2,
     * ...).
     * </p>
     *
     * @param args the args
     * @return the named params
     */
    public Object getNamedParams(Object[] args) {
        final int paramCount = names.size();
        if (args == null || paramCount == 0) {
            return null;
        } else if (!hasParamAnnotation && paramCount == 1) {
            Object value = args[names.firstKey()];
            return wrapToMapIfCollection(value, useActualParamName ? names.get(0) : null);
        } else {
            final Map<String, Object> param = new MapperMethod.ParamMap<>();
            int i = 0;
            for (Map.Entry<Integer, String> entry : names.entrySet()) {
                param.put(entry.getValue(), args[entry.getKey()]);
                // add generic param names (param1, param2, ...)
                final String genericParamName = GENERIC_NAME_PREFIX + (i + 1);
                // ensure not to overwrite parameter named with @CommandParam
                if (!names.containsValue(genericParamName)) {
                    param.put(genericParamName, args[entry.getKey()]);
                }
                i++;
            }
            return param;
        }
    }

    /**
     * Wrap to a {@link MapperMethod.ParamMap} if object is {@link Collection} or array.
     *
     * @param object          a parameter object
     * @param actualParamName an actual parameter name
     *                        (If specify a name, set an object to {@link MapperMethod.ParamMap} with specified name)
     * @return a {@link MapperMethod.ParamMap}
     * @since 3.5.5
     */
    public static Object wrapToMapIfCollection(Object object, String actualParamName) {
        if (object instanceof Collection) {
            MapperMethod.ParamMap<Object> map = new MapperMethod.ParamMap<>();
            map.put("collection", object);
            if (object instanceof List) {
                map.put("list", object);
            }
            Optional.ofNullable(actualParamName).ifPresent(name -> map.put(name, object));
            return map;
        } else if (object != null && object.getClass().isArray()) {
            MapperMethod.ParamMap<Object> map = new MapperMethod.ParamMap<>();
            map.put("array", object);
            Optional.ofNullable(actualParamName).ifPresent(name -> map.put(name, object));
            return map;
        }
        return object;
    }

}
