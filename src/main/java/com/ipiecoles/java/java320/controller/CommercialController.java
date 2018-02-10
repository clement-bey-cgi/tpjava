package com.ipiecoles.java.java320.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ipiecoles.java.java320.model.Commercial;
import com.ipiecoles.java.java320.service.CommercialService;
import com.ipiecoles.java.java320.service.EmployeService;

@Controller
@RequestMapping(value="/commercials")
public class CommercialController {
	
	@Autowired
	private CommercialService commercialService;
	
	@Autowired 
	private EmployeService employeService;
	
	/**
	 * Persiste un Commercial envoyé via formulaire
	 * */
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String createCommercial(Map<String, Object> model, HttpServletRequest request) {
		String nom = (String) request.getParameter("nom");
		String prenom = (String) request.getParameter("prenom");
		Double salaire = (Double)Double.parseDouble(request.getParameter("salaire"));
		String matricule = (String) request.getParameter("matricule");
		LocalDate dateEmbauche = LocalDate.parse(request.getParameter("dateEmbauche"));

		Commercial commercial = new Commercial(nom, prenom, matricule, dateEmbauche, salaire);
		Commercial createdCommercial = commercialService.creerEmploye(commercial);
		if (createdCommercial == null) {
			model.put("message", "Impossible de créer ce commercial ! ");
			return "erreur";
		}
		model.put("createdCommercial", createdCommercial);
		model.put("nombreEmployes", employeService.countAllEmploye());
		return "index";
	}
	
}
