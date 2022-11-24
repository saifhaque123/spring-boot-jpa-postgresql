package com.travl.dtos.response;

import lombok.Data;

@Data
public class ResponseData {
    private boolean success;
    private String errorMessage;
    private Object data;
}
