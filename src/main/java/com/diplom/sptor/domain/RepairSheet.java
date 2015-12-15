package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by user on 16.12.2015.
 */
@Entity
@Table(name = "toir.repair_sheet")
@Proxy(lazy=false)
public class RepairSheet implements Serializable {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private  int repair_sheet_id;
    private  int type_of_equipment_id;
    private  int type_of_maintenance_id;
    private  int equipment_id;
    private  Date start_date;
    private  Date end_date;
    private  long sheet_number;
    private  int warranty_period;
    private  String responsible_for_delivery;
    private  String responsible_for_reception;
    private  String description;

    public RepairSheet() {}

    public RepairSheet(int type_of_equipment_id, int type_of_maintenance_id, int equipment_id,
                       Date start_date, Date end_date, long sheet_number, int warranty_period,
                       String responsible_for_delivery, String responsible_for_reception, String description) {
        this.type_of_equipment_id = type_of_equipment_id;
        this.type_of_maintenance_id = type_of_maintenance_id;
        this.equipment_id = equipment_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.sheet_number = sheet_number;
        this.warranty_period = warranty_period;
        this.responsible_for_delivery = responsible_for_delivery;
        this.responsible_for_reception = responsible_for_reception;
        this.description = description;
    }

    public int getRepair_sheet_id() {
        return repair_sheet_id;
    }

    public void setRepair_sheet_id(int repair_sheet_id) {
        this.repair_sheet_id = repair_sheet_id;
    }

    public int getType_of_equipment_id() {
        return type_of_equipment_id;
    }

    public void setType_of_equipment_id(int type_of_equipment_id) {
        this.type_of_equipment_id = type_of_equipment_id;
    }

    public int getType_of_maintenance_id() {
        return type_of_maintenance_id;
    }

    public void setType_of_maintenance_id(int type_of_maintenance_id) {
        this.type_of_maintenance_id = type_of_maintenance_id;
    }

    public int getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(int equipment_id) {
        this.equipment_id = equipment_id;
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

    public String getResponsible_for_delivery() {
        return responsible_for_delivery;
    }

    public void setResponsible_for_delivery(String responsible_for_delivery) {
        this.responsible_for_delivery = responsible_for_delivery;
    }

    public String getResponsible_for_reception() {
        return responsible_for_reception;
    }

    public void setResponsible_for_reception(String responsible_for_reception) {
        this.responsible_for_reception = responsible_for_reception;
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
        if (!(o instanceof RepairSheet)) return false;

        RepairSheet that = (RepairSheet) o;

        if (getRepair_sheet_id() != that.getRepair_sheet_id()) return false;
        if (getType_of_equipment_id() != that.getType_of_equipment_id()) return false;
        if (getType_of_maintenance_id() != that.getType_of_maintenance_id()) return false;
        if (getEquipment_id() != that.getEquipment_id()) return false;
        if (getSheet_number() != that.getSheet_number()) return false;
        if (getWarranty_period() != that.getWarranty_period()) return false;
        if (!getStart_date().equals(that.getStart_date())) return false;
        if (!getEnd_date().equals(that.getEnd_date())) return false;
        if (!getResponsible_for_delivery().equals(that.getResponsible_for_delivery())) return false;
        if (!getResponsible_for_reception().equals(that.getResponsible_for_reception())) return false;
        return !(getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null);

    }

    @Override
    public int hashCode() {
        int result = getRepair_sheet_id();
        result = 31 * result + getType_of_equipment_id();
        result = 31 * result + getType_of_maintenance_id();
        result = 31 * result + getEquipment_id();
        result = 31 * result + getStart_date().hashCode();
        result = 31 * result + getEnd_date().hashCode();
        result = 31 * result + (int) (getSheet_number() ^ (getSheet_number() >>> 32));
        result = 31 * result + getWarranty_period();
        result = 31 * result + getResponsible_for_delivery().hashCode();
        result = 31 * result + getResponsible_for_reception().hashCode();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
