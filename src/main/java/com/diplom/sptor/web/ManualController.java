package com.diplom.sptor.web;

import com.diplom.sptor.domain.*;
import com.diplom.sptor.model.UserFormModel;
import com.diplom.sptor.service.*;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 05.08.2016.
 */
@Controller
public class ManualController {

    @Autowired
    SpareService spareService;

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    UserService userService;

    @Autowired
    SubdivisionService subdivisionService;

    @Autowired
    StatusService statusService;

    @Autowired
    RepairSheetService repairSheetService;

    @Autowired
    StatusOfEqService statusOfEqService;

    @Autowired
    TypeOfEquipmentService typeOfEquipmentService;

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

    @ApiOperation(value = "getUsers", notes = "Get all technological cards")
    @RequestMapping(value = "/manual", method = RequestMethod.GET,
            produces = "application/json")
    public String getManual(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("spareForm", new User());
        Status status1 = statusService.getStatusById(1);
        Status status2 = statusService.getStatusById(2);
        model.addAttribute("active_req", repairSheetService.getRepairSheetByStatus(status1).size());
        model.addAttribute("confirm_req", repairSheetService.getRepairSheetByStatus(status2).size());
        model.addAttribute("subdivisions", subdivisionService.getAllSubdivisions());
        model.addAttribute("user", userService.getUserBySso(getPrincipal()).getLast_name() + " " +
                userService.getUserBySso(getPrincipal()).getFirst_name());
        return "manual";
    }


    @ApiOperation(value = "getUsers", notes = "Get all technological cards")
    @RequestMapping(value = "/users/all", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    List<User> getUsers(Model model) {
        return userService.getUsers();
    }

    @ApiOperation(value = "getUsers", notes = "Get all technological cards")
    @RequestMapping(value = "/users", method = RequestMethod.GET,
            produces = "application/json")
    public String getUsersView(Model model) {
        model.addAttribute("subdivisions", subdivisionService.getAllSubdivisions());
        model.addAttribute("userForm", new User());
        model.addAttribute("current_user", userService.getUserBySso(getPrincipal()).getLast_name() + " " +
                userService.getUserBySso(getPrincipal()).getFirst_name());
        Status status1 = statusService.getStatusById(1);
        Status status2 = statusService.getStatusById(2);
        model.addAttribute("active_req", repairSheetService.getRepairSheetByStatus(status1).size());
        model.addAttribute("confirm_req", repairSheetService.getRepairSheetByStatus(status2).size());
        return "users";
    }

    @ApiOperation(value = "addUser", notes = "Get all technological cards")
    @RequestMapping(value = "/users", method = RequestMethod.POST,
            produces = "application/json")
    public String addUsers(@Valid User user, BindingResult bindingResult) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        if(bindingResult.hasErrors()){
            return "redirect:/users";
        }
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(user.getPassword().getBytes("UTF-8"));
        byte [] digest = md.digest();
        user.setPassword(String.format("%064x", new java.math.BigInteger(1, digest)));
        userService.addUser(user);
        return "redirect:/users";
    }

    @ApiOperation(value = "getEquipments", notes = "Get all technological cards")
    @RequestMapping(value = "/equipment/all", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody List<Equipment> getEquipments(Model model) {
        return equipmentService.getAllEquipment();
    }

    @ApiOperation(value = "getEquipments", notes = "Get all technological cards")
    @RequestMapping(value = "/equipment", method = RequestMethod.GET,
            produces = "application/json")
    public String getEquipmentView(Model model) {
        model.addAttribute("eqType", typeOfEquipmentService.getAllTypes());
        model.addAttribute("eqSub", subdivisionService.getAllSubdivisions());
        model.addAttribute("eqResp", this.userService.getUsers());
        model.addAttribute("eqStatus", statusOfEqService.listStatus());
        model.addAttribute("subdivisions", subdivisionService.getAllSubdivisions());
        model.addAttribute("current_user", userService.getUserBySso(getPrincipal()).getLast_name() + " " +
                userService.getUserBySso(getPrincipal()).getFirst_name());
        Status status1 = statusService.getStatusById(1);
        Status status2 = statusService.getStatusById(2);
        model.addAttribute("active_req", repairSheetService.getRepairSheetByStatus(status1).size());
        model.addAttribute("confirm_req", repairSheetService.getRepairSheetByStatus(status2).size());
        return "equipments";
    }

    @ApiOperation(value = "addEquipment", notes = "Get all technological cards")
    @RequestMapping(value = "/equipment", method = RequestMethod.POST,
            produces = "application/json")
    public String addEquipment(@RequestParam String equipment_name,
                               @RequestParam int type_of_equipment_id,
                               @RequestParam int subdivision_id,
                               @RequestParam int inventory_number,
                               @RequestParam int graduation_year,
                               @RequestParam String producer_of_equipment,
                               @RequestParam double working_hours,
                               @RequestParam int status,
                               @RequestParam String description) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = formatter.parse(String.valueOf(graduation_year));
        Equipment equipment = new Equipment();
        equipment.setEquipmentName(equipment_name);
        equipment.setTypeOfEquipment(typeOfEquipmentService.getTypeById(type_of_equipment_id));
        equipment.setSubdivision(subdivisionService.getSubdivisionById(subdivision_id));
        equipment.setInventoryNumber(inventory_number);
        equipment.setGraduationYear(date);
        equipment.setProducerOfEquipment(producer_of_equipment);
        equipment.setWorkingHours(working_hours);
        equipment.setStatus(statusOfEqService.getStatusById(status));
        equipment.setDescription(description);
        equipmentService.addEquipment(equipment);
        return "manual";
    }

