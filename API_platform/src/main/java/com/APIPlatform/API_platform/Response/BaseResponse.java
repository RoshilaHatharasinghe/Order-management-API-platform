package com.APIPlatform.API_platform.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {

    private Object data;
    private String message;

    public BaseResponse(Object data, String message) {
        this.data = data;
        this.message = message;
    }
}
