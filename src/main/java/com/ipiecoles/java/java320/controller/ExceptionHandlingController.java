package com.ipiecoles.java.java320.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import exception.MyNotFoundException;

@Controller
public class ExceptionHandlingController {
	
	@ExceptionHandler(MyNotFoundException.class)
	public String Error() {
		return "erreur";
	}
}
