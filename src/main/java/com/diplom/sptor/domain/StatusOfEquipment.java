package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by user on 10.05.2016.
 */
@Entity
@Table(name = "toir.status_of_equipment")
@Proxy(lazy=false)
public class StatusOfEquipment {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private int status_id;

    private String status;

    private String description;

    public StatusOfEquipment() {
    }

    public StatusOfEquipment(String status, String description) {
        this.status = status;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatusOfEquipment)) return false;

        StatusOfEquipment that = (StatusOfEquipment) o;

        if (getStatus_id() != that.getStatus_id()) return false;
        if (!getStatus().equals(that.getStatus())) return false;
        return !(getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null);

    }

    @Override
    public int hashCode() {
        int result = getStatus_id();
        result = 31 * result + getStatus().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
