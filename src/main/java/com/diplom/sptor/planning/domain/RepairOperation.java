package com.diplom.sptor.planning.domain;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="toir.operations")
@Proxy(lazy=false)
public class RepairOperation {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    @Column(name = "operation_id")
    private int operationId;

    @Column(name = "equipment_id")
    private int equipmentId;

    @Column(name = "type_of_maintenance_id")
    private int typeOfMaintenanceId;

    @Column(name = "organization_id")
    private int organizationId;

    private Date date;

    private double duration;

    @Column(name = "graph_id")
    private int graphicId;

    public RepairOperation() {}

    public RepairOperation(int operationId, int equipmentId, int typeOfMaintenanceId, int organizationId,
                           Date date, double duration, int graphicId) {
        this.operationId = operationId;
        this.equipmentId = equipmentId;
        this.typeOfMaintenanceId = typeOfMaintenanceId;
        this.organizationId = organizationId;
        this.date = date;
        this.duration = duration;
        this.graphicId = graphicId;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getTypeOfMaintenanceId() {
        return typeOfMaintenanceId;
    }

    public void setTypeOfMaintenanceId(int typeOfMaintenanceId) {
        this.typeOfMaintenanceId = typeOfMaintenanceId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getGraphicId() {
        return graphicId;
    }

    public void setGraphicId(int graphicId) {
        this.graphicId = graphicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepairOperation that = (RepairOperation) o;

        if (operationId != that.operationId) return false;
        if (equipmentId != that.equipmentId) return false;
        if (typeOfMaintenanceId != that.typeOfMaintenanceId) return false;
        if (organizationId != that.organizationId) return false;
        if (Double.compare(that.duration, duration) != 0) return false;
        if (graphicId != that.graphicId) return false;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = operationId;
        result = 31 * result + equipmentId;
        result = 31 * result + typeOfMaintenanceId;
        result = 31 * result + organizationId;
        result = 31 * result + date.hashCode();
        temp = Double.doubleToLongBits(duration);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + graphicId;
        return result;
    }
}