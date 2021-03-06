package com.langyastudio.cloud.exception;


import com.langyastudio.common.data.EC;
import com.langyastudio.common.data.IErrorCode;

/**
 * 自定义异常处理
 */
public class MyException extends RuntimeException
{
    private Integer code;

    public MyException(IErrorCode ec)
    {
        super(ec.getMsg());
        this.code = ec.getCode();
    }

    public MyException(String msg)
    {
        super(msg);
        this.code = EC.ERROR.getCode();
    }

    public MyException(Integer code, String msg)
    {
        super(msg);
        this.code = code;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }
}
