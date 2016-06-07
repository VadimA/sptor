package com.diplom.sptor.web;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.RepairSheet;
import com.diplom.sptor.service.*;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 02.02.2016.
 */
@Controller
public class RepairController {

    private EquipmentService EquipmentService;
    private SubdivisionService SubdivisionService;
    private TypeOfEquipmentService TypeOfEquipmentService;
    private RepairSheetService RepairSheetService;

    @Autowired(required = true)
    public void setTypeOfEquipmentService(TypeOfEquipmentService TypeOfEquipmentService) {
        this.TypeOfEquipmentService = TypeOfEquipmentService;
    }

    @Autowired(required = true)
    public void setEquipmentService(EquipmentService EquipmentService) {
        this.EquipmentService = EquipmentService;
    }

    @Autowired(required = true)
    public void setSubdivisionService(SubdivisionService SubdivisionService) {
        this.SubdivisionService = SubdivisionService;
    }

    @Autowired(required = true)
    public void setRepairSheetService(RepairSheetService RepairSheetService) {
        this.RepairSheetService = RepairSheetService;
    }

    /**
     * GET list of all equipments.
     *
     * @return list
     */


  //  @ApiOperation(value = "getRepairSheet", notes = "Get repair sheet by ID")
  //  @RequestMapping(value = "/{repairSheetId}", method = RequestMethod.GET,
  //          produces = "application/json")
  //  public @ResponseBody RepairSheet getRepairSheet(@ApiParam(value = "ID of question that" +
  //          "needs to be fetched", required = true,
  //          defaultValue = "1")@PathVariable(value = "repairSheetId")int id) {
  //      return RepairSheetService.getRepairSheetById(id);
  //  }
//
//
//
  //  @ApiOperation(value = "updateRepairSheet", notes = "Update repair sheet by ID")
  //  @RequestMapping(value = "/{id}", method = RequestMethod.PUT,
  //          produces = "application/json")
  //  public String editRepairSheet(@PathVariable("id") int id, Model model){
  //      model.addAttribute("repair_sheet", this.RepairSheetService.getRepairSheetById((id)));
  //      model.addAttribute("listRepairSheet", this.RepairSheetService.listRepairSheets());
//
  //      return "repair_sheet";
  //  }
//
  //  @ApiOperation(value = "deleteRepairSheet", notes = "Delete repair sheet by ID")
  //  @RequestMapping(value = "/{repairSheetId}", method = RequestMethod.DELETE,
  //          produces = "application/json")
  //  public @ResponseBody String deleteRepairSheet(@ApiParam(value = "ID of repair sheet" +
  //          "needs to be fetched", required = true,
  //          defaultValue = "1")@PathVariable("repairSheetId") int id){
  //      this.RepairSheetService.deleteRepairSheet(id);
  //      return "Deleted successfully";
  //  }


}