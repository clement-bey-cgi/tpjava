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
import com.ipiecoles.java.java320.model.Commercial;
import com.ipiecoles.java.java320.service.CommercialService;

@Controller
@RequestMapping(value="/commercials")
public class CommercialController {
	
	@Autowired
	private CommercialService commercialService;
	
	/**
	 * Persiste un Commercial envoyé via formulaire
	 * 
	 * @Param request
	 * 				la requete contant les infos du formulaire 
	 * 
	 * @Param model
	 * 				les attributs de la requete
	 * 
	 * @Param response
	 * 					necessaire a la redirection
	 * 
	 * @param session
	 * 				pour acceder au nombre d employe
	 * @throws IOException 
	 * */
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public void createCommercial(Map<String, Object> model, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {
		String nom = (String) request.getParameter("nom");
		String prenom = (String) request.getParameter("prenom");
		Double salaire = Double.parseDouble(request.getParameter("salaire"));
		String matricule = (String) request.getParameter("matricule");
		LocalDate dateEmbauche = LocalDate.parse(request.getParameter("dateEmbauche"));

		Commercial commercial = new Commercial(nom, prenom, matricule, dateEmbauche, salaire);
		Commercial createdCommercial = commercialService.creerEmploye(commercial);
		
		// gestion des erreurs possibles en persistant
		if (createdCommercial == null) {
			model.put("message", "Impossible de créer ce commercial ! ");
			response.sendRedirect("http://localhost:8080/printerror/");
		}
		
		model.put("createdCommercial", createdCommercial);
		
		Long nombreEmployees = (Long)session.getServletContext().getAttribute("nombreEmployes");
		nombreEmployees++;
		session.getServletContext().setAttribute("nombreEmployes", nombreEmployees);
		
		response.sendRedirect("http://localhost:8080/employes/"+String.valueOf(createdCommercial.getId()));
	}
	
	/**
	 * Modifie un commercial persisté en base de donné
	 * 
	 * @param id 
	 * 			id du commercial a modifier
	 * @param model
	 * 				les attributs de la requete
	 * @param request, reponse 
	 * 				request pour recupérer les infos du formulaire
	 * 				response pour la redirection post modification
	 * */
	@RequestMapping(value="/{id}", method = RequestMethod.POST)
	public void modifyCommercial(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response,
	Map<String,Object> model) throws Exception {	
		Commercial actualCommercial = commercialService.findById(id);
		
		actualCommercial.setCaAnnuel(Double.parseDouble(request.getParameter("caAnnuel")));
		actualCommercial.setDateEmbauche(LocalDate.parse(request.getParameter("dateEmbauche")));
		actualCommercial.setMatricule(request.getParameter("matricule"));
		actualCommercial.setNom(request.getParameter("nom"));
		actualCommercial.setPerformance(Integer.valueOf(request.getParameter("performance")));
		actualCommercial.setPrenom(request.getParameter("prenom"));
		actualCommercial.setSalaire(Double.parseDouble(request.getParameter("salaire")));
		
		Commercial modifiedCommercial = commercialService.updateEmploye(id, actualCommercial);
		if(modifiedCommercial == null) {
			model.put("messageErreur", "Impossible de modifier ce commercial");
			response.sendRedirect("http://localhost:8080/printerror");			
		}
		
		response.sendRedirect("http://localhost:8080/employes/"+id);
	}
}
