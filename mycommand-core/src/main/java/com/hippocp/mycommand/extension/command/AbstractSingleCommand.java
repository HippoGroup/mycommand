package com.hippocp.mycommand.extension.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象单条命令
 *
 * @author ZhouYifan
 * @date 2022/1/18 10:23
 */
public abstract class AbstractSingleCommand<Children extends AbstractSingleCommand<Children>>
        extends AbstractCommand<Children> {

    private static final Logger log = LoggerFactory.getLogger(AbstractSingleCommand.class);

}
