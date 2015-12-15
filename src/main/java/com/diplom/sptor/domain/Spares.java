package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by user on 14.12.2015.
 */
@Entity
@Table(name = "toir.spares")
@Proxy(lazy=false)
public class Spares implements Serializable{

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int spare_id;

    private String spare_name;

    private String spare_producer;

    private double price;

    private String description;

    public Spares() {}

    public Spares(String spare_name, String spare_producer, double price, String description) {
        this.spare_name = spare_name;
        this.spare_producer = spare_producer;
        this.price = price;
        this.description = description;
    }

    public int getSpare_id() {
        return spare_id;
    }

    public void setSpare_id(int spare_id) {
        this.spare_id = spare_id;
    }

    public String getSpare_name() {
        return spare_name;
    }

    public void setSpare_name(String spare_name) {
        this.spare_name = spare_name;
    }

    public String getSpare_producer() {
        return spare_producer;
    }

    public void setSpare_producer(String spare_producer) {
        this.spare_producer = spare_producer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
        if (!(o instanceof Spares)) return false;

        Spares spares = (Spares) o;

        if (getSpare_id() != spares.getSpare_id()) return false;
        if (Double.compare(spares.getPrice(), getPrice()) != 0) return false;
        if (!getSpare_name().equals(spares.getSpare_name())) return false;
        if (getSpare_producer() != null ? !getSpare_producer().equals(spares.getSpare_producer()) : spares.getSpare_producer() != null)
            return false;
        return !(getDescription() != null ? !getDescription().equals(spares.getDescription()) : spares.getDescription() != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getSpare_id();
        result = 31 * result + getSpare_name().hashCode();
        result = 31 * result + (getSpare_producer() != null ? getSpare_producer().hashCode() : 0);
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
