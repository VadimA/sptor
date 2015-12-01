package com.diplom.sptor.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Controller
@RequestMapping("/")
public class EquipmentController {

	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping("/users")
	public String equipments(Model model) {

		model.addAttribute("equipment", entityManager.createQuery("select u from equipment u").getResultList());

		return "equipment";
	}
}