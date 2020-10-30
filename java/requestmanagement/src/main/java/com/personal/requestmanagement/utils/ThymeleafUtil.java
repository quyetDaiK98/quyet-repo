package com.personal.requestmanagement.utils;

import com.google.gson.Gson;
import com.personal.requestmanagement.constant.CommonConst;
import com.personal.requestmanagement.model.Message;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


public class ThymeleafUtil {

	public static void insertContent(Model model, String fragPath, String fragment, String title, String pageTitle) {
		model.addAttribute("fragPath", fragPath);

		model.addAttribute("fragment", fragment);

		model.addAttribute("title", title);

		model.addAttribute("pageTitle", pageTitle);
	}

	public static void errorMessages(Model model, Errors errors){
		if(errors != null && errors.getErrorCount() > 0){
			List<Message> messages = new ArrayList<>();
			for (ObjectError error : errors.getAllErrors()) {
				Message message = new Message(CommonConst.ERROR_FORM, error.getDefaultMessage());
				messages.add(message);
			}
			model.addAttribute("message", new Gson().toJson(messages));
		} else
			errorMessage(model);
	}

	public static void errorMessage(Model model){
		model.addAttribute("message", new Gson().toJson(new Message(CommonConst.ERROR_ACTION_CODE, CommonConst.ERROR_ACTION_MESS)));
	}

	public static void errorMessage(RedirectAttributes redirAttrs){
		redirAttrs.addFlashAttribute("message", new Gson().toJson(new Message(CommonConst.ERROR_ACTION_CODE, CommonConst.ERROR_ACTION_MESS)));
	}

	public static void successMessage(Model model){
		model.addAttribute("message", new Gson().toJson(new Message(CommonConst.SUCCESS_ACTION_CODE, CommonConst.SUCCESS_ACTION_MESS)));
	}

	public static void successMessage(RedirectAttributes redirAttrs){
		redirAttrs.addFlashAttribute("message", new Gson().toJson(new Message(CommonConst.SUCCESS_ACTION_CODE, CommonConst.SUCCESS_ACTION_MESS)));
	}
}
