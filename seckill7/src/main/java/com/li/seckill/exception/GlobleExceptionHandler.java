package com.li.seckill.exception;

import com.li.seckill.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){

        e.printStackTrace();
        if(e instanceof GlobalException){
            GlobalException ex= (GlobalException) e;
            return ex.getResult();
        }
        else if(e instanceof BindException){
            BindException ex= (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String defaultMessage = error.getDefaultMessage();
            return Result.PARAM_ERROR.fillMsg(defaultMessage);
        }
        else{
            return Result.SERVER_ERROR;
        }
    }
}
