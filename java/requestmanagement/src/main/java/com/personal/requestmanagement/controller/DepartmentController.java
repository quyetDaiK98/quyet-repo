package com.personal.requestmanagement.controller;

import com.personal.requestmanagement.model.dto.DepartmentDto;
import com.personal.requestmanagement.service.DepartmentService;
import com.personal.requestmanagement.utils.ThymeleafUtil;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @RequestMapping("/department")
    public String list(Model model, @RequestParam(name = "code", required = false, defaultValue = "") String code, @RequestParam(name = "name", required = false, defaultValue = "") String name){
        ThymeleafUtil.insertContent(model, "fragments/department", "list", "Danh sách phòng ban", "Quản lý phòng ban");

        model.addAttribute("list", departmentService.findAllDto(code, name));
        model.addAttribute("code", code);
        model.addAttribute("name", name);
        return "index";
    }

    @RequestMapping(value = {"/saveDept", "/saveDept/{id}"}, method = RequestMethod.GET)
    public String save(Model model, @PathVariable Optional<Long> id){
        String title = id.orElse((long) 0) == 0 ? "Thêm mới phòng ban" : "Chỉnh sửa phòng ban";
    	ThymeleafUtil.insertContent(model, "fragments/department", "save", title, "Quản lý phòng ban");
    	
    	DepartmentDto dto = departmentService.getOneDto(id.orElse((long) 0));
    	
    	model.addAttribute("dto", dto == null ? new DepartmentDto() : dto);
    	
    	
        return "index";
    }

    @RequestMapping(value = {"/saveDept", "/saveDept/{id}"}, method = RequestMethod.POST)
    public String doSave(Model model, @Valid DepartmentDto dto, Errors errors){
        if(errors != null && errors.getErrorCount() == 0 && departmentService.save(dto)){

        }
        return "index";
    }




}
