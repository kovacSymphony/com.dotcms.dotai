package com.dotcms.plugin.dotai.model;

public class AIErrorResponseDTO {

    private String error;
    private String message;

    public AIErrorResponseDTO(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
