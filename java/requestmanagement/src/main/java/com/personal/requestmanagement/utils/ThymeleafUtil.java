package com.personal.requestmanagement.utils;

import org.springframework.ui.Model;


public class ThymeleafUtil {

	public static void insertContent(Model model, String fragPath, String fragment, String title, String pageTitle) {
		model.addAttribute("fragPath", fragPath);

		model.addAttribute("fragment", fragment);

		model.addAttribute("title", title);

		model.addAttribute("pageTitle", pageTitle);
	}
}
