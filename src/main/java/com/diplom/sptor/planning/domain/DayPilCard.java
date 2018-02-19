package com.diplom.sptor.planning.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import  org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by user on 12.02.2018.
 */
public class DayPilCard {

    private int id;

    private String text;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    private Date start;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME)
    private Date end;

    private int resource;

    private String color;

    public DayPilCard(int id, String text, Date  start, Date  end, int resource, String color) {
        this.id = id;
        this.text = text;
        this.start = start;
        this.end = end;
        this.resource = resource;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date  getStart() {
        return start;
    }

    public void setStart(Date  start) {
        this.start = start;
    }

    public Date  getEnd() {
        return end;
    }

    public void setEnd(Date  end) {
        this.end = end;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
