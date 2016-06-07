package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 14.03.2016.
 */
@Entity
@Table(name = "toir.working_hours_of_eq")
@Proxy(lazy=false)
public class WorkingHours implements Serializable {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int work_hour_id;

    @ManyToOne()
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    private Date date_of_adding;

    private double value;

    @ManyToOne()
    @JoinColumn(name = "responsible")
    private User responsible;

    public WorkingHours() {}

    public WorkingHours(Equipment equipment, Date date_of_adding, double value, User responsible) {
        this.equipment = equipment;
        this.date_of_adding = date_of_adding;
        this.value = value;
        this.responsible = responsible;
    }

    public int getWork_hour_id() {
        return work_hour_id;
    }

    public void setWork_hour_id(int work_hour_id) {
        this.work_hour_id = work_hour_id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Date getDate_of_adding() {
        return date_of_adding;
    }

    public void setDate_of_adding(Date date_of_adding) {
        this.date_of_adding = date_of_adding;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkingHours)) return false;

        WorkingHours that = (WorkingHours) o;

        if (getWork_hour_id() != that.getWork_hour_id()) return false;
        if (Double.compare(that.getValue(), getValue()) != 0) return false;
        if (!getEquipment().equals(that.getEquipment())) return false;
        if (!getDate_of_adding().equals(that.getDate_of_adding())) return false;
        return getResponsible().equals(that.getResponsible());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getWork_hour_id();
        result = 31 * result + getEquipment().hashCode();
        result = 31 * result + getDate_of_adding().hashCode();
        temp = Double.doubleToLongBits(getValue());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getResponsible().hashCode();
        return result;
    }
}
