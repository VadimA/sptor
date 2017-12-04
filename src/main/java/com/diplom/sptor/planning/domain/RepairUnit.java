package com.diplom.sptor.planning.domain;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RepairUnit {

    private int equipmentId;

    private int typeOfMaintenanceId;

    private Date lastDateOfMaintenance;

    private Date nextDateOfMaintenance;

    private double current_working_hours;

    private double last_working_hours;

    public RepairUnit() {
    }

    public RepairUnit(int equipmentId, int typeOfMaintenanceId, Date lastDateOfMaintenance, Date nextDateOfMaintenance,
                      double current_working_hours, double last_working_hours) {
        this.equipmentId = equipmentId;
        this.typeOfMaintenanceId = typeOfMaintenanceId;
        this.lastDateOfMaintenance = lastDateOfMaintenance;
        this.nextDateOfMaintenance = nextDateOfMaintenance;
        this.current_working_hours = current_working_hours;
        this.last_working_hours = last_working_hours;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getTypeOfMaintenanceId() {
        return typeOfMaintenanceId;
    }

    public void setTypeOfMaintenanceId(int typeOfMaintenanceId) {
        this.typeOfMaintenanceId = typeOfMaintenanceId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepairUnit that = (RepairUnit) o;

        if (equipmentId != that.equipmentId) return false;
        if (typeOfMaintenanceId != that.typeOfMaintenanceId) return false;
        if (Double.compare(that.current_working_hours, current_working_hours) != 0) return false;
        if (Double.compare(that.last_working_hours, last_working_hours) != 0) return false;
        if (lastDateOfMaintenance != null ? !lastDateOfMaintenance.equals(that.lastDateOfMaintenance) : that.lastDateOfMaintenance != null)
            return false;
        return nextDateOfMaintenance != null ? nextDateOfMaintenance.equals(that.nextDateOfMaintenance) : that.nextDateOfMaintenance == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = equipmentId;
        result = 31 * result + typeOfMaintenanceId;
        result = 31 * result + (lastDateOfMaintenance != null ? lastDateOfMaintenance.hashCode() : 0);
        result = 31 * result + (nextDateOfMaintenance != null ? nextDateOfMaintenance.hashCode() : 0);
        temp = Double.doubleToLongBits(current_working_hours);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(last_working_hours);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
