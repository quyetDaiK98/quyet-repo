package com.personal.requestmanagement.utils;

import org.springframework.ui.Model;


public class ThymeleafUtil {

	public static void insertContent(Model model, String contFrag) {
		model.addAttribute("fragPath","fragments");

		model.addAttribute("contFrag",contFrag);


	}
}
