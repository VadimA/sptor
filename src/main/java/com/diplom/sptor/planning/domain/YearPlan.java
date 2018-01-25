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
        DateTime lastDateOfMaintenance;
        DateTime nextDateOfMaintenance;
        double current_working_hours;
        double rest_of_working_hours;
        double last_working_hours;

        for(TypeOfMaintenance typeOfMaintenance : typeOfMaintenanceList){
            for(Equipment equipment : equipmentList) {
                lastDateOfMaintenance = planningUtils.getLastDateOfMaintenanceByEquipment(equipment, typeOfMaintenance);
                nextDateOfMaintenance = planningUtils.getNextDateOfMaintenanceByEquipment(equipment, typeOfMaintenance);
                if(nextDateOfMaintenance != null && lastDateOfMaintenance != null) {
                    current_working_hours = equipment.getWorkingHours();
                    last_working_hours = planningUtils.getWorkingHoursByEquipmentAfterDate(equipment, lastDateOfMaintenance);
                    rest_of_working_hours = planningUtils.getRestOfWorkingHoursBeforeMaintenance(equipment, typeOfMaintenance);
                    if (nextDateOfMaintenance.getMonthOfYear() == dateOfCreation.getMonth() || rest_of_working_hours <= 0) {
                        RepairUnit repairUnit = new RepairUnit(equipment.getEquipmentId(), typeOfMaintenance.getType_of_maintenance_id(),
                                lastDateOfMaintenance.toDate(), nextDateOfMaintenance.toDate(), current_working_hours, last_working_hours);
                        repairUnitList.add(repairUnit);
                    }
                }
                else{
                    if (planningUtils.getRestOfWorkingHoursBeforeMaintenance(equipment, typeOfMaintenance) <= 0) {
                        RepairUnit repairUnit = new RepairUnit(equipment.getEquipmentId(), typeOfMaintenance.getType_of_maintenance_id(),
                                null, null, equipment.getWorkingHours(), equipment.getWorkingHours());
                        repairUnitList.add(repairUnit);
                    }
                }
            }
        }
        return repairUnitList;
    }
}