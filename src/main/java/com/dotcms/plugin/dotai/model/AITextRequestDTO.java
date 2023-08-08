package com.dotcms.plugin.dotai.model;

public class AITextRequestDTO {

    private String prompt;

    public AITextRequestDTO(String prompt) {
        this.prompt = prompt;
    }

    public AITextRequestDTO() {
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
