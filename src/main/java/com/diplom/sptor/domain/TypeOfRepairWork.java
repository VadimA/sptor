package com.diplom.sptor.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "toir.type_of_repair_work")
@Proxy(lazy=false)
public class TypeOfRepairWork implements Serializable {

    @Id
    @GenericGenerator(name="keygen" , strategy="increment")
    @GeneratedValue(generator="keygen")
    private int type_of_repair_work_id;

    private String type_of_repair_work_name;

    private String description;

    private int duration;

    public TypeOfRepairWork() {}

    public TypeOfRepairWork(String type_of_repair_work_name, String description, int duration) {
        this.type_of_repair_work_name = type_of_repair_work_name;
        this.description = description;
        this.duration = duration;
    }

    public int getType_of_repair_work_id() {
        return type_of_repair_work_id;
    }

    public void setType_of_repair_work_id(int type_of_repair_work_id) {
        this.type_of_repair_work_id = type_of_repair_work_id;
    }

    public String getType_of_repair_work_name() {
        return type_of_repair_work_name;
    }

    public void setType_of_repair_work_name(String type_of_repair_work_name) {
        this.type_of_repair_work_name = type_of_repair_work_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeOfRepairWork that = (TypeOfRepairWork) o;

        if (type_of_repair_work_id != that.type_of_repair_work_id) return false;
        if (duration != that.duration) return false;
        if (!type_of_repair_work_name.equals(that.type_of_repair_work_name)) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = type_of_repair_work_id;
        result = 31 * result + type_of_repair_work_name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + duration;
        return result;
    }
}
