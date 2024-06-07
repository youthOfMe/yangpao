package com.niuma.yangpao.exception;

import com.niuma.yangpao.common.BaseResponse;
import com.niuma.yangpao.common.ErrorCode;
import com.niuma.yangpao.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GloballExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException exception) {
        log.error("businessException: " + exception.getMessage());
        return ResultUtils.error(exception.getCode(), exception.getMessage(), exception.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException exception) {
        log.error("runtimeException: {}", exception);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, exception.getMessage(), "");
    }
}
