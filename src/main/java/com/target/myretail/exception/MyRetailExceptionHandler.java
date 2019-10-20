package com.target.myretail.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyRetailExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MyRetailExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ ProductNotFoundException.class})
    @ResponseBody
    public String handleException (final ProductNotFoundException exception)
            throws JsonProcessingException
    {
        Map<String, String> map = new HashMap<>();
        map.put("code", exception.getErrorCode());
        map.put("message", exception.getMessage());
        String json = new ObjectMapper().writeValueAsString(map);
        LOG.info(json);
        return json;
    }

}
