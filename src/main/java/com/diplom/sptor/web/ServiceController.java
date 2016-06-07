package com.diplom.sptor.web;

import com.diplom.sptor.domain.*;
import com.diplom.sptor.model.UserFormModel;
import com.diplom.sptor.service.*;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.DriverManager;
import java.util.*;

import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@Api( value = "sptor", description = "API os SPTOR system")
public class ServiceController {
	private EquipmentService EquipmentService;
	private SubdivisionService SubdivisionService;
	private TypeOfEquipmentService TypeOfEquipmentService;
	private SpareService SpareService;
	private TechnologicalCardService TechnologicalCardService;
	private RepairSheetService RepairSheetService;
	private UserService UserService;
	private ComponentService ComponentService;
	private TypeOfMaintenanceService TypeOfMaintenanceService;
	private StatusService StatusService;
	private DownTimeService DownTimeService;
	private StatusOfEqService StatusOfEqService;

	@Autowired(required=true)
	public void setEquipmentService(EquipmentService EquipmentService){
		this.EquipmentService = EquipmentService;
	}

	@Autowired(required=true)
	public void setSubdivisionService(SubdivisionService SubdivisionService){
		this.SubdivisionService = SubdivisionService;
	}

	@Autowired(required=true)
	public void setTypeOfEquipmentService(TypeOfEquipmentService TypeOfEquipmentService){
		this.TypeOfEquipmentService = TypeOfEquipmentService;
	}

	@Autowired(required=true)
	public void setSpareService(SpareService SpareService){
		this.SpareService = SpareService;
	}

	@Autowired(required=true)
	public void setTechnologicalCardService(TechnologicalCardService TechnologicalCardService){
		this.TechnologicalCardService = TechnologicalCardService;
	}

	@Autowired(required=true)
	public void setRepairSheetService(RepairSheetService RepairSheetService){
		this.RepairSheetService = RepairSheetService;
	}

	@Autowired(required=true)
	public void setUserService(UserService UserService){
		this.UserService = UserService;
	}

	@Autowired(required=true)
	public void setComponentService(ComponentService ComponentService){
		this.ComponentService = ComponentService;
	}

	@Autowired(required=true)
	public void setTypeOfMaintenanceService(TypeOfMaintenanceService TypeOfMaintenanceService) {
		this.TypeOfMaintenanceService = TypeOfMaintenanceService;
	}

	@Autowired(required=true)
	public void setStatusService(StatusService StatusService) {
		this.StatusService = StatusService;
	}

	@Autowired(required=true)
	public void setDownTimeService(DownTimeService DownTimeService) {
		this.DownTimeService = DownTimeService;
	}

	@Autowired(required=true)
	public void setStatusOfEqService(StatusOfEqService StatusOfEqService) {
		this.StatusOfEqService = StatusOfEqService;
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/sptor", method = RequestMethod.GET,
			produces = "application/json")
	public String getStarted(Model model) {
		model.addAttribute("user", UserService.findBySso(getPrincipal()).getLast_name() + " " +
				UserService.findBySso(getPrincipal()).getFirst_name());
		Status status1 = StatusService.getStatusById(1);
		Status status2 = StatusService.getStatusById(2);
		model.addAttribute("active_req", this.RepairSheetService.getRepairSheetByStatus(status1).size());
		model.addAttribute("confirm_req", this.RepairSheetService.getRepairSheetByStatus(status2).size());
		model.addAttribute("subdivisions", this.SubdivisionService.listSubdivisions());
		return "home";
	}

	@RequestMapping(value = "/about", method = RequestMethod.GET,
			produces = "application/json")
	public String getAbout(Model model) {
		model.addAttribute("user", UserService.findBySso(getPrincipal()).getLast_name() + " " +
				UserService.findBySso(getPrincipal()).getFirst_name());
		Status status1 = StatusService.getStatusById(1);
		Status status2 = StatusService.getStatusById(2);
		model.addAttribute("active_req", this.RepairSheetService.getRepairSheetByStatus(status1).size());
		model.addAttribute("confirm_req", this.RepairSheetService.getRepairSheetByStatus(status2).size());
		model.addAttribute("subdivisions", this.SubdivisionService.listSubdivisions());
		return "orders";
	}

