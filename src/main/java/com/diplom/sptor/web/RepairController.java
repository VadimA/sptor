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
@RequestMapping(value = "/rep")
public class RepairController {
   // private EquipmentService EquipmentService;
   // private SubdivisionService SubdivisionService;
   // private RepairSheetService RepairSheetService;
   // private TypeOfMaintenanceService TypeOfMaintenanceService;
   // private StatusService StatusService;
   // private StatusOfEqService StatusOfEqService;

    @Autowired
    UserService userService;

    @Autowired
    SpareService spareService;

    @Autowired
    ComponentManager componentManager;
  //@Autowired(required=true)
  //public void setEquipmentService(EquipmentService EquipmentService){
  //    this.EquipmentService = EquipmentService;
  //}

  //@Autowired(required=true)
  //public void setSubdivisionService(SubdivisionService SubdivisionService){
  //    this.SubdivisionService = SubdivisionService;
  //}

  //@Autowired(required=true)
  //public void setRepairSheetService(RepairSheetService RepairSheetService){
  //    this.RepairSheetService = RepairSheetService;
  //}

   // @Autowired(required=true)
   // public void setTypeOfMaintenanceService(TypeOfMaintenanceService TypeOfMaintenanceService) {
   //     this.TypeOfMaintenanceService = TypeOfMaintenanceService;
   // }
//
   // @Autowired(required=true)
   // public void setStatusService(StatusService StatusService) {
   //     this.StatusService = StatusService;
   // }
//
   // @Autowired(required=true)
   // public void setStatusOfEqService(StatusOfEqService StatusOfEqService) {
   //     this.StatusOfEqService = StatusOfEqService;
   // }

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
    @RequestMapping(value = "/", method = RequestMethod.GET,
            produces = "application/json")
    public String RepairStart(Model model) {
       // model.addAttribute("repairSheet", new RepairSheet());
       // model.addAttribute("listRepair", this.RepairSheetService.listRepairSheets());
       // model.addAttribute("status_one", this.RepairSheetService.getRepairSheetByStatus(this.StatusService.getStatusById(1)).size());
       // model.addAttribute("status_two", this.RepairSheetService.getRepairSheetByStatus(this.StatusService.getStatusById(2)).size() + this.RepairSheetService.getRepairSheetByStatus(this.StatusService.getStatusById(3)).size());
       // model.addAttribute("status_three", this.RepairSheetService.getRepairSheetByStatus(this.StatusService.getStatusById(4)).size());
       // model.addAttribute("status_four", this.RepairSheetService.getRepairSheetByStatus(this.StatusService.getStatusById(5)).size());
       // model.addAttribute("status_all", this.RepairSheetService.listRepairSheets().size());
       // model.addAttribute("subdivisions", this.SubdivisionService.listSubdivisions());
        model.addAttribute("components", componentManager.getAllComponents());
        model.addAttribute("user", userService.getUserBySso(getPrincipal()).getLast_name() + " VAAN " +
                userService.getUserBySso(getPrincipal()).getFirst_name());
      //  model.addAttribute("listEquipments", this.EquipmentService.listEquipments());
      //  model.addAttribute("listTypeOfMaintenance", this.TypeOfMaintenanceService.listType());
      // Status status1 = StatusService.getStatusById(1);
      // Status status2 = StatusService.getStatusById(2);
      // model.addAttribute("active_req", this.RepairSheetService.getRepairSheetByStatus(status1).size());
      // model.addAttribute("confirm_req", this.RepairSheetService.getRepairSheetByStatus(status2).size());
      // model.addAttribute("listStatus", this.StatusService.listStatus());
        return "repair";
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
     //   DateTime end_date = dateTime.plusDays(this.TypeOfMaintenanceService.getTypeById(type_of_maintenance_id).getDuration());
     //   Status status = this.StatusService.getStatusById(1);
        User resp_for_delivery = userService.getUserById(1);
        User resp_for_reception = userService.getUserById(1);
     //   Equipment equipment = this.EquipmentService.getEquipmentById(equipment_id);
        Components components1 = componentManager.getComponentById(components);
      //  Subdivisions subdivisions = this.SubdivisionService.getSubdivisionById(subdivision_id);
     //   TypeOfMaintenance typeOfMaintenance = this.TypeOfMaintenanceService.getTypeById(type_of_maintenance_id);
        int sheet_number = equipment_id + 33000;
        int warranty_period = type_of_maintenance_id;
      // this.RepairSheetService.addRepairSheet(new RepairSheet(typeOfMaintenance, equipment,
      //         components1, subdivisions, start, end_date.toDate(), sheet_number, warranty_period,
      //         resp_for_delivery, resp_for_reception, description, status));

        deleteComponentInStock(components1);
      //  changeStatusOfEquipment(equipment, status, typeOfMaintenance);

        return "redirect:/repair";

    }

    public void deleteComponentInStock(Components components){
        Spares spares =  spareService.getSpareById(components.getSpare().getSpare_id());
        spares.setAmount_in_stock(spares.getAmount_in_stock() - 1);
        //this.SpareService.updateSpare(spares);
    }

