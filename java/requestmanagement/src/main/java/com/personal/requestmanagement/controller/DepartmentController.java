package com.personal.requestmanagement.controller;

import com.personal.requestmanagement.constant.CommonConst;
import com.personal.requestmanagement.model.dto.DepartmentDto;
import com.personal.requestmanagement.service.DepartmentService;
import com.personal.requestmanagement.utils.ThymeleafUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
    @Secured(CommonConst.ROLE_OPERATOR)
    public String list(Model model, @RequestParam(name = "code", required = false, defaultValue = "") String code, @RequestParam(name = "name", required = false, defaultValue = "") String name){
        ThymeleafUtil.insertContent(model, "fragments/department", "list", "Danh sách phòng ban", "Quản lý phòng ban");

        model.addAttribute("list", departmentService.findAllDto(code, name));
        model.addAttribute("code", code);
        model.addAttribute("name", name);

        return "index";
    }

    @GetMapping("/save")
    @Secured(CommonConst.ROLE_OPERATOR)
    public String save(Model model, DepartmentDto dto){
        String title = dto.getId() == 0 ? "Thêm mới phòng ban" : "Chỉnh sửa phòng ban";
    	ThymeleafUtil.insertContent(model, "fragments/department", "save", title, "Quản lý phòng ban");

    	if(dto.getId() != 0)
            dto = departmentService.getOneDto(dto.getId());
    	
    	model.addAttribute("dto", dto == null ? new DepartmentDto() : dto);
    	
    	
        return "index";
    }

    @PostMapping("/save")
    @Secured(CommonConst.ROLE_OPERATOR)
    public String doSave(Model model, @ModelAttribute @Valid DepartmentDto dto, Errors errors, RedirectAttributes redirAttrs){
        if(errors != null && errors.getErrorCount() == 0 && departmentService.save(dto)){
            ThymeleafUtil.successMessage(redirAttrs);
            return "redirect:/department";
        }

        if(errors != null && errors.getErrorCount() > 0){
    		ThymeleafUtil.errorMessages(model, errors);
    	} else ThymeleafUtil.errorMessage(model);
        
        return this.save(model, dto);
    }

    @GetMapping("/del")
    @Secured(CommonConst.ROLE_OPERATOR)
    public String delete(Model model, RedirectAttributes redirAttrs, @RequestParam(name = "id") Long id){
        id = id == null ? 0 : id;
        if(departmentService.remove(id))
            ThymeleafUtil.successMessage(redirAttrs);
        else
            ThymeleafUtil.errorMessage(redirAttrs);

        return "redirect:/department";
    }




}
