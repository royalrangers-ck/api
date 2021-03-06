package com.royalrangers.utils;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.royalrangers.dto.ResponseResult;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ResponseBuilder {

    public static ResponseResult success() {
        return new ResponseResult(true, new EmptyJsonResponse());
    }

    public static ResponseResult success(Object data) {
        return new ResponseResult(true, data);
    }

    public static ResponseResult success(String message) {
        return new ResponseResult(true, new ResponseMessage(message));
    }
    public static ResponseResult success(String key, String value) {
        Map<String, String> data = new HashMap<>();
        data.put(key, value);
        return new ResponseResult(true, data);
    }

    public static ResponseResult fail() {
        return new ResponseResult(false, new EmptyJsonResponse());
    }

    public static ResponseResult fail(String message) {
        log.error(message);
        return new ResponseResult(false, new ResponseMessage(message));
    }

    @JsonSerialize
    private static class EmptyJsonResponse { }

    @Getter
    @JsonSerialize
    private static class ResponseMessage{
        private String message;

        ResponseMessage (String message) {
            this.message = message;
        }
    }
}
