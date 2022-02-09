package com.langyastudio.cloud.dubbo.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.rpc.RpcContext;

/**
 * Logger Utilities.
 */
@Log4j2
public abstract class LoggerUtils
{
    public static void log(String url, Object result)
    {
        String message = String
                .format("The client[%s] uses '%s' protocol to call %s : %s",
                        RpcContext.getContext().getRemoteHostName(),
                        RpcContext.getContext().getUrl() == null ? "N/A"
                                : RpcContext.getContext().getUrl().getProtocol(),
                        url, result);

        if (log.isInfoEnabled())
        {
			log.info(message);
        }
    }
}