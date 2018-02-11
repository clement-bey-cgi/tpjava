package com.ipiecoles.java.java320.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ipiecoles.java.java320.service.EmployeService;

@Controller
@RequestMapping(value="")
public class MainController {
	
	@Autowired
	private EmployeService employeService;
	
	/***
	 * Accueil/Consigne
	 * */
	@RequestMapping(value="", method = RequestMethod.GET)
	public String getHome(Map<String, Object> model, HttpSession session) {
		
		if (session.getServletContext().getAttribute("nombreEmployes") == null) {
			session.getServletContext().setAttribute("nombreEmployes", employeService.countAllEmploye());
		}

		return "index";
	}
	
}
