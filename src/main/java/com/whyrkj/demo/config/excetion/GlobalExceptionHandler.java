package com.whyrkj.demo.config.excetion;

import com.whyrkj.demo.config.model.ResultData;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultData handleException(MethodArgumentNotValidException ex) {
        logger.error("GlobalExceptionResolver.exception={}", ex);
        ResultData rd = new ResultData();
        BindingResult bindingResult = ex.getBindingResult();
        String errorMsg = "";
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            if(CollectionUtils.isNotEmpty(errors)){
                errorMsg = errors.get(0).getDefaultMessage();
            }
        }
        rd.setMessage(errorMsg);
        rd.setStatus(400);
        return rd;
    }
}