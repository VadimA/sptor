package com.diplom.sptor.web;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.domain.Subdivisions;
import com.diplom.sptor.service.EquipmentService;
import com.diplom.sptor.service.SubdivisionService;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired(required=true)
	public void setEquipmentService(EquipmentService EquipmentService){
		this.EquipmentService = EquipmentService;
	}

	@Autowired(required=true)
	public void setSubdivisionService(SubdivisionService SubdivisionService){
		this.SubdivisionService = SubdivisionService;
	}



	@RequestMapping("/")
	public String homepage(ModelMap model){
		return "index";
	}


	@RequestMapping(value = "/equipment", method = RequestMethod.GET)
	public String listEquipments(Model model) {
		model.addAttribute("equipment", new Equipment());
		model.addAttribute("listEquipments", this.EquipmentService.listEquipments());

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
		return "redirect:/equipment";


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
}