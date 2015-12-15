package com.diplom.sptor.web;

import com.diplom.sptor.domain.*;
import com.diplom.sptor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class EquipmentController {
	private EquipmentService EquipmentService;
	private SubdivisionService SubdivisionService;
	private TypeOfEquipmentService TypeOfEquipmentService;
	private SpareService SpareService;
	private TechnologicalCardService TechnologicalCardService;
	private RepairSheetService RepairSheetService;

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

	@RequestMapping("/")
	public String homepage(ModelMap model){
		return "index";
	}


	@RequestMapping(value = "/equipment", method = RequestMethod.GET)
	public String listEquipments(Model model) {
		model.addAttribute("equipment", new Equipment());
		model.addAttribute("listEquipment", this.EquipmentService.listEquipments());

		return "equipment";
	}

	@RequestMapping(value= "/equipment/add", method = RequestMethod.POST)
	public String addEquipment(@ModelAttribute("equipment") Equipment equipment){
		if(equipment.getEquipment_id() == 0){
			//new person, add it
			this.EquipmentService.addEquipment(equipment);
		}else{
			//existing person, call update
			this.EquipmentService.addEquipment(equipment);
		}
		return "redirect:/equipments";


	}
	@RequestMapping(value = "/subdivisions", method = RequestMethod.GET)
	public String listSubdivisions(Model model) {
		model.addAttribute("subdivision", new Subdivisions());
		model.addAttribute("listSubdivisions", this.SubdivisionService.listSubdivisions());

		return "subdivision";
	}

	@RequestMapping(value= "/subdivisions/add", method = RequestMethod.POST)
	public String addSubdivision(@ModelAttribute("subdivisions") Subdivisions subdivision){
			if(subdivision.getSubdivision_id() == 0){
				//new person, add it
				this.SubdivisionService.addSubdivision(subdivision);
			}else{
				//existing person, call update
				this.SubdivisionService.updateSubdivision(subdivision);
			}

			return "redirect:/subdivisions";

		}

	@RequestMapping("/edit/{id}")
	public String editSubdivision(@PathVariable("id") int id, Model model){
		this.SubdivisionService.updateSubdivision(this.SubdivisionService.getSubdivisionById(id));
		model.addAttribute("subdivision", this.SubdivisionService.getSubdivisionById((id)));
		model.addAttribute("listSubdivisions", this.SubdivisionService.listSubdivisions());

		return "subdivision";
	}

	@RequestMapping(value = "/remove/{id}")
	public String removeSubdivision(@PathVariable("id") int id){

		this.SubdivisionService.deleteSubdivision(id);
		return "redirect:/subdivisions";
	}


	@RequestMapping(value = "/type_of_equipment", method = RequestMethod.GET)
	public String listTypes(Model model) {
		model.addAttribute("type_of_equipment", new TypeOfEquipment());
		model.addAttribute("listTypes", this.TypeOfEquipmentService.listType());
		return "type_of_equipment";
	}

	@RequestMapping(value= "/type_of_equipment/add", method = RequestMethod.POST)
	public String addType(@ModelAttribute("type_of_equipment") TypeOfEquipment typeOfEquipment){
		if(typeOfEquipment.getType_of_equipment_id() == 0){
			//new person, add it
			this.TypeOfEquipmentService.addType(typeOfEquipment);
		}else{
			//existing person, call update
			this.TypeOfEquipmentService.updateType(typeOfEquipment);
		}

		return "redirect:/type_of_equipment";

	}

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
	}

	@RequestMapping(value = "/cards", method = RequestMethod.GET)
	public String listCards(Model model) {
		model.addAttribute("technological_card", new TechnologicalCard());
		model.addAttribute("listCards", this.TechnologicalCardService.listCards());
		return "technological_card";
	}

	@RequestMapping(value= "/cards/add", method = RequestMethod.POST)
	public String addCard(@ModelAttribute("cards") TechnologicalCard technologicalCard){
		if(technologicalCard.getTechnological_card_id() == 0){
			//new person, add it
			this.TechnologicalCardService.addCard(technologicalCard);
		}else{
			//existing person, call update
			this.TechnologicalCardService.updateCard(technologicalCard);
		}

		return "redirect:/cards";

	}

	@RequestMapping("cards/edit/{id}")
	public String editCard(@PathVariable("id") int id, Model model){
		model.addAttribute("technological_card", this.TechnologicalCardService.getCardById((id)));
		model.addAttribute("listCards", this.TechnologicalCardService.listCards());

		return "technological_card";
	}

	@RequestMapping(value = "cards/remove/{id}")
	public String removeCard(@PathVariable("id") int id){

		this.TechnologicalCardService.deleteCard(id);
		return "redirect:/cards";
	}


	@RequestMapping(value = "/repair_sheets", method = RequestMethod.GET)
	public String listRepairSheets(Model model) {
		model.addAttribute("repair_sheet", new RepairSheet());
		model.addAttribute("listRepairSheet", this.RepairSheetService.listRepairSheets());
		return "repair_sheet";
	}

	@RequestMapping(value= "/repair_sheets/add", method = RequestMethod.POST)
	public String addRepairSheet(@ModelAttribute("repair_sheets") RepairSheet repairSheet){
		if(repairSheet.getRepair_sheet_id() == 0){
			//new person, add it
			this.RepairSheetService.addRepairSheet(repairSheet);
		}else{
			//existing person, call update
			this.RepairSheetService.updateRepairSheet(repairSheet);
		}

		return "redirect:/repair_sheets";

	}

	@RequestMapping("repair_sheets/edit/{id}")
	public String editRepairSheet(@PathVariable("id") int id, Model model){
		model.addAttribute("repair_sheet", this.RepairSheetService.getRepairSheetById((id)));
		model.addAttribute("listRepairSheet", this.RepairSheetService.listRepairSheets());

		return "repair_sheet";
	}

	@RequestMapping(value = "repair_sheets/remove/{id}")
	public String removeRepairSheet(@PathVariable("id") int id){

		this.RepairSheetService.deleteRepairSheet(id);
		return "redirect:/repair_sheets";
	}
}