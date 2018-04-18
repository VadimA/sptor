package com.diplom.sptor.planning;

import com.diplom.sptor.domain.*;
import com.diplom.sptor.domain.Graphic;
import com.diplom.sptor.domain.RepairOperation;
import com.diplom.sptor.planning.domain.RepairUnit;
import com.diplom.sptor.service.*;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    UserService userService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    RepairOperationService repairOperationService;

    public static final int DAY_IN_MONTH = 30;

    public static final List<LocalDate> holidays = Arrays.asList(new LocalDate(2018, 1, 1), new LocalDate(2018, 1, 2),
                                                                new LocalDate(2018, 1, 3), new LocalDate(2018, 1, 4),
                                                                new LocalDate(2018, 1, 5), new LocalDate(2018, 1, 8),
                                                                new LocalDate(2018, 2, 23), new LocalDate(2018, 3, 8),
                                                                new LocalDate(2018, 3, 9), new LocalDate(2018, 4, 30));

    public Double getWorkingHoursByEquipmentInYear(Equipment equipment){
        Double sum = 0.0;
        List<WorkingHours>workingHoursList = workingHoursService.getWorkingHoursByEquipment(equipment);
        for(WorkingHours workingHours : workingHoursList){
            if(workingHours.getDate_of_adding().after(new DateTime().minusYears(1).toDate())) {
                sum += workingHours.getValue();
            }
        }
        System.out.println("Total sum= " + sum + " ::: " + new DateTime().minusYears(1).toDate());
        return sum;
    }
    public Double getWorkingHoursByEquipmentAfterLastRepair(Equipment equipment, Date date){
        Double sum = 0.0;
            //TODO remove duplicate call of getWorkingHoursByEquipment
            List<WorkingHours> workingHoursList = workingHoursService.getWorkingHoursByEquipment(equipment);
            for (WorkingHours workingHours : workingHoursList) {
                if (workingHours.getDate_of_adding().after(date)) {
                    sum += workingHours.getValue();
                }
            }
        return sum != 0.0 ? sum : equipment.getWorkingHours();
    }

    public Optional<Date> getLastDateOfMaintenanceByEquipment(Equipment equipment, TypeOfMaintenance typeOfMaintenance){
        List<TechnologicalCard> technologicalCards = technologicalCardService.findByEquipmentAndTypeOfMaintenance(equipment, typeOfMaintenance);
        if(technologicalCards.size()>0 && !technologicalCards.isEmpty()) {
            technologicalCards.stream().sorted(Comparator.comparing(TechnologicalCard::getEnd_date).reversed()).collect(Collectors.toList());
            return Optional.ofNullable(technologicalCards.get(0).getEnd_date());
        }
        return Optional.ofNullable(equipment.getDateOfBeginExploitation());
    }

    public Optional<Date> getNextDateOfMaintenanceByEquipment(Equipment equipment, TypeOfMainToEquipment typeOfMainToEquipment, Date lastDateOfMaintenance){
        if(typeOfMainToEquipment != null && lastDateOfMaintenance != null) {
            return Optional.ofNullable(new DateTime(lastDateOfMaintenance).plusDays(typeOfMainToEquipment.getPeriodicity() * DAY_IN_MONTH).toDate());
        }
        else{
            return Optional.ofNullable(new LocalDate(equipment.getDateOfBeginExploitation()).plusDays(
                    typeOfMainToEquipment.getPeriodicity() * DAY_IN_MONTH).toDate());
        }
    }

    public double getRestOfWorkingHoursBeforeMaintenance(Equipment equipment, TypeOfMaintenance typeOfMaintenance,
                                                         TypeOfMainToEquipment typeOfMainToEquipment, Date lastDateOfMaintenance){
        double limitOfWorkingHours = typeOfMainToEquipment != null ? typeOfMainToEquipment.getWork_hours_limit() : 0.0;
        double currentValue = getWorkingHoursByEquipmentAfterLastRepair(equipment, lastDateOfMaintenance);
        return currentValue - limitOfWorkingHours;
    }

    public double getWorkingHoursInFutureMonth(Equipment equipment, Date dateOfCreation){
        double working_hours = 0.0;
        if(dateOfCreation != null){
            int daysCount = Days.daysBetween(new LocalDate(dateOfCreation) , new LocalDate(dateOfCreation).dayOfMonth().withMaximumValue()).getDays();
            working_hours = equipment.getWorkingHours() + (equipment.getTypeOfEquipment().getMonth_work_hours() / DAY_IN_MONTH) * daysCount;
        }
        return working_hours;
    }

    public TypeOfMainToEquipment getTypeOfMainToEquipment(Equipment equipment , TypeOfMaintenance typeOfMaintenance) {
        return typeOfMainToEquipmentService.findByTypeOfEquipmentIdAndTypeOfMaintenanceId(
                equipment.getTypeOfEquipment().getType_of_equipment_id(), typeOfMaintenance.getType_of_maintenance_id());
    }

    public User getCurrentUser(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userService.getUserBySso(userName);
    }

    public void sortRepairUnitListByPriority(List<RepairUnit> repairUnitList){
        repairUnitList.sort(Comparator.comparingInt(RepairUnit::getPriority));
    }

    public boolean checkConstraint(List<RepairOperation> repairOperationList, RepairUnit repairUnit){
        //if(checkFreeOrganization(repairOperationList))
        return false;
    }

    public boolean checkHolidays(LocalDate date){
        boolean isHolidays = false;
        if((date.getDayOfWeek() == 6 || date.getDayOfWeek() == 7)
            ||(holidays.contains(date))){
            isHolidays = true;
        }
        return isHolidays;
    }

    //TODO refactor to JAVA 8 lambda's
    public Optional<Organization> getFreeOrganizationInDateInterval(List<RepairOperation> repairOperationList){
        Optional<Organization> freeOrganization = null;
        List<Organization> organizationList = organizationService.getOrganizations();
        if(repairOperationList != null && !repairOperationList.isEmpty()){
            ListIterator listIterator = repairOperationList.listIterator();
            while (listIterator.hasNext()){
                RepairOperation repairOperation = (RepairOperation) listIterator.next();
                organizationList.remove(repairOperation.getOrganization());
            }
            if(organizationList != null && !organizationList.isEmpty() && organizationList.get(0) != null){
                freeOrganization = Optional.ofNullable(organizationList.get(0));
            }
        }
        else{
            freeOrganization = Optional.ofNullable(organizationList.get(0));
        }
        return freeOrganization;
    }

    public List<RepairOperation> getListOperationInDateInterval(Date date, List<RepairOperation> repairOperationList){
        List<RepairOperation> newRepairOperationList = new ArrayList<>();
        if(repairOperationList != null && !repairOperationList.isEmpty()) {
            for (RepairOperation operation : repairOperationList) {
                if (operation.getStartDate().compareTo(date) != 1 && operation.getEndDate().compareTo(date) != -1) {
                    newRepairOperationList.add(operation);
                }
            }
        }
        return newRepairOperationList;
    }

    public List<RepairOperation> fillRepairInMonth(Graphic graphic, List<RepairUnit> repairUnitList){
        List<RepairOperation> repairOperationList = new ArrayList<>();
        Optional<Organization> freeOrganization;
        Date date = graphic.getMonth();
        date.setDate(1);
        LocalDate localDate = new LocalDate(date);
        System.out.println("VAAN Date = " + date);
        RepairOperation currentRepairOperation;
        Date endDate;
        boolean isAdding;
        for(RepairUnit repairUnit : repairUnitList){
            isAdding = false;
            while (!isAdding) {
                freeOrganization = getFreeOrganizationInDateInterval(getListOperationInDateInterval(localDate.toDate(),
                                    repairOperationList));
                if (freeOrganization != null && !checkHolidays(localDate) && !checkHolidays(localDate.plusDays(
                        repairUnit.getTypeOfMaintenance().getDuration()))) {
                    System.out.println("VAAN ADDED DATE = " + localDate.toDate() + " FORM=" + localDate);
                    int duration = getTypeOfMainToEquipment(repairUnit.getEquipment(), repairUnit.getTypeOfMaintenance()).getDuration();
                    currentRepairOperation = new RepairOperation(repairUnit.getEquipment(), repairUnit.getTypeOfMaintenance(),
                            freeOrganization.get(), localDate.toDate(), localDate.plusDays(duration).toDate(), graphic, false);

                    repairOperationList.add(currentRepairOperation);
                    repairOperationService.addRepairOperation(currentRepairOperation);
                    isAdding = true;
                } else {
                    localDate = localDate.plusDays(1);
                    System.out.println("VAAN free NEW DATE" + localDate);
                }
            }
        }
        return repairOperationList;
    }
}