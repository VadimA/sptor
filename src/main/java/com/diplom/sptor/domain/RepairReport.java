package com.diplom.sptor.domain;

/**
 * Created by user on 22.04.2016.
 */
public class RepairReport {

    public String type_of_maintenance;

    public String subdivision;

    public String sheet_number;

    public String start_date;

    public String type_of_equipment;

    public String equipment;

    public String producer;

    public String lastRepair;

    public String responsible;

    public String category;

    public String getType_of_maintenance() {
        return type_of_maintenance;
    }

    public void setType_of_maintenance(String type_of_maintenance) {
        this.type_of_maintenance = type_of_maintenance;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public String getSheet_number() {
        return sheet_number;
    }

    public void setSheet_number(String sheet_number) {
        this.sheet_number = sheet_number;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getType_of_equipment() {
        return type_of_equipment;
    }

    public void setType_of_equipment(String type_of_equipment) {
        this.type_of_equipment = type_of_equipment;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getLastRepair() {
        return lastRepair;
    }

    public void setLastRepair(String lastRepair) {
        this.lastRepair = lastRepair;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
