package com.diplom.sptor.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="toir.operations")
@Proxy(lazy=false)
public class RepairOperation  implements Serializable {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    @Column(name = "operation_id")
    private int operationId;

    @ManyToOne()
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne()
    @JoinColumn(name = "type_of_maintenance_id")
    private TypeOfMaintenance typeOfMaintenance;

    @ManyToOne()
    @JoinColumn(name = "contr_organization_id")
    private Organization organization;

    @Column(name = "start_date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Europe/Moscow")
    private Date startDate;

    @Column(name = "end_date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Europe/Moscow")
    private Date endDate;

    @ManyToOne()
    @JoinColumn(name = "graph_id")
    private Graphic graphicId;

    @Column(name = "is_violation")
    private boolean isViolation;

    public RepairOperation() {}

    public RepairOperation(Equipment equipment, TypeOfMaintenance typeOfMaintenance, Organization organization,
                           Date startDate, Date endDate, Graphic graphicId, boolean isViolation) {
        this.equipment = equipment;
        this.typeOfMaintenance = typeOfMaintenance;
        this.organization = organization;
        this.startDate = startDate;
        this.endDate = endDate;
        this.graphicId = graphicId;
        this.isViolation = isViolation;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public TypeOfMaintenance getTypeOfMaintenance() {
        return typeOfMaintenance;
    }

    public void setTypeOfMaintenance(TypeOfMaintenance typeOfMaintenance) {
        this.typeOfMaintenance = typeOfMaintenance;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Graphic getGraphicId() {
        return graphicId;
    }

    public void setGraphicId(Graphic graphicId) {
        this.graphicId = graphicId;
    }

    public boolean isViolation() {
        return isViolation;
    }

    public void setViolation(boolean violation) {
        isViolation = violation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepairOperation that = (RepairOperation) o;

        if (operationId != that.operationId) return false;
        if (isViolation != that.isViolation) return false;
        if (!equipment.equals(that.equipment)) return false;
        if (!typeOfMaintenance.equals(that.typeOfMaintenance)) return false;
        if (!organization.equals(that.organization)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        return graphicId.equals(that.graphicId);
    }

    @Override
    public int hashCode() {
        int result = operationId;
        result = 31 * result + equipment.hashCode();
        result = 31 * result + typeOfMaintenance.hashCode();
        result = 31 * result + organization.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + graphicId.hashCode();
        result = 31 * result + (isViolation ? 1 : 0);
        return result;
    }
}