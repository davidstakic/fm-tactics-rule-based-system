package com.ftn.sbnz.model.cep;

public class ClosingLeadEvent {
    private Long timestamp;
    private Integer minute;

    public ClosingLeadEvent() {
    }

    public ClosingLeadEvent(Long timestamp, Integer minute) {
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
        return "ClosingLeadEvent{" +
                "timestamp=" + timestamp +
                ", minute=" + minute +
                '}';
    }
}
