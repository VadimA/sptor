package com.diplom.sptor.planning;


import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Organization;
import com.diplom.sptor.domain.TypeOfMaintenance;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class RepairOperation {

    private Equipment equipment;

    private TypeOfMaintenance typeOfMaintenance;

    private Organization organization;

    private Date date;

    private double duration;

    public RepairOperation() {}

    public RepairOperation(Equipment equipment, TypeOfMaintenance typeOfMaintenance) {
        this.equipment = equipment;
        this.typeOfMaintenance = typeOfMaintenance;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepairOperation that = (RepairOperation) o;

        if (!equipment.equals(that.equipment)) return false;
        return typeOfMaintenance.equals(that.typeOfMaintenance);
    }

    @Override
    public int hashCode() {
        int result = equipment.hashCode();
        result = 31 * result + typeOfMaintenance.hashCode();
        return result;
    }
}

