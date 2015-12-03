package com.diplom.sptor.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by user on 25.11.2015.
 */
@Entity
@Table(name = "toir.equipment")
public class Equipment implements Serializable{

    @Id
    @Column(name = "equipment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipment_id;
    @Column(name = "equipment_name")
    private String equipment_name;
    @Column(name = "type_of_equipment_id")
    private Integer type_of_equipment_id;
    @Column(name = "subdivision_id")
    private Integer subdivision_id;
    @Column(name = "inventory_number")
    private Integer inventory_number;
    @Column(name = "graduation_year")
    private Date graduation_year;
    @Column(name = "producer_of_equipment")
    private String producer_of_equipment;
    @Column(name = "description")
    private String description;

    public Equipment() {
    }

    public Integer getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(Integer equipment_id) {
        this.equipment_id = equipment_id;
    }

    public String getEquipment_name() {
        return equipment_name;
    }

    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    public Integer getType_of_equipment_id() {
        return type_of_equipment_id;
    }

    public void setType_of_equipment_id(Integer type_of_equipment_id) {
        this.type_of_equipment_id = type_of_equipment_id;
    }

    public Integer getSubdivision_id() {
        return subdivision_id;
    }

    public void setSubdivision_id(Integer subdivision_id) {
        this.subdivision_id = subdivision_id;
    }

    public Integer getInventory_number() {
        return inventory_number;
    }

    public void setInventory_number(Integer inventory_number) {
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

        if (!getEquipment_id().equals(equipment.getEquipment_id())) return false;
        if (!getEquipment_name().equals(equipment.getEquipment_name())) return false;
        if (!getType_of_equipment_id().equals(equipment.getType_of_equipment_id())) return false;
        if (!getSubdivision_id().equals(equipment.getSubdivision_id())) return false;
        if (!getInventory_number().equals(equipment.getInventory_number())) return false;
        if (getGraduation_year() != null ? !getGraduation_year().equals(equipment.getGraduation_year()) : equipment.getGraduation_year() != null)
            return false;
        if (getProducer_of_equipment() != null ? !getProducer_of_equipment().equals(equipment.getProducer_of_equipment()) : equipment.getProducer_of_equipment() != null)
            return false;
        return !(getDescription() != null ? !getDescription().equals(equipment.getDescription()) : equipment.getDescription() != null);

    }

    @Override
    public int hashCode() {
        int result = getEquipment_id().hashCode();
        result = 31 * result + getEquipment_name().hashCode();
        result = 31 * result + getType_of_equipment_id().hashCode();
        result = 31 * result + getSubdivision_id().hashCode();
        result = 31 * result + getInventory_number().hashCode();
        result = 31 * result + (getGraduation_year() != null ? getGraduation_year().hashCode() : 0);
        result = 31 * result + (getProducer_of_equipment() != null ? getProducer_of_equipment().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
