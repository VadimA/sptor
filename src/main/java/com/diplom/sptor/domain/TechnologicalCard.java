package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 15.12.2015.
 */
@Entity
@Table(name = "toir.technological_card")
@Proxy(lazy=false)
public class TechnologicalCard implements Serializable{

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int technological_card_id;

    @ManyToOne()
    @JoinColumn(name = "type_of_maintenance_id")
    private TypeOfMaintenance typeOfMaintenance;

    @ManyToOne()
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    private long technological_card_number;
    private Date start_date;
    private Date  end_date;

    @ManyToOne()
    @JoinColumn(name = "responsible_for_delivery")
    private Organization responsible_for_delivery;

    @ManyToOne()
    @JoinColumn(name = "responsible_for_reception")
    private User responsible_for_reception;

    private String description;

    public TechnologicalCard(){}

    public TechnologicalCard(TypeOfMaintenance type_of_maintenance, Equipment equipment, long technological_card_number,
                             Date start_date, Date end_date, Organization responsible_for_delivery, User responsible_for_reception,
                             String description) {
        this.typeOfMaintenance = type_of_maintenance;
        this.equipment = equipment;
        this.technological_card_number = technological_card_number;
        this.start_date = start_date;
        this.end_date = end_date;
        this.responsible_for_delivery = responsible_for_delivery;
        this.responsible_for_reception = responsible_for_reception;
        this.description = description;
    }

    public int getTechnological_card_id() {
        return technological_card_id;
    }

    public void setTechnological_card_id(int technological_card_id) {
        this.technological_card_id = technological_card_id;
    }

    public TypeOfMaintenance getType_of_maintenance() {
        return typeOfMaintenance;
    }

    public void setType_of_maintenance(TypeOfMaintenance type_of_maintenance) {
        this.typeOfMaintenance = type_of_maintenance;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public long getTechnological_card_number() {
        return technological_card_number;
    }

    public void setTechnological_card_number(long technological_card_number) {
        this.technological_card_number = technological_card_number;
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

    public Organization getResponsible_for_delivery() {
        return responsible_for_delivery;
    }

    public void setResponsible_for_delivery(Organization responsible_for_delivery) {
        this.responsible_for_delivery = responsible_for_delivery;
    }

    public User getResponsible_for_reception() {
        return responsible_for_reception;
    }

    public void setResponsible_for_reception(User responsible_for_reception) {
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
        if (!(o instanceof TechnologicalCard)) return false;

        TechnologicalCard that = (TechnologicalCard) o;

        if (getTechnological_card_id() != that.getTechnological_card_id()) return false;
        if (getTechnological_card_number() != that.getTechnological_card_number()) return false;
        if (!getType_of_maintenance().equals(that.getType_of_maintenance())) return false;
        if (!getEquipment().equals(that.getEquipment())) return false;
        if (!getStart_date().equals(that.getStart_date())) return false;
        if (getEnd_date() != null ? !getEnd_date().equals(that.getEnd_date()) : that.getEnd_date() != null)
            return false;
        if (!getResponsible_for_delivery().equals(that.getResponsible_for_delivery())) return false;
        if (getResponsible_for_reception() != null ? !getResponsible_for_reception().equals(that.getResponsible_for_reception()) : that.getResponsible_for_reception() != null)
            return false;
        return !(getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null);

    }

    @Override
    public int hashCode() {
        int result = getTechnological_card_id();
        result = 31 * result + getType_of_maintenance().hashCode();
        result = 31 * result + getEquipment().hashCode();
        result = 31 * result + (int) (getTechnological_card_number() ^ (getTechnological_card_number() >>> 32));
        result = 31 * result + getStart_date().hashCode();
        result = 31 * result + (getEnd_date() != null ? getEnd_date().hashCode() : 0);
        result = 31 * result + getResponsible_for_delivery().hashCode();
        result = 31 * result + (getResponsible_for_reception() != null ? getResponsible_for_reception().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
