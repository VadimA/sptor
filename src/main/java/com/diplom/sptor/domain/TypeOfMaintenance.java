package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by user on 25.03.2016.
 */
@Entity
@Table(name = "toir.type_of_maintenance")
@Proxy(lazy=false)
public class TypeOfMaintenance implements Serializable{

    @Id
    @GenericGenerator(name="kaugen" , strategy="increment")
    @GeneratedValue(generator="kaugen")
    private int type_of_maintenance_id;

    private String type_of_maintenance_name;

    private String description;

    public TypeOfMaintenance() {}

    public TypeOfMaintenance(String type_of_maintenance_name, String description) {
        this.type_of_maintenance_name = type_of_maintenance_name;
        this.description = description;
    }

    public int getType_of_maintenance_id() {
        return type_of_maintenance_id;
    }

    public void setType_of_maintenance_id(int type_of_maintenance_id) {
        this.type_of_maintenance_id = type_of_maintenance_id;
    }

    public String getType_of_maintenance_name() {
        return type_of_maintenance_name;
    }

    public void setType_of_maintenance_name(String type_of_maintenance_name) {
        this.type_of_maintenance_name = type_of_maintenance_name;
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
        if (!(o instanceof TypeOfMaintenance)) return false;

        TypeOfMaintenance that = (TypeOfMaintenance) o;

        if (type_of_maintenance_id != that.type_of_maintenance_id) return false;
        if (!type_of_maintenance_name.equals(that.type_of_maintenance_name)) return false;
        return !(description != null ? !description.equals(that.description) : that.description != null);

    }

    @Override
    public int hashCode() {
        int result = type_of_maintenance_id;
        result = 31 * result + type_of_maintenance_name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
