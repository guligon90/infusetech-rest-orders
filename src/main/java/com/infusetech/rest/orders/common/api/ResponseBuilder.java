package com.infusetech.rest.orders.common.api;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseBuilder<T> {
    public ResponseEntity<ApiResponse<T>> buildResponse(
        HttpHeaders httpHeader,
        int httpStatusCode,
        String message,
        T data,
        Map<String, Object> otherParams
    ) {
        return new ApiResponse.ApiResponseBuilder<T>(httpStatusCode, message)
            .withHttpHeader(httpHeader)
            .withData(data)
            .withOtherParams(otherParams)
            .build();
    }

    public ResponseEntity<ApiResponse<T>> buildResponse(
        int httpStatusCode,
        String message,
        T data,
        Map<String, Object> otherParams
    ) {
        return new ApiResponse.ApiResponseBuilder<T>(httpStatusCode, message)
            .withData(data)
            .withOtherParams(otherParams)
            .build();
    }

    public ResponseEntity<ApiResponse<T>> buildResponse(
        int httpStatusCode,
        String message,
        Map<String, Object> otherParams
    ) {
        return new ApiResponse.ApiResponseBuilder<T>(httpStatusCode, message)
            .withOtherParams(otherParams)
            .build();
    }

    public ResponseEntity<ApiResponse<T>> buildResponse(
        int httpStatusCode,
        String message
    ) {
        return new ApiResponse.ApiResponseBuilder<T>(httpStatusCode, message).build();
    }

    public ResponseEntity<ApiResponse<T>> buildResponse(
        HttpHeaders httpHeader,
        int httpStatusCode,
        String message,
        T data
    ) {
        return new ApiResponse.ApiResponseBuilder<T>(httpStatusCode, message)
            .withHttpHeader(httpHeader)
            .withData(data)
            .build();
    }

    public ResponseEntity<ApiResponse<T>> buildResponse(
        HttpHeaders httpHeader,
        int httpStatusCode,
        String message,
        Map<String, Object> otherParams
    ) {
        return new ApiResponse.ApiResponseBuilder<T>(httpStatusCode, message)
            .withHttpHeader(httpHeader)
            .withOtherParams(otherParams)
            .build();
    }

    public ResponseEntity<ApiResponse<T>> buildResponse(
        HttpHeaders httpHeader,
        int httpStatusCode,
        String message
    ) {
        return new ApiResponse.ApiResponseBuilder<T>(httpStatusCode, message)
            .withHttpHeader(httpHeader)
            .build();
    }

    public ResponseEntity<ApiResponse<T>> buildResponse(
        int httpStatusCode,
        String message,
        T data
    ) {
        return new ApiResponse.ApiResponseBuilder<T>(httpStatusCode, message)
            .withData(data)
            .build();
    }
}
