package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 16.12.2015.
 */
@Entity
@Table(name = "repair_sheet")
@Proxy(lazy=false)
public class RepairSheet implements Serializable {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private  int repair_sheet_id;
    @ManyToOne()
    @JoinColumn(name = "type_of_maintenance_id")
    private  TypeOfMaintenance type_of_maintenance;
    @ManyToOne()
    @JoinColumn(name = "equipment_id")
    private  Equipment equipment;
    @ManyToOne()
    @JoinColumn(name = "component_id")
    private Components component;
    @ManyToOne()
    @JoinColumn(name = "subdivision_id")
    private Subdivisions subdivision;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private  Date start_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private  Date end_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private  Date confirm_date;

    private  long sheet_number;
    private  int warranty_period;

    @ManyToOne()
    @JoinColumn(name = "responsible_for_delivery")
    private  User responsibleForDelivery;

    @ManyToOne()
    @JoinColumn(name = "responsible_for_reception")
    private  User responsibleForReception;
    private  String description;

    @ManyToOne()
    @JoinColumn(name = "status")
    private  Status status;

    public RepairSheet() {}

    public RepairSheet(TypeOfMaintenance type_of_maintenance, Equipment equipment, Components component,
                       Subdivisions subdivision, Date start_date, Date end_date, long sheet_number, int warranty_period,
                       User responsibleForDelivery, User responsibleForReception, String description, Status status) {
        this.type_of_maintenance = type_of_maintenance;
        this.equipment = equipment;
        this.component = component;
        this.subdivision = subdivision;
        this.start_date = start_date;
        this.end_date = end_date;
        this.sheet_number = sheet_number;
        this.warranty_period = warranty_period;
        this.responsibleForDelivery = responsibleForDelivery;
        this.responsibleForReception = responsibleForReception;
        this.description = description;
        this.status = status;
    }

    public int getRepair_sheet_id() {
        return repair_sheet_id;
    }

    public void setRepair_sheet_id(int repair_sheet_id) {
        this.repair_sheet_id = repair_sheet_id;
    }

    public TypeOfMaintenance getType_of_maintenance() {
        return type_of_maintenance;
    }

    public void setType_of_maintenance(TypeOfMaintenance type_of_maintenance) {
        this.type_of_maintenance = type_of_maintenance;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Components getComponent() {
        return component;
    }

    public void setComponent(Components component) {
        this.component = component;
    }

    public Subdivisions getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(Subdivisions subdivision_id) {
        this.subdivision = subdivision_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public long getSheet_number() {
        return sheet_number;
    }

    public void setSheet_number(long sheet_number) {
        this.sheet_number = sheet_number;
    }

    public int getWarranty_period() {
        return warranty_period;
    }

    public void setWarranty_period(int warranty_period) {
        this.warranty_period = warranty_period;
    }

    public User getResponsibleForDelivery() {
        return responsibleForDelivery;
    }

    public void setResponsibleForDelivery(User responsibleForDelivery) {
        this.responsibleForDelivery = responsibleForDelivery;
    }

    public User getResponsibleForReception() {
        return responsibleForReception;
    }

    public void setResponsibleForReception(User responsibleForReception) {
        this.responsibleForReception = responsibleForReception;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getConfirm_date() {
        return confirm_date;
    }

    public void setConfirm_date(Date confirm_date) {
        this.confirm_date = confirm_date;
    }

    public void addDescription(String description){
        String oldDescription = this.getDescription();
        this.setDescription(oldDescription+ " | " + description);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepairSheet)) return false;

        RepairSheet that = (RepairSheet) o;

        if (getRepair_sheet_id() != that.getRepair_sheet_id()) return false;
        if (getSheet_number() != that.getSheet_number()) return false;
        if (getWarranty_period() != that.getWarranty_period()) return false;
        if (!getType_of_maintenance().equals(that.getType_of_maintenance())) return false;
        if (!getEquipment().equals(that.getEquipment())) return false;
        if (getComponent() != null ? !getComponent().equals(that.getComponent()) : that.getComponent() != null)
            return false;
        if (!getSubdivision().equals(that.getSubdivision())) return false;
        if (!getStart_date().equals(that.getStart_date())) return false;
        if (getEnd_date() != null ? !getEnd_date().equals(that.getEnd_date()) : that.getEnd_date() != null)
            return false;
        if (!getResponsibleForDelivery().equals(that.getResponsibleForDelivery())) return false;
        if (getResponsibleForReception() != null ? !getResponsibleForReception().equals(that.getResponsibleForReception()) : that.getResponsibleForReception() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        return getStatus().equals(that.getStatus());

    }

    @Override
    public int hashCode() {
        int result = getRepair_sheet_id();
        result = 31 * result + getType_of_maintenance().hashCode();
        result = 31 * result + getEquipment().hashCode();
        result = 31 * result + (getComponent() != null ? getComponent().hashCode() : 0);
        result = 31 * result + getSubdivision().hashCode();
        result = 31 * result + getStart_date().hashCode();
        result = 31 * result + (getEnd_date() != null ? getEnd_date().hashCode() : 0);
        result = 31 * result + (int) (getSheet_number() ^ (getSheet_number() >>> 32));
        result = 31 * result + getWarranty_period();
        result = 31 * result + getResponsibleForDelivery().hashCode();
        result = 31 * result + (getResponsibleForReception() != null ? getResponsibleForReception().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getStatus().hashCode();
        return result;
    }
}
