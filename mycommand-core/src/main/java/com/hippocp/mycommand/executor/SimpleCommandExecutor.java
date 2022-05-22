package com.hippocp.mycommand.executor;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import com.hippocp.easy.code.util.string.StringUtil;
import com.hippocp.mycommand.customize.CommandFileCustomize;
import com.hippocp.mycommand.customize.DefaultCommandFile;
import com.hippocp.mycommand.extension.command.AbstractCommand;
import com.hippocp.mycommand.mapping.BoundCommand;
import com.hippocp.mycommand.mapping.MappedStatement;
import com.hippocp.mycommand.session.ResultHandlerStream;
import org.apache.commons.exec.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZhouYifan
 * @date 2022/3/10
 */
public class SimpleCommandExecutor extends BaseCommandExecutor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 正则目标，一个或多个空格，利用预编译功能，加快正则匹配速度
     */
    private static final Pattern PATTERN = Pattern.compile("[ ]+");


    @Override
    public int executeSync(MappedStatement ms, Object parameterObject) throws IOException {
        CommandWrapper wrapper = getCommandWrapper(ms, parameterObject);
        return executeSyncByMapper(wrapper, ms, parameterObject);
    }

    /**
     * 根据Mapper同步执行子进程
     *
     * @param wrapper         {@link CommandWrapper}
     * @param ms              {@link MappedStatement}
     * @param parameterObject 参数
     * @return 子进程退出值
     * @throws IOException
     */
    public int executeSyncByMapper(CommandWrapper wrapper, MappedStatement ms, Object parameterObject) throws IOException {
        // 命令
        String command = wrapper.getCommand();
        String inFileCommand = wrapper.getInFileCommand();
        // 流
        ResultHandlerStream stream = getTargetObject(parameterObject, ResultHandlerStream.class);
        // 返回命令字符串
        returnCommand(stream, command, inFileCommand);
        // 准备命令
        CommandLine commandLine = wrapper.getCommandLine();
        // 进程破坏者
        ProcessDestroyer destroyer = getTargetObject(parameterObject, ProcessDestroyer.class);
        // 获得执行器
        Executor executor = buildAndConfigExecutor(ms, new DefaultExecutor(), stream, null, destroyer);
        // 记录开始日志
        executeSyncStartLog(command, inFileCommand);
        // 执行并记录结束日志
        return executeSyncEndAndLog(commandLine, executor);
    }

    @Override
    public int executeSync(
            AbstractCommand command,
            Integer exitValueNumber,
            Integer timeoutSeconds,
            ResultHandlerStream stream,
            CommandFileCustomize customize,
            ProcessDestroyer destroyer
    ) throws IOException {
        CommandWrapper wrapper = getCommandWrapper(command, customize);
        return executeSyncByInvoke(wrapper, exitValueNumber, timeoutSeconds, stream, destroyer);
    }

    public int executeSyncByInvoke(CommandWrapper wrapper, Integer exitValueNumber, Integer timeoutSeconds,
                                   ResultHandlerStream stream, ProcessDestroyer destroyer) throws IOException {
        // 命令
        String command = wrapper.getCommand();
        String inFileCommand = wrapper.getInFileCommand();
        // 返回命令字符串
        returnCommand(stream, command, inFileCommand);
        // 准备命令
        CommandLine commandLine = wrapper.getCommandLine();
        Executor executor = buildAndConfigExecutor(exitValueNumber, timeoutSeconds, new DefaultExecutor(),
                stream, null, destroyer);
        executeSyncStartLog(command, inFileCommand);
        return executeSyncEndAndLog(commandLine, executor);
    }

    protected void executeSyncStartLog(String command, String inFileCommand) {
        // 输出日志
        if (log.isInfoEnabled()) {
            log.info("--------------------同步阻塞执行命令日志记录开始--------------------");
            log.info("本次同步阻塞执行命令：{}", command);
            log.info("本次执行的文件内命令：{}", inFileCommand);
        }
    }

    protected int executeSyncEndAndLog(CommandLine commandLine, Executor executor) throws IOException {
        // 子进程开始执行时间
        long startTime = System.currentTimeMillis();
        // 执行命令
        int exitValue = executor.execute(commandLine);
        // 子进程执行完成时间
        long endTime = System.currentTimeMillis();
        // 子进程总执行时间
        long executeTime = endTime - startTime;
        // 格式化的执行总时间 xx秒xxx毫秒
        String executeTimeFormat = DateUtil.formatBetween(executeTime);
        // 视频转码日志记录
        // 输出日志
        if (log.isInfoEnabled()) {
            log.info("本次同步阻塞命令执行总耗时：{}", executeTimeFormat);
            log.info("--------------------同步阻塞执行命令日志记录结束--------------------");
        }
        return exitValue;
    }

    @Override
    public void executeAsync(MappedStatement ms, Object parameterObject) throws IOException {
        CommandWrapper wrapper = getCommandWrapper(ms, parameterObject);
        executeAsyncByMapper(wrapper, ms, parameterObject);
    }

    protected void executeAsyncByMapper(CommandWrapper wrapper, MappedStatement ms, Object parameterObject) throws IOException {
        // 命令
        String command = wrapper.getCommand();
        String inFileCommand = wrapper.getInFileCommand();
        // 流
        ResultHandlerStream stream = getTargetObject(parameterObject, ResultHandlerStream.class);
        // 返回命令字符串
        returnCommand(stream, command, inFileCommand);
        // 准备命令
        CommandLine commandLine = wrapper.getCommandLine();
        // 进程破坏者
        ProcessDestroyer destroyer = getTargetObject(parameterObject, ProcessDestroyer.class);
        // 设置命令执行器
        Executor executor = buildAndConfigExecutor(ms, new DefaultExecutor(), stream, null, destroyer);
        executeAsyncStartAndLog(command, inFileCommand, commandLine, executor, stream);
    }

    protected void executeAsyncStartAndLog(
            String command,
            String inFileCommand,
            CommandLine commandLine,
            Executor executor,
            ResultHandlerStream stream
    ) throws IOException {
        // 设置异步执行
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        // 执行
        executor.execute(commandLine, resultHandler);
        // 输出日志
        if (log.isInfoEnabled()) {
            log.info("--------------------异步执行命令日志记录--------------------");
            log.info("本次异步执行已开始，命令：{}", command);
            log.info("本次执行的文件内命令：{}", inFileCommand);
        }
        if (stream != null) {
            // 返回用于异步进程处理的“ExecuteResultHandler”的默认实现
            stream.setDefaultExecuteResultHandler(resultHandler);
            // 返回用于异步进程处理的“ExecuteResultHandler”的自定义实现
            stream.setResultHandler(resultHandler);
        }
    }


    @Override
    public void executeAsync(
            AbstractCommand command,
            Integer exitValueNumber,
            Integer timeoutSeconds,
            ResultHandlerStream stream,
            CommandFileCustomize customize,
            ProcessDestroyer destroyer
    ) throws IOException {
        CommandWrapper wrapper = getCommandWrapper(command, customize);
        executeAsyncByInvoke(wrapper, exitValueNumber, timeoutSeconds, stream, destroyer);
    }

    protected void executeAsyncByInvoke(CommandWrapper wrapper, Integer exitValueNumber, Integer timeoutSeconds,
                                        ResultHandlerStream stream, ProcessDestroyer destroyer) throws IOException {
        // 命令
        String command = wrapper.getCommand();
        String inFileCommand = wrapper.getInFileCommand();
        // 返回命令字符串
        returnCommand(stream, command, inFileCommand);
        // 准备命令
        CommandLine commandLine = wrapper.getCommandLine();
        // 底层命令执行器
        Executor executor = buildAndConfigExecutor(exitValueNumber, timeoutSeconds, new DefaultExecutor(),
                stream, null, destroyer);
        executeAsyncStartAndLog(command, inFileCommand, commandLine, executor, stream);
    }

    /**
     * 从parameterObject中获取目标对象，找不到则返回null
     *
     * @param parameterObject 参数
     * @return {@link ResultHandlerStream}
     */
    protected <T> T getTargetObject(Object parameterObject, Class<T> targetClass) {

        boolean isMap = Map.class.isAssignableFrom(parameterObject.getClass());
        // 参数不是Map结束方法
        if (!isMap) {
            return null;
        }

        String targetKey = null;
        Map<String, Object> parameterMap = (Map<String, Object>) parameterObject;
        Set<Map.Entry<String, Object>> entrySet = parameterMap.entrySet();
        // 遍历寻找目标
        for (Map.Entry<String, Object> entry : entrySet) {
            Object value = entry.getValue();

            // 是否为目标对象
            boolean isTargetClass;
            if (value == null) {
                isTargetClass = false;
            } else {
                isTargetClass = targetClass.isAssignableFrom(value.getClass());
            }

            if (isTargetClass) {
                targetKey = entry.getKey();
            }

        }

        // 不为空代表找到目标
        if (targetKey != null) {
            // 根据目标key返回对象
            return (T) parameterMap.get(targetKey);
        } else {
            return null;
        }

    }


    /**
     * 构建与配置底层的命令执行器
     *
     * @param ms       {@link MappedStatement}
     * @param executor 底层的命令执行器 {@link Executor}
     * @param stream   结果处理流
     * @return {@link Executor}
     */
    protected Executor buildAndConfigExecutor(
            MappedStatement ms,
            Executor executor,
            ResultHandlerStream stream
    ) {
        return buildAndConfigExecutor(ms, executor, stream, null, null);
    }

    /**
     * 构建与配置底层的命令执行器
     *
     * @param ms        {@link MappedStatement}
     * @param executor  底层的命令执行器 {@link Executor}
     * @param stream    结果处理流
     * @param destroyer 子进程破坏者
     * @return {@link Executor}
     */
    protected Executor buildAndConfigExecutor(
            MappedStatement ms,
            Executor executor,
            ResultHandlerStream stream,
            File workDirectory,
            ProcessDestroyer destroyer
    ) {
        return buildAndConfigExecutor(ms.getExitValueNumber(), ms.getTimeout(), executor,
                stream, null, null);
    }

    protected Executor buildAndConfigExecutor(
            Integer exitValueNumber,
            Integer timeoutSeconds,
            Executor executor,
            ResultHandlerStream stream
    ) {
        return buildAndConfigExecutor(exitValueNumber, timeoutSeconds, executor,
                stream, null, null);
    }

    protected Executor buildAndConfigExecutor(
            Integer exitValueNumber,
            Integer timeoutSeconds,
            Executor executor,
            ResultHandlerStream stream,
            File workDirectory,
            ProcessDestroyer destroyer
    ) {
        // 设置该命令执行成功时的返回码，若返回不是该数值则抛出异常
        if (exitValueNumber == null) {
            // 默认0位正常退出
            exitValueNumber = 0;
        }
        executor.setExitValue(exitValueNumber);
        // 子进程超时时间
        if (timeoutSeconds == null) {
            // 默认24小时
            timeoutSeconds = 24 * 3600;
        }
        // 超时时间毫秒
        int milliseconds = secondsToMillisecond(timeoutSeconds);
        ExecuteWatchdog watchdog = new ExecuteWatchdog(milliseconds);
        executor.setWatchdog(watchdog);
        // 子进程流
        // 日志记录等其它功能应该由抽象类ResultHandlerStream的子类实现
        ExecuteStreamHandler pumpStreamHandler;
        if (stream != null) {
            pumpStreamHandler = new PumpStreamHandler(stream);
        } else {
            pumpStreamHandler = new PumpStreamHandler();
        }
        executor.setStreamHandler(pumpStreamHandler);
        // 设置子进程工作目录
        if (workDirectory != null) {
            executor.setWorkingDirectory(workDirectory);
        }
        // 设置进程破坏者
        if (destroyer != null) {
            executor.setProcessDestroyer(destroyer);
        }

        return executor;
    }

    /**
     * 获取命令字符串
     *
     * @param ms              {@link MappedStatement}
     * @param parameterObject 参数
     * @return 命令字符串
     */
    protected CommandWrapper getCommandWrapper(MappedStatement ms, Object parameterObject) {
        // 从BoundCommand获取命令
        BoundCommand boundCommand = ms.getBoundSql(parameterObject);
        // XML中的原始命令
        String originCommand = boundCommand.getSql();
        // 纠正去空白字符后的命令
        String rectifyCommand = rectifyCommand(originCommand);
        // 整合空格后的命令
        String integrateSpaceCommand = integrateSpace(rectifyCommand);
        // 准备命令文件
        CommandFileCustomize customize = this.getTargetObject(parameterObject, CommandFileCustomize.class);
        // 创建
        CommandFileCustomize newCustomize = createCommandFile(customize);
        File commandFile = newCustomize.getCommandFile();
        // 写入命令
        newCustomize.writeCommandFile(integrateSpaceCommand, commandFile);
        // 构建CommandLine
        CommandLine commandLine = buildCommandLine(commandFile);
        // 取出命令数组
        String[] commands = commandLine.toStrings();
        // 转为字符串，一行命令
        String commandStr = ArrayUtil.join(commands, " ");
        // 构造对象
        return new CommandWrapper(commandStr, commandLine, integrateSpaceCommand, commandFile);
    }

    /**
     * 获取命令字符串
     *
     * @return 命令字符串
     */
    protected CommandWrapper getCommandWrapper(AbstractCommand command, CommandFileCustomize customize) {
        command.assemble();
        // 创建
        CommandFileCustomize newCustomize = createCommandFile(customize);
        File commandFile = newCustomize.getCommandFile();
        // 获取原始命令行对象
        CommandLine originalCommandLine = command.getCommandLine();
        // 获取用于存放在文件中的命令
        String[] commandArray = originalCommandLine.toStrings();
        String inFileCommand = ArrayUtil.join(commandArray, " ");
        // 写入命令
        newCustomize.writeCommandFile(inFileCommand, commandFile);
        // 构建CommandLine
        CommandLine commandLine = buildCommandLine(commandFile);
        // 取出命令数组
        String[] commands = commandLine.toStrings();
        // 转为字符串，一行命令
        String commandStr = ArrayUtil.join(commands, " ");
        // 构造对象
        return new CommandWrapper(commandStr, commandLine, inFileCommand, commandFile);
    }

    /**
     * 创建命令文件
     *
     * @param customize 命令文件定制器
     * @return 命令文件
     */
    protected CommandFileCustomize createCommandFile(CommandFileCustomize customize) {
        // Mapper接口方法参数没有CommandFileCustomize，使用默认命令文件
        if (customize == null) {
            customize = new DefaultCommandFile();
        }
        // 创建
        File commandFile = customize.createCommandFile();
        // 无法提供自定义命令文件，使用默认命令文件
        if (commandFile == null) {
            customize = new DefaultCommandFile();
            commandFile = customize.createCommandFile();
        }
        customize.setCommandFile(commandFile);
        return customize;
    }


    /**
     * 返回命令
     *
     * @param stream        结果处理流
     * @param command       命令
     * @param inFileCommand 文件内命令
     */
    protected void returnCommand(ResultHandlerStream stream, String command, String inFileCommand) {
        if (stream != null) {
            // 返回命令字符串
            stream.setCommand(command);
            stream.setInFileCommand(inFileCommand);
        }
    }


    protected CommandLine buildCommandLine(File commandFile) {
        // 获取命令文件绝对路径
        String commandFilePath = commandFile.getAbsolutePath();

        CommandLine commandLine;
        if (FileUtil.isWindows()) {
            // 添加到执行命令
            commandLine = CommandLine.parse(commandFilePath);
        } else {
            commandLine = new CommandLine("/bin/bash");
            // 添加到执行命令
            commandLine.addArgument(commandFilePath);
        }

        return commandLine;
    }


    /**
     * 纠正命令，去除字符串前后的英文空格，以及字符串内容中的制表符、windows与unix换行符
     *
     * @param command 命令字符串
     * @return 纠正完成命令字符串
     */
    protected String rectifyCommand(String command) {
        return StringUtil.trimBlankCharacter(command);
    }

    /**
     * 整合空格
     *
     * @param command 命令字符串
     * @return 整合完成的命令字符串
     */
    protected String integrateSpace(String command) {
        Matcher matcher = PATTERN.matcher(command);
        return matcher.replaceAll(" ");
    }

    /**
     * 秒转换为毫秒
     *
     * @param timeoutSeconds 超时时间（秒）
     * @return 超时时间（毫秒）
     */
    protected int secondsToMillisecond(int timeoutSeconds) {
        final int oneThousand = 1000;
        return timeoutSeconds * oneThousand;
    }

    /**
     * 命令包装
     */
    protected static class CommandWrapper {
        /**
         * 命令框架执行的命令
         */
        private String command;

        /**
         * 命令行对象
         */
        private CommandLine commandLine;

        /**
         * 文件内真实执行的命令
         */
        private String inFileCommand;

        /**
         * 可执行的命令文件
         */
        private File commandFile;

        public CommandWrapper(String command, CommandLine commandLine, String inFileCommand, File commandFile) {
            this.command = command;
            this.commandLine = commandLine;
            this.inFileCommand = inFileCommand;
            this.commandFile = commandFile;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public CommandLine getCommandLine() {
            return commandLine;
        }

        public void setCommandLine(CommandLine commandLine) {
            this.commandLine = commandLine;
        }

        public String getInFileCommand() {
            return inFileCommand;
        }

        public void setInFileCommand(String inFileCommand) {
            this.inFileCommand = inFileCommand;
        }

        public File getCommandFile() {
            return commandFile;
        }

        public void setCommandFile(File commandFile) {
            this.commandFile = commandFile;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("commandWrapper{");
            sb.append("command='").append(command).append('\'');
            sb.append(", commandLine=").append(commandLine);
            sb.append(", inFileCommand='").append(inFileCommand).append('\'');
            sb.append(", commandFile=").append(commandFile);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CommandWrapper that = (CommandWrapper) o;
            return Objects.equals(command, that.command) && Objects.equals(commandLine, that.commandLine) && Objects.equals(inFileCommand, that.inFileCommand) && Objects.equals(commandFile, that.commandFile);
        }

        @Override
        public int hashCode() {
            return Objects.hash(command, commandLine, inFileCommand, commandFile);
        }
    }

}
