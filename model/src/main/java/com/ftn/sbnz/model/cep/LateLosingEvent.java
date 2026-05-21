package com.ftn.sbnz.model.cep;

public class LateLosingEvent {
    private Long timestamp;
    private Integer minute;

    public LateLosingEvent() {
    }

    public LateLosingEvent(Long timestamp, Integer minute) {
        this.timestamp = timestamp;
        this.minute = minute;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "LateLosingEvent{" +
                "timestamp=" + timestamp +
                ", minute=" + minute +
                '}';
    }
}
