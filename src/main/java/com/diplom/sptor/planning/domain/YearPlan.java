package com.diplom.sptor.planning.domain;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.TypeOfMaintenance;
import com.diplom.sptor.planning.PlanningUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class YearPlan{

    private boolean isYear;

    private Date dateOfCreation;

    @Autowired
    PlanningUtils planningUtils;

    public void setPlanningUtils(PlanningUtils planningUtils) {
        this.planningUtils = planningUtils;
    }

    public YearPlan() {}

    public YearPlan(boolean isYear, Date dateOfCreation) {
        this.isYear = isYear;
        this.dateOfCreation = dateOfCreation;
    }

    public boolean isYear() {
        return isYear;
    }

    public void setYear(boolean year) {
        isYear = year;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Graphic generatePlan(){
        return new Graphic();
    }

    public List<RepairUnit> getListOfEquipmentsWhereNeedsInMaintenanceMonth(List<Equipment> equipmentList, List<TypeOfMaintenance> typeOfMaintenanceList){
        List<RepairUnit> repairUnitList = new ArrayList<RepairUnit>();
        DateTime lastDateOfMaintenance = null;
        DateTime nextDateOfMaintenance = null;
        double current_working_hours = 0.0;
        double last_working_hours = 0.0;

        for(TypeOfMaintenance typeOfMaintenance : typeOfMaintenanceList){
            for(Equipment equipment : equipmentList) {
                lastDateOfMaintenance = planningUtils.getLastDateOfMaintenanceByEquipment(equipment, typeOfMaintenance);
                nextDateOfMaintenance = planningUtils.getNextDateOfMaintenanceByEquipment(equipment, typeOfMaintenance);
                if(nextDateOfMaintenance != null && lastDateOfMaintenance != null) {
                    current_working_hours = equipment.getWorkingHours();
                    last_working_hours = planningUtils.getWorkingHoursByEquipmentAfterDate(equipment, new DateTime(lastDateOfMaintenance));
                    if (nextDateOfMaintenance.getMonthOfYear() == dateOfCreation.getMonth() || last_working_hours <= 0) {
                        RepairUnit repairUnit = new RepairUnit(equipment.getEquipmentId(), typeOfMaintenance.getType_of_maintenance_id(),
                                lastDateOfMaintenance.toDate(), nextDateOfMaintenance.toDate(), current_working_hours, last_working_hours);
                        repairUnitList.add(repairUnit);
                    }
                }
                else{
                    if (equipment.getWorkingHours() >= planningUtils.getNormativeWorkingHoursByEquipment(equipment, typeOfMaintenance)) {
                        RepairUnit repairUnit = new RepairUnit(equipment.getEquipmentId(), typeOfMaintenance.getType_of_maintenance_id(),
                                null, null, equipment.getWorkingHours(), 0);
                        repairUnitList.add(repairUnit);
                    }
                }
            }
        }
        return repairUnitList;
    }
}