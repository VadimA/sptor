package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by user on 25.02.2016.
 */
@Entity
@Table(name = "toir.components")
@Proxy(lazy=false)
public class Components {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int component_id;

    @ManyToOne()
    @JoinColumn(name = "spare_id")
    private Spares spare;

    @ManyToOne()
    @JoinColumn(name = "type_of_equipment_id")
    private TypeOfEquipment type_of_equipment_components;

    int amount;

    public Components() {
    }

    public Components(Spares spare, int amount, TypeOfEquipment type_of_equipment_components) {
        this.spare = spare;
        this.amount = amount;
        this.type_of_equipment_components = type_of_equipment_components;
    }

    public int getComponent_id() {
        return component_id;
    }

    public void setComponent_id(int component_id) {
        this.component_id = component_id;
    }

    public Spares getSpare() {
        return spare;
    }

    public void setSpare(Spares spare) {
        this.spare = spare;
    }

    public TypeOfEquipment getType_of_equipment_components() {
        return type_of_equipment_components;
    }

    public void setType_of_equipment_components(TypeOfEquipment type_of_equipment_components) {
        this.type_of_equipment_components = type_of_equipment_components;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Components)) return false;

        Components that = (Components) o;

        if (getComponent_id() != that.getComponent_id()) return false;
        if (getAmount() != that.getAmount()) return false;
        if (!getSpare().equals(that.getSpare())) return false;
        return getType_of_equipment_components().equals(that.getType_of_equipment_components());

    }

    @Override
    public int hashCode() {
        int result = getComponent_id();
        result = 31 * result + getSpare().hashCode();
        result = 31 * result + getType_of_equipment_components().hashCode();
        result = 31 * result + getAmount();
        return result;
    }
}
