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

	private EquipmentServiceImpl equipmentService;

	public void setEquipmentService(EquipmentServiceImpl equipmentService){
		this.equipmentService = equipmentService;
	}

	@RequestMapping(value = "/equipment", method = RequestMethod.GET)
	public String listEquipments(Model model) {
		List<Equipment> equipments = new ArrayList<Equipment>(2);
		Equipment equipment1 = new Equipment();
		equipment1.setEquipment_ID(2);
		equipment1.setEquipment_name("aaaa");
		Equipment equipment2 = new Equipment();
		equipment2.setEquipment_ID(3);
		equipment2.setEquipment_name("bbb");
		equipments.add(equipment1);
		equipments.add(equipment2);
		model.addAttribute("equipment", new Equipment());
		model.addAttribute("listEquipments", this.equipmentService.listEquipments());
		return "equipment";
	}

	@RequestMapping(value= "/equipment/add", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("equipment") Equipment equipment){

			//new person, add it
			this.equipmentService.addEquipment(equipment);
		return "redirect:/equipment";

	}
}