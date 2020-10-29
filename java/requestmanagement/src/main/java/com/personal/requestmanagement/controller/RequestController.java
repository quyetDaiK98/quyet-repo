package com.personal.requestmanagement.controller;

import com.personal.requestmanagement.service.UserService;
import com.personal.requestmanagement.utils.ThymeleafUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/request")
public class RequestController {

    @GetMapping("")
    public String list(Model model, @RequestParam(name = "username", required = false, defaultValue = "") String username, @RequestParam(name = "email", required = false, defaultValue = "") String email){
        ThymeleafUtil.insertContent(model, "fragments/request", "list", "Danh sách đề nghị", "Danh sách đề nghị");

//        model.addAttribute("list", userService.findAllDto(code, name));
//        model.addAttribute("code", code);
//        model.addAttribute("name", name);

        return "index";
    }
}
