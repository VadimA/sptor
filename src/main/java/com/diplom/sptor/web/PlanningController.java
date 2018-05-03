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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
                planningUtils.getCurrentUser(), month, 1, false);
        graphicService.addGraphic(graphic);
        planningUtils.sortRepairUnitListByPriority(repairUnitList);
        return planningUtils.fillRepairInMonth(graphic, repairUnitList);
    }

    @RequestMapping(value = "/planning/repair_cycle/{equipmentId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<String> getRepairCycleByEquipment(@PathVariable(value = "equipmentId")int equipmentId, Model model){
        Equipment equipment = equipmentService.getEquipmentById(equipmentId);
        List<String> maintenanceList = planningUtils.getRepairCycleByEquipment(equipment);
        model.addAttribute("number_of_cycle", planningUtils.getNumberOfCurrentMaintenance(equipment, maintenanceList));
        System.out.println("VAAN REPAIRCYCLE number = " + equipmentId);
        return maintenanceList;
    }

    public List<RepairUnit> getRepairUnitList(){
        List<Equipment> allEquipments = equipmentService.getAllEquipment();
        List<TypeOfMaintenance> allTypes = typeOfMaintenanceService.getAllTypes();
        yearPlan.setDateOfCreation(new Date());
        yearPlan.setYear(false);
        return yearPlan.getListOfEquipmentsWhereNeedsInMaintenanceMonth(allEquipments, allTypes, yearPlan.getDateOfCreation());
    }

    @RequestMapping(value = "/planning/graphics/month", method = RequestMethod.GET,
            produces = "application/json")
    public String getPagePPRGraphic(Model model) {
        return "ppr_graphic";
    }

    @RequestMapping(value = "/planning/graphics/year", method = RequestMethod.GET,
            produces = "application/json")
    public String getPageYearPPRGraphic(Model model) {
        return "ppr_graphic_year";
    }

    @ApiOperation(value = "getEquipments", notes = "Get all equipments")
    @RequestMapping(value = "/planning/repair_cycle", method = RequestMethod.GET,
            produces = "application/json")
    public String getPageRepairCycle(Model model) {
        return "repair_cycle";
    }
}
