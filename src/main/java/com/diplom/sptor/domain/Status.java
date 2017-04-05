package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by user on 11.03.2016.
 */
@Entity
@Table(name = "status_of_request")
@Proxy(lazy=false)
public class Status {


    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private int status_id;

    private String status;

    public Status() {
    }

    public Status(String status) {
        this.status = status;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Status)) return false;

        Status status1 = (Status) o;

        if (getStatus_id() != status1.getStatus_id()) return false;
        return getStatus().equals(status1.getStatus());

    }

    @Override
    public int hashCode() {
        int result = getStatus_id();
        result = 31 * result + getStatus().hashCode();
        return result;
    }
}