package com.diplom.sptor.web;

import com.diplom.sptor.domain.*;
import com.diplom.sptor.service.*;
import com.stormpath.sdk.lang.Strings;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.DriverManager;
import java.util.*;
import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@SessionAttributes("current_user")
@Api( value = "sptor", description = "API os SPTOR system")
public class ServiceController {

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
	DocumentService documentService;

	@Autowired
	RepairSheetService repairSheetService;

	@Autowired
	TechnologicalCardService technologicalCardService;

	//@Autowired
	//PasswordEncoder passwordEncoder;

	private Logger logger = Logger.getLogger(ServiceController.class);

	@RequestMapping(value = "/sptor", method = RequestMethod.GET,
			produces = "application/json")
	public String getStarted(Model model, HttpServletRequest request) {

		/** Add twilio service in auth. **/
		//try {
		//	TwilioRestClient client = new TwilioRestClient("AC80ff21ef923177f41a3baaa31baf8e60", "ef641481fe97cc6a96e6d2a79cbde021");
//
		//	List<NameValuePair> params = new ArrayList<NameValuePair>();
		//	params.add(new BasicNameValuePair("To", "+79878358881"));
		//	params.add(new BasicNameValuePair("From", "+13346978168"));
		//	params.add(new BasicNameValuePair("Body", "New login for: vadim@gmail.ru from:  192.168.0.1"));
//
		//	MessageFactory messageFactory = client.getAccount().getMessageFactory();
		//	try {
		//		Message message = messageFactory.create(params);
		//		System.out.println("Message successfuly sent via Twilio. Sid: {}" + message.getSid());
		//	} catch (TwilioRestException e) {
		//		System.out.println("Error communicating with Twilio: {}" + e.getErrorMessage());
		//	}
		//}catch (Exception e){System.out.println(e);}
		model.addAttribute("current_user", userService.getUserBySso(getPrincipal()).getLast_name() + " " +
				userService.getUserBySso(getPrincipal()).getFirst_name());
		Status status1 = statusService.getStatusById(1);
		Status status2 = statusService.getStatusById(2);
		model.addAttribute("active_req", repairSheetService.getRepairSheetByStatus(status1).size());
		model.addAttribute("confirm_req", repairSheetService.getRepairSheetByStatus(status2).size());
		model.addAttribute("subdivisions", subdivisionService.getAllSubdivisions());
		return "homePage";
	}

	@RequestMapping(value = "/about", method = RequestMethod.GET,
			produces = "application/json")
	public String getAbout(Model model) {
		Status status1 = statusService.getStatusById(1);
		Status status2 = statusService.getStatusById(2);
		model.addAttribute("active_req", repairSheetService.getRepairSheetByStatus(status1).size());
		model.addAttribute("confirm_req", repairSheetService.getRepairSheetByStatus(status2).size());
		model.addAttribute("subdivisions", subdivisionService.getAllSubdivisions());
		return "orders";
	}

