package com.diplom.sptor.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Controller
@RequestMapping("/")
public class HelloController {
	Calendar cal = new GregorianCalendar();
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
		model.addAttribute("message", "Hello world!" + (new Date(System.currentTimeMillis())).toString());
		return "hello";
	}
}