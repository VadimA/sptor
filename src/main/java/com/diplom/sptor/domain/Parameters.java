package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * Created by user on 02.03.2016.
 */
@Entity
@Table(name = "parameters")
@Proxy(lazy=false)
public class Parameters {

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int parameter_id;

    private String parameter_name;

    @ManyToOne()
    @JoinColumn(name = "type_of_equipment_id")
    private TypeOfEquipment typeOfEquipment;

    private String parameter_value;

    @ManyToOne()
    @JoinColumn(name = "measure_id")
    private Measures measure;

    public Parameters() {
    }

    public Parameters(Measures measure, String parameter_name, TypeOfEquipment typeOfEquipment, String parameter_value) {
        this.measure = measure;
        this.parameter_name = parameter_name;
        this.typeOfEquipment = typeOfEquipment;
        this.parameter_value = parameter_value;
    }

    public int getParameter_id() {
        return parameter_id;
    }

    public void setParameter_id(int parameter_id) {
        this.parameter_id = parameter_id;
    }

    public String getParameter_name() {
        return parameter_name;
    }

    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }

    public TypeOfEquipment getTypeOfEquipment() {
        return typeOfEquipment;
    }

    public void setTypeOfEquipment(TypeOfEquipment typeOfEquipment) {
        this.typeOfEquipment = typeOfEquipment;
    }

    public String getParameter_value() {
        return parameter_value;
    }

    public void setParameter_value(String parameter_value) {
        this.parameter_value = parameter_value;
    }

    public Measures getMeasure() {
        return measure;
    }

    public void setMeasure(Measures measure) {
        this.measure = measure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parameters)) return false;

        Parameters that = (Parameters) o;

        if (getParameter_id() != that.getParameter_id()) return false;
        if (!getParameter_name().equals(that.getParameter_name())) return false;
        if (!getTypeOfEquipment().equals(that.getTypeOfEquipment())) return false;
        if (!getParameter_value().equals(that.getParameter_value())) return false;
        if (!getMeasure().equals(that.getMeasure())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getParameter_id();
        result = 31 * result + getParameter_name().hashCode();
        result = 31 * result + getTypeOfEquipment().hashCode();
        result = 31 * result + getParameter_value().hashCode();
        result = 31 * result + getMeasure().hashCode();
        return result;
    }
}
