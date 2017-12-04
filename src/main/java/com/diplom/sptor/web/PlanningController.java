package com.diplom.sptor.web;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.TypeOfMaintenance;
import com.diplom.sptor.planning.domain.RepairUnit;
import com.diplom.sptor.planning.domain.YearPlan;
import com.diplom.sptor.service.EquipmentService;
import com.diplom.sptor.service.TypeOfMainToEquipmentService;
import com.diplom.sptor.service.TypeOfMaintenanceService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by vanosov on 20.11.2017.
 */
@Controller
public class PlanningController {

    @Autowired
    YearPlan yearPlan;

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    TypeOfMaintenanceService typeOfMaintenanceService;

    public void setYearPlan(YearPlan yearPlan) {
        this.yearPlan = yearPlan;
    }

    @ApiOperation(value = "getEquipmentBySubId", notes = "Get equipment by Subdivision_Id")
    @RequestMapping(value = "/planning/equipments", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    List<RepairUnit> getParametersForEquipment(Model model){
        List<Equipment> allEquipments = equipmentService.getAllEquipment();
        List<TypeOfMaintenance> allTypes = typeOfMaintenanceService.getAllTypes();
        yearPlan.setDateOfCreation(new Date());
        yearPlan.setYear(false);
        return yearPlan.getListOfEquipmentsWhereNeedsInMaintenanceMonth(allEquipments, allTypes);
    }
}
