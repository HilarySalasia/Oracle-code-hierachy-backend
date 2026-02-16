package com.turnquest.code_hierachy.models;

public class ContentPart {
    private String text;

    public ContentPart() {
    }

    public ContentPart(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}