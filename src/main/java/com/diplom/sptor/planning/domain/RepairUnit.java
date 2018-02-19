package com.diplom.sptor.planning.domain;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.TypeOfMaintenance;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RepairUnit {

    private Equipment equipment;

    private TypeOfMaintenance typeOfMaintenance;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Europe/Moscow")
    private Date lastDateOfMaintenance;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Europe/Moscow")
    private Date nextDateOfMaintenance;

    private double current_working_hours;

    private double last_working_hours;

    private int priority;

    public RepairUnit() {
    }

    public RepairUnit(Equipment equipment, TypeOfMaintenance typeOfMaintenance, Date lastDateOfMaintenance,
                      Date nextDateOfMaintenance, double current_working_hours, double last_working_hours, int priority) {
        this.equipment = equipment;
        this.typeOfMaintenance = typeOfMaintenance;
        this.lastDateOfMaintenance = lastDateOfMaintenance;
        this.nextDateOfMaintenance = nextDateOfMaintenance;
        this.current_working_hours = current_working_hours;
        this.last_working_hours = last_working_hours;
        this.priority = priority;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public TypeOfMaintenance getTypeOfMaintenance() {
        return typeOfMaintenance;
    }

    public void setTypeOfMaintenance(TypeOfMaintenance typeOfMaintenance) {
        this.typeOfMaintenance = typeOfMaintenance;
    }

    public Date getLastDateOfMaintenance() {
        return lastDateOfMaintenance;
    }

    public void setLastDateOfMaintenance(Date lastDateOfMaintenance) {
        this.lastDateOfMaintenance = lastDateOfMaintenance;
    }

    public Date getNextDateOfMaintenance() {
        return nextDateOfMaintenance;
    }

    public void setNextDateOfMaintenance(Date nextDateOfMaintenance) {
        this.nextDateOfMaintenance = nextDateOfMaintenance;
    }

    public double getCurrent_working_hours() {
        return current_working_hours;
    }

    public void setCurrent_working_hours(double current_working_hours) {
        this.current_working_hours = current_working_hours;
    }

    public double getLast_working_hours() {
        return last_working_hours;
    }

    public void setLast_working_hours(double last_working_hours) {
        this.last_working_hours = last_working_hours;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepairUnit that = (RepairUnit) o;

        if (Double.compare(that.current_working_hours, current_working_hours) != 0) return false;
        if (Double.compare(that.last_working_hours, last_working_hours) != 0) return false;
        if (priority != that.priority) return false;
        if (!equipment.equals(that.equipment)) return false;
        if (!typeOfMaintenance.equals(that.typeOfMaintenance)) return false;
        if (lastDateOfMaintenance != null ? !lastDateOfMaintenance.equals(that.lastDateOfMaintenance) : that.lastDateOfMaintenance != null)
            return false;
        return nextDateOfMaintenance != null ? nextDateOfMaintenance.equals(that.nextDateOfMaintenance) : that.nextDateOfMaintenance == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = equipment.hashCode();
        result = 31 * result + typeOfMaintenance.hashCode();
        result = 31 * result + (lastDateOfMaintenance != null ? lastDateOfMaintenance.hashCode() : 0);
        result = 31 * result + (nextDateOfMaintenance != null ? nextDateOfMaintenance.hashCode() : 0);
        temp = Double.doubleToLongBits(current_working_hours);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(last_working_hours);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + priority;
        return result;
    }
}
