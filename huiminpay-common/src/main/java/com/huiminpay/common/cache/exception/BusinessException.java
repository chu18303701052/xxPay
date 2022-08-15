package com.huiminpay.common.cache.exception;

import com.huiminpay.common.cache.domain.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException{

    private ErrorCode errorCode;
    //private String errMessage;

}
