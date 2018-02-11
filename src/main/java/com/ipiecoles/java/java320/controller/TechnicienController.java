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

import com.ipiecoles.java.java320.model.Commercial;
import com.ipiecoles.java.java320.model.Manager;
import com.ipiecoles.java.java320.model.Technicien;
import com.ipiecoles.java.java320.service.EmployeService;
import com.ipiecoles.java.java320.service.ManagerService;
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
		Integer grade = Integer.valueOf(req.getParameter("grade"));
		String nom = (String) req.getParameter("nom");
		String prenom = (String) req.getParameter("prenom");
		Double salaire = (Double)Double.parseDouble(req.getParameter("salaire"));
		String matricule = (String) req.getParameter("matricule");
		LocalDate dateEmbauche = LocalDate.parse(req.getParameter("dateEmbauche"));
		Technicien technicienToPersist = new Technicien(nom, prenom, matricule, dateEmbauche, salaire, grade);
		technicienService.saveNewTechnicien(technicienToPersist);
		model.put("nombreEmployes", employeService.countAllEmploye());
		return "index";
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.POST)
	public void modifyTechnicien(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response, Map<String,Object> model) throws Exception {
		model.put("nombreEmployes", employeService.countAllEmploye());
		
		Technicien actualTechnicien = technicienService.findById(id);
		actualTechnicien.setDateEmbauche(LocalDate.parse(request.getParameter("dateEmbauche")));
		actualTechnicien.setMatricule(request.getParameter("matricule"));
		actualTechnicien.setNom(request.getParameter("nom"));
		actualTechnicien.setGrade(Integer.valueOf(request.getParameter("grade")));
		actualTechnicien.setPrenom(request.getParameter("prenom"));
		actualTechnicien.setSalaire(Double.parseDouble(request.getParameter("salaire")));
		
		Technicien modifiedTechnicien = technicienService.updateThisTechnicien(actualTechnicien);
		
		response.sendRedirect("http://localhost:8080/employes/"+id);
	}
	
	@RequestMapping(value="/{id}/removemanager/{requestStartId}", method = RequestMethod.GET)
	public void setThisTechnicianFreeFromManager(@PathVariable("id")Long id, @PathVariable("requestStartId")Long requestStartId, HttpServletResponse response, 
	Map<String, Object> model) throws IOException {
		Technicien actualTechnicien = technicienService.findById(id);
		actualTechnicien.setManager(null);
		technicienService.updateThisTechnicien(actualTechnicien);
		
		model.put("nombreEmployes", employeService.countAllEmploye());
		response.sendRedirect("http://localhost:8080/employes/"+String.valueOf(requestStartId));
	}
	
	@RequestMapping(value="/{id}/addmanager", method = RequestMethod.GET)
	public void addThisTechnicianAManager(@PathVariable("id")Long id, 
	@RequestParam("matricule")String matricule, HttpServletResponse response, 
	Map<String, Object> model) throws IOException {
		
		Technicien actualTechnicien = technicienService.findById(id);
		Manager manager = (Manager)employeService.findMyMatricule(matricule);
		actualTechnicien.setManager(null);
		actualTechnicien.setManager(manager);
		technicienService.updateThisTechnicien(actualTechnicien);
		
		model.put("nombreEmployes", employeService.countAllEmploye());
		response.sendRedirect("http://localhost:8080/employes/"+String.valueOf(id));
	}
}
