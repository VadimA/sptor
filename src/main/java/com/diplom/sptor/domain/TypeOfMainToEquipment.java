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

    @JoinColumn(name = "type_of_equipment_id")
    private int typeOfEquipmentId;

    @JoinColumn(name = "type_of_maintenance_id")
    private int typeOfMaintenanceId;

    private double duration;

    private double complexity;

    private double periodicity;

    public TypeOfMainToEquipment() {
    }

    public TypeOfMainToEquipment(int typeOfEquipmentId, int typeOfMaintenanceId, double duration, double complexity,
                                 double periodicity) {
        this.typeOfEquipmentId = typeOfEquipmentId;
        this.typeOfMaintenanceId = typeOfMaintenanceId;
        this.duration = duration;
        this.complexity = complexity;
        this.periodicity = periodicity;
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
        return Double.compare(that.periodicity, periodicity) == 0;

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
        temp = Double.doubleToLongBits(periodicity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