    @ApiOperation(value = "getEquipments", notes = "Get all technological cards")
    @RequestMapping(value = "/spare/all", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody List<Spares> getSpare(Model model) {
        return spareService.getAllSpares();
    }

    @ApiOperation(value = "getEquipments", notes = "Get all technological cards")
    @RequestMapping(value = "/spare", method = RequestMethod.GET,
            produces = "application/json")
    public String getSpareView(Model model) {
        model.addAttribute("subdivisions", subdivisionService.getAllSubdivisions());
        model.addAttribute("current_user", userService.getUserBySso(getPrincipal()).getLast_name() + " " +
                userService.getUserBySso(getPrincipal()).getFirst_name());
        Status status1 = statusService.getStatusById(1);
        Status status2 = statusService.getStatusById(2);
        model.addAttribute("active_req", repairSheetService.getRepairSheetByStatus(status1).size());
        model.addAttribute("confirm_req", repairSheetService.getRepairSheetByStatus(status2).size());
        model.addAttribute("spareForm", new Spares());
        return "spares";
    }

    @ApiOperation(value = "addEquipment", notes = "Get all technological cards")
    @RequestMapping(value = "/spare", method = RequestMethod.POST,
            produces = "application/json")
    public String addSpare(@Valid Spares spares, BindingResult bindingResult) throws ParseException {

        if(bindingResult.hasErrors()){
            return "redirect:/spare";
        }
        spareService.saveSpare(spares);
        return "redirect:/spare";
    }

    @ApiOperation(value = "getEquipments", notes = "Get all technological cards")
    @RequestMapping(value = "/subdivision/all", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody List<Subdivisions> getSubdivision(Model model) {
        return subdivisionService.getAllSubdivisions();
    }

    @ApiOperation(value = "getEquipments", notes = "Get all technological cards")
    @RequestMapping(value = "/subdivision", method = RequestMethod.GET,
            produces = "application/json")
    public String getSubdivisionView(Model model) {
        model.addAttribute("subdivisions", subdivisionService.getAllSubdivisions());
        model.addAttribute("current_user", userService.getUserBySso(getPrincipal()).getLast_name() + " " +
                userService.getUserBySso(getPrincipal()).getFirst_name());
        Status status1 = statusService.getStatusById(1);
        Status status2 = statusService.getStatusById(2);
        model.addAttribute("active_req", repairSheetService.getRepairSheetByStatus(status1).size());
        model.addAttribute("confirm_req", repairSheetService.getRepairSheetByStatus(status2).size());
        return "subdivisions";
    }

    @ApiOperation(value = "addEquipment", notes = "Get all technological cards")
    @RequestMapping(value = "/subdivision", method = RequestMethod.POST,
            produces = "application/json")
    public String addSubdivision(@RequestParam String subdivision_name,
                                 @RequestParam String abbreviation,
                                 @RequestParam String responsible,
                                 @RequestParam String description) throws ParseException {
        Subdivisions subdivisions = new Subdivisions();
        subdivisions.setSubdivision_name(subdivision_name);
        subdivisions.setAbbreviation(abbreviation);
        subdivisions.setResponsible(responsible);
        subdivisions.setDescription(description);
        subdivisionService.addSubdivision(subdivisions);
        return "manual";
    }

    @RequestMapping(value = "/type_of_equipment", method = RequestMethod.GET)
    public String listTypes(Model model) {
        model.addAttribute("type_of_equipment", new TypeOfEquipment());
        model.addAttribute("listTypes", typeOfEquipmentService.getAllTypes());
        return "type_of_equipment";
    }

    @RequestMapping(value= "/type_of_equipment/add", method = RequestMethod.POST)
    public String addType(@ModelAttribute("type_of_equipment") TypeOfEquipment typeOfEquipment, Model model){
        if(typeOfEquipment.getType_of_equipment_id() == 0){
            //new person, add it
            typeOfEquipmentService.addType(typeOfEquipment);
        }else{
            //existing person, call update
            typeOfEquipmentService.updateType(typeOfEquipment);
        }
        model.addAttribute("type_of_equipment", new TypeOfEquipment());
        return "type_of_equipment";

    }
}
