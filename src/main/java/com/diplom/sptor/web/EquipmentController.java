package com.diplom.sptor.web;

import com.diplom.sptor.domain.*;
import com.diplom.sptor.service.*;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
public class EquipmentController {

    private EquipmentService EquipmentService;
    private SubdivisionService SubdivisionService;
    private TypeOfEquipmentService TypeOfEquipmentService;
    private ParametersService ParametersService;
    private UserService UserService;
    private WorkingHoursService WorkingHoursService;
    private RepairSheetService RepairSheetService;
    private StatusService StatusService;
    private TypeOfMaintenanceService TypeOfMaintenanceService;
    private DownTimeService DownTimeService;

    @Autowired(required=true)
    public void setTypeOfEquipmentService(TypeOfEquipmentService TypeOfEquipmentService){
        this.TypeOfEquipmentService = TypeOfEquipmentService;
    }

    @Autowired(required=true)
    public void setEquipmentService(EquipmentService EquipmentService){
        this.EquipmentService = EquipmentService;
    }

    @Autowired(required=true)
    public void setSubdivisionService(SubdivisionService SubdivisionService){
        this.SubdivisionService = SubdivisionService;
    }

    @Autowired(required=true)
    public void setParametersService(ParametersService ParametersService){
        this.ParametersService = ParametersService;
    }

    @Autowired(required=true)
    public void setUserService(UserService UserService){
        this.UserService = UserService;
    }

    @Autowired(required=true)
    public void setWorkingHoursService(WorkingHoursService WorkingHoursService){
        this.WorkingHoursService = WorkingHoursService;
    }

    @Autowired(required=true)
    public void setRepairSheetService(RepairSheetService RepairSheetService){
        this.RepairSheetService = RepairSheetService;
    }

    @Autowired(required=true)
    public void setStatusService(StatusService StatusService) {
        this.StatusService = StatusService;
    }

    @Autowired(required=true)
    public void setTypeOfMaintenanceService(TypeOfMaintenanceService TypeOfMaintenanceService) {
        this.TypeOfMaintenanceService = TypeOfMaintenanceService;
    }

    @Autowired(required=true)
    public void setDownTimeService(DownTimeService DownTimeService) {
        this.DownTimeService = DownTimeService;
    }

    /**
     * GET list of all equipments.
     * @return list
     */

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

    @ApiOperation(value = "/equipments", notes = "Get all equipments")
    @RequestMapping(value ="/equipments", method = RequestMethod.GET,
            produces = "application/json")
    public String getEquipments(Model model) {

        String username = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        int subdivisionsCount = SubdivisionService.listSubdivisions().size();
        model.addAttribute("equipment", new Equipment());
        model.addAttribute("subdivisionsCount", subdivisionsCount);
        model.addAttribute("subdivisions", SubdivisionService.listSubdivisions());
        model.addAttribute("workingHours", new WorkingHours());
        model.addAttribute("allRepair", RepairSheetService.listRepairSheets());
        model.addAttribute("user", UserService.findBySso(username).getLast_name() + " " +
                UserService.findBySso(username).getFirst_name());
        Status status1 = StatusService.getStatusById(1);
        Status status2 = StatusService.getStatusById(2);
        model.addAttribute("active_req", this.RepairSheetService.getRepairSheetByStatus(status1).size());
        model.addAttribute("confirm_req", this.RepairSheetService.getRepairSheetByStatus(status2).size());
        model.addAttribute("techCard", new TechnologicalCard());
        model.addAttribute("listTypeOfMaintenance", this.TypeOfMaintenanceService.listType());
        model.addAttribute("allEquipment", this.EquipmentService.listEquipments().size());
        model.addAttribute("workEquipment", this.EquipmentService.getEquipmentsByStatus(1).size());
        model.addAttribute("TOEquipment", this.EquipmentService.getEquipmentsByStatus(2).size());
        model.addAttribute("repairEquipment", this.EquipmentService.getEquipmentsByStatus(3).size());
        model.addAttribute("ConservEquipment", this.EquipmentService.getEquipmentsByStatus(4).size());
        return "equipment";
        //return EquipmentService.listEquipments();
    }

