package com.personal.requestmanagement.controller;

import com.personal.requestmanagement.model.dto.DepartmentDto;
import com.personal.requestmanagement.service.DepartmentService;
import com.personal.requestmanagement.utils.ThymeleafUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("")
    public String list(Model model, @RequestParam(name = "code", required = false, defaultValue = "") String code, @RequestParam(name = "name", required = false, defaultValue = "") String name){
        ThymeleafUtil.insertContent(model, "fragments/department", "list", "Danh sách phòng ban", "Quản lý phòng ban");

        model.addAttribute("list", departmentService.findAllDto(code, name));
        model.addAttribute("code", code);
        model.addAttribute("name", name);
        return "index";
    }

    @GetMapping("/save")
    public String save(Model model, DepartmentDto dto){
        String title = dto.getId() == 0 ? "Thêm mới phòng ban" : "Chỉnh sửa phòng ban";
    	ThymeleafUtil.insertContent(model, "fragments/department", "save", title, "Quản lý phòng ban");

    	if(dto.getId() != 0)
            dto = departmentService.getOneDto(dto.getId());
    	
    	model.addAttribute("dto", dto == null ? new DepartmentDto() : dto);
    	
    	
        return "index";
    }

    @PostMapping("/save")
    public String doSave(Model model, @ModelAttribute @Valid DepartmentDto dto, Errors errors, RedirectAttributes redirAttrs){
        if(errors != null && errors.getErrorCount() == 0 && departmentService.save(dto)){
            ThymeleafUtil.successMessage(redirAttrs);
            return "redirect:/department";
        }

        ThymeleafUtil.errorMessages(model, errors);
        return this.save(model, dto);
    }




}
