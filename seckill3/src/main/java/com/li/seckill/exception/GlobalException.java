package com.li.seckill.exception;

import com.li.seckill.result.Result;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
public class GlobalException extends RuntimeException {

    public GlobalException(){

    }
    private Result result;
    public GlobalException(Result result){
        this.result=result;
    }

    public Result getResult() {
        return result;
    }
}