    @RequestMapping(value = "/equipments/type_of_equipment/{subdivisionId}", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    Set<String> getTypeBySubdivision(@ApiParam(value = "ID of equipment that" +
            "needs to be fetched", required = true,
            defaultValue = "1")@PathVariable("subdivisionId")int subdivisionId) {
        Set<Equipment> equipments = SubdivisionService.getSubdivisionById(subdivisionId).getEquipments_sub();
        Set<String> typesOfEquipments = new HashSet<String>();
        Iterator<Equipment> eqIterator = equipments.iterator();
        while(eqIterator.hasNext()){
            typesOfEquipments.add(eqIterator.next().getType_of_equipment().getType_of_equipment_name());
        }
        return typesOfEquipments;
    }

    @RequestMapping(value = "/working_hours/{equipmentId}", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    List<WorkingHours> getWorkingHoursByEquipment(@ApiParam(value = "ID of equipment that" +
            "needs to be fetched", required = true,
            defaultValue = "1")@PathVariable("equipmentId")int equipmentId, Model model) {
        Equipment equipment = this.EquipmentService.getEquipmentById(equipmentId);
        List<WorkingHours> workingHourses = WorkingHoursService.getWorkingHoursByEquipmentId(equipment);
        return workingHourses;
    }

    @RequestMapping(value = "/working_hours/add",  method = RequestMethod.POST)
    public String addWorkingHours(
            @RequestParam double value,
            @RequestParam int equipment_id
    ) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String sso = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User responsible = UserService.findBySso(sso);
        Equipment equipment = this.EquipmentService.getEquipmentById(equipment_id);
        double new_value=0.0;
        int list_size = this.WorkingHoursService.getWorkingHoursByEquipmentId(equipment).size();
        if(list_size==0){
            new_value = value;
        }
        else {
            double old_value = this.WorkingHoursService.getWorkingHoursByEquipmentId(equipment).get(list_size - 1).getValue();
            new_value = old_value + value;
        }
        this.WorkingHoursService.addWorkingHours(new WorkingHours(equipment, date, new_value, responsible));
        return "redirect:/equipments";

    }

    @RequestMapping(value = "/downtime/{equipmentId}", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    List<DownTime> getDownTimeByEquipment(@ApiParam(value = "ID of equipment that" +
            "needs to be fetched", required = true,
            defaultValue = "1")@PathVariable("equipmentId")int equipmentId, Model model) {
        Equipment equipment = this.EquipmentService.getEquipmentById(equipmentId);
        List<DownTime> downTimes = DownTimeService.getDownTimeByEquipmentId(equipment);
        return downTimes;
    }

    @RequestMapping(value = "/downtime/add",  method = RequestMethod.POST)
    public String addDownTime(
            @RequestParam double value,
            @RequestParam int equipment_id
    ) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String sso = ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User responsible = UserService.findBySso(sso);
        Equipment equipment = this.EquipmentService.getEquipmentById(equipment_id);
        double new_value=0.0;
        int list_size = this.DownTimeService.getDownTimeByEquipmentId(equipment).size();
        if(list_size==0){
            new_value = value;
        }
        else {
            double old_value = this.DownTimeService.getDownTimeByEquipmentId(equipment).get(list_size - 1).getValue();
            new_value = old_value + value;
        }
        this.DownTimeService.addDownTime(new DownTime(equipment, date, new_value, responsible));
        return "redirect:/equipments";

    }

    @ApiOperation(value = "getEquipmentByd", notes = "Get equipment by Id")
    @RequestMapping(value = "/equipments/{equipmentId}", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    Equipment getEquipmentById(@ApiParam(value = "ID of equipment that" +
            "needs to be fetched", required = true,
            defaultValue = "1")@PathVariable("equipmentId")int equipmentId) {
        return EquipmentService.getEquipmentById(equipmentId);
    }

    /**
     * GET equipment by Subdivision_Id.pu
     * @return equipment
     */
   @ApiOperation(value = "getEquipmentBySubId", notes = "Get equipment by Subdivision_Id")
   @RequestMapping(value = "/equipments/subdivisions/{subdivisionId}", method = RequestMethod.GET,
           produces = "application/json")
   public @ResponseBody
   Set<Equipment> getEquipmentBySubId(@ApiParam(value = "ID of equipment that" +
           "needs to be fetched", required = true,
           defaultValue = "1")@PathVariable(value = "subdivisionId")int subdivisionId) {
       return SubdivisionService.getSubdivisionById(subdivisionId).getEquipments_sub();
   }

    /**
     * GET equipment by Subdivision_Id.pu
     * @return equipment
     */
    @ApiOperation(value = "getEquipmentBySubId", notes = "Get equipment by Subdivision_Id")
    @RequestMapping(value = "/equipments/work/subdivisions/{subdivisionId}", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    Set<Equipment> getEquipmentBySubIdInWork(@ApiParam(value = "ID of equipment that" +
            "needs to be fetched", required = true,
            defaultValue = "1")@PathVariable(value = "subdivisionId")int subdivisionId) {
        Set<Equipment> equipments = SubdivisionService.getSubdivisionById(subdivisionId).getEquipments_sub();
        for(Iterator<Equipment> equipment_role = equipments.iterator(); equipment_role.hasNext();){
            Equipment equipment = equipment_role.next();
            if(equipment.getStatus().getStatus_id()!=1){
                equipment_role.remove();
            }
        }
        return equipments;
    }

    @ApiOperation(value = "getEquipmentBySubId", notes = "Get equipment by Subdivision_Id")
    @RequestMapping(value = "/equipments/parameters/{typeId}", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    List<Parameters> getParametersForEquipment(@ApiParam(value = "ID of equipment that" +
            "needs to be fetched", required = true,
            defaultValue = "1")@PathVariable(value = "typeId")int typeId) {
        TypeOfEquipment typeOfEquipment = TypeOfEquipmentService.getTypeById(typeId);
        return ParametersService.getParametersByType(typeOfEquipment);
    }

    /**
     * POST a new equipment.
     * @return .
     */
    @ApiOperation(value = "addEquipment", notes = "Add new equipment")
    @RequestMapping(method = RequestMethod.POST,
            produces = "application/json")
    public Equipment addEquipment(@ApiParam(
            defaultValue = "{\n" +
                    "  \"equipment_name\":\"Name\",\n" +
                    "  \"type_of_equipment_id\":  1,\n" +
                    "  \"subdivision_id\":  1,\n" +
                    "  \"inventory_number\":  123456,\n" +
                    "  \"graduation_year\":  \"2003-03-11\",\n" +
                    "  \"producer_of_equipment\":\"Ivanov\",\n" +
                    "  \"description\":\"Description\"\n" +
                    "}")@RequestBody Equipment equipment){
        return EquipmentService.addEquipment(equipment);
    }

    /**
     * POST update equipment by Id.
     * @return equipment
     */
    @ApiOperation(value = "updateEquipment", notes = "Update equipment by ID")
    @RequestMapping(value = "/{equipmentId}", method = RequestMethod.PUT,
            produces = "application/json")
    public @ResponseBody String updateEquipment(@ApiParam(
            defaultValue = "{\n" +
                    "  \"equipment_name\":\"Name\",\n" +
                    "  \"type_of_equipment_id\":  1,\n" +
                    "  \"subdivision_id\":  1,\n" +
                    "  \"inventory_number\":  123456,\n" +
                    "  \"graduation_year\":  \"2003-03-11\",\n" +
                    "  \"producer_of_equipment\":\"Ivanov\",\n" +
                    "  \"description\":\"Description\"\n" +
                    "}")@RequestBody Equipment equipment, @PathVariable(value = "equipmentId") int equipmentId) {
        if(EquipmentService.getEquipmentById(equipmentId)!=null){
            Equipment updatedEquipment = equipment;
            updatedEquipment.setEquipment_id(equipmentId);
            EquipmentService.updateEquipment(updatedEquipment);
        }
        return "Update successfully";
    }

    /**
     * DELETE delete equipment by Id.
     * @return equipment
     */
    @ApiOperation(value = "deleteEquipment", notes = "Delete equipment by ID")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = "application/json")
    public @ResponseBody String deleteEquipment(@ApiParam(value = "ID of equipment that" +
            "needs to be fetched", required = true,
            defaultValue = "1")@PathVariable(value = "id")int id) {
        EquipmentService.deleteEquipment(id);
        return "Deleted successfully";
    }


}
