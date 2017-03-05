package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by user on 14.04.2016.
 */
@Entity
@Table(name = "downtime")
@Proxy(lazy=false)
public class DownTime {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int down_time_id;

    @ManyToOne()
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    private Date date_of_adding;

    private double value;

    @ManyToOne()
    @JoinColumn(name = "responsible")
    private User responsible;

    public DownTime() {}

    public DownTime(Equipment equipment, Date date_of_adding, double value, User responsible) {
        this.equipment = equipment;
        this.date_of_adding = date_of_adding;
        this.value = value;
        this.responsible = responsible;
    }

    public int getDown_time_id() {
        return down_time_id;
    }

    public void setDown_time_id(int down_time_id) {
        this.down_time_id = down_time_id;
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
        if (!(o instanceof DownTime)) return false;

        DownTime downTime = (DownTime) o;

        if (getDown_time_id() != downTime.getDown_time_id()) return false;
        if (Double.compare(downTime.getValue(), getValue()) != 0) return false;
        if (!getEquipment().equals(downTime.getEquipment())) return false;
        if (!getDate_of_adding().equals(downTime.getDate_of_adding())) return false;
        return getResponsible().equals(downTime.getResponsible());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getDown_time_id();
        result = 31 * result + getEquipment().hashCode();
        result = 31 * result + getDate_of_adding().hashCode();
        temp = Double.doubleToLongBits(getValue());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getResponsible().hashCode();
        return result;
    }
}
