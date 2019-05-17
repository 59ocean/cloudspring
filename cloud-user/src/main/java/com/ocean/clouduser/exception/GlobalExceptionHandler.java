package com.ocean.clouduser.exception;

import com.ocean.cloudcommon.utils.ApiResponse;
import com.ocean.cloudcommon.utils.R;
import groovy.util.logging.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    MessageSource messageSource;

    //参数异常
    @ExceptionHandler({org.springframework.web.bind.MissingServletRequestParameterException.class})
    @ResponseBody
    public ApiResponse processRequestParameterException(HttpServletRequest request,
                                              HttpServletResponse response,
                                              MissingServletRequestParameterException e) {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        ApiResponse result = new ApiResponse();
        result.setCode(500);
        result.setMsg(
                messageSource.getMessage("500",
                        null, LocaleContextHolder.getLocale()) + e.getParameterName());
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResponse processDefaultException(HttpServletResponse response,
            Exception e) {
        //log.error("Server exception", e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setContentType("application/json;charset=UTF-8");
        ApiResponse result = new ApiResponse();
        result.setCode(500);
        result.setMsg(messageSource.getMessage("500", null,
                LocaleContextHolder.getLocale()));
        return result;
    }

/*    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ApiResponse processApiException(HttpServletResponse response,
            ApiException e) {
        ApiResponse result = new ApiResponse();
        response.setStatus(e.getApiResultStatus().getHttpStatus());
        response.setContentType("application/json;charset=UTF-8");
        result.setCode(e.getApiResultStatus().getApiResultStatus());
        String message = messageSource.getMessage(e.getApiResultStatus().getMessageResourceName(),
                null, LocaleContextHolder.getLocale());
        result.setMessage(message);
        //log.error("Knowned exception", e.getMessage(), e);
        return result;
    }

    *
     * 内部微服务异常统一处理方法
     
    @ExceptionHandler(InternalApiException.class)
    @ResponseBody
    public ApiResponse processMicroServiceException(HttpServletResponse response,
            InternalApiException e) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        ApiResponse result = new ApiResponse();
        result.setCode(e.getCode());
        result.setMessage(e.getMessage());
        return result;
    }*/
}
