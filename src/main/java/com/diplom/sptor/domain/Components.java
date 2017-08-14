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
    @Column(name = "component_id")
    private int componentId;

    @ManyToOne()
    @JoinColumn(name = "spare_id")
    private Spares spare;

    @ManyToOne()
    @JoinColumn(name = "type_of_equipment_id")
    private TypeOfEquipment typeOfEquipment;

    int amount;

    public Components() {
    }

    public Components(Spares spare, int amount, TypeOfEquipment typeOfEquipment) {
        this.spare = spare;
        this.amount = amount;
        this.typeOfEquipment = typeOfEquipment;
    }

    public int getComponentId() {
        return componentId;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public Spares getSpare() {
        return spare;
    }

    public void setSpare(Spares spare) {
        this.spare = spare;
    }

    public TypeOfEquipment getTypeOfEquipment() {
        return typeOfEquipment;
    }

    public void setTypeOfEquipment(TypeOfEquipment typeOfEquipment) {
        this.typeOfEquipment = typeOfEquipment;
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

        if (getComponentId() != that.getComponentId()) return false;
        if (getAmount() != that.getAmount()) return false;
        if (!getSpare().equals(that.getSpare())) return false;
        return getTypeOfEquipment().equals(that.getTypeOfEquipment());

    }

    @Override
    public int hashCode() {
        int result = getComponentId();
        result = 31 * result + getSpare().hashCode();
        result = 31 * result + getTypeOfEquipment().hashCode();
        result = 31 * result + getAmount();
        return result;
    }
}