  // public void changeStatusOfEquipment(Equipment equipment, Status status, TypeOfMaintenance typeOfMaintenance){
  //     int type = typeOfMaintenance.getType_of_maintenance_id();
  //     switch (status.getStatus_id()){
  //         case 1:
  //             if(type==1||type==2||type==6||type==7||type==8){
  //                 equipment.setStatus(this.StatusOfEqService.getStatusById(2));
  //                 this.EquipmentService.updateEquipment(equipment);
  //             }
  //             else if(type==3||type==4||type==5){
  //                 equipment.setStatus(this.StatusOfEqService.getStatusById(3));
  //                 this.EquipmentService.updateEquipment(equipment);
  //             }
  //             else{
  //                 equipment.setStatus(this.StatusOfEqService.getStatusById(4));
  //                 this.EquipmentService.updateEquipment(equipment);
  //             }
  //             break;
  //         case 4:
  //             equipment.setStatus(this.StatusOfEqService.getStatusById(1));
  //             this.EquipmentService.updateEquipment(equipment);
  //             break;
  //         case 5:
  //             equipment.setStatus(this.StatusOfEqService.getStatusById(1));
  //             this.EquipmentService.updateEquipment(equipment);
  //             break;
  //     }
  // }

  //  @ApiOperation(value = "getRepairSheetById", notes = "Get all equipments")
  //  @RequestMapping(value = "/{repairId}", method = RequestMethod.GET,
  //          produces = "application/json")
  //  public @ResponseBody RepairSheet getRepairById(@PathVariable("repairId") int repairId, Model model) {
  //      return this.RepairSheetService.getRepairSheetById(repairId);
  //  }

  // @ApiOperation(value = "getRepairSheetById", notes = "Get all equipments")
  // @RequestMapping(value = "/equipment/{equipmentId}", method = RequestMethod.GET,
  //         produces = "application/json")
  // public @ResponseBody List<RepairSheet> getRepairByEquipmentId(@PathVariable("equipmentId") int equipmentId, Model model) {
  //     List<RepairSheet> repairSheets = this.RepairSheetService.getRepairSheetByEquipment(this.EquipmentService.getEquipmentById(equipmentId));
  //     Iterator<RepairSheet> iterator = repairSheets.iterator();
  //     while(iterator.hasNext()){
  //         RepairSheet repairSheet = iterator.next();
  //         if(repairSheet.getType_of_maintenance().getType_of_maintenance_id()==3||repairSheet.getType_of_maintenance().getType_of_maintenance_id()==4
  //                 ||repairSheet.getType_of_maintenance().getType_of_maintenance_id()==5){
  //             iterator.remove();
  //         }
  //     }

  //     return this.RepairSheetService.getRepairSheetByEquipment(this.EquipmentService.getEquipmentById(equipmentId));
  // }

   // @ApiOperation(value = "getAllRepairSheet", notes = "Get all equipments")
   // @RequestMapping(value = "/all", method = RequestMethod.GET,
   //         produces = "application/json")
   // public @ResponseBody List<RepairSheet> getAllRepair(Model model) {
   //     return this.RepairSheetService.listRepairSheets();
   // }
//
  //  @ApiOperation(value = "getRepairSheetById", notes = "Get all equipments")
  //  @RequestMapping(value = "/{repairId}", method = RequestMethod.POST,
  //          produces = "application/json")
  //  public void updateStatusRepair(@PathVariable("repairId") int repairId,
  //                                 @RequestParam String date,
  //                                 @RequestParam int status,
  //                                 @RequestParam String description, Model model) throws ParseException  {
  //      RepairSheet repairSheet= this.RepairSheetService.getRepairSheetById(repairId);
  //      Status statusNew = this.StatusService.getStatusById(status);
  //      repairSheet.setStatus(statusNew);
  //      repairSheet.setDescription(description);
  //      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  //      Date newDate = formatter.parse(date);
  //      if(status==5){
  //          repairSheet.setEnd_date(newDate);
  //      }
  //      else {
  //          repairSheet.setConfirm_date(newDate);
  //      }
  //      this.RepairSheetService.updateRepairSheet(repairSheet);
//
  //      changeStatusOfEquipment(repairSheet.getEquipment(),statusNew, repairSheet.getType_of_maintenance());
  //  }
//
  //  @ApiOperation(value = "getRepairSheetById", notes = "Get all equipments")
  //  @RequestMapping(value = "/confirm/{repairId}", method = RequestMethod.POST,
  //          produces = "application/json")
  //  public void confirmRepair(@PathVariable("repairId") int repairId,
  //                            @RequestParam int status,
  //                            @RequestParam String description, Model model) throws ParseException  {
  //      RepairSheet repairSheet= this.RepairSheetService.getRepairSheetById(repairId);
  //      Status statusNew = this.StatusService.getStatusById(status);
  //      repairSheet.setStatus(statusNew);
  //      if (description.length()!=0) {
  //          repairSheet.addDescription(description);
  //      }
  //      this.RepairSheetService.updateRepairSheet(repairSheet);
  //  }


}