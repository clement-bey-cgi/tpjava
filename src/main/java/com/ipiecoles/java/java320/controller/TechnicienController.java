package com.ipiecoles.java.java320.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ipiecoles.java.java320.model.Technicien;
import com.ipiecoles.java.java320.service.EmployeService;
import com.ipiecoles.java.java320.service.TechnicienService;

@Controller
@RequestMapping(value="/techniciens")
public class TechnicienController {
	
	@Autowired
	private TechnicienService technicienService;
	
	@Autowired
	private EmployeService employeService;
	
	@RequestMapping(value="/save")
	public String createNewTechnicien(Map<String, Object> model, HttpServletRequest req) {
		String nom = (String) req.getParameter("nom");
		String prenom = (String) req.getParameter("prenom");
		Double salaire = (Double)Double.parseDouble(req.getParameter("salaire"));
		String matricule = (String) req.getParameter("matricule");
		LocalDate dateEmbauche = LocalDate.parse(req.getParameter("dateEmbauche"));
		
		Technicien technicienToPersist = new Technicien(nom,prenom,salaire,matricule,dateEmbauche);
		technicienService.saveNewTechnicien(technicienToPersist);
		model.put("nombreEmployes", employeService.countAllEmploye());
		return "index";
	}
}
