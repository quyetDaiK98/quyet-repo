package com.personal.requestmanagement.controller;

import com.personal.requestmanagement.model.entity.User;
import com.personal.requestmanagement.service.UserService;
import com.personal.requestmanagement.utils.ThymeleafUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	@RequestMapping({"/", "/index"})
	public String home(HttpSession session,Model model) {
		ThymeleafUtil.insertContent(model,"layout", "content", "Dashboard", "Dashboard");

		User user = userService.getCurrentUser();
		session.setAttribute("user", user);



		return "index";
	}
	
	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}
}
