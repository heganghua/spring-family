package xyz.ganghua.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import xyz.ganghua.dto.ResponseResult;

@ControllerAdvice
public class FamilyControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseResult<?> heandleException(Exception e) {
        String message = e.getMessage();
        return ResponseResult.failed(message);
    }

}
