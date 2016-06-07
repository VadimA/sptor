package com.diplom.sptor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 25.11.2015.
 */
@Entity
@Table(name = "toir.equipment")
@Proxy(lazy=false)
public class Equipment implements Serializable{

    //@Column(name = "equipment_id")
    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int equipment_id;

    private String equipment_name;

    @ManyToOne()
    @JoinColumn(name = "type_of_equipment_id")
    private TypeOfEquipment type_of_equipment;

    @ManyToOne()
    @JoinColumn(name = "subdivision_id")
    private Subdivisions subdivision;

    private int inventory_number;

    private Date graduation_year;

    private String producer_of_equipment;

    private String description;

    private double working_hours;

    @ManyToOne()
    @JoinColumn(name = "status")
    private StatusOfEquipment status;

    public Equipment() {}

    public Equipment(String equipment_name, TypeOfEquipment type_of_equipment, Subdivisions subdivision,
                     int inventory_number, Date graduation_year, String producer_of_equipment, String description,
                     double working_hours, StatusOfEquipment status) {
        this.equipment_name = equipment_name;
        this.type_of_equipment = type_of_equipment;
        this.subdivision = subdivision;
        this.inventory_number = inventory_number;
        this.graduation_year = graduation_year;
        this.producer_of_equipment = producer_of_equipment;
        this.description = description;
        this.working_hours = working_hours;
        this.status = status;
    }

    public int getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(int equipment_id) {
        this.equipment_id = equipment_id;
    }

    public String getEquipment_name() {
        return equipment_name;
    }

    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    public TypeOfEquipment getType_of_equipment() {
        return type_of_equipment;
    }

    public void setType_of_equipment(TypeOfEquipment type_of_equipment) {
        this.type_of_equipment = type_of_equipment;
    }

    public Subdivisions getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivisions subdivision) {
        this.subdivision = subdivision;
    }

    public int getInventory_number() {
        return inventory_number;
    }

    public void setInventory_number(int inventory_number) {
        this.inventory_number = inventory_number;
    }

    public Date getGraduation_year() {
        return graduation_year;
    }

    public void setGraduation_year(Date graduation_year) {
        this.graduation_year = graduation_year;
    }

    public String getProducer_of_equipment() {
        return producer_of_equipment;
    }

    public void setProducer_of_equipment(String producer_of_equipment) {
        this.producer_of_equipment = producer_of_equipment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(double working_hours) {
        this.working_hours = working_hours;
    }

    public StatusOfEquipment getStatus() {
        return status;
    }

    public void setStatus(StatusOfEquipment status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipment)) return false;

        Equipment equipment = (Equipment) o;

        if (getEquipment_id() != equipment.getEquipment_id()) return false;
        if (getInventory_number() != equipment.getInventory_number()) return false;
        if (Double.compare(equipment.getWorking_hours(), getWorking_hours()) != 0) return false;
        if (!getEquipment_name().equals(equipment.getEquipment_name())) return false;
        if (!getType_of_equipment().equals(equipment.getType_of_equipment())) return false;
        if (!getSubdivision().equals(equipment.getSubdivision())) return false;
        if (getGraduation_year() != null ? !getGraduation_year().equals(equipment.getGraduation_year()) : equipment.getGraduation_year() != null)
            return false;
        if (!getProducer_of_equipment().equals(equipment.getProducer_of_equipment())) return false;
        if (getDescription() != null ? !getDescription().equals(equipment.getDescription()) : equipment.getDescription() != null)
            return false;
        return getStatus().equals(equipment.getStatus());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getEquipment_id();
        result = 31 * result + getEquipment_name().hashCode();
        result = 31 * result + getType_of_equipment().hashCode();
        result = 31 * result + getSubdivision().hashCode();
        result = 31 * result + getInventory_number();
        result = 31 * result + (getGraduation_year() != null ? getGraduation_year().hashCode() : 0);
        result = 31 * result + getProducer_of_equipment().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        temp = Double.doubleToLongBits(getWorking_hours());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getStatus().hashCode();
        return result;
    }
}
