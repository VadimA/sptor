package com.diplom.sptor.planning;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.TypeOfMaintenance;
import com.diplom.sptor.planning.domain.GeneralPlan;
import com.diplom.sptor.service.EquipmentService;
import com.diplom.sptor.service.TypeOfMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class PlanGenerator {

    @Autowired
    GeneralPlan generalPlan;

    public void setGeneralPlan(GeneralPlan generalPlan) {
        this.generalPlan = generalPlan;
    }
}
