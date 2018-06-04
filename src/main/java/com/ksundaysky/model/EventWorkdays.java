package com.ksundaysky.model;

import javax.persistence.Column;

public class EventWorkdays {

    private String start ;

    private String end ;

    private String dow;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDow() {
        return dow;
    }

    public void setDow(String dow) {
        this.dow = dow;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public EventWorkdays() {
        super();
    }

    public EventWorkdays(String start, String end, String dow,String title) {
        this.start = start;
        this.end = end;
        this.dow = dow;
        this.title = title;
    }
}
