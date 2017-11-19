package com.diplom.sptor.web;

import com.diplom.sptor.domain.*;
import com.diplom.sptor.planning.PlanningUtils;
import com.diplom.sptor.service.*;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by user on 02.02.2016.
 */
@Controller
public class EquipmentController {

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    UserService userService;

    @Autowired
    WorkingHoursService workingHoursService;

    @Autowired
    DownTimeService downTimeService;

    @Autowired
    SubdivisionService subdivisionService;

    @Autowired
    TypeOfMaintenanceService typeOfMaintenanceService;

    @Autowired
    StatusService statusService;

    @Autowired
    StatusOfEqService statusOfEqService;

    @Autowired
    ParametersService parametersService;

    @Autowired
    DocumentService documentService;

    @Autowired
    RepairSheetService repairSheetService;

    @Autowired
    TypeOfEquipmentService typeOfEquipmentService;

    @Autowired
    PlanningUtils planningUtils;
    /**
     * GET list of all equipments.
     * @return list
     */

    Equipment cur_equipment=null;
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

        int subdivisionsCount = subdivisionService.getAllSubdivisions().size();
        model.addAttribute("equipment", new Equipment());
        model.addAttribute("subdivisionsCount", subdivisionsCount);
        model.addAttribute("subdivisions", subdivisionService.getAllSubdivisions());
        model.addAttribute("workingHours", new WorkingHours());
        model.addAttribute("allRepair", repairSheetService.getAllRepairSheets());
        Status status1 = statusService.getStatusById(1);
        Status status2 = statusService.getStatusById(2);
        model.addAttribute("active_req", repairSheetService.getRepairSheetByStatus(status1).size());
        model.addAttribute("confirm_req", repairSheetService.getRepairSheetByStatus(status2).size());
        model.addAttribute("techCard", new TechnologicalCard());
        model.addAttribute("listTypeOfMaintenance", typeOfMaintenanceService.getAllTypes());
        model.addAttribute("allEquipment", equipmentService.getAllEquipment().size());
        model.addAttribute("workEquipment", equipmentService.getEquipmentsByStatus(statusOfEqService.getStatusById(1)).size());
        model.addAttribute("TOEquipment", equipmentService.getEquipmentsByStatus(statusOfEqService.getStatusById(2)).size());
        model.addAttribute("repairEquipment", equipmentService.getEquipmentsByStatus(statusOfEqService.getStatusById(3)).size());
        model.addAttribute("ConservEquipment", equipmentService.getEquipmentsByStatus(statusOfEqService.getStatusById(4)).size());
        model.addAttribute("document", new Document());
        return "equipment";
    }

   @RequestMapping(value = "/equipments/type_of_equipment/{subdivisionId}", method = RequestMethod.GET,
           produces = "application/json")
   public @ResponseBody
   Set<String> getTypeBySubdivision(@ApiParam(value = "ID of equipment that" +
           "needs to be fetched", required = true,
           defaultValue = "1")@PathVariable("subdivisionId")int subdivisionId) {
       Set<Equipment> equipments = subdivisionService.getSubdivisionById(subdivisionId).getEquipments_sub();
       Set<String> typesOfEquipments = new HashSet<String>();
       Iterator<Equipment> eqIterator = equipments.iterator();
       while(eqIterator.hasNext()){
           typesOfEquipments.add(eqIterator.next().getTypeOfEquipment().getType_of_equipment_name());
       }
       return typesOfEquipments;
   }

   @RequestMapping(value = "/working_hours/{equipmentId}", method = RequestMethod.GET,
           produces = "application/json")
   public @ResponseBody
   List<WorkingHours> getWorkingHoursByEquipment(@ApiParam(value = "ID of equipment that" +
           "needs to be fetched", required = true,
           defaultValue = "1")@PathVariable("equipmentId")int equipmentId, Model model) {
       Equipment equipment = equipmentService.getEquipmentById(equipmentId);
       List<WorkingHours> workingHourses = workingHoursService.getWorkingHoursByEquipment(equipment);
       return workingHourses;
   }

    @RequestMapping(value = "/working_hours/year/{equipmentId}", method = RequestMethod.GET,
            produces = "application/json")
    public @ResponseBody
    Double getWorkingHoursByEquipmentInYear(@ApiParam(value = "ID of equipment that" +
            "needs to be fetched", required = true,
            defaultValue = "1")@PathVariable("equipmentId")int equipmentId, Model model) {
        Equipment equipment = equipmentService.getEquipmentById(equipmentId);
        return planningUtils.getWorkingHoursByEquipmentInYear(equipment);
    }

   @RequestMapping(value = "/working_hours/add",  method = RequestMethod.POST)
   public String addWorkingHours(
           @RequestParam double value,
           @RequestParam int equipment_id
   ) throws ParseException {
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       Date date = new Date();
       String sso = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
       User responsible = userService.getUserBySso(sso);
       Equipment equipment = equipmentService.getEquipmentById(equipment_id);
       double new_value = 0.0;
       int list_size = workingHoursService.getWorkingHoursByEquipment(equipment).size();
       if (list_size == 0) {
           new_value = value;
       } else {
           double old_value = workingHoursService.getWorkingHoursByEquipment(equipment).get(list_size - 1).getValue();
           new_value = old_value + value;
       }
       workingHoursService.addWorkingHours(new WorkingHours(equipment, date, new_value, responsible));
       return "redirect:/equipments";
   }

   @RequestMapping(value = "/downtime/{equipmentId}", method = RequestMethod.GET,
           produces = "application/json")
   public @ResponseBody
   List<DownTime> getDownTimeByEquipment(@ApiParam(value = "ID of equipment that" +
           "needs to be fetched", required = true,
           defaultValue = "1")@PathVariable("equipmentId")int equipmentId, Model model){
           Equipment equipment = equipmentService.getEquipmentById(equipmentId);
           List<DownTime> downTimes = downTimeService.getDownTimeByEquipment(equipment);
           return downTimes;
       }
   @RequestMapping(value = "/downtime/add",  method = RequestMethod.POST)
   public String addDownTime(
           @RequestParam double value,
           @RequestParam int equipment_id
   ) throws ParseException {
       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       Date date = new Date();
       String sso = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
       User responsible = userService.getUserBySso(sso);
       Equipment equipment = equipmentService.getEquipmentById(equipment_id);
       double new_value = 0.0;
       int list_size = downTimeService.getDownTimeByEquipment(equipment).size();
       if (list_size == 0) {
           new_value = value;
       } else {
           double old_value = downTimeService.getDownTimeByEquipment(equipment).get(list_size - 1).getValue();
           new_value = old_value + value;
       }
       downTimeService.addDownTime(new DownTime(equipment, date, new_value, responsible));
       return "redirect:/equipments";
   }

   @ApiOperation(value = "getEquipmentByd", notes = "Get equipment by Id")
   @RequestMapping(value = "/equipments/{equipmentId}", method = RequestMethod.GET,
           produces = "application/json")
   public @ResponseBody
   Equipment getEquipmentById(@ApiParam(value = "ID of equipment that" +
           "needs to be fetched", required = true,
           defaultValue = "1")@PathVariable("equipmentId")int equipmentId, Model model) {
       model.addAttribute("current_equipment", equipmentService.getEquipmentById(equipmentId).getEquipmentId());
       return equipmentService.getEquipmentById(equipmentId);
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
       return subdivisionService.getSubdivisionById(subdivisionId).getEquipments_sub();
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
       Set<Equipment> equipments = subdivisionService.getSubdivisionById(subdivisionId).getEquipments_sub();
       for (Iterator<Equipment> equipment_role = equipments.iterator(); equipment_role.hasNext(); ) {
           Equipment equipment = equipment_role.next();
           if (equipment.getStatus().getStatus_id() != 1) {
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
           defaultValue = "1")@PathVariable(value = "typeId")int typeId){
           TypeOfEquipment typeOfEquipment = typeOfEquipmentService.getTypeById(typeId);
           return parametersService.getParametersByType(typeOfEquipment);
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
           return equipmentService.addEquipment(equipment);
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
       if (equipmentService.getEquipmentById(equipmentId) != null) {
           Equipment updatedEquipment = equipment;
           updatedEquipment.setEquipmentId(equipmentId);
           equipmentService.updateEquipment(updatedEquipment);
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
       equipmentService.deleteEquipment(id);
       return "Deleted successfully";
   }

   @RequestMapping("/documents/{equipmentId}")
   public @ResponseBody List<Document> index(Map<String, Object> map, @PathVariable(value = "equipmentId") int equipmentId, Model model) {
       map.put("document", new Document());
       Equipment equipment = equipmentService.getEquipmentById(equipmentId);
       map.put("cur_eq", equipment.getEquipmentId());
       cur_equipment = equipment;
       map.put("documentList", documentService.getDocumentsByEquipment(equipment));
       return this.documentService.getDocumentsByEquipment(equipment);
   }
   @RequestMapping(value = "documents/save", method = RequestMethod.POST)
   public String save(
           @ModelAttribute("document") Document document,
           @RequestParam("file") MultipartFile file) throws SerialException, SQLException{
       System.out.println("Name:" + document.getDocument_name());
       System.out.println("Desc:" + document.getDescription());
       System.out.println("File:" + file.getName());
       System.out.println("ContentType:" + file.getContentType());
       try {
           Blob blob = new javax.sql.rowset.serial.SerialBlob(IOUtils.toByteArray(file.getInputStream()));
           System.out.println("Blob:" + blob);
           document.setPath(file.getOriginalFilename());
           document.setContent(IOUtils.toByteArray(file.getInputStream()));
           document.setContent_type(file.getContentType());
           document.setDate_of_adding(new Date());
           document.setEquipment(cur_equipment);
       } catch (IOException e) {
           e.printStackTrace();
       }
       try {
           documentService.addDocument(document);
       } catch(Exception e) {
           e.printStackTrace();
       }
       return "redirect:/equipments";
   }
   @RequestMapping("documents/download/{documentId}")
   public String download(@PathVariable("documentId")
                          Integer documentId, HttpServletResponse response) {
       Document doc = documentService.getDocument(documentId);
       try {
           response.setHeader("Content-Disposition", "inline;filename=\"" +doc.getPath()+ "\"");
           OutputStream out = response.getOutputStream();
           response.setContentType(doc.getContent_type());
           IOUtils.copy(new ByteArrayInputStream(doc.getContent()), out);
           out.flush();
           out.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }
   @RequestMapping("documents/remove/{documentId}")
   public String remove(@PathVariable("documentId")
                        Integer documentId) {
       documentService.deleteDocument(documentId);
       return "documents";
   }
}


