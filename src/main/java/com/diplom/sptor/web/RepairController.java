package com.diplom.sptor.web;

import com.diplom.sptor.domain.*;
import com.diplom.sptor.service.*;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by user on 02.02.2016.
 */
@Controller
@RequestMapping(value = "/repair")
public class RepairController {

    @Autowired
    SpareService spareService;

    @Autowired
    ComponentService componentService;

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    UserService userService;

    @Autowired
    SubdivisionService subdivisionService;

    @Autowired
    TypeOfMaintenanceService typeOfMaintenanceService;

    @Autowired
    StatusService statusService;

    @Autowired
    RepairSheetService repairSheetService;

    @Autowired
    StatusOfEqService statusOfEqService;


    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @ApiOperation(value = "getEquipments", notes = "Get all equipments")
    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json")
    public String RepairStart(Model model) {
        model.addAttribute("repairSheet", new RepairSheet());
        model.addAttribute("listRepair", repairSheetService.getAllRepairSheets());
        model.addAttribute("status_one", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(1)).size());
        model.addAttribute("status_two", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(2)).size() + repairSheetService.getRepairSheetByStatus(statusService.getStatusById(3)).size());
        model.addAttribute("status_three", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(4)).size());
        model.addAttribute("status_four", repairSheetService.getRepairSheetByStatus(statusService.getStatusById(5)).size());
        model.addAttribute("status_all", repairSheetService.getAllRepairSheets().size());
        model.addAttribute("subdivisions", subdivisionService.getAllSubdivisions());
        model.addAttribute("components", componentService.getAllComponents());
        model.addAttribute("current_user", userService.getUserBySso(getPrincipal()).getLast_name() + " " +
                userService.getUserBySso(getPrincipal()).getFirst_name());
        model.addAttribute("listEquipments", equipmentService.getAllEquipment());
        model.addAttribute("listTypeOfMaintenance", typeOfMaintenanceService.getAllTypes());
        Status status1 = statusService.getStatusById(1);
        Status status2 = statusService.getStatusById(2);
        model.addAttribute("active_req", repairSheetService.getRepairSheetByStatus(status1).size());
        model.addAttribute("confirm_req", repairSheetService.getRepairSheetByStatus(status2).size());
        model.addAttribute("listStatus", repairSheetService.getAllRepairSheets());
        return "repairPage";
    }



    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addRepairSheet(
            @RequestParam int subdivision_id,
            @RequestParam int equipment_id,
            @RequestParam int components,
            @RequestParam int type_of_maintenance_id,
            @RequestParam String start_date,
            @RequestParam String description
    ) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date start = formatter.parse(start_date);
        Date end = formatter.parse(start_date);
        DateTime dateTime = new DateTime(end);
        DateTimeFormatter dtf = DateTimeFormat.forPattern("mm-dd-yyyy");
        DateTime end_date = dateTime.plusDays(typeOfMaintenanceService.getTypeById(type_of_maintenance_id).getDuration());
        Status status = statusService.getStatusById(1);
        User resp_for_delivery = userService.getUserById(1);
        User resp_for_reception = userService.getUserById(1);
        Equipment equipment = equipmentService.getEquipmentById(equipment_id);
        Components components1 = componentService.getComponentById(components);
        Subdivisions subdivisions = subdivisionService.getSubdivisionById(subdivision_id);
        TypeOfMaintenance typeOfMaintenance = typeOfMaintenanceService.getTypeById(type_of_maintenance_id);
        int sheet_number = equipment_id + 33000;
        int warranty_period = type_of_maintenance_id;
        repairSheetService.addRepairSheet(new RepairSheet(typeOfMaintenance, equipment,
                components1, subdivisions, start, end_date.toDate(), sheet_number, warranty_period,
                resp_for_delivery, resp_for_reception, description, status));

        deleteComponentInStock(components1);
        changeStatusOfEquipment(equipment, status, typeOfMaintenance);

        return "repairPage";

    }

    public void deleteComponentInStock(Components components){
        Spares spares =  spareService.getSpareById(components.getSpare().getSpare_id());
        spares.setAmount_in_stock(spares.getAmount_in_stock() - 1);
        //this.SpareService.updateSpare(spares);
    }

    public void changeStatusOfEquipment(Equipment equipment, Status status, TypeOfMaintenance typeOfMaintenance){
        int type = typeOfMaintenance.getType_of_maintenance_id();
        switch (status.getStatus_id()){
            case 1:
                if(type==1||type==2||type==6||type==7||type==8){
                    equipment.setStatus(statusOfEqService.getStatusById(2));
                    equipmentService.updateEquipment(equipment);
                }
                else if(type==3||type==4||type==5){
                    equipment.setStatus(statusOfEqService.getStatusById(3));
                    equipmentService.updateEquipment(equipment);
                }
                else{
                    equipment.setStatus(statusOfEqService.getStatusById(4));
                    equipmentService.updateEquipment(equipment);
                }
                break;
            case 4:
                equipment.setStatus(statusOfEqService.getStatusById(1));
                equipmentService.updateEquipment(equipment);
                break;
            case 5:
                equipment.setStatus(statusOfEqService.getStatusById(1));
                equipmentService.updateEquipment(equipment);
                break;
        }
    }

    @ApiOperation(value = "getRepairSheetById", notes = "Get all equipments")
    @RequestMapping(value = "/{repairId}", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody RepairSheet getRepairById(@PathVariable("repairId") int repairId, Model model) {
        return repairSheetService.getRepairSheetById(repairId);
    }

    @ApiOperation(value = "getRepairSheetById", notes = "Get all equipments")
    @RequestMapping(value = "/equipment/{equipmentId}", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody List<RepairSheet> getRepairByEquipmentId(@PathVariable("equipmentId") int equipmentId, Model model) {
        List<RepairSheet> repairSheets = repairSheetService.getRepairSheetByEquipment(equipmentService.getEquipmentById(equipmentId));
        Iterator<RepairSheet> iterator = repairSheets.iterator();
        while(iterator.hasNext()){
            RepairSheet repairSheet = iterator.next();
            if(repairSheet.getType_of_maintenance().getType_of_maintenance_id()==3||repairSheet.getType_of_maintenance().getType_of_maintenance_id()==4
                    ||repairSheet.getType_of_maintenance().getType_of_maintenance_id()==5){
                iterator.remove();
            }
        }

        return repairSheetService.getRepairSheetByEquipment(equipmentService.getEquipmentById(equipmentId));
    }

    @ApiOperation(value = "getAllRepairSheet", notes = "Get all equipments")
    @RequestMapping(value = "/all", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody List<RepairSheet> getAllRepair(Model model) {
        return repairSheetService.getAllRepairSheets();
    }

    @ApiOperation(value = "getRepairSheetById", notes = "Get all equipments")
    @RequestMapping(value = "/repair/{repairId}", method = RequestMethod.POST,
            produces = "application/json")
    public void updateStatusRepair(@PathVariable("repairId") int repairId,
                                   @RequestParam String date,
                                   @RequestParam int status,
                                   @RequestParam String description, Model model) throws ParseException  {
        RepairSheet repairSheet= repairSheetService.getRepairSheetById(repairId);
        Status statusNew = statusService.getStatusById(status);
        repairSheet.setStatus(statusNew);
        repairSheet.setDescription(description);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = formatter.parse(date);
        if(status==5){
            repairSheet.setEnd_date(newDate);
        }
        else {
            repairSheet.setConfirm_date(newDate);
        }
        repairSheetService.updateRepairSheet(repairSheet);

        changeStatusOfEquipment(repairSheet.getEquipment(),statusNew, repairSheet.getType_of_maintenance());
    }

    @ApiOperation(value = "getRepairSheetById", notes = "Get all equipments")
    @RequestMapping(value = "/confirm/{repairId}", method = RequestMethod.POST,
            produces = "application/json")
    public void confirmRepair(@PathVariable("repairId") int repairId,
                              @RequestParam int status,
                              @RequestParam String description, Model model) throws ParseException  {
        RepairSheet repairSheet= repairSheetService.getRepairSheetById(repairId);
        Status statusNew = statusService.getStatusById(status);
        repairSheet.setStatus(statusNew);
        if (description.length()!=0) {
            repairSheet.addDescription(description);
        }
        repairSheetService.updateRepairSheet(repairSheet);
    }

}