	@RequestMapping(value = {"","/", "/login"}, method = RequestMethod.GET,
			produces = "application/json")
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}


	@RequestMapping(value = "/auth/denied", method = RequestMethod.GET,
			produces = "application/json")
	public String access_denied(Model model) {
		return "denied";
	}

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


	@RequestMapping(value = "/components/{equipmentId}", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody List<Components> getComponentsByType(@PathVariable(value = "equipmentId")int equipmentId, Model model) {
		Equipment equipment = this.EquipmentService.getEquipmentById(equipmentId);
		TypeOfEquipment typeOfEquipment = equipment.getType_of_equipment();
		//TypeOfEquipment typeOfEquipment=  TypeOfEquipmentService.getTypeById(typeId);
		return ComponentService.getComponentsByType(typeOfEquipment);
	}

	@RequestMapping(value = "/components/{componentId}/equipment/{equipmentId}", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody List<RepairSheet> getTypeOfMaintenanceByComponents(@PathVariable(value = "componentId")int componentId, @PathVariable(value = "equipmentId")int equipmentId,  Model model) {
		Equipment equipment = this.EquipmentService.getEquipmentById(equipmentId);
		Components components = this.ComponentService.getComponentById(componentId);
		return this.RepairSheetService.getTypeOfMaintenanceOfRepairByEquipmentAndComponents(equipment, components);
	}

	@ApiOperation(value = "getSubdivisions", notes = "Get all subdivisions")
	@RequestMapping(value = "/subdivisions", method = RequestMethod.GET,
			produces = "application/json")
	public String getSubdivisions() {
		return "subdivision";
		//return SubdivisionService.listSubdivisions();
	}

	/**
	 * GET equipment by Id.
	 * @return equipment
	 */
	@ApiOperation(value = "getSubdivision", notes = "Get subdivisiob by ID")
	@RequestMapping(value = "/subdivisions/{id}", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody Subdivisions getSubdivision(@ApiParam(value = "ID of subdivision that" +
			"needs to be fetched", required = true,
			defaultValue = "1")@PathVariable(value = "id")int id) {
		return SubdivisionService.getSubdivisionById(id);
	}

	@ApiOperation(value = "addSubdivision", notes = "Add new subdivision")
	@RequestMapping(value = "/subdivisions", method = RequestMethod.POST,
			produces = "application/json")
	public String addSubdivision(@ApiParam(
			defaultValue = "{\n" +
					"  \"subdivision_name\":\"Name\",\n" +
					"  \"abbreviation\":  \"Abbreviation\",\n" +
					"  \"responsible\":\"Ivanov\",\n" +
					"  \"description\":\"Description\"\n" +
					"}")@RequestBody Subdivisions subdivision){

		this.SubdivisionService.addSubdivision(subdivision);
		return "Added successfully";

	}

	@ApiOperation(value = "getEquipments", notes = "Get all equipments")
	@RequestMapping(value = "/repair", method = RequestMethod.GET,
			produces = "application/json")
	public String RepairStart(Model model) {
		model.addAttribute("repairSheet", new RepairSheet());
		model.addAttribute("listRepair", this.RepairSheetService.listRepairSheets());
		model.addAttribute("status_one", this.RepairSheetService.getRepairSheetByStatus(this.StatusService.getStatusById(1)).size());
		model.addAttribute("status_two", this.RepairSheetService.getRepairSheetByStatus(this.StatusService.getStatusById(2)).size() + this.RepairSheetService.getRepairSheetByStatus(this.StatusService.getStatusById(2)).size());
		model.addAttribute("status_three", this.RepairSheetService.getRepairSheetByStatus(this.StatusService.getStatusById(5)).size());
		model.addAttribute("status_four", this.RepairSheetService.getRepairSheetByStatus(this.StatusService.getStatusById(4)).size());
		model.addAttribute("status_all", this.RepairSheetService.listRepairSheets().size());
		model.addAttribute("subdivisions", this.SubdivisionService.listSubdivisions());
		model.addAttribute("components", this.ComponentService.listComponents());
		model.addAttribute("user", UserService.findBySso(getPrincipal()).getLast_name() + " " +
				UserService.findBySso(getPrincipal()).getFirst_name());
		model.addAttribute("listEquipments", this.EquipmentService.listEquipments());
		model.addAttribute("listTypeOfMaintenance", this.TypeOfMaintenanceService.listType());
		Status status1 = StatusService.getStatusById(1);
		Status status2 = StatusService.getStatusById(2);
		model.addAttribute("active_req", this.RepairSheetService.getRepairSheetByStatus(status1).size());
		model.addAttribute("confirm_req", this.RepairSheetService.getRepairSheetByStatus(status2).size());
		model.addAttribute("listStatus", this.StatusService.listStatus());
		return "repair";
	}



	@RequestMapping(value = "/repair/add", method = RequestMethod.POST)
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
		Status status = this.StatusService.getStatusById(1);
		User resp_for_delivery = this.UserService.findById(1);
		User resp_for_reception = this.UserService.findById(1);
		Equipment equipment = this.EquipmentService.getEquipmentById(equipment_id);
		Components components1 = this.ComponentService.getComponentById(components);
		Subdivisions subdivisions = this.SubdivisionService.getSubdivisionById(subdivision_id);
		TypeOfMaintenance typeOfMaintenance = this.TypeOfMaintenanceService.getTypeById(type_of_maintenance_id);
		int sheet_number = equipment_id + 33000;
		int warranty_period = type_of_maintenance_id;
		this.RepairSheetService.addRepairSheet(new RepairSheet(typeOfMaintenance, equipment,
				components1, subdivisions, start, end, sheet_number, warranty_period,
				resp_for_delivery, resp_for_reception, description, status));

		deleteComponentInStock(components1);
		changeStatusOfEquipment(equipment, status, typeOfMaintenance);

		return "redirect:/repair";

	}

	public void deleteComponentInStock(Components components){
		Spares spares =  this.SpareService.getSpareById(components.getSpare().getSpare_id());
		spares.setAmount_in_stock(spares.getAmount_in_stock()-1);
		this.SpareService.updateSpare(spares);
	}

	public void changeStatusOfEquipment(Equipment equipment, Status status, TypeOfMaintenance typeOfMaintenance){
		int type = typeOfMaintenance.getType_of_maintenance_id();
		switch (status.getStatus_id()){
			case 1:
				if(type==1||type==2||type==6||type==7||type==8){
					equipment.setStatus(this.StatusOfEqService.getStatusById(2));
					this.EquipmentService.updateEquipment(equipment);
				}
				else if(type==3||type==4||type==5){
					equipment.setStatus(this.StatusOfEqService.getStatusById(3));
					this.EquipmentService.updateEquipment(equipment);
				}
				else{
					equipment.setStatus(this.StatusOfEqService.getStatusById(4));
					this.EquipmentService.updateEquipment(equipment);
				}
				break;
			case 4:
				equipment.setStatus(this.StatusOfEqService.getStatusById(1));
				this.EquipmentService.updateEquipment(equipment);
				break;
			case 5:
				equipment.setStatus(this.StatusOfEqService.getStatusById(1));
				this.EquipmentService.updateEquipment(equipment);
				break;
		}
	}

	@ApiOperation(value = "getRepairSheetById", notes = "Get all equipments")
	@RequestMapping(value = "/repair/{repairId}", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody RepairSheet getRepairById(@PathVariable("repairId") int repairId, Model model) {
		return this.RepairSheetService.getRepairSheetById(repairId);
	}

	@ApiOperation(value = "getRepairSheetById", notes = "Get all equipments")
	@RequestMapping(value = "/repair/equipment/{equipmentId}", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody List<RepairSheet> getRepairByEquipmentId(@PathVariable("equipmentId") int equipmentId, Model model) {
		List<RepairSheet> repairSheets = this.RepairSheetService.getRepairSheetByEquipment(this.EquipmentService.getEquipmentById(equipmentId));
		for(RepairSheet repairSheet : repairSheets){
			if(repairSheet.getType_of_maintenance().getType_of_maintenance_id()==3||repairSheet.getType_of_maintenance().getType_of_maintenance_id()==4
					||repairSheet.getType_of_maintenance().getType_of_maintenance_id()==5){
				repairSheets.remove(repairSheet);
			}
		}

		return this.RepairSheetService.getRepairSheetByEquipment(this.EquipmentService.getEquipmentById(equipmentId));
	}

	@ApiOperation(value = "getAllRepairSheet", notes = "Get all equipments")
	@RequestMapping(value = "/repair/all", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody List<RepairSheet> getAllRepair(Model model) {
		return this.RepairSheetService.listRepairSheets();
	}

	@ApiOperation(value = "getRepairSheetById", notes = "Get all equipments")
	@RequestMapping(value = "/repair/{repairId}", method = RequestMethod.POST,
			produces = "application/json")
	public void updateStatusRepair(@PathVariable("repairId") int repairId,
								   						@RequestParam String date,
														@RequestParam int status,
														@RequestParam String description, Model model) throws ParseException  {
		RepairSheet repairSheet= this.RepairSheetService.getRepairSheetById(repairId);
		Status statusNew = this.StatusService.getStatusById(status);
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
		this.RepairSheetService.updateRepairSheet(repairSheet);

		changeStatusOfEquipment(repairSheet.getEquipment(),statusNew, repairSheet.getType_of_maintenance());
	}

	@ApiOperation(value = "getRepairSheetById", notes = "Get all equipments")
	@RequestMapping(value = "/repair/confirm/{repairId}", method = RequestMethod.POST,
			produces = "application/json")
	public void confirmRepair(@PathVariable("repairId") int repairId,
								   @RequestParam int status,
								   @RequestParam String description, Model model) throws ParseException  {
		RepairSheet repairSheet= this.RepairSheetService.getRepairSheetById(repairId);
		Status statusNew = this.StatusService.getStatusById(status);
		repairSheet.setStatus(statusNew);
		if (description.length()!=0) {
			repairSheet.addDescription(description);
		}
		this.RepairSheetService.updateRepairSheet(repairSheet);
	}

	@RequestMapping(value = "/report/repair/act_in/{repairId}", method = RequestMethod.POST)
	public String generateReport(@PathVariable("repairId") int repairId,
								 @RequestParam String toir_type, Model model, HttpServletRequest request,HttpServletResponse response) throws ParseException {

		String reportFileName = "Act_in_repair_report";

		Connection conn = null;
		try {
			try {

				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5430/zaprib","Vadim","wadim888");

			if (conn != null)
			{
				System.out.println("Database Connected");
			}
			else
			{
				System.out.println(" connection Failed ");
			}

			System.out.println("toir_type " + toir_type);

			JasperReport jasperReport = getCompiledFile(reportFileName, request);


			List<RepairReport> reportItems = new ArrayList<RepairReport>();
			RepairSheet repairSheet = RepairSheetService.getRepairSheetById(repairId);
			RepairReport repairReport = new RepairReport();
			repairReport.setType_of_maintenance(repairSheet.getType_of_maintenance().getType_of_maintenance_name());
			repairReport.setSubdivision(repairSheet.getSubdivision().getSubdivision_name());
			repairReport.setSheet_number("" + repairSheet.getSheet_number());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
			repairReport.setStart_date(dateFormat.format(repairSheet.getStart_date()));
			repairReport.setEquipment(repairSheet.getEquipment().getEquipment_name());
			repairReport.setProducer(repairSheet.getEquipment().getProducer_of_equipment());
			repairReport.setType_of_equipment(repairSheet.getEquipment().getType_of_equipment().getType_of_equipment_name());
			String lastRepair="";
			if(this.RepairSheetService.getRepairSheetByEquipment(repairSheet.getEquipment()).size()>1){
				RepairSheet lastRepairSheet = this.RepairSheetService.getRepairSheetByEquipment(repairSheet.getEquipment()).get(1);
				lastRepair = lastRepairSheet.getType_of_maintenance().getType_of_maintenance_name() + ", "+
						dateFormat.format(lastRepairSheet.getEnd_date());
			}
			else{
				lastRepair = "не было";
			}
			repairReport.setLastRepair(lastRepair);
			repairReport.setResponsible(UserService.findBySso(getPrincipal()).getLast_name());
			reportItems.add(repairReport);
			JRDataSource datasource = new JRBeanCollectionDataSource(reportItems);

				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, datasource);
			try {
				String outputFile = "C:\\Users\\user\\Dropbox\\MyProjects\\SPTOR\\acts\\" + "Акт_ввода_№" + repairReport.getSheet_number() + ".docx";
				JRDocxExporter exporter = new JRDocxExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile);
				exporter.exportReport();
				System.out.println("successfully");
			}catch (Exception e){
				System.out.println("Export exception");
			}
				//generateReportHtml(jasperPrint, request, response); // For HTML report

			//}

			//else if  (rptFormat.equalsIgnoreCase("pdf") )  {
//
			//	generateReportPDF(response, hmParams, jasperReport, conn); // For PDF report
//
			//}

		} catch (Exception sqlExp) {

			System.out.println( "Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return null;

	}

	@RequestMapping(value = "/report/repair/act_out/{repairId}", method = RequestMethod.POST)
	 public String generateActOut(@PathVariable("repairId") int repairId,
								  @RequestParam String toir_type,Model model, HttpServletRequest request,HttpServletResponse response) throws ParseException {

		String reportFileName = "ppr_report";

		Connection conn = null;
		try {
			try {

				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Please include Classpath Where your MySQL Driver is located");
				e.printStackTrace();
			}

			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5430/zaprib","Vadim","wadim888");

			if (conn != null)
			{
				System.out.println("Database Connected");
			}
			else
			{
				System.out.println(" connection Failed ");
			}

			System.out.println("toir_type " + toir_type);

			JasperReport jasperReport = getCompiledFile(reportFileName, request);


			List<RepairReport> reportItems = new ArrayList<RepairReport>();
			RepairSheet repairSheet = RepairSheetService.getRepairSheetById(repairId);
			RepairReport repairReport = new RepairReport();
			repairReport.setType_of_maintenance(repairSheet.getType_of_maintenance().getType_of_maintenance_name());
			repairReport.setSubdivision(repairSheet.getSubdivision().getSubdivision_name());
			repairReport.setSheet_number("" + repairSheet.getSheet_number());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
			repairReport.setStart_date(dateFormat.format(repairSheet.getStart_date()));
			repairReport.setEquipment(repairSheet.getEquipment().getEquipment_name());
			repairReport.setProducer(repairSheet.getEquipment().getProducer_of_equipment());
			repairReport.setType_of_equipment(repairSheet.getEquipment().getType_of_equipment().getType_of_equipment_name());
			repairReport.setCategory("" + repairSheet.getSheet_number() / 10000);
			String lastRepair="";
			if(this.RepairSheetService.getRepairSheetByEquipment(repairSheet.getEquipment()).size()>1){
				RepairSheet lastRepairSheet = this.RepairSheetService.getRepairSheetByEquipment(repairSheet.getEquipment()).get(1);
				lastRepair = lastRepairSheet.getType_of_maintenance().getType_of_maintenance_name() + ","+
						dateFormat.format(lastRepairSheet.getEnd_date());
			}
			else{
				lastRepair = "не было";
			}
			repairReport.setLastRepair(lastRepair);
			repairReport.setResponsible(UserService.findBySso(getPrincipal()).getLast_name());
			reportItems.add(repairReport);
			JRDataSource datasource = new JRBeanCollectionDataSource(reportItems);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, datasource);
			try {
				String outputFile = "C:\\Users\\user\\Dropbox\\MyProjects\\SPTOR\\acts\\"  + "Акт_вывода_№" + repairReport.getSheet_number() + ".docx";
				JRDocxExporter exporter = new JRDocxExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFile);
				exporter.exportReport();
				System.out.println("successfully");
			}catch (Exception e){
				System.out.println("Export exception");
			}
			//generateReportHtml(jasperPrint, request, response); // For HTML report

			//}

			//else if  (rptFormat.equalsIgnoreCase("pdf") )  {
//
			//	generateReportPDF(response, hmParams, jasperReport, conn); // For PDF report
//
			//}

		} catch (Exception sqlExp) {

			System.out.println( "Exception::" + sqlExp.toString());

		} finally {

			try {

				if (conn != null) {
					conn.close();
					conn = null;
				}

			} catch (SQLException expSQL) {

				System.out.println("SQLExp::CLOSING::" + expSQL.toString());

			}

		}

		return null;

	}

	@RequestMapping(value = "/report/ppr", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response) throws IOException {

		File file = new File("C:\\Users\\user\\Dropbox\\MyProjects\\SPTOR\\acts\\ppr.doc");

		if(!file.exists()){
			String errorMessage = "Sorry. The file you are looking for does not exist";
			System.out.println(errorMessage);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}

		String mimeType= URLConnection.guessContentTypeFromName(file.getName());
		if(mimeType==null){
			System.out.println("mimetype is not detectable, will take default");
			mimeType = "application/octet-stream";
		}

		System.out.println("mimetype : "+mimeType);

		response.setContentType(mimeType);

        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));


        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
		//response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

		response.setContentLength((int)file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		//Copy bytes from source to destination(outputstream in this example), closes both streams.
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}

	private JasperReport getCompiledFile(String fileName, HttpServletRequest request) throws JRException {
		System.out.println("path " + request.getSession().getServletContext().getRealPath("/jasper/" + fileName + ".jasper"));
		File reportFile = new File( request.getSession().getServletContext().getRealPath("/jasper/" + fileName + ".jasper"));
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(request.getSession().getServletContext().getRealPath("/jasper/" + fileName + ".jrxml"), request.getSession().getServletContext().getRealPath("/jasper/" + fileName + ".jasper"));
		}
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
		return jasperReport;
	}

	private void generateReportHtml( JasperPrint jasperPrint, HttpServletRequest req, HttpServletResponse resp) throws IOException, JRException {
		HtmlExporter exporter=new HtmlExporter();
		List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		jasperPrintList.add(jasperPrint);
		exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintList));
		exporter.setExporterOutput( new SimpleHtmlExporterOutput(resp.getWriter()));
		SimpleHtmlReportConfiguration configuration =new SimpleHtmlReportConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();

	}

	private void generateReportPDF (HttpServletResponse resp, Map parameters, JasperReport jasperReport, Connection conn)throws JRException, NamingException, SQLException, IOException {
		byte[] bytes = null;
		bytes = JasperRunManager.runReportToPdf(jasperReport, parameters, conn);
		resp.reset();
		resp.resetBuffer();
		resp.setContentType("application/pdf");
		resp.setContentLength(bytes.length);
		ServletOutputStream ouputStream = resp.getOutputStream();
		ouputStream.write(bytes, 0, bytes.length);
		ouputStream.flush();
		ouputStream.close();
	}


	@ApiOperation(value = "getEquipments", notes = "Get all equipments")
	@RequestMapping(value = "/graphics", method = RequestMethod.GET,
			produces = "application/json")
	public String startGraphics(Model model) {
		Status status1 = StatusService.getStatusById(1);
		Status status2 = StatusService.getStatusById(2);
		model.addAttribute("active_req", this.RepairSheetService.getRepairSheetByStatus(status1).size());
		model.addAttribute("confirm_req", this.RepairSheetService.getRepairSheetByStatus(status2).size());
		model.addAttribute("user", UserService.findBySso(getPrincipal()).getLast_name() + " " +
				UserService.findBySso(getPrincipal()).getFirst_name());
		model.addAttribute("subdivisions", this.SubdivisionService.listSubdivisions());
		return "graphics";
	}
	/**
	 * POST update subdivision by Id.
	 * @return subdivision
	 */
	@ApiOperation(value = "updateSubdivision", notes = "Update subdivision by ID")
	@RequestMapping(value = "/subdivisions/{subdivisionId}", method = RequestMethod.PUT,
			produces = "application/json")
	public @ResponseBody String updateSubdivision(@ApiParam(
			defaultValue = "{\n" +
					"  \"subdivision_name\":\"Name\",\n" +
					"  \"abbreviation\":  \"Abbreviation\",\n" +
					"  \"responsible\":\"Ivanov\",\n" +
					"  \"description\":\"Description\"\n" +
					"}")@RequestBody Subdivisions subdivision, @PathVariable(value = "subdivisionId")int subdivisionId) {
		if(SubdivisionService.getSubdivisionById(subdivisionId) !=null){
			Subdivisions updateSubdivision = subdivision;
			updateSubdivision.setSubdivision_id(subdivisionId);
			SubdivisionService.updateSubdivision(updateSubdivision);
		};
		return "Update successfully";
	}

	/**
	 * DELETE delete subdivision by Id.
	 * @return subdivision
	 */
	@ApiOperation(value = "deleteSubdivision", notes = "Delete subdivision by ID")
	@RequestMapping(value = "/subdivisions/{id}", method = RequestMethod.DELETE,
			produces = "application/json")
	public @ResponseBody String deleteSubdivision(@ApiParam(value = "ID of subdivision that" +
			"needs to be fetched", required = true,
			defaultValue = "1")@PathVariable(value = "id")int id) {
		SubdivisionService.deleteSubdivision(id);
		return "Deleted successfully";
	}


	@ApiOperation(value = "getCards", notes = "Get all technological cards")
	@RequestMapping(value = "/cards", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody List<TechnologicalCard> getCards() {
		return TechnologicalCardService.listCards();
	}

	@ApiOperation(value = "getCards", notes = "Get all technological cards")
	@RequestMapping(value = "/cards/{id}", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody TechnologicalCard getCardById(@PathVariable(value = "id")int id) {
		return TechnologicalCardService.getCardById(id);
	}

	@ApiOperation(value = "getCard", notes = "Get technological card by ID")
	@RequestMapping(value = "/cards/equipment/{id}", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody List<TechnologicalCard> getCard(@ApiParam(value = "ID of question that" +
			"needs to be fetched", required = true,
			defaultValue = "1")@PathVariable(value = "id")int id) {
		Equipment equipment = this.EquipmentService.getEquipmentById(id);
		return TechnologicalCardService.getTechCardByEquipment(equipment);
	}


	@ApiOperation(value = "addCard", notes = "Add new card")
	@RequestMapping(value = "/cards/add", method = RequestMethod.POST,
			produces = "application/json")
	public  String addCard(
			@RequestParam int equipment_id,
			@RequestParam int type_of_maintenance_id,
			@RequestParam String start_date,
			@RequestParam String description) throws ParseException{
		TechnologicalCard technologicalCard = new TechnologicalCard();
		technologicalCard.setType_of_maintenance(this.TypeOfMaintenanceService.getTypeById(type_of_maintenance_id));
		technologicalCard.setEquipment(this.EquipmentService.getEquipmentById(equipment_id));
		technologicalCard.setTechnological_card_number(22000 + equipment_id);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		technologicalCard.setStart_date(formatter.parse(start_date));
		technologicalCard.setEnd_date(formatter.parse(start_date));
		technologicalCard.setResponsible_for_delivery(UserService.findBySso(getPrincipal()));
		technologicalCard.setResponsible_for_reception(UserService.findBySso(getPrincipal()));
		technologicalCard.setDescription(description);
		TechnologicalCardService.addCard(technologicalCard);
		return "redirect:/equipments";

	}

	@ApiOperation(value = "updateCards", notes = "Update cards by ID")
	@RequestMapping(value = "/cards/{cardId}", method = RequestMethod.PUT,
			produces = "application/json")
	public @ResponseBody String updateCard(@PathVariable("cardId") int cardId, TechnologicalCard technologicalCard){
		if(TechnologicalCardService.getCardById(cardId)!= null){
			TechnologicalCard updatetechnologicalCard = technologicalCard;
			updatetechnologicalCard.setTechnological_card_id(cardId);
			TechnologicalCardService.updateCard(updatetechnologicalCard);
		}
		return "Update successfully";
	}

	@ApiOperation(value = "deleteCard", notes = "Delete card by ID")
	@RequestMapping(value = "/cards/{cardId}", method = RequestMethod.DELETE,
			produces = "application/json")
	public @ResponseBody String deleteCard(@ApiParam(value = "ID of card that" +
			"needs to be fetched", required = true,
			defaultValue = "1")@PathVariable(value = "cardId")int id) {
		TechnologicalCardService.deleteCard(id);
		return "Deleted successfully";
	}

	@ApiOperation(value = "getUsers", notes = "Get all technological cards")
	@RequestMapping(value = "/manual", method = RequestMethod.GET,
			produces = "application/json")
	public String getManual(Model model) {
		model.addAttribute("userForm", new UserFormModel());
		Status status1 = StatusService.getStatusById(1);
		Status status2 = StatusService.getStatusById(2);
		model.addAttribute("active_req", this.RepairSheetService.getRepairSheetByStatus(status1).size());
		model.addAttribute("confirm_req", this.RepairSheetService.getRepairSheetByStatus(status2).size());
		model.addAttribute("subdivisions", this.SubdivisionService.listSubdivisions());
		model.addAttribute("user", UserService.findBySso(getPrincipal()).getLast_name() + " " +
				UserService.findBySso(getPrincipal()).getFirst_name());
		return "manual";
	}


	@ApiOperation(value = "getUsers", notes = "Get all technological cards")
	@RequestMapping(value = "/users/all", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody List<User> getUsers(Model model) {
		return UserService.getUsers();
	}

	@ApiOperation(value = "getUsers", notes = "Get all technological cards")
	@RequestMapping(value = "/users", method = RequestMethod.GET,
			produces = "application/json")
	public String getUsersView(Model model) {
		model.addAttribute("subdivisions", this.SubdivisionService.listSubdivisions());
		model.addAttribute("userForm", new UserFormModel());
		model.addAttribute("user", UserService.findBySso(getPrincipal()).getLast_name() + " " +
				UserService.findBySso(getPrincipal()).getFirst_name());
		Status status1 = StatusService.getStatusById(1);
		Status status2 = StatusService.getStatusById(2);
		model.addAttribute("active_req", this.RepairSheetService.getRepairSheetByStatus(status1).size());
		model.addAttribute("confirm_req", this.RepairSheetService.getRepairSheetByStatus(status2).size());
		return "users";
	}

	@ApiOperation(value = "addUser", notes = "Get all technological cards")
	@RequestMapping(value = "/users", method = RequestMethod.POST,
			produces = "application/json")
	public String addUsers(@RequestParam String ssoid,
						   @RequestParam String first_name,
						   @RequestParam String last_name,
						   @RequestParam String password,
						   @RequestParam String email) {
		User user = new User();
		user.setFirst_name(first_name);
		user.setLast_name(last_name);
		user.setEmail(email);
		user.setssoid(ssoid);
		user.setPassword(passwordEncoder.encodePassword(password, ssoid));
		this.UserService.addUser(user);
		return "manual";
	}

	@ApiOperation(value = "getEquipments", notes = "Get all technological cards")
	@RequestMapping(value = "/equipment/all", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody List<Equipment> getEquipments(Model model) {
		return EquipmentService.listEquipments();
	}

	@ApiOperation(value = "getEquipments", notes = "Get all technological cards")
	@RequestMapping(value = "/equipment", method = RequestMethod.GET,
			produces = "application/json")
	public String getEquipmentView(Model model) {
		model.addAttribute("eqType", this.TypeOfEquipmentService.listType());
		model.addAttribute("eqSub", this.SubdivisionService.listSubdivisions());
		model.addAttribute("eqResp", this.UserService.getUsers());
		model.addAttribute("eqStatus", this.StatusOfEqService.listStatus());
		model.addAttribute("subdivisions", this.SubdivisionService.listSubdivisions());
		model.addAttribute("user", UserService.findBySso(getPrincipal()).getLast_name() + " " +
				UserService.findBySso(getPrincipal()).getFirst_name());
		Status status1 = StatusService.getStatusById(1);
		Status status2 = StatusService.getStatusById(2);
		model.addAttribute("active_req", this.RepairSheetService.getRepairSheetByStatus(status1).size());
		model.addAttribute("confirm_req", this.RepairSheetService.getRepairSheetByStatus(status2).size());
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
		equipment.setEquipment_name(equipment_name);
		equipment.setType_of_equipment(this.TypeOfEquipmentService.getTypeById(type_of_equipment_id));
		equipment.setSubdivision(this.SubdivisionService.getSubdivisionById(subdivision_id));
		equipment.setInventory_number(inventory_number);
		equipment.setGraduation_year(date);
		equipment.setProducer_of_equipment(producer_of_equipment);
		equipment.setWorking_hours(working_hours);
		equipment.setStatus(this.StatusOfEqService.getStatusById(status));
		equipment.setDescription(description);
		this.EquipmentService.addEquipment(equipment);
		return "manual";
	}

	@ApiOperation(value = "getEquipments", notes = "Get all technological cards")
	@RequestMapping(value = "/spare/all", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody List<Spares> getSpare(Model model) {
		return SpareService.listSpares();
	}

	@ApiOperation(value = "getEquipments", notes = "Get all technological cards")
	@RequestMapping(value = "/spare", method = RequestMethod.GET,
			produces = "application/json")
	public String getSpareView(Model model) {
		model.addAttribute("subdivisions", this.SubdivisionService.listSubdivisions());
		model.addAttribute("user", UserService.findBySso(getPrincipal()).getLast_name() + " " +
				UserService.findBySso(getPrincipal()).getFirst_name());
		Status status1 = StatusService.getStatusById(1);
		Status status2 = StatusService.getStatusById(2);
		model.addAttribute("active_req", this.RepairSheetService.getRepairSheetByStatus(status1).size());
		model.addAttribute("confirm_req", this.RepairSheetService.getRepairSheetByStatus(status2).size());
		return "spare";
	}

	@ApiOperation(value = "addEquipment", notes = "Get all technological cards")
	@RequestMapping(value = "/spare", method = RequestMethod.POST,
			produces = "application/json")
	public String addSpare(@RequestParam String spare_name,
							   @RequestParam String spare_producer,
							   @RequestParam double price,
						       @RequestParam int amount_in_stock,
							   @RequestParam String description) throws ParseException {
		Spares spare = new Spares();
		spare.setSpare_name(spare_name);
		spare.setSpare_producer(spare_producer);
		spare.setPrice(price);
		spare.setAmount_in_stock(amount_in_stock);
		spare.setDescription(description);
		this.SpareService.addSpare(spare);
		return "manual";
	}

	@ApiOperation(value = "getEquipments", notes = "Get all technological cards")
	@RequestMapping(value = "/subdivision/all", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody List<Subdivisions> getSubdivision(Model model) {
		return SubdivisionService.listSubdivisions();
	}

	@ApiOperation(value = "getEquipments", notes = "Get all technological cards")
	@RequestMapping(value = "/subdivision", method = RequestMethod.GET,
			produces = "application/json")
	public String getSubdivisionView(Model model) {
		model.addAttribute("subdivisions", this.SubdivisionService.listSubdivisions());
		model.addAttribute("user", UserService.findBySso(getPrincipal()).getLast_name() + " " +
				UserService.findBySso(getPrincipal()).getFirst_name());
		Status status1 = StatusService.getStatusById(1);
		Status status2 = StatusService.getStatusById(2);
		model.addAttribute("active_req", this.RepairSheetService.getRepairSheetByStatus(status1).size());
		model.addAttribute("confirm_req", this.RepairSheetService.getRepairSheetByStatus(status2).size());
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
		this.SubdivisionService.addSubdivision(subdivisions);
		return "manual";
	}

	@RequestMapping(value = "/type_of_equipment", method = RequestMethod.GET)
	public String listTypes(Model model) {
		model.addAttribute("type_of_equipment", new TypeOfEquipment());
		model.addAttribute("listTypes", this.TypeOfEquipmentService.listType());
		return "type_of_equipment";
	}

	@RequestMapping(value= "/type_of_equipment/add", method = RequestMethod.POST)
	public String addType(@ModelAttribute("type_of_equipment") TypeOfEquipment typeOfEquipment, Model model){
		if(typeOfEquipment.getType_of_equipment_id() == 0){
			//new person, add it
			this.TypeOfEquipmentService.addType(typeOfEquipment);
		}else{
			//existing person, call update
			this.TypeOfEquipmentService.updateType(typeOfEquipment);
		}
		model.addAttribute("type_of_equipment", new TypeOfEquipment());
		return "type_of_equipment";

	}


/*
	@RequestMapping("type/edit/{id}")
	public String editType(@PathVariable("id") int id, Model model){
		model.addAttribute("type_of_equipment", this.TypeOfEquipmentService.getTypeById((id)));
		model.addAttribute("listTypes", this.TypeOfEquipmentService.listType());

		return "type_of_equipment";
	}

	@RequestMapping(value = "type/remove/{id}")
	public String removeType(@PathVariable("id") int id){

		this.TypeOfEquipmentService.deleteType(id);
		return "redirect:/type_of_equipment";
	}

	@RequestMapping(value = "/spares", method = RequestMethod.GET)
	public String listSpares(Model model) {
		model.addAttribute("spare", new Spares());
		model.addAttribute("listSpares", this.SpareService.listSpares());
		return "spare";
	}

	@RequestMapping(value= "/spares/add", method = RequestMethod.POST)
	public String addSpare(@ModelAttribute("spares") Spares spares){
		if(spares.getSpare_id() == 0){
			//new person, add it
			this.SpareService.addSpare(spares);
		}else{
			//existing person, call update
			this.SpareService.updateSpare(spares);
		}

		return "redirect:/spares";

	}

	@RequestMapping("spares/edit/{id}")
	public String editSpare(@PathVariable("id") int id, Model model){
		model.addAttribute("spare", this.SpareService.getSpareById((id)));
		model.addAttribute("listSpares", this.SpareService.listSpares());

		return "spare";
	}

	@RequestMapping(value = "spares/remove/{id}")
	public String removeSpare(@PathVariable("id") int id){

		this.SpareService.deleteSpare(id);
		return "redirect:/spares";
	}*/

}