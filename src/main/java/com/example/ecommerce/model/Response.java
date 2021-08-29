package com.example.ecommerce.model;

import lombok.Data;

@Data
public class Response {
    private String code;
    private String message;
    private Object data;

    public Response(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public Response() {
    }
}
