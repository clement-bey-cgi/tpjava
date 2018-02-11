package com.ipiecoles.java.java320.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/printerror")
public class ErreurController {
	
	/**
	 * Sert de cible pour les redirection. 
	 * La requete transmise contient un attribut "messageErreur"
	 * qui sera affichée dans la vue
	 * */
	@RequestMapping("")
	public String printError() {
		return "erreur";
	}
}
