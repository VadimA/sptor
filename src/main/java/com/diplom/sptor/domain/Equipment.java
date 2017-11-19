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

    @Column(name = "downtime")
    private double downtime;

    @ManyToOne()
    @JoinColumn(name = "status")
    private StatusOfEquipment status;

    public Equipment() {}

    public Equipment(String equipmentName, TypeOfEquipment typeOfEquipment, Subdivisions subdivision,
                     int inventoryNumber, Date graduationYear, String producerOfEquipment, String description,
                     double workingHours, double downtime, StatusOfEquipment status) {
        this.equipmentName = equipmentName;
        this.typeOfEquipment = typeOfEquipment;
        this.subdivision = subdivision;
        this.inventoryNumber = inventoryNumber;
        this.graduationYear = graduationYear;
        this.producerOfEquipment = producerOfEquipment;
        this.description = description;
        this.workingHours = workingHours;
        this.downtime = downtime;
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

    public double getDowntime() {
        return downtime;
    }

    public void setDowntime(double downtime) {
        this.downtime = downtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Equipment equipment = (Equipment) o;

        if (equipmentId != equipment.equipmentId) return false;
        if (inventoryNumber != equipment.inventoryNumber) return false;
        if (Double.compare(equipment.workingHours, workingHours) != 0) return false;
        if (Double.compare(equipment.downtime, downtime) != 0) return false;
        if (!equipmentName.equals(equipment.equipmentName)) return false;
        if (!typeOfEquipment.equals(equipment.typeOfEquipment)) return false;
        if (!subdivision.equals(equipment.subdivision)) return false;
        if (graduationYear != null ? !graduationYear.equals(equipment.graduationYear) : equipment.graduationYear != null)
            return false;
        if (!producerOfEquipment.equals(equipment.producerOfEquipment)) return false;
        if (description != null ? !description.equals(equipment.description) : equipment.description != null)
            return false;
        return status.equals(equipment.status);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = equipmentId;
        result = 31 * result + equipmentName.hashCode();
        result = 31 * result + typeOfEquipment.hashCode();
        result = 31 * result + subdivision.hashCode();
        result = 31 * result + inventoryNumber;
        result = 31 * result + (graduationYear != null ? graduationYear.hashCode() : 0);
        result = 31 * result + producerOfEquipment.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(workingHours);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(downtime);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + status.hashCode();
        return result;
    }
}
