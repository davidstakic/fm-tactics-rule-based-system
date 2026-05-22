package com.ftn.sbnz.model.dto;

public class BackwardRequirement {
    private String title;
    private String description;

    public BackwardRequirement() {
    }

    public BackwardRequirement(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
