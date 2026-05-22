package com.ftn.sbnz.model.forward;

public class TacticalAdvantage {
    private String title;
    private String description;

    public TacticalAdvantage() {
    }

    public TacticalAdvantage(String title, String description) {
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

    @Override
    public String toString() {
        return "TacticalAdvantage{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
