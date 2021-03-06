package com.ipiecoles.java.java320.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.ipiecoles.java.java320.service.TechnicienService;

@Controller
@RequestMapping(value="/techniciens")
public class TechnicienController {
	
	@Autowired
	private TechnicienService technicienService;
	
	@Autowired
	private EmployeService employeService;
	
	/**
	 * Persiste un nouveau technicien en base
	 * @param req		pour acceder au formulaire envoyé en post
	 * @param response		pour la redirection
	 * @param model			les attributs de la requete
	 * @throws IOException 
	 * */
	@RequestMapping(value="/save")
	public void createNewTechnicien (HttpServletRequest req, HttpSession session, HttpServletResponse response,
	Map<String, Object> model) throws IOException {
		Integer grade = Integer.valueOf(req.getParameter("grade"));
		String nom = (String) req.getParameter("nom");
		String prenom = (String) req.getParameter("prenom");
		Double salaire = (Double)Double.parseDouble(req.getParameter("salaire"));
		String matricule = (String) req.getParameter("matricule");
		LocalDate dateEmbauche = LocalDate.parse(req.getParameter("dateEmbauche"));
		
		Technicien technicienToPersist = new Technicien(nom, prenom, matricule, dateEmbauche, salaire, grade);
		Technicien persistedTechnicien = technicienService.saveNewTechnicien(technicienToPersist);
		
		if(persistedTechnicien == null) {
			model.put("messageErreur", "Impossible de creer ce technicien");
			response.sendRedirect("http://localhost:8080/printerror");			
		}
		
		Long nombreEmployees = (Long)session.getServletContext().getAttribute("nombreEmployes");
		nombreEmployees++;
		session.getServletContext().setAttribute("nombreEmployes", nombreEmployees);
		
		response.sendRedirect("http://localhost:8080/employes/"+String.valueOf(persistedTechnicien.getId()));
	}
	
	/**
	 * Modifie un technicien persisté en base de donné
	 * 
	 * @param id 
	 * 			id du technicien a modifier
	 * 
	 * @param request, reponse 
	 * 				request pour recupérer les infos du formulaire
	 * 				response pour la redirection post modification
	 * 
	 * @param model
	 * 				les attributs de la requete
	 * */
	@RequestMapping(value="/{id}", method = RequestMethod.POST)
	public void modifyTechnicien(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response,
	Map<String, Object> model) throws Exception {
		
		Technicien actualTechnicien = technicienService.findById(id);
		actualTechnicien.setDateEmbauche(LocalDate.parse(request.getParameter("dateEmbauche")));
		actualTechnicien.setMatricule(request.getParameter("matricule"));
		actualTechnicien.setNom(request.getParameter("nom"));
		actualTechnicien.setGrade(Integer.valueOf(request.getParameter("grade")));
		actualTechnicien.setPrenom(request.getParameter("prenom"));
		actualTechnicien.setSalaire(Double.parseDouble(request.getParameter("salaire")));
		
		Technicien modifiedTechnicien = technicienService.updateThisTechnicien(actualTechnicien);
		
		if(modifiedTechnicien == null) {
			model.put("messageErreur", "Impossible de modifier ce technicien");
			response.sendRedirect("http://localhost:8080/printerror");			
		}
		
		response.sendRedirect("http://localhost:8080/employes/"+id);
	}
	
	/**
	 * Retire le manager d'un technicien 
	 * @param id		id du technicien
	 * @param requestStartId		determine si la requete vient du technicien ou du manager (pour redirection à la fin)
	 * @param response		necessaire pour la redirection
	 * */
	@RequestMapping(value="/{id}/removemanager/{requestStartId}", method = RequestMethod.GET)
	public void setThisTechnicianFreeFromManager(@PathVariable("id")Long id, @PathVariable("requestStartId")Long requestStartId, HttpServletResponse response) throws IOException {
		Technicien actualTechnicien = technicienService.findById(id);
		actualTechnicien.setManager(null);
		technicienService.updateThisTechnicien(actualTechnicien);
		
		response.sendRedirect("http://localhost:8080/employes/"+String.valueOf(requestStartId));
	}
	
	/**
	 * Ajoute un manager au technicien 
	 * 
	 * @param matricule			le matricule du manager
	 * @param id 				l'id du technicien cible
	 * @param model				les attributs de la requete pour la vue
	 * @param response			necessaire pour la redirection
	 * */
	@RequestMapping(value="/{id}/addmanager", method = RequestMethod.GET)
	public void addThisTechnicianAManager(@PathVariable("id")Long id, 
	@RequestParam("matricule")String matricule, HttpServletResponse response) throws IOException {
		
		Technicien actualTechnicien = technicienService.findById(id);
		Manager manager = (Manager)employeService.findMyMatricule(matricule);
		actualTechnicien.setManager(null);
		actualTechnicien.setManager(manager);
		technicienService.updateThisTechnicien(actualTechnicien);
		
		response.sendRedirect("http://localhost:8080/employes/"+String.valueOf(id));
	}
}