	@RequestMapping(value = "/planning", method = RequestMethod.GET,
			produces = "application/json")
	public String getPlanning(Model model) {
		return "planning";
	}

	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET,
			produces = "application/json")
	public String login(Model model) {
		logger.warn("Start application. Login page");
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
		Equipment equipment = equipmentService.getEquipmentById(equipmentId);
		TypeOfEquipment typeOfEquipment = equipment.getTypeOfEquipment();
		//TypeOfEquipment typeOfEquipment=  TypeOfEquipmentService.getTypeById(typeId);
		return componentService.getComponentByType(typeOfEquipment);
	}

	@RequestMapping(value = "/components/{componentId}/equipment/{equipmentId}", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody List<RepairSheet> getTypeOfMaintenanceByComponents(@PathVariable(value = "componentId")int componentId, @PathVariable(value = "equipmentId")int equipmentId,  Model model) {
		Equipment equipment = equipmentService.getEquipmentById(equipmentId);
		Components components = componentService.getComponentById(componentId);
		return repairSheetService.getTypeOfMaintenanceOfRepairByEquipmentAndComponents(equipment, components);
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
		return subdivisionService.getSubdivisionById(id);
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

		this.subdivisionService.addSubdivision(subdivision);
		return "Added successfully";

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
			RepairSheet repairSheet = repairSheetService.getRepairSheetById(repairId);
			RepairReport repairReport = new RepairReport();
			repairReport.setType_of_maintenance(repairSheet.getType_of_maintenance().getType_of_maintenance_name());
			repairReport.setSubdivision(repairSheet.getSubdivision().getSubdivision_name());
			repairReport.setSheet_number("" + repairSheet.getSheet_number());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
			repairReport.setStart_date(dateFormat.format(repairSheet.getStart_date()));
			repairReport.setEquipment(repairSheet.getEquipment().getEquipmentName());
			repairReport.setProducer(repairSheet.getEquipment().getProducerOfEquipment());
			repairReport.setType_of_equipment(repairSheet.getEquipment().getTypeOfEquipment().getType_of_equipment_name());
			String lastRepair="";
			if(repairSheetService.getRepairSheetByEquipment(repairSheet.getEquipment()).size()>1){
				RepairSheet lastRepairSheet = repairSheetService.getRepairSheetByEquipment(repairSheet.getEquipment()).get(1);
				lastRepair = lastRepairSheet.getType_of_maintenance().getType_of_maintenance_name() + ", "+
						dateFormat.format(lastRepairSheet.getEnd_date());
			}
			else{
				lastRepair = "не было";
			}
			repairReport.setLastRepair(lastRepair);
			repairReport.setResponsible(userService.getUserBySso(getPrincipal()).getLast_name());
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
			RepairSheet repairSheet = repairSheetService.getRepairSheetById(repairId);
			RepairReport repairReport = new RepairReport();
			repairReport.setType_of_maintenance(repairSheet.getType_of_maintenance().getType_of_maintenance_name());
			repairReport.setSubdivision(repairSheet.getSubdivision().getSubdivision_name());
			repairReport.setSheet_number("" + repairSheet.getSheet_number());
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
			repairReport.setStart_date(dateFormat.format(repairSheet.getStart_date()));
			repairReport.setEquipment(repairSheet.getEquipment().getEquipmentName());
			repairReport.setProducer(repairSheet.getEquipment().getProducerOfEquipment());
			repairReport.setType_of_equipment(repairSheet.getEquipment().getTypeOfEquipment().getType_of_equipment_name());
			repairReport.setCategory("" + repairSheet.getSheet_number() / 10000);
			String lastRepair="";
			if(repairSheetService.getRepairSheetByEquipment(repairSheet.getEquipment()).size()>1){
				RepairSheet lastRepairSheet = repairSheetService.getRepairSheetByEquipment(repairSheet.getEquipment()).get(1);
				lastRepair = lastRepairSheet.getType_of_maintenance().getType_of_maintenance_name() + ","+
						dateFormat.format(lastRepairSheet.getEnd_date());
			}
			else{
				lastRepair = "не было";
			}
			repairReport.setLastRepair(lastRepair);
			repairReport.setResponsible(userService.getUserBySso(getPrincipal()).getLast_name());
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

		//if(!file.exists()){
		//	String errorMessage = "Sorry. The file you are looking for does not exist";
		//	System.out.println(errorMessage);
		//	OutputStream outputStream = response.getOutputStream();
		//	outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
		//	outputStream.close();
		//	return;
		//}

		String mimeType= URLConnection.guessContentTypeFromName(file.getName());
		if(mimeType==null){
			System.out.println("mimetype is not detectable, will take default");
			mimeType = "application/octet-stream";
		}

		System.out.println("mimetype : "+mimeType);

		response.setContentType(mimeType);

		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));

		response.setContentLength((int)file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

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
		Status status1 = statusService.getStatusById(1);
		Status status2 = statusService.getStatusById(2);
		model.addAttribute("active_req", repairSheetService.getRepairSheetByStatus(status1).size());
		model.addAttribute("confirm_req", repairSheetService.getRepairSheetByStatus(status2).size());
		model.addAttribute("subdivisions", subdivisionService.getAllSubdivisions());
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
		if(subdivisionService.getSubdivisionById(subdivisionId) !=null){
			Subdivisions updateSubdivision = subdivision;
			updateSubdivision.setSubdivision_id(subdivisionId);
			subdivisionService.updateSubdivision(updateSubdivision);
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
		subdivisionService.deleteSubdivision(id);
		return "Deleted successfully";
	}


	@ApiOperation(value = "getCards", notes = "Get all technological cards")
	@RequestMapping(value = "/cards", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody List<TechnologicalCard> getCards() {
		return technologicalCardService.getAllCards();
	}

	@ApiOperation(value = "getCards", notes = "Get all technological cards")
	@RequestMapping(value = "/cards/{id}", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody TechnologicalCard getCardById(@PathVariable(value = "id")int id) {
		return technologicalCardService.getCardById(id);
	}

	@ApiOperation(value = "getCard", notes = "Get technological card by ID")
	@RequestMapping(value = "/cards/equipment/{id}", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody List<TechnologicalCard> getCard(@ApiParam(value = "ID of question that" +
			"needs to be fetched", required = true,
			defaultValue = "1")@PathVariable(value = "id")int id) {
		Equipment equipment = equipmentService.getEquipmentById(id);
		return technologicalCardService.getTechCardByEquipment(equipment);
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
		technologicalCard.setType_of_maintenance(typeOfMaintenanceService.getTypeById(type_of_maintenance_id));
		technologicalCard.setEquipment(equipmentService.getEquipmentById(equipment_id));
		technologicalCard.setTechnological_card_number(22000 + equipment_id);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		technologicalCard.setStart_date(formatter.parse(start_date));
		technologicalCard.setEnd_date(formatter.parse(start_date));
		technologicalCard.setResponsible_for_delivery(userService.getUserBySso(getPrincipal()));
		technologicalCard.setResponsible_for_reception(userService.getUserBySso(getPrincipal()));
		technologicalCard.setDescription(description);
		technologicalCardService.addCard(technologicalCard);
		return "redirect:/equipments";

	}

	@ApiOperation(value = "updateCards", notes = "Update cards by ID")
	@RequestMapping(value = "/cards/{cardId}", method = RequestMethod.PUT,
			produces = "application/json")
	public @ResponseBody String updateCard(@PathVariable("cardId") int cardId, TechnologicalCard technologicalCard){
		if(technologicalCardService.getCardById(cardId)!= null){
			TechnologicalCard updatetechnologicalCard = technologicalCard;
			updatetechnologicalCard.setTechnological_card_id(cardId);
			technologicalCardService.updateCard(updatetechnologicalCard);
		}
		return "Update successfully";
	}

	@ApiOperation(value = "deleteCard", notes = "Delete card by ID")
	@RequestMapping(value = "/cards/{cardId}", method = RequestMethod.DELETE,
			produces = "application/json")
	public @ResponseBody String deleteCard(@ApiParam(value = "ID of card that" +
			"needs to be fetched", required = true,
			defaultValue = "1")@PathVariable(value = "cardId")int id) {
		technologicalCardService.deleteCard(id);
		return "Deleted successfully";
	}
}