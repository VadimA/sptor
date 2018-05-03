package com.diplom.sptor.planning.domain;

import com.diplom.sptor.domain.*;
import com.diplom.sptor.planning.PlanningUtils;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class YearPlan {

    private boolean isYear;

    private Date dateOfCreation;

    public static final int DAY_IN_MONTH = 30;

    @Autowired
    PlanningUtils planningUtils;

    public void setPlanningUtils(PlanningUtils planningUtils) {
        this.planningUtils = planningUtils;
    }

    public YearPlan() {
    }

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

    public Graphic generatePlan() {
        return new Graphic();
    }

    public List<RepairUnit> getListOfEquipmentsWhereNeedsInMaintenanceMonth(List<Equipment> equipmentList, List<TypeOfMaintenance> typeOfMaintenanceList, Date date) {
        System.out.println("VAAN EQUIPMENT = " + equipmentList.get(0).getEquipmentName());
        planningUtils.getNumberOfCurrentMaintenance(equipmentList.get(0),planningUtils.getRepairCycleByEquipment(equipmentList.get(0)));
        List<RepairUnit> repairUnitList = new ArrayList<RepairUnit>();
        List<Integer> equipmentsIdList = new ArrayList<>();
        Date lastDateOfMaintenance;
        Optional<Date> nextDateOfMaintenance;
        double current_working_hours;
        double last_working_hours;
        TypeOfMainToEquipment typeOfMainToEquipment;
        typeOfMaintenanceList = typeOfMaintenanceList.stream().sorted(Comparator.comparingInt
                (TypeOfMaintenance::getPriority).reversed()).collect(Collectors.toList());
        System.out.println("Reversed order after sorting = " + typeOfMaintenanceList.get(0).getType_of_maintenance_name());

        for (TypeOfMaintenance typeOfMaintenance : typeOfMaintenanceList) {
            for (Equipment equipment : equipmentList) {
                if (!equipmentsIdList.contains(equipment.getEquipmentId())) {
                    typeOfMainToEquipment = planningUtils.getTypeOfMainToEquipment(equipment, typeOfMaintenance);
                    Optional<TechnologicalCard> technologicalCard = planningUtils.getLastMaintenanceByEquipment(equipment, typeOfMaintenance);
                    lastDateOfMaintenance = technologicalCard.isPresent() ? technologicalCard.get().getEnd_date() : equipment.getDateOfBeginExploitation();
                    if (lastDateOfMaintenance != null) {
                        nextDateOfMaintenance = planningUtils.getNextDateOfMaintenanceByEquipment(equipment,typeOfMainToEquipment,
                                lastDateOfMaintenance);
                        current_working_hours = equipment.getWorkingHours();
                        last_working_hours = planningUtils.getWorkingHoursByEquipmentAfterLastRepair(equipment, lastDateOfMaintenance);
                        if (nextDateOfMaintenance.get().getMonth() == date.getMonth() || last_working_hours >= typeOfMainToEquipment.getWork_hours_limit()) {
                            RepairUnit repairUnit = new RepairUnit(equipment, typeOfMaintenance,
                                    lastDateOfMaintenance, nextDateOfMaintenance.get(), current_working_hours, last_working_hours,
                                    typeOfMaintenance.getPriority());
                            repairUnitList.add(repairUnit);
                            equipmentsIdList.add(equipment.getEquipmentId());
                        }
                    } else {
                        System.out.println("VAAN daysBetween = " + Days.daysBetween(new LocalDate(dateOfCreation), new LocalDate(equipment.getDateOfBeginExploitation())).getDays());
                        if (Days.daysBetween(new LocalDate(dateOfCreation), new LocalDate(equipment.getDateOfBeginExploitation())).getDays() >= typeOfMainToEquipment.getPeriodicity() * DAY_IN_MONTH) {
                            RepairUnit repairUnit = new RepairUnit(equipment, typeOfMaintenance,
                                    dateOfCreation, new LocalDate(dateOfCreation).plusDays(typeOfMainToEquipment.getPeriodicity() * DAY_IN_MONTH).toDate(),
                                    equipment.getWorkingHours(), equipment.getWorkingHours(), typeOfMaintenance.getPriority());
                            repairUnitList.add(repairUnit);
                            equipmentsIdList.add(equipment.getEquipmentId());

                        } else {
                            System.out.println("VAAN getWorkingHoursInFutureMonth = " + planningUtils.getWorkingHoursInFutureMonth(equipment, dateOfCreation));
                            if (planningUtils.getWorkingHoursInFutureMonth(equipment, dateOfCreation) >=
                                    typeOfMainToEquipment.getWork_hours_limit()) {
                                RepairUnit repairUnit = new RepairUnit(equipment, typeOfMaintenance,
                                        dateOfCreation, new LocalDate(dateOfCreation).plusDays(typeOfMainToEquipment.getPeriodicity() * DAY_IN_MONTH).toDate(),
                                        equipment.getWorkingHours(), equipment.getWorkingHours(), typeOfMaintenance.getPriority());
                                repairUnitList.add(repairUnit);
                                equipmentsIdList.add(equipment.getEquipmentId());
                            }
                        }
                    }
                }
            }
        }
        return repairUnitList;
    }

    public void saveRepairUnit(){

    }
}