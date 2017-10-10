package com.diplom.sptor.planning;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.WorkingHours;
import com.diplom.sptor.service.EquipmentService;
import com.diplom.sptor.service.WorkingHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 21.09.2017.
 */
@Component
public class PlanningUtils {

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    static WorkingHoursService workingHoursService;

    Map<Equipment,Date> lastDateOfRepairMap = null;

    private Map<Equipment,Date> getLastDateOfRepairByEquipments(){
        for(Equipment equipment : equipmentService.getAllEquipment()){
            lastDateOfRepairMap.put(equipment, equipment.getLastDateOfRepair());
        }
        return lastDateOfRepairMap;
    }
}
