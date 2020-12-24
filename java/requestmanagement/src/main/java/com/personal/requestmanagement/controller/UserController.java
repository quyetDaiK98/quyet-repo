package com.personal.requestmanagement.controller;

import com.personal.requestmanagement.constant.CommonConst;
import com.personal.requestmanagement.model.dto.UserDto;
import com.personal.requestmanagement.repository.UserRepository;
import com.personal.requestmanagement.service.DepartmentService;
import com.personal.requestmanagement.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    DepartmentService departmentService;

    @GetMapping("")
    @Secured(CommonConst.ROLE_OPERATOR)
    public String list(Model model, @RequestParam(name = "deptId", required = false) Long deptId, @RequestParam(name = "userName", required = false, defaultValue = "") String userName){
        ThymeleafUtil.insertContent(model, "fragments/user", "list", "Danh sách người dùng", "Quản lý người dùng");
        if(deptId == null)
            deptId = (long) 0;
        model.addAttribute("list", userService.findAllDto(deptId, userName));
        model.addAttribute("deptId", deptId);
        model.addAttribute("userName", userName);
        model.addAttribute("listDept", departmentService.findAllDto("", ""));

        return "index";
    }

    @GetMapping("/save")
    @Secured(CommonConst.ROLE_OPERATOR)
    public String save(Model model, UserDto dto){
        String title = dto.getId() == 0 ? "Thêm mới người dùng" : "Chỉnh sửa người dùng";
        ThymeleafUtil.insertContent(model, "fragments/user", "save", title, "Quản lý người dùng");

        if(dto.getId() != 0)
            dto = userRepository.findOneDto(dto.getId());

        model.addAttribute("dto", dto == null ? new UserDto() : dto);


        return "index";
    }

    @PostMapping("/save")
    @Secured(CommonConst.ROLE_OPERATOR)
    public String doSave(Model model, @ModelAttribute @Valid UserDto dto, Errors errors, RedirectAttributes redirAttrs){
        if(errors != null && errors.getErrorCount() == 0 && userService.save(dto)){
            ThymeleafUtil.successMessage(redirAttrs);
            return "redirect:/user";
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
        try{
            userRepository.deleteById(id);
            ThymeleafUtil.successMessage(redirAttrs);
            return "redirect:/user";
        } catch (Exception ex){
            ex.printStackTrace();
        }
            
        ThymeleafUtil.errorMessage(redirAttrs);
        return "redirect:/user";
    }
}
