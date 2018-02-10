package com.ipiecoles.java.java320.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ipiecoles.java.java320.model.Manager;
import com.ipiecoles.java.java320.service.EmployeService;
import com.ipiecoles.java.java320.service.ManagerService;

@Controller
@RequestMapping("/managers")
public class ManagerController {

	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private EmployeService employeService;
	
	@RequestMapping(value="/save")
	public String createNewManager(Map<String, Object> model, HttpServletRequest req) {
		String nom = (String) req.getParameter("nom");
		String prenom = (String) req.getParameter("prenom");
		Double salaire = (Double)Double.parseDouble(req.getParameter("salaire"));
		String matricule = (String) req.getParameter("matricule");
		LocalDate dateEmbauche = LocalDate.parse(req.getParameter("dateEmbauche"));
		
		Manager managerToPersist = new Manager(nom,prenom,salaire,matricule,dateEmbauche);
		Manager createdManager = managerService.saveThisManager(managerToPersist);
		model.put("nombreEmployes", employeService.countAllEmploye());
		return "index";
	}
}
