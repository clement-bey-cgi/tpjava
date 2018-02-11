package com.ipiecoles.java.java320.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipiecoles.java.java320.model.Manager;
import com.ipiecoles.java.java320.model.Technicien;
import com.ipiecoles.java.java320.service.EmployeService;
import com.ipiecoles.java.java320.service.ManagerService;
import com.ipiecoles.java.java320.service.TechnicienService;

@Controller
@RequestMapping("/managers")
public class ManagerController {

	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private EmployeService employeService;
	
	@Autowired
	private TechnicienService technicienService;
	
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
	
	@RequestMapping(value="/{id}", method = RequestMethod.POST)
	public void modifyManager(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response, Map<String,Object> model) throws Exception {
		model.put("nombreEmployes", employeService.countAllEmploye());
		
		Manager actualManager = managerService.findOne(id);
		actualManager.setDateEmbauche(LocalDate.parse(request.getParameter("dateEmbauche")));
		actualManager.setMatricule(request.getParameter("matricule"));
		actualManager.setNom(request.getParameter("nom"));
		actualManager.setPrenom(request.getParameter("prenom"));
		actualManager.setSalaire(Double.parseDouble(request.getParameter("salaire")));
		
		Manager modifiedManager = managerService.updateThisManager(actualManager);
		
		response.sendRedirect("http://localhost:8080/employes/"+id);
	}
	
	@RequestMapping(value="/{id}/addtechnicien", method = RequestMethod.GET)
	public void addThisManagerATechnician(@PathVariable("id")Long id, 
	@RequestParam("matricule")String matricule, HttpServletResponse response, 
	Map<String, Object> model) throws IOException {
		
		
		Technicien technicien = (Technicien)employeService.findMyMatricule(matricule);
		Manager manager = managerService.findOne(id);
		technicien.setManager(null);
		technicien.setManager(manager);
		technicienService.updateThisTechnicien(technicien);
		
		model.put("nombreEmployes", employeService.countAllEmploye());
		response.sendRedirect("http://localhost:8080/employes/"+String.valueOf(id));
	}
}
