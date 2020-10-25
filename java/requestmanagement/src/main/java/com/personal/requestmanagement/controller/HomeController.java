package com.personal.requestmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping({"/", "/index"})
	public String home(Model model) {
		model.addAttribute("test", "quyetDaiK");
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}
}
