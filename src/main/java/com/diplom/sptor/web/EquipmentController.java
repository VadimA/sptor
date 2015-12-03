package com.diplom.sptor.web;

import com.diplom.sptor.domain.Equipment;
import com.diplom.sptor.service.EquipmentService;
import com.diplom.sptor.service.EquipmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class EquipmentController {
	private EquipmentService EquipmentService;

	@Autowired(required=true)
	public void setEquipmentService(EquipmentService EquipmentService){
		this.EquipmentService = EquipmentService;
	}

	@RequestMapping(value = "/equipment", method = RequestMethod.GET)
	public String listEquipments(Model model) {
		model.addAttribute("equipment", new Equipment());
		model.addAttribute("listEquipments", this.EquipmentService.listEquipments());
		return "equipment";
	}

	@RequestMapping(value= "/equipment/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("equipment") Equipment equipment){
			this.EquipmentService.addEquipment(equipment);
		return "redirect:/equipment";

	}
}