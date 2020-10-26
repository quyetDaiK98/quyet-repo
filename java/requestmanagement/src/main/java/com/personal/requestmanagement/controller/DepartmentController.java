package com.personal.requestmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DepartmentController {

    @RequestMapping("/department")
    public String list(){
        return "department/list";
    }
}
