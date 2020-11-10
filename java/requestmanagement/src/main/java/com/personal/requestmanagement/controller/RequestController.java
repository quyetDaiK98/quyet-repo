package com.personal.requestmanagement.controller;

import com.personal.requestmanagement.constant.CommonConst;
import com.personal.requestmanagement.model.dto.RequestDto;
import com.personal.requestmanagement.model.entity.Role;
import com.personal.requestmanagement.model.entity.User;
import com.personal.requestmanagement.model.search.SearchRequest;
import com.personal.requestmanagement.service.RequestService;
import com.personal.requestmanagement.service.UserService;
import com.personal.requestmanagement.utils.ThymeleafUtil;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/request")
public class RequestController {
	
	@Autowired
	RequestService requestService;

	@Autowired
    UserService userService;
	
    @GetMapping("")
    public String list(Model model, @Valid SearchRequest searchDto, Errors errors){
        ThymeleafUtil.insertContent(model, "fragments/request", "list", "Danh sách đề nghị", "Đề nghị cá nhân");

        model.addAttribute("status", CommonConst.REQUEST_STATUS);

        model.addAttribute("type", CommonConst.REQUEST_TYPE);

        model.addAttribute("searchDto", searchDto);

        User user = userService.getCurrentUser();

        searchDto.setRole(((Role) user.getRoles().toArray()[0]).getRoleCode());
        searchDto.setUserId(user.getId());
        searchDto.setDeptId(user.getDepartment() != null ? user.getDepartment().getId() : 0);

        List<RequestDto> list = requestService.getAllDto(searchDto);
        model.addAttribute("list", list);
        ThymeleafUtil.errorMessages(model, errors);
        return "index";
    }

    @GetMapping("/info")
    public String info(Model model, RedirectAttributes redirAttrs, @RequestParam(name = "id") Long id){
        id = id == null ? 0 : id;
        RequestDto dto = requestService.findOneDto(id);
        return this.leave(model, dto);
    }

    @GetMapping("/del")
    public String delete(Model model, RedirectAttributes redirAttrs, @RequestParam(name = "id") Long id){
        id = id == null ? 0 : id;
        if(requestService.remove(id))
            ThymeleafUtil.successMessage(redirAttrs);
        else
            ThymeleafUtil.errorMessage(redirAttrs);

        return "redirect:/request";
    }
    
    @GetMapping("/leave")
    @Secured("ROLE_EMP")
    public String leave(Model model, RequestDto dto){
        ThymeleafUtil.insertContent(model, "fragments/request", "leave", "Đề nghị xin nghỉ phép", "Đề nghị cá nhân");

        if(dto.getId() != 0)
            dto = requestService.findOneDto(dto.getId());

    	model.addAttribute("dto", dto == null ? new RequestDto() : dto);

        return "index";
    }
    
    @PostMapping("/leave/save")
    public String leaveSave(Model model, @ModelAttribute @Valid RequestDto dto, Errors errors, RedirectAttributes redirAttrs){
    	if(errors != null && errors.getErrorCount() == 0 && requestService.save(dto)){
            ThymeleafUtil.successMessage(redirAttrs);
            return "redirect:/request";
        }
    	
    	if(errors != null && errors.getErrorCount() > 0){
    		ThymeleafUtil.errorMessages(model, errors);
    	} else ThymeleafUtil.errorMessage(model);
        dto.setStatus(0);
        return this.leave(model, dto);

    }
    
    @GetMapping("/manager")
    public String list4Manager(Model model, @Valid SearchRequest searchDto, Errors errors){
        ThymeleafUtil.insertContent(model, "fragments/confirmRequest", "list", "Danh sách đề nghị", "Đề nghị phê duyệt");

        model.addAttribute("status", CommonConst.REQUEST_STATUS);

        model.addAttribute("type", CommonConst.REQUEST_TYPE);

        model.addAttribute("searchDto", searchDto);

        User user = userService.getCurrentUser();

        searchDto.setRole(((Role) user.getRoles().toArray()[0]).getRoleCode());
        searchDto.setUserId(user.getId());
        searchDto.setDeptId(user.getDepartment() != null ? user.getDepartment().getId() : 0);

        List<RequestDto> list = requestService.getAllDto(searchDto);
        model.addAttribute("list", list);
        ThymeleafUtil.errorMessages(model, errors);
        return "index";
    }
    
    @GetMapping("/operator")
    public String list4Operator(Model model, @Valid SearchRequest searchDto, Errors errors){
        ThymeleafUtil.insertContent(model, "fragments/processRequest", "list", "Danh sách đề nghị", "Đề nghị xử lý");

        model.addAttribute("status", CommonConst.REQUEST_STATUS);

        model.addAttribute("type", CommonConst.REQUEST_TYPE);

        model.addAttribute("searchDto", searchDto);

        User user = userService.getCurrentUser();

        searchDto.setRole(((Role) user.getRoles().toArray()[0]).getRoleCode());
        searchDto.setUserId(user.getId());
        searchDto.setDeptId(user.getDepartment() != null ? user.getDepartment().getId() : 0);

        List<RequestDto> list = requestService.getAllDto(searchDto);
        model.addAttribute("list", list);
        ThymeleafUtil.errorMessages(model, errors);
        return "index";
    }

}
