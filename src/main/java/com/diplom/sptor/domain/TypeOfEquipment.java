package com.diplom.sptor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 03.12.2015.
 */
@Entity
@Table(name = "toir.type_of_equipment")
@Proxy(lazy=false)
public class TypeOfEquipment implements Serializable {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int type_of_equipment_id;
    private String type_of_equipment_name;
    private String description;
    private double month_work_hours;
    @OneToMany(mappedBy = "typeOfEquipment",fetch = FetchType.LAZY)
    @JsonIgnore
    private final Set<Equipment> equipments_type = new HashSet<Equipment>();

  // @OneToMany(mappedBy = "type_of_equipment_components",fetch = FetchType.LAZY)
  // @JsonIgnore
  // private final Set<ComponentModel> spares = new HashSet<ComponentModel>();

    public TypeOfEquipment() {}

    public TypeOfEquipment(String type_of_equipment_name, String description) {
        this.type_of_equipment_name = type_of_equipment_name;
        this.description = description;
    }
    public int getType_of_equipment_id() {
        return type_of_equipment_id;
    }

    public void setType_of_equipment_id(int type_of_equipment_id) {
        this.type_of_equipment_id = type_of_equipment_id;
    }

    public String getType_of_equipment_name() {
        return type_of_equipment_name;
    }

    public void setType_of_equipment_name(String type_of_equipment_name) {
        this.type_of_equipment_name = type_of_equipment_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMonth_work_hours() {
        return month_work_hours;
    }

    public void setMonth_work_hours(double month_work_hours) {
        this.month_work_hours = month_work_hours;
    }

    public Set<Equipment> getEquipments_type() {
        return equipments_type;
    }
    public void addEquipments(Equipment equipment) {
        equipment.setTypeOfEquipment(this);
        this.equipments_type.add(equipment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeOfEquipment that = (TypeOfEquipment) o;

        if (type_of_equipment_id != that.type_of_equipment_id) return false;
        if (Double.compare(that.month_work_hours, month_work_hours) != 0) return false;
        if (!type_of_equipment_name.equals(that.type_of_equipment_name)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return equipments_type.equals(that.equipments_type);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = type_of_equipment_id;
        result = 31 * result + type_of_equipment_name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(month_work_hours);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + equipments_type.hashCode();
        return result;
    }
}
