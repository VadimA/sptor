package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by user on 25.11.2015.
 */
@Entity
@Table(name = "toir.equipment")
public class Equipment implements Serializable{

    //@Column(name = "equipment_id")
    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int equipment_id;

    private String equipment_name;


    private int type_of_equipment_id;


    private int subdivision_id;

    private int inventory_number;

    private Date graduation_year;

    private String producer_of_equipment;

    private String description;

    public Equipment() {}

    public Equipment(String equipment_name, int type_of_equipment_id,
                     int subdivision_id, int inventory_number, Date graduation_year,
                     String producer_of_equipment, String description) {
        this.equipment_name = equipment_name;
        this.type_of_equipment_id = type_of_equipment_id;
        this.subdivision_id = subdivision_id;
        this.inventory_number = inventory_number;
        this.graduation_year = graduation_year;
        this.producer_of_equipment = producer_of_equipment;
        this.description = description;
    }

    public Integer getEquipment_id() {
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

    public int getType_of_equipment_id() {
        return type_of_equipment_id;
    }

    public void setType_of_equipment_id(int type_of_equipment_id) {
        this.type_of_equipment_id = type_of_equipment_id;
    }

    public int getSubdivision_id() {
        return subdivision_id;
    }

    public void setSubdivision_id(int subdivision_id) {
        this.subdivision_id = subdivision_id;
    }

    public Integer getInventory_number() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Equipment)) return false;

        Equipment equipment = (Equipment) o;

        if (getEquipment_id() != equipment.getEquipment_id()) return false;
        if (getType_of_equipment_id() != equipment.getType_of_equipment_id()) return false;
        if (getSubdivision_id() != equipment.getSubdivision_id()) return false;
        if (getInventory_number() != equipment.getInventory_number()) return false;
        if (!getEquipment_name().equals(equipment.getEquipment_name())) return false;
        if (getGraduation_year() != null ? !getGraduation_year().equals(equipment.getGraduation_year()) : equipment.getGraduation_year() != null)
            return false;
        if (!getProducer_of_equipment().equals(equipment.getProducer_of_equipment())) return false;
        return !(getDescription() != null ? !getDescription().equals(equipment.getDescription()) : equipment.getDescription() != null);

    }

    @Override
    public int hashCode() {
        int result = getEquipment_id();
        result = 31 * result + getEquipment_name().hashCode();
        result = 31 * result + getType_of_equipment_id();
        result = 31 * result + getSubdivision_id();
        result = 31 * result + getInventory_number();
        result = 31 * result + (getGraduation_year() != null ? getGraduation_year().hashCode() : 0);
        result = 31 * result + getProducer_of_equipment().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
