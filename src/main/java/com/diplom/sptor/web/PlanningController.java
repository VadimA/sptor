package com.diplom.sptor.web;

import com.diplom.sptor.domain.*;
import com.diplom.sptor.planning.PlanningUtils;
import com.diplom.sptor.planning.domain.Hierarchy;
import com.diplom.sptor.planning.domain.RepairUnit;
import com.diplom.sptor.planning.domain.YearPlan;
import com.diplom.sptor.service.*;
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

    @Autowired
    RepairSheetService repairSheetService;

    @Autowired
    SubdivisionService subdivisionService;

    @Autowired
    StatusService statusService;

    @Autowired
    TechnologicalCardService technologicalCardService;

    @Autowired
    RepairOperationService repairOperationService;

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
        model.addAttribute("listRepair", repairSheetService.getAllRepairSheets());
        model.addAttribute("status_one", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(1)).size());
        model.addAttribute("status_two", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(2)).size() + repairSheetService.getRepairSheetByStatus(statusService.getStatusById(3)).size());
        model.addAttribute("status_three", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(4)).size());
        model.addAttribute("status_four", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(5)).size());
        model.addAttribute("status_all", repairSheetService.getAllRepairSheets().size());
        model.addAttribute("subdivisions", subdivisionService.getAllSubdivisions());
        model.addAttribute("listEquipments", equipmentService.getAllEquipment());
        model.addAttribute("listTypeOfMaintenance", typeOfMaintenanceService.getAllTypes());
        Status status1 = statusService.getStatusById(1);
        Status status2 = statusService.getStatusById(2);
        model.addAttribute("active_req", repairSheetService.getRepairSheetByStatus(status1).size());
        model.addAttribute("confirm_req", repairSheetService.getRepairSheetByStatus(status2).size());
        model.addAttribute("active_techcard", technologicalCardService.getTechnologicalCardByStatus(status1).size());
        model.addAttribute("confirm_techcard", technologicalCardService.getTechnologicalCardByStatus(status2).size());
        model.addAttribute("listStatus", repairSheetService.getAllRepairSheets());
        return "ppr_graphic";
    }

    @RequestMapping(value = "/planning/repair_cycle/equipments/all", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Hierarchy> getHierarchyListOfEquipment(Model model) {
        List<Hierarchy> hierarchies = new ArrayList<>();
        List<Subdivisions> subdivisionsList = subdivisionService.getAllSubdivisions();
        for(Subdivisions subdivisions : subdivisionsList){
            Hierarchy hierarchy = new Hierarchy();
            hierarchy.setText(subdivisions.getSubdivision_name());
            List<Hierarchy> eqNames = new ArrayList<>();
            for(Equipment equipment : subdivisions.getEquipments_sub()){
                Hierarchy hierarchyLocal = new Hierarchy();
                hierarchyLocal.setText(equipment.getEquipmentName());
                eqNames.add(hierarchyLocal);
            }
            hierarchy.setNodes(eqNames);
            hierarchies.add(hierarchy);
        }
        return hierarchies;
    }

    @RequestMapping(value = "/planning/graphics/year", method = RequestMethod.GET,
            produces = "application/json")
    public String getPageYearPPRGraphic(Model model) {
        model.addAttribute("listRepair", repairSheetService.getAllRepairSheets());
        model.addAttribute("status_one", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(1)).size());
        model.addAttribute("status_two", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(2)).size() + repairSheetService.getRepairSheetByStatus(statusService.getStatusById(3)).size());
        model.addAttribute("status_three", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(4)).size());
        model.addAttribute("status_four", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(5)).size());
        model.addAttribute("status_all", repairSheetService.getAllRepairSheets().size());
        model.addAttribute("subdivisions", subdivisionService.getAllSubdivisions());
        model.addAttribute("listEquipments", equipmentService.getAllEquipment());
        model.addAttribute("listTypeOfMaintenance", typeOfMaintenanceService.getAllTypes());
        Status status1 = statusService.getStatusById(1);
        Status status2 = statusService.getStatusById(2);
        model.addAttribute("active_req", repairSheetService.getRepairSheetByStatus(status1).size());
        model.addAttribute("confirm_req", repairSheetService.getRepairSheetByStatus(status2).size());
        model.addAttribute("active_techcard", technologicalCardService.getTechnologicalCardByStatus(status1).size());
        model.addAttribute("confirm_techcard", technologicalCardService.getTechnologicalCardByStatus(status2).size());
        model.addAttribute("listStatus", repairSheetService.getAllRepairSheets());
        return "ppr_graphic_year";
    }
    @RequestMapping(value = "/planning/graphics", method = RequestMethod.GET,
            produces = "application/json")
    public String getPageNewPPRGraphic(Model model) {
        model.addAttribute("listRepair", repairSheetService.getAllRepairSheets());
        model.addAttribute("status_one", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(1)).size());
        model.addAttribute("status_two", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(2)).size() + repairSheetService.getRepairSheetByStatus(statusService.getStatusById(3)).size());
        model.addAttribute("status_three", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(4)).size());
        model.addAttribute("status_four", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(5)).size());
        model.addAttribute("status_all", repairSheetService.getAllRepairSheets().size());
        model.addAttribute("subdivisions", subdivisionService.getAllSubdivisions());
        model.addAttribute("listEquipments", equipmentService.getAllEquipment());
        model.addAttribute("listTypeOfMaintenance", typeOfMaintenanceService.getAllTypes());
        Status status1 = statusService.getStatusById(1);
        Status status2 = statusService.getStatusById(2);
        model.addAttribute("active_req", repairSheetService.getRepairSheetByStatus(status1).size());
        model.addAttribute("confirm_req", repairSheetService.getRepairSheetByStatus(status2).size());
        model.addAttribute("active_techcard", technologicalCardService.getTechnologicalCardByStatus(status1).size());
        model.addAttribute("confirm_techcard", technologicalCardService.getTechnologicalCardByStatus(status2).size());
        model.addAttribute("listStatus", repairSheetService.getAllRepairSheets());
        return "ppr_graphic_new";
    }

    @ApiOperation(value = "getEquipments", notes = "Get all equipments")
    @RequestMapping(value = "/planning/repair_cycle", method = RequestMethod.GET,
            produces = "application/json")
    public String getPageRepairCycle(Model model) {
        return "repair_cycle";
    }

    public void approvePPRPlan(Graphic graphic){
        graphic.setStatus(2);
        List<RepairOperation> repairOperationList = repairOperationService.getAllRepairSheets();
        for(RepairOperation operation: repairOperationList){
            if(operation.getGraphicId().getGraphicId() == graphic.getGraphicId()){
                operation.setViolation(true);
                repairOperationService.update(operation);
            }
        }
    }

    public void resetOtherGraphics(Graphic graphic){
        List<Graphic> graphicList = graphicService.getAllGraphics();
        List<RepairOperation> repairOperationList = null;
        for(Graphic graphicDB : graphicList){
            if(graphicDB.getGraphicId() != graphic.getGraphicId() && graphicDB.getStatus() == 2){
                graphicDB.setGraphicId(3);
                repairOperationList = repairOperationService.getRepairSheetByGraphicId(graphicDB.getGraphicId());
                for(RepairOperation operation: repairOperationList){
                    operation.setViolation(false);
                    repairOperationService.update(operation);
                }
            }
        }
    }

    public void addNewMaintenanceOnPPRPlan(List<RepairOperation> repairOperationList){
        for(RepairOperation repairOperation : repairOperationList){
            TechnologicalCard techCard = new TechnologicalCard();
            techCard.setEquipment(repairOperation.getEquipment());
            techCard.setTypeOfMaintenance(repairOperation.getTypeOfMaintenance());
            techCard.setStatus(statusService.getStatusById(1));
            techCard.setStart_date(new Date());
            techCard.setResponsible_for_delivery(repairOperation.getOrganization());
            techCard.setStart_date(repairOperation.getStartDate());
            techCard.setEnd_date(repairOperation.getEndDate());
            techCard.setResponsible_for_reception(planningUtils.getCurrentUser());
            techCard.setTechnological_card_id(planningUtils.getLastTechCardId());
            techCard.setTechnological_card_number(planningUtils.getLastTechCardNumber());
            technologicalCardService.addCard(techCard);
        }
    }
}
