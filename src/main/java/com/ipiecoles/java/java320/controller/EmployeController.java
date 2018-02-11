package com.ipiecoles.java.java320.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.ipiecoles.java.java320.model.Employe;
import com.ipiecoles.java.java320.model.Manager;
import com.ipiecoles.java.java320.model.Technicien;
import com.ipiecoles.java.java320.service.EmployeService;
import com.ipiecoles.java.java320.service.ManagerService;
import com.ipiecoles.java.java320.service.TechnicienService;


@Controller
@RequestMapping(value="/employes")
public class EmployeController {
	
	@Autowired
	private EmployeService employeService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private TechnicienService technicienService;
	
	/**
	 * Permet d'accéder au détail d'un employé.
	 * 
	 * @param id 
	 * 			l'id de l'employé
	 * 
	 * @model
	 * 		le scope de la requete
	 * */
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String consultEmployeeInfos(@PathVariable("id")Long id, Map<String, Object> model) {
		model.put("nombreEmployes", employeService.countAllEmploye());
		Employe employe = employeService.findById(id);
		if(employe == null ) {
			model.put("messageErreur", "Employe Introuvable");
			return "erreur";
		}
		model.put("consultedEmployee", employe);
		model.put("consultedEmployeeYearBonus", employe.getPrimeAnnuelle());
		model = this.employeDetailsContextConfiguration(model);
		
		return "employes/detail";
	}
	
	/**
	 * Accède à l'écran de consultation d'un employe grace à son matricule
	 * 
	 * @param matricule
	 * 				le matricule de l'employé
	 * @param model
	 * 				le scope de la requete
	 * */
	@RequestMapping(value="", method = RequestMethod.GET, params = {"matricule"})
	public String employeeByMatricule(@RequestParam("matricule")String matricule, Map<String, Object> model) {
		model.put("nombreEmployes", employeService.countAllEmploye());
		Employe employe = employeService.findMyMatricule(matricule);
		if(employe == null ) {
			model.put("messageErreur", "Employe Introuvable");
			return "erreur";
		}
		model.put("consultedEmployee", employe);
		model = this.employeDetailsContextConfiguration(model);

		return "employes/detail";
	}
	
	/**
	 * Recupère tous les employés, selon une pagination souhaitée
	 * 
	 * @param page le numero de la page souhaitée
	 * @param size nbre de référence dans la page
	 * @param sortProperty attribut souhaité pour le tri
	 * @param sortDirection ASC OU DESC
	 * @param model le contexte de la requete
	 * */
	@RequestMapping(value="", method = RequestMethod.GET, params = {"page", "size", "sortProperty", "sortDirection"})
	public 	String findAllEmployees(
			@RequestParam("page") Integer page,
			@RequestParam("size") Integer size,
			@RequestParam("sortProperty") String sortProperty,
			@RequestParam("sortDirection") String sortDirection,
			Map<String, Object> model) {
		model.put("nombreEmployes", employeService.countAllEmploye());
		Page<Employe> pageEmploye = employeService.findAllEmployes(page, size, sortProperty, sortDirection);
		model.put("employeeList", pageEmploye.getContent());
		model.put("listPageNumber", page);
		model.put("pageSize", size);
		model.put("pageSortProperty", sortProperty);
		model.put("pageSortDirection", sortDirection);
		return "employes/liste";
	}
	
	/**Acces au formulaire de creation commercial*/
	@RequestMapping(value="/new/commercial", method = RequestMethod.GET)
	public String getCommercialCreationForm(Map<String, Object> model) {
		model.put("commercialCreationMode", "enabled");
		model.put("nombreEmployes", employeService.countAllEmploye());
		return "employes/detail";
	}
	
	/**Acces au formulaire de creation technicien*/
	@RequestMapping(value="/new/technicien", method = RequestMethod.GET)
	public String getTechnicianCreationForm(Map<String, Object> model) {
		model.put("technicianCreationMode", "enabled");
		model.put("nombreEmployes", employeService.countAllEmploye());
		return "employes/detail";
	}
	
	/**Acces au formulaire de creation manager*/
	@RequestMapping(value="/new/manager", method = RequestMethod.GET)
	public String getManagerCreationForm(Map<String, Object> model) {
		model.put("managerCreationMode", "enabled");
		model.put("nombreEmployes", employeService.countAllEmploye());
		return "employes/detail";
	}
	
	/**
	 * Permet la suppression d'un employe / redirection vers la liste des employés
	 * 
	 * @param
	 * 		id de l'employe a supprimer
	 * @throws IOException 
	 *
	 * */
	@RequestMapping(value="/{id}/delete", method = RequestMethod.GET)
	public void deleteThisEmpoye(@PathVariable("id")Long id, Map<String, Object> model, HttpServletResponse response) throws IOException {
		// Control afin de savoir si l'employe est un manager : si ca en est un
		// on doit d'abord le désaffecter des techniciens affiliés pour ne pas violer de clef etrangère
		Manager manager = managerService.findOne(id);
		if (manager != null) {
			Set<Technicien> managerTechnicians = manager.getEquipe();
			Iterator<Technicien> iterator = managerTechnicians.iterator();
			
			while(iterator.hasNext()) {
				Technicien technicien = iterator.next();
				technicien.setManager(null);
				technicienService.updateThisTechnicien(technicien);
			}
		}
		
		employeService.deleteEmploye(id);
		
		model.put("nombreEmployes", employeService.countAllEmploye());
		
		response.sendRedirect("http://localhost:8080/employes?page=0&size=10&sortProperty=matricule&sortDirection=ASC");
	}
	
	/**
	 * Configure les critères communs à tous les ecran de consultation employe (detail.jsp)
	 * @model
	 * 		le scope de la requete, doit contenir un objet Employe avec une clef "consultedEmployee"
	 * */
	public Map<String, Object> employeDetailsContextConfiguration(Map<String, Object> model) {
		model.put("consultationMode","enabled");
		Employe employe = (Employe) model.get("consultedEmployee");
		String employeeClass = employe.getClass().getName();
		if (employeeClass.equals("com.ipiecoles.java.java320.model.Manager")) {
			model.put("employeeJob", "man");
		}
		else if (employeeClass.equals("com.ipiecoles.java.java320.model.Technicien")) {
			model.put("employeeJob", "tec");
		}
		else if (employeeClass.equals("com.ipiecoles.java.java320.model.Commercial")) {
			model.put("employeeJob", "com");
		}
		
		return model;
	}
}
