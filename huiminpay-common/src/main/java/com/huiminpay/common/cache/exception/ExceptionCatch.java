package com.huiminpay.common.cache.exception;

import com.huiminpay.common.cache.domain.CommonErrorCode;
import com.huiminpay.common.cache.domain.RestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//异常捕获类 两种异常 一种预知异常一种不预知异常
//@ControllerAdvice//对controller层增强 异常增强 一旦启用就看不到真正的错误了 所以一般开发阶段注释掉
@Slf4j
public class ExceptionCatch {
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public RestErrorResponse catchBusinessException(BusinessException businessException){
        return new RestErrorResponse(businessException.getErrorCode().getCode(),businessException.getErrorCode().getDesc());
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestErrorResponse catchBusinessException(Exception e){
        return new RestErrorResponse(CommonErrorCode.UNKNOWN.getCode(), CommonErrorCode.UNKNOWN.getDesc());
    }





}
