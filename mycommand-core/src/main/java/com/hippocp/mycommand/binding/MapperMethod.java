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
package com.hippocp.mycommand.binding;


import com.hippocp.mycommand.mapping.CommandType;
import com.hippocp.mycommand.mapping.MappedStatement;
import com.hippocp.mycommand.reflection.ParamNameResolver;
import com.hippocp.mycommand.reflection.TypeParameterResolver;
import com.hippocp.mycommand.session.CommandSession;
import com.hippocp.mycommand.session.Configuration;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class MapperMethod {

    private final SqlCommand command;
    private final MethodSignature method;

    public MapperMethod(Class<?> mapperInterface, Method method, Configuration config) {
        this.command = new SqlCommand(config, mapperInterface, method);
        this.method = new MethodSignature(config, mapperInterface, method);
    }

    public Object execute(CommandSession commandSession, Object[] args) {
        Object result;
        CommandType commandType = command.getType();
        switch (commandType) {
            case SYNC: {
                Object param = method.convertArgsToSqlCommandParam(args);
                result = commandSession.executeSync(command.getName(), param);
                break;
            }
            case ASYNC: {
                Object param = method.convertArgsToSqlCommandParam(args);
                commandSession.executeAsync(command.getName(), param);
                result = null;
                break;
            }
            default:
                throw new BindingException("Unknown execution method for: " + command.getName());
        }

        boolean isAsyncCommandType = commandType.equals(CommandType.ASYNC);
        boolean isNullResult = result == null;
        boolean isPrimitive = method.getReturnType().isPrimitive();
        boolean isNotReturnsVoid = !method.returnsVoid();
        if (isAsyncCommandType && isNullResult && isPrimitive && isNotReturnsVoid) {
            throw new BindingException("Async tag correspond mapper method not allowed have return value"
                    + " mapper method '" + command.getName()
                    + " attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
        }

        return result;
    }

    private Object rowCountResult(int rowCount) {
        final Object result;
        if (method.returnsVoid()) {
            result = null;
        } else if (Integer.class.equals(method.getReturnType()) || Integer.TYPE.equals(method.getReturnType())) {
            result = rowCount;
        } else if (Long.class.equals(method.getReturnType()) || Long.TYPE.equals(method.getReturnType())) {
            result = (long) rowCount;
        } else if (Boolean.class.equals(method.getReturnType()) || Boolean.TYPE.equals(method.getReturnType())) {
            result = rowCount > 0;
        } else {
            throw new BindingException("Mapper method '" + command.getName() + "' has an unsupported return type: " + method.getReturnType());
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private <E> Object convertToArray(List<E> list) {
        Class<?> arrayComponentType = method.getReturnType().getComponentType();
        Object array = Array.newInstance(arrayComponentType, list.size());
        if (arrayComponentType.isPrimitive()) {
            for (int i = 0; i < list.size(); i++) {
                Array.set(array, i, list.get(i));
            }
            return array;
        } else {
            return list.toArray((E[]) array);
        }
    }


    public static class ParamMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -2212268410512043556L;

        @Override
        public V get(Object key) {
            if (!super.containsKey(key)) {
                throw new BindingException("Parameter '" + key + "' not found. Available parameters are " + keySet());
            }
            return super.get(key);
        }

    }

    public static class SqlCommand {

        private final String name;
        private final CommandType type;

        public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
            final String methodName = method.getName();
            final Class<?> declaringClass = method.getDeclaringClass();
            MappedStatement ms = resolveMappedStatement(mapperInterface, methodName, declaringClass,
                    configuration);
            if (ms == null) {
//        if (method.getAnnotation(Flush.class) != null) {
//          name = null;
//          type = CommandType.FLUSH;
//        } else {
                throw new BindingException("Invalid bound statement (not found): "
                        + mapperInterface.getName() + "." + methodName);
//        }
            } else {
                name = ms.getId();
                type = ms.getSqlCommandType();
                if (type == CommandType.UNKNOWN) {
                    throw new BindingException("Unknown execution method for: " + name);
                }
            }
        }

        public String getName() {
            return name;
        }

        public CommandType getType() {
            return type;
        }

        private MappedStatement resolveMappedStatement(Class<?> mapperInterface, String methodName,
                                                       Class<?> declaringClass, Configuration configuration) {
            String statementId = mapperInterface.getName() + "." + methodName;
            if (configuration.hasStatement(statementId)) {
                return configuration.getMappedStatement(statementId);
            } else if (mapperInterface.equals(declaringClass)) {
                return null;
            }
            for (Class<?> superInterface : mapperInterface.getInterfaces()) {
                if (declaringClass.isAssignableFrom(superInterface)) {
                    MappedStatement ms = resolveMappedStatement(superInterface, methodName,
                            declaringClass, configuration);
                    if (ms != null) {
                        return ms;
                    }
                }
            }
            return null;
        }
    }

    public static class MethodSignature {

        //    private final boolean returnsMany;
//    private final boolean returnsMap;
        private final boolean returnsVoid;
        //    private final boolean returnsCursor;
//    private final boolean returnsOptional;
        private final Class<?> returnType;
        //    private final String mapKey;
//    private final Integer resultHandlerIndex;
//    private final Integer rowBoundsIndex;
        private final ParamNameResolver paramNameResolver;

        public MethodSignature(Configuration configuration, Class<?> mapperInterface, Method method) {
            Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface);
            if (resolvedReturnType instanceof Class<?>) {
                this.returnType = (Class<?>) resolvedReturnType;
            } else if (resolvedReturnType instanceof ParameterizedType) {
                this.returnType = (Class<?>) ((ParameterizedType) resolvedReturnType).getRawType();
            } else {
                this.returnType = method.getReturnType();
            }
            this.returnsVoid = void.class.equals(this.returnType);
//      this.returnsMany = configuration.getObjectFactory().isCollection(this.returnType) || this.returnType.isArray();
//      this.returnsCursor = Cursor.class.equals(this.returnType);
//      this.returnsOptional = Optional.class.equals(this.returnType);
//      this.mapKey = getMapKey(method);
//      this.returnsMap = this.mapKey != null;
//      this.rowBoundsIndex = getUniqueParamIndex(method, RowBounds.class);
//      this.resultHandlerIndex = getUniqueParamIndex(method, ResultHandler.class);
            this.paramNameResolver = new ParamNameResolver(configuration, method);
        }

        public Object convertArgsToSqlCommandParam(Object[] args) {
            return paramNameResolver.getNamedParams(args);
        }

//    public boolean hasRowBounds() {
//      return rowBoundsIndex != null;
//    }

//    public RowBounds extractRowBounds(Object[] args) {
//      return hasRowBounds() ? (RowBounds) args[rowBoundsIndex] : null;
//    }

//    public boolean hasResultHandler() {
//      return resultHandlerIndex != null;
//    }

//    public ResultHandler extractResultHandler(Object[] args) {
//      return hasResultHandler() ? (ResultHandler) args[resultHandlerIndex] : null;
//    }

        public Class<?> getReturnType() {
            return returnType;
        }

//    public boolean returnsMany() {
//      return returnsMany;
//    }

//    public boolean returnsMap() {
//      return returnsMap;
//    }

        public boolean returnsVoid() {
            return returnsVoid;
        }

//    public boolean returnsCursor() {
//      return returnsCursor;
//    }

        /**
         * return whether return type is {@code java.util.Optional}.
         *
         * @return return {@code true}, if return type is {@code java.util.Optional}
         * @since 3.5.0
         */
//    public boolean returnsOptional() {
//      return returnsOptional;
//    }
        private Integer getUniqueParamIndex(Method method, Class<?> paramType) {
            Integer index = null;
            final Class<?>[] argTypes = method.getParameterTypes();
            for (int i = 0; i < argTypes.length; i++) {
                if (paramType.isAssignableFrom(argTypes[i])) {
                    if (index == null) {
                        index = i;
                    } else {
                        throw new BindingException(method.getName() + " cannot have multiple " + paramType.getSimpleName() + " parameters");
                    }
                }
            }
            return index;
        }

//    public String getMapKey() {
//      return mapKey;
//    }

//    private String getMapKey(Method method) {
//      String mapKey = null;
//      if (Map.class.isAssignableFrom(method.getReturnType())) {
//        final MapKey mapKeyAnnotation = method.getAnnotation(MapKey.class);
//        if (mapKeyAnnotation != null) {
//          mapKey = mapKeyAnnotation.value();
//        }
//      }
//      return mapKey;
//    }
    }

}
