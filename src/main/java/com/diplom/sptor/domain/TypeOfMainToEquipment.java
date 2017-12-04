package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Table(name = "toir.type_of_main_to_equipment")
@Proxy(lazy=false)
public class TypeOfMainToEquipment {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int type_of_main_to_equipment_id;

    @Column(name = "type_of_equipment_id")
    private int typeOfEquipmentId;

    @Column(name = "type_of_maintenance_id")
    private int typeOfMaintenanceId;

    private double duration;

    private double complexity;

    private int periodicity;

    private double work_hours_limit;

    public TypeOfMainToEquipment() {
    }

    public TypeOfMainToEquipment(int typeOfEquipmentId, int typeOfMaintenanceId, double duration, double complexity,
                                 int periodicity, double work_hours_limit) {
        this.typeOfEquipmentId = typeOfEquipmentId;
        this.typeOfMaintenanceId = typeOfMaintenanceId;
        this.duration = duration;
        this.complexity = complexity;
        this.periodicity = periodicity;
        this.work_hours_limit = work_hours_limit;
    }

    public int getType_of_main_to_equipment_id() {
        return type_of_main_to_equipment_id;
    }

    public void setType_of_main_to_equipment_id(int type_of_main_to_equipment_id) {
        this.type_of_main_to_equipment_id = type_of_main_to_equipment_id;
    }

    public int getTypeOfEquipmentId() {
        return typeOfEquipmentId;
    }

    public void setTypeOfEquipmentId(int typeOfEquipmentId) {
        this.typeOfEquipmentId = typeOfEquipmentId;
    }

    public int getTypeOfMaintenanceId() {
        return typeOfMaintenanceId;
    }

    public void setTypeOfMaintenanceId(int typeOfMaintenanceId) {
        this.typeOfMaintenanceId = typeOfMaintenanceId;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getComplexity() {
        return complexity;
    }

    public void setComplexity(double complexity) {
        this.complexity = complexity;
    }

    public int getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(int periodicity) {
        this.periodicity = periodicity;
    }

    public double getWork_hours_limit() {
        return work_hours_limit;
    }

    public void setWork_hours_limit(double work_hours_limit) {
        this.work_hours_limit = work_hours_limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeOfMainToEquipment that = (TypeOfMainToEquipment) o;

        if (type_of_main_to_equipment_id != that.type_of_main_to_equipment_id) return false;
        if (typeOfEquipmentId != that.typeOfEquipmentId) return false;
        if (typeOfMaintenanceId != that.typeOfMaintenanceId) return false;
        if (Double.compare(that.duration, duration) != 0) return false;
        if (Double.compare(that.complexity, complexity) != 0) return false;
        if (periodicity != that.periodicity) return false;
        return Double.compare(that.work_hours_limit, work_hours_limit) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = type_of_main_to_equipment_id;
        result = 31 * result + typeOfEquipmentId;
        result = 31 * result + typeOfMaintenanceId;
        temp = Double.doubleToLongBits(duration);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(complexity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + periodicity;
        temp = Double.doubleToLongBits(work_hours_limit);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
