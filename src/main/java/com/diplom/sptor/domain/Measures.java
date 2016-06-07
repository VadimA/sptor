package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by user on 02.03.2016.
 */
@Entity
@Table(name = "toir.measures")
@Proxy(lazy=false)
public class Measures {
    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int measure_id;

    private String measure_value;

    public Measures() {
    }

    public Measures(String measure_value) {
        this.measure_value = measure_value;
    }

    public int getMeasure_id() {
        return measure_id;
    }

    public void setMeasure_id(int measure_id) {
        this.measure_id = measure_id;
    }

    public String getMeasure_value() {
        return measure_value;
    }

    public void setMeasure_value(String measure_value) {
        this.measure_value = measure_value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Measures)) return false;

        Measures measures = (Measures) o;

        if (getMeasure_id() != measures.getMeasure_id()) return false;
        return getMeasure_value().equals(measures.getMeasure_value());

    }

    @Override
    public int hashCode() {
        int result = getMeasure_id();
        result = 31 * result + getMeasure_value().hashCode();
        return result;
    }
}
