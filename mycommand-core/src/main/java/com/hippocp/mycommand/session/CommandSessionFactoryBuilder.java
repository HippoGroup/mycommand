package com.hippocp.mycommand.session;

/**
 * @author ZhouYifan
 * @date 2022/3/14
 */
public class CommandSessionFactoryBuilder {

    public CommandSessionFactory build(Configuration config) {
        return new DefaultCommandSessionFactory(config);
    }

}
