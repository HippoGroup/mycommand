package com.hippocp.mycommand.executor;

/**
 * @author ZhouYifan
 * @date 2022/3/10
 */
public abstract class BaseCommandExecutor implements CommandExecutor {

    protected CommandExecutor wrapper;

    @Override
    public void setExecutorWrapper(CommandExecutor wrapper) {
        this.wrapper = wrapper;
    }

}
