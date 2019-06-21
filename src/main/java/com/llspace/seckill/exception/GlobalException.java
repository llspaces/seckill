package com.llspace.seckill.exception;

import com.llspace.seckill.utils.CodeMsg;
import lombok.Data;

/**
 * <p>@filename GlobalException</p>
 * <p>
 * <p>@description 全局异常信息</p>
 *
 * @author llspace
 * @version 1.0
 * @since 2019/6/20 18:05
 **/
@Data
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }
}
