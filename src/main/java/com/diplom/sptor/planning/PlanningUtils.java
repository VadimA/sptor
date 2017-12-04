package com.diplom.sptor.planning;

import com.diplom.sptor.domain.*;
import com.diplom.sptor.service.EquipmentService;
import com.diplom.sptor.service.TechnologicalCardService;
import com.diplom.sptor.service.TypeOfMainToEquipmentService;
import com.diplom.sptor.service.WorkingHoursService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by user on 21.09.2017.
 */
@Component
public class PlanningUtils {

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    WorkingHoursService workingHoursService;

    @Autowired
    TechnologicalCardService technologicalCardService;

    @Autowired
    TypeOfMainToEquipmentService typeOfMainToEquipmentService;

    public Double getWorkingHoursByEquipmentInYear(Equipment equipment){
        Double sum = 0.0;
        List<WorkingHours>workingHoursList = workingHoursService.getWorkingHoursByEquipment(equipment);
        for(WorkingHours workingHours : workingHoursList){
            if(workingHours.getDate_of_adding().getYear() == new Date().getYear()) {
                sum += workingHours.getValue();
            }
        }
        return sum;
    }

    public Double getWorkingHoursByEquipmentInMonth(Equipment equipment){
        Double sum = 0.0;
        List<WorkingHours>workingHoursList = workingHoursService.getWorkingHoursByEquipment(equipment);
        for(WorkingHours workingHours : workingHoursList){
            if(workingHours.getDate_of_adding().getYear() == new Date().getYear()
                    && workingHours.getDate_of_adding().getMonth() == new Date().getMonth() ) {
                sum += workingHours.getValue();
            }
        }
        return sum;
    }

    public Double getWorkingHoursByEquipmentAfterDate(Equipment equipment, DateTime date){
        Double sum = 0.0;
        List<WorkingHours>workingHoursList = workingHoursService.getWorkingHoursByEquipment(equipment);
        for(WorkingHours workingHours : workingHoursList){
            if(workingHours.getDate_of_adding().after(date.toDate())) {
                sum += workingHours.getValue();
            }
        }
        return sum;
    }

    public DateTime getLastDateOfMaintenanceByEquipment(Equipment equipment, TypeOfMaintenance typeOfMaintenance){
        List<TechnologicalCard> technologicalCards = technologicalCardService.findByEquipmentAndTypeOfMaintenance(equipment, typeOfMaintenance);
        if(technologicalCards.size()>0 && !technologicalCards.isEmpty()) {
            Collections.sort(technologicalCards, new Comparator<TechnologicalCard>() {
                        public int compare(TechnologicalCard o1, TechnologicalCard o2) {
                            return o1.getEnd_date().compareTo(o2.getEnd_date());
                        }
                    });
            return new DateTime(technologicalCards.get(technologicalCards.size()-1).getEnd_date());
        }
        return null;
    }

    public DateTime getNextDateOfMaintenanceByEquipment(Equipment equipment, TypeOfMaintenance typeOfMaintenance){
        DateTime lastDateOfMaintenance = new DateTime(getLastDateOfMaintenanceByEquipment(equipment, typeOfMaintenance));
        TypeOfMainToEquipment typeOfMainToEquipment = typeOfMainToEquipmentService.findByTypeOfEquipmentIdAndTypeOfMaintenanceId(
                equipment.getEquipmentId(), typeOfMaintenance.getType_of_maintenance_id());
        if(typeOfMainToEquipment != null) {
            return lastDateOfMaintenance != null ? lastDateOfMaintenance.plusDays(typeOfMainToEquipment.getPeriodicity())
                    : new DateTime().plusDays(typeOfMainToEquipment.getPeriodicity());
        }
        else{
            return null;
        }
    }

    public double getRestOfWorkingHoursBeforeMaintenance(Equipment equipment, TypeOfMaintenance typeOfMaintenance){
        double limitOfWorkingHours = typeOfMainToEquipmentService.findByTypeOfEquipmentIdAndTypeOfMaintenanceId(
                equipment.getEquipmentId(), typeOfMaintenance.getType_of_maintenance_id()).getWork_hours_limit();
        double curentValue = getWorkingHoursByEquipmentAfterDate(equipment, getLastDateOfMaintenanceByEquipment(
                equipment, typeOfMaintenance));
        return curentValue - limitOfWorkingHours;
    }

    public Double getNormativeWorkingHoursByEquipment(Equipment equipment , TypeOfMaintenance typeOfMaintenance){
        TypeOfMainToEquipment typeOfMainToEquipment = typeOfMainToEquipmentService.findByTypeOfEquipmentIdAndTypeOfMaintenanceId(equipment.getEquipmentId(), typeOfMaintenance.getType_of_maintenance_id());
        return typeOfMainToEquipment != null ? typeOfMainToEquipment.getWork_hours_limit() : 0;
    }
}
