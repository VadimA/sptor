package com.diplom.sptor.planning.domain;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.TypeOfMainToEquipment;
import com.diplom.sptor.domain.TypeOfMaintenance;
import com.diplom.sptor.planning.PlanningUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Months;
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

    public List<RepairUnit> getListOfEquipmentsWhereNeedsInMaintenanceMonth(List<Equipment> equipmentList, List<TypeOfMaintenance> typeOfMaintenanceList, Date date) {
        List<RepairUnit> repairUnitList = new ArrayList<RepairUnit>();
        DateTime lastDateOfMaintenance;
        DateTime nextDateOfMaintenance;
        double current_working_hours;
        double last_working_hours;
        TypeOfMainToEquipment typeOfMainToEquipment;

        for (TypeOfMaintenance typeOfMaintenance : typeOfMaintenanceList) {
            for (Equipment equipment : equipmentList) {
                typeOfMainToEquipment = planningUtils.getTypeOfMainToEquipment(equipment, typeOfMaintenance);
                lastDateOfMaintenance = planningUtils.getLastDateOfMaintenanceByEquipment(equipment, typeOfMaintenance);
                nextDateOfMaintenance = planningUtils.getNextDateOfMaintenanceByEquipment(equipment, typeOfMaintenance, typeOfMainToEquipment);
                if (nextDateOfMaintenance != null && lastDateOfMaintenance != null) {
                    current_working_hours = equipment.getWorkingHours();
                    last_working_hours = planningUtils.getWorkingHoursByEquipmentAfterDate(equipment, new DateTime(lastDateOfMaintenance));
                    if (nextDateOfMaintenance.getMonthOfYear() == date.getMonth() || last_working_hours <= 0) {
                        RepairUnit repairUnit = new RepairUnit(equipment.getEquipmentId(), typeOfMaintenance.getType_of_maintenance_id(),
                                lastDateOfMaintenance.toDate(), nextDateOfMaintenance.toDate(), current_working_hours, last_working_hours,
                                typeOfMaintenance.getPriority());
                        repairUnitList.add(repairUnit);
                    }
                } else {
                    if (Months.monthsBetween(new LocalDate(dateOfCreation), new LocalDate(date)).getMonths() == 0) {
                        if (planningUtils.getRestOfWorkingHoursBeforeMaintenance(equipment, typeOfMaintenance, typeOfMainToEquipment,lastDateOfMaintenance) <= 0) {
                            RepairUnit repairUnit = new RepairUnit(equipment.getEquipmentId(), typeOfMaintenance.getType_of_maintenance_id(),
                                    null, null, equipment.getWorkingHours(), equipment.getWorkingHours(), typeOfMaintenance.getPriority());
                            repairUnitList.add(repairUnit);
                        }
                    } else {
                        if (planningUtils.getWorkingHoursInFutureMonth(equipment, date, dateOfCreation) >=
                                planningUtils.getTypeOfMainToEquipment(equipment, typeOfMaintenance).getWork_hours_limit()) {
                            RepairUnit repairUnit = new RepairUnit(equipment.getEquipmentId(), typeOfMaintenance.getType_of_maintenance_id(),
                                    null, null, equipment.getWorkingHours(), equipment.getWorkingHours(), typeOfMaintenance.getPriority());
                            repairUnitList.add(repairUnit);
                        }
                    }
                }
            }
        }
        return planningUtils.fetchPriorityUnitFromList(repairUnitList);
    }
}