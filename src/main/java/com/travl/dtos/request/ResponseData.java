package com.travl.dtos.request;

import lombok.Data;

@Data
public class ResponseData {
    private boolean success;
    private String errorMessage;
    private Object data;
}
