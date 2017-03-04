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
@Table(name = "equipment")
@Proxy(lazy=false)
public class Equipment implements Serializable{

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    @Column(name = "equipment_id")
    private int equipmentId;

    @Column(name = "equipment_name")
    private String equipmentName;

    @ManyToOne()
    @JoinColumn(name = "type_of_equipment_id")
    private TypeOfEquipment typeOfEquipment;

    @ManyToOne()
    @JoinColumn(name = "subdivision_id")
    private Subdivisions subdivision;

    @Column(name = "inventory_number")
    private int inventoryNumber;

    @Column(name = "graduation_year")
    private Date graduationYear;

    @Column(name = "producer_of_equipment")
    private String producerOfEquipment;

    private String description;

    @Column(name = "working_hours")
    private double workingHours;

    @ManyToOne()
    @JoinColumn(name = "status")
    private StatusOfEquipment status;

    public Equipment() {}

    public Equipment(String equipmentName, TypeOfEquipment typeOfEquipment, Subdivisions subdivision,
                     int inventoryNumber, Date graduationYear, String producerOfEquipment, String description,
                     double workingHours, StatusOfEquipment status) {
        this.equipmentName = equipmentName;
        this.typeOfEquipment = typeOfEquipment;
        this.subdivision = subdivision;
        this.inventoryNumber = inventoryNumber;
        this.graduationYear = graduationYear;
        this.producerOfEquipment = producerOfEquipment;
        this.description = description;
        this.workingHours = workingHours;
        this.status = status;
    }

    public Equipment(String equipmentName, int typeOfEquipment, int subdivision,
                     int inventoryNumber, Date graduationYear, String producerOfEquipment, String description,
                     double workingHours, int status) {
        this.equipmentName = equipmentName;
        this.typeOfEquipment.setType_of_equipment_id(typeOfEquipment);
        this.subdivision.setSubdivision_id(subdivision);
        this.inventoryNumber = inventoryNumber;
        this.graduationYear = graduationYear;
        this.producerOfEquipment = producerOfEquipment;
        this.description = description;
        this.workingHours = workingHours;
        this.status.setStatus_id(status);
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public TypeOfEquipment getTypeOfEquipment() {
        return typeOfEquipment;
    }

    public void setTypeOfEquipment(TypeOfEquipment typeOfEquipment) {
        this.typeOfEquipment = typeOfEquipment;
    }

    public Subdivisions getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivisions subdivision) {
        this.subdivision = subdivision;
    }

    public int getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(int inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public Date getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(Date graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getProducerOfEquipment() {
        return producerOfEquipment;
    }

    public void setProducerOfEquipment(String producerOfEquipment) {
        this.producerOfEquipment = producerOfEquipment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
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

        if (getEquipmentId() != equipment.getEquipmentId()) return false;
        if (getInventoryNumber() != equipment.getInventoryNumber()) return false;
        if (Double.compare(equipment.getWorkingHours(), getWorkingHours()) != 0) return false;
        if (!getEquipmentName().equals(equipment.getEquipmentName())) return false;
        if (!getTypeOfEquipment().equals(equipment.getTypeOfEquipment())) return false;
        if (!getSubdivision().equals(equipment.getSubdivision())) return false;
        if (getGraduationYear() != null ? !getGraduationYear().equals(equipment.getGraduationYear()) : equipment.getGraduationYear() != null)
            return false;
        if (!getProducerOfEquipment().equals(equipment.getProducerOfEquipment())) return false;
        if (getDescription() != null ? !getDescription().equals(equipment.getDescription()) : equipment.getDescription() != null)
            return false;
        return getStatus().equals(equipment.getStatus());

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getEquipmentId();
        result = 31 * result + getEquipmentName().hashCode();
        result = 31 * result + getTypeOfEquipment().hashCode();
        result = 31 * result + getSubdivision().hashCode();
        result = 31 * result + getInventoryNumber();
        result = 31 * result + (getGraduationYear() != null ? getGraduationYear().hashCode() : 0);
        result = 31 * result + getProducerOfEquipment().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        temp = Double.doubleToLongBits(getWorkingHours());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getStatus().hashCode();
        return result;
    }
}
