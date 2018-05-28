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

    @Autowired
    TypeOfMaintenanceService typeOfMaintenanceService;

    @Autowired
    RepairSheetService repairSheetService;

    @Autowired
    GraphicService graphicService;

    public static final int DAY_IN_MONTH = 30;

    public static final List<LocalDate> holidays = Arrays.asList(new LocalDate(2018, 1, 1), new LocalDate(2018, 1, 2),
            new LocalDate(2018, 1, 3), new LocalDate(2018, 1, 4),
            new LocalDate(2018, 1, 5), new LocalDate(2018, 1, 8),
            new LocalDate(2018, 2, 23), new LocalDate(2018, 3, 8),
            new LocalDate(2018, 3, 9), new LocalDate(2018, 4, 30));

    public static List<LocalDate> getHolidays() {
        return holidays;
    }

    public Double getWorkingHoursByEquipmentInYear(Equipment equipment) {
        Double sum = 0.0;
        List<WorkingHours> workingHoursList = workingHoursService.getWorkingHoursByEquipment(equipment);
        for (WorkingHours workingHours : workingHoursList) {
            if (workingHours.getDate_of_adding().after(new DateTime().minusYears(1).toDate())) {
                sum += workingHours.getValue();
            }
        }
        System.out.println("Total sum= " + sum + " ::: " + new DateTime().minusYears(1).toDate());
        return sum;
    }

    public Double getWorkingHoursByEquipmentAfterLastRepair(Equipment equipment, Date date) {
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

    public Optional<Date> getNextDateOfMaintenanceByEquipment(Equipment equipment, TypeOfMainToEquipment typeOfMainToEquipment, Date lastDateOfMaintenance) {
        if (typeOfMainToEquipment != null && lastDateOfMaintenance != null) {
            return Optional.ofNullable(new DateTime(lastDateOfMaintenance).plusDays(typeOfMainToEquipment.getPeriodicity() * DAY_IN_MONTH).toDate());
        } else {
            return Optional.ofNullable(new LocalDate(equipment.getDateOfBeginExploitation()).plusDays(
                    typeOfMainToEquipment.getPeriodicity() * DAY_IN_MONTH).toDate());
        }
    }

    public Optional<TechnologicalCard> getLastMaintenanceByEquipment(Equipment equipment, TypeOfMaintenance typeOfMaintenance) {
        List<TechnologicalCard> technologicalCards = technologicalCardService.findByEquipmentAndTypeOfMaintenance(equipment, typeOfMaintenance);
        if (technologicalCards.size() > 0 && !technologicalCards.isEmpty()) {
            technologicalCards = technologicalCards.stream().sorted(Comparator.comparing(TechnologicalCard::getEnd_date).reversed()).collect(Collectors.toList());
            return Optional.ofNullable(technologicalCards.get(0));
        }
        return Optional.empty();
    }

    public Optional<RepairSheet> getLastRepairByEquipment(Equipment equipment) {
        List<RepairSheet> repairSheetList = repairSheetService.getRepairSheetByEquipment(equipment);
        if (repairSheetList.size() > 0 && !repairSheetList.isEmpty()) {
            repairSheetList = repairSheetList.stream().sorted(Comparator.comparing(RepairSheet::getEnd_date).reversed()).collect(Collectors.toList());
            return Optional.ofNullable(repairSheetList.get(0));
        }
        return Optional.empty();
    }

    public double getRestOfWorkingHoursBeforeMaintenance(Equipment equipment, TypeOfMaintenance typeOfMaintenance,
                                                         TypeOfMainToEquipment typeOfMainToEquipment, Date lastDateOfMaintenance) {
        double limitOfWorkingHours = typeOfMainToEquipment != null ? typeOfMainToEquipment.getWork_hours_limit() : 0.0;
        double currentValue = getWorkingHoursByEquipmentAfterLastRepair(equipment, lastDateOfMaintenance);
        return currentValue - limitOfWorkingHours;
    }

    public double getWorkingHoursInFutureMonth(Equipment equipment, Date dateOfCreation) {
        double working_hours = 0.0;
        if (dateOfCreation != null) {
            int daysCount = Days.daysBetween(new LocalDate(dateOfCreation), new LocalDate(dateOfCreation).dayOfMonth().withMaximumValue()).getDays();
            working_hours = equipment.getWorkingHours() + (equipment.getTypeOfEquipment().getMonth_work_hours() / DAY_IN_MONTH) * daysCount;
        }
        return working_hours;
    }

    public TypeOfMainToEquipment getTypeOfMainToEquipment(Equipment equipment, TypeOfMaintenance typeOfMaintenance) {
        return typeOfMainToEquipmentService.findByTypeOfEquipmentIdAndTypeOfMaintenanceId(
                equipment.getTypeOfEquipment().getType_of_equipment_id(), typeOfMaintenance.getType_of_maintenance_id());
    }

    public User getCurrentUser() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userService.getUserBySso(userName);
    }

    public void sortRepairUnitListByPriority(List<RepairUnit> repairUnitList) {
        repairUnitList.sort(Comparator.comparingInt(RepairUnit::getPriority));
    }

    public boolean checkConstraint(List<RepairOperation> repairOperationList, RepairUnit repairUnit) {
        //if(checkFreeOrganization(repairOperationList))
        return false;
    }

    public boolean checkHolidays(LocalDate date) {
        boolean isHolidays = false;
        if ((date.getDayOfWeek() == 6 || date.getDayOfWeek() == 7)
                || (getHolidays().contains(date))) {
            isHolidays = true;
        }
        return isHolidays;
    }

    //TODO refactor to JAVA 8 lambda's
    public Optional<Organization> getFreeOrganizationInDateInterval(List<RepairOperation> repairOperationList) {
        Optional<Organization> freeOrganization = null;
        List<Organization> organizationList = organizationService.getOrganizations();
        if (repairOperationList != null && !repairOperationList.isEmpty()) {
            ListIterator listIterator = repairOperationList.listIterator();
            while (listIterator.hasNext()) {
                RepairOperation repairOperation = (RepairOperation) listIterator.next();
                organizationList.remove(repairOperation.getOrganization());
            }
            if (organizationList != null && !organizationList.isEmpty() && organizationList.get(0) != null) {
                freeOrganization = Optional.ofNullable(organizationList.get(0));
            }
        } else {
            freeOrganization = Optional.ofNullable(organizationList.get(0));
        }
        return freeOrganization;
    }

    public List<RepairOperation> getListOperationInDateInterval(Date date, List<RepairOperation> repairOperationList) {
        List<RepairOperation> newRepairOperationList = new ArrayList<>();
        if (repairOperationList != null && !repairOperationList.isEmpty()) {
            for (RepairOperation operation : repairOperationList) {
                if (operation.getStartDate().compareTo(date) != 1 && operation.getEndDate().compareTo(date) != -1) {
                    newRepairOperationList.add(operation);
                }
            }
        }
        return newRepairOperationList;
    }

    public List<RepairOperation> fillRepairInMonth(Graphic graphic, List<RepairUnit> repairUnitList) {
        List<RepairOperation> repairOperationList = new ArrayList<>();
        Optional<Organization> freeOrganization;
        Date date = graphic.getMonth();
        date.setDate(1);
        LocalDate localDate = new LocalDate(date);
        System.out.println("VAAN Date = " + date);
        RepairOperation currentRepairOperation;
        Date endDate;
        boolean isAdding;
        for (RepairUnit repairUnit : repairUnitList) {
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

    //public List<String> getRepairCycleByEquipment(int equipmentId){
    //    List<String> repairCycle = new ArrayList<>();
    //    Map<Integer, String> map = new HashMap<>();
    //    int typeOfEquipmentId = equipmentService.getEquipmentById(equipmentId).getTypeOfEquipment().getType_of_equipment_id();
    //    List<TypeOfMainToEquipment> typeOfMainToEquipmentList = typeOfMainToEquipmentService.findByTypeOfEquipmentId(typeOfEquipmentId)
    //            .stream().sorted(Comparator.comparingInt(TypeOfMainToEquipment::getPeriodicity)).collect(Collectors.toList());
    //    int sumPeriodicity = 0;
    //    while(!sumPeriodicity <= typeOfMainToEquipmentList.get(typeOfMainToEquipmentList.size()).getPeriodicity()){
    //        if(sumPeriodicity < typeOfMainToEquipmentList.){
    //            map
    //        }
    //    }
    //}

    public List<String> getRepairCycleByEquipment(Equipment equipment){
        List<TypeOfMainToEquipment> typeOfMainToEquipmentList = typeOfMainToEquipmentService.findByTypeOfEquipmentId(equipment.getTypeOfEquipment().getType_of_equipment_id());
        typeOfMainToEquipmentList = typeOfMainToEquipmentList.stream().sorted(Comparator.comparingInt(TypeOfMainToEquipment::getPeriodicity).reversed()).collect(Collectors.toList());
        List<String> repairCycleList = new ArrayList<>();
        int step = typeOfMainToEquipmentList.get(typeOfMainToEquipmentList.size()-1).getPeriodicity();
        int sum_of_periodicity = step;
        System.out.println("VAAN step = " + step);
        typeOfMainToEquipmentList.stream().forEach(e -> System.out.println("VAAN typeOfMainTo = " + e.getType_of_main_to_equipment_id()));
        while (sum_of_periodicity <= typeOfMainToEquipmentList.get(0).getPeriodicity()){
            for(int i =0; i < typeOfMainToEquipmentList.size(); i++){
                System.out.println("VAAN in for = " + typeOfMainToEquipmentList.get(i).getType_of_main_to_equipment_id());
                if(sum_of_periodicity >= typeOfMainToEquipmentList.get(i).getPeriodicity() &&
                       sum_of_periodicity % typeOfMainToEquipmentList.get(i).getPeriodicity() == 0){
                    sum_of_periodicity += step;
                    repairCycleList.add(typeOfMaintenanceService.getTypeById(typeOfMainToEquipmentList.get(i).getTypeOfMaintenanceId()).getType_of_maintenance_name());
                    System.out.println("VAAN SUCCESS = " + typeOfMainToEquipmentList.get(i).getType_of_main_to_equipment_id());
                    System.out.println("VAAN SUCCESS2 = " + sum_of_periodicity);
                    break;
                }
            }
        }
        repairCycleList.stream().forEach(e -> System.out.println("ELEM = " + e));
        return repairCycleList;
    }

    public Integer getNumberOfCurrentMaintenance(Equipment equipment, List<String> repairCycle){
        System.out.println("VAAN in  getNumberOfCurrentMaintenance = " );
        int number = 0;
        double sumOfWorkingHours = 0;
        if(repairCycle != null && !repairCycle.isEmpty()){
            TypeOfMaintenance lastTypeOfMaintenance = technologicalCardService.getTechCardByEquipment(equipment).stream().sorted(Comparator.
                    comparing(TechnologicalCard::getEnd_date).reversed()).collect(Collectors.toList()).get(0).getType_of_maintenance();
            TypeOfMainToEquipment typeOfMainToEquipment = typeOfMainToEquipmentService.
                    findByTypeOfEquipmentIdAndTypeOfMaintenanceId(equipment.getEquipmentId(), lastTypeOfMaintenance.getType_of_maintenance_id());
            for(int i = 0; i < repairCycle.size(); i++){
                if(repairCycle.get(i).equals(lastTypeOfMaintenance.getType_of_maintenance_name())){
                    System.out.println("VAAN in TRUE= " + lastTypeOfMaintenance.getType_of_maintenance_name());
                    sumOfWorkingHours += typeOfMainToEquipment.getWork_hours_limit();
                    if(equipment.getWorkingHours() <= sumOfWorkingHours){
                        number = i + 1;
                        System.out.println("VAAN 777 = " + sumOfWorkingHours + " VS - " + equipment.getWorkingHours() + " FINALLY = " + number);
                        break;
                    }
                }
            }

        }

        return number;
    }

    public void setRightRepairOperation(Graphic graphic) {
        List<RepairOperation> repairOperationList = repairOperationService.getRepairSheetByGraphicId(graphic.getGraphicId());
        repairOperationList.forEach(e -> e.setViolation(true));
    }

    public Optional<List<RepairUnit>> getLastMaintenancesByEquipment(Equipment equipment) {
        List<RepairUnit> repairUnitList = new ArrayList<RepairUnit>();
        List<TypeOfMaintenance> typeOfMaintenanceList = typeOfMaintenanceService.getAllTypes();
        for(TypeOfMaintenance typeOfMaintenance : typeOfMaintenanceList){
            Optional<TechnologicalCard> technologicalCard = getLastMaintenanceByEquipment(equipment, typeOfMaintenance);
            if(technologicalCard.isPresent()){
                TypeOfMainToEquipment typeOfMainToEquipment = getTypeOfMainToEquipment(equipment, typeOfMaintenance);
                RepairUnit repairUnit = new RepairUnit();
                repairUnit.setTypeOfMaintenance(typeOfMaintenance);
                repairUnit.setLastDateOfMaintenance(technologicalCard.get().getEnd_date());
                repairUnit.setNextDateOfMaintenance(getNextDateOfMaintenanceByEquipment(equipment, typeOfMainToEquipment, technologicalCard.get().getEnd_date()).get());
                repairUnitList.add(repairUnit);
            }
        }
        return Optional.of(repairUnitList);
    }

    public Integer getLastTechCardId(){
        List<TechnologicalCard> technologicalCards = technologicalCardService.getAllCards();
        return technologicalCards.stream().sorted(Comparator.comparing(TechnologicalCard::getTechnological_card_id)
                .reversed()).collect(Collectors.toList()).get(0).getTechnological_card_id();
    }

    public long getLastTechCardNumber(){
        List<TechnologicalCard> technologicalCards = technologicalCardService.getAllCards();
        return technologicalCards.stream().sorted(Comparator.comparing(TechnologicalCard::getTechnological_card_number)
                .reversed()).collect(Collectors.toList()).get(0).getTechnological_card_number();
    }

    public boolean checkExistsOfPlan(Date date, boolean isYear){
        boolean isExists = false;
        if(date != null) {
            for (Graphic graphic : graphicService.getAllGraphics()) {
                if (graphic.getMonth().getMonth() == date.getMonth() &&
                        graphic.isYear() == isYear) {
                    isExists = true;
                }
            }
        }
        return  isExists;
    }
}