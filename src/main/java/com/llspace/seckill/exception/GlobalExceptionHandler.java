package com.llspace.seckill.exception;

import com.llspace.seckill.utils.CodeMsg;
import com.llspace.seckill.utils.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>@filename GlobalExceptionHandler</p>
 * <p>
 * <p>@description 全局异常处理类</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/20 18:07
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        if(e instanceof GlobalException) {
            GlobalException exception = (GlobalException) e;
            return Result.error(exception.getCodeMsg());
        }else if(e instanceof BindException) {
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else if(e instanceof NoHandlerFoundException){
            NoHandlerFoundException ex = (NoHandlerFoundException) e;
            String msg = ex.getMessage();
            return Result.error(CodeMsg.NOT_FOUND.fillArgs(msg));
        }else{
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

}
