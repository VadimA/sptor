package com.diplom.sptor.web;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.TypeOfMaintenance;
import com.diplom.sptor.planning.PlanningUtils;
import com.diplom.sptor.domain.Graphic;
import com.diplom.sptor.domain.RepairOperation;
import com.diplom.sptor.planning.domain.RepairUnit;
import com.diplom.sptor.planning.domain.YearPlan;
import com.diplom.sptor.service.EquipmentService;
import com.diplom.sptor.service.GraphicService;
import com.diplom.sptor.service.TypeOfMaintenanceService;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    PlanningUtils planningUtils;

    @Autowired
    GraphicService graphicService;

    public void setYearPlan(YearPlan yearPlan) {
        this.yearPlan = yearPlan;
    }

    @ApiOperation(value = "getEquipmentBySubId", notes = "Get equipment by Subdivision_Id")
    @RequestMapping(value = "/planning/equipments", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    List<RepairUnit> getParametersForEquipment(Model model){
        return getRepairUnitList();
    }

    @RequestMapping(value = "/planning/graphics/new", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<RepairOperation> makeInitialGraphic(Model model){
        List<RepairUnit> repairUnitList = getRepairUnitList();
        Date month = new Date();
        Graphic graphic = new Graphic(new Date(), planningUtils.getCurrentUser(), new Date(),
                planningUtils.getCurrentUser(), month, false);
        graphicService.addGraphic(graphic);
        planningUtils.sortRepairUnitListByPriority(repairUnitList);
        return planningUtils.fillRepairInMonth(graphicService.getAllGraphics().get(0), repairUnitList);
    }

    public List<RepairUnit> getRepairUnitList(){
        List<Equipment> allEquipments = equipmentService.getAllEquipment();
        List<TypeOfMaintenance> allTypes = typeOfMaintenanceService.getAllTypes();
        yearPlan.setDateOfCreation(new Date());
        yearPlan.setYear(false);
        return yearPlan.getListOfEquipmentsWhereNeedsInMaintenanceMonth(allEquipments, allTypes, yearPlan.getDateOfCreation());
    }

}
