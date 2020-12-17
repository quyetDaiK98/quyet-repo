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
    @Secured(CommonConst.ROLE_EMP)
    public String list(Model model, @Valid SearchRequest searchDto, Errors errors){
        ThymeleafUtil.insertContent(model, "fragments/request", "list", "Danh sách đề nghị", "Đề nghị cá nhân");

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
        model.addAttribute("statusStr", dto != null ? dto.getStatusStr() : "");
        return this.leave(model, dto);
    }

    @GetMapping("/del")
    @Secured(CommonConst.ROLE_EMP)
    public String delete(Model model, RedirectAttributes redirAttrs, @RequestParam(name = "id") Long id){
        id = id == null ? 0 : id;
        if(requestService.remove(id))
            ThymeleafUtil.successMessage(redirAttrs);
        else
            ThymeleafUtil.errorMessage(redirAttrs);

        return "redirect:/request";
    }
    
    @GetMapping("/leave")
    @Secured(CommonConst.ROLE_EMP)
    public String leave(Model model, RequestDto dto){
        ThymeleafUtil.insertContent(model, "fragments/request", "leave", "Đề nghị xin nghỉ phép", "Đề nghị cá nhân");

        if(dto.getId() != 0)
            dto = requestService.findOneDto(dto.getId());

    	model.addAttribute("dto", dto == null ? new RequestDto() : dto);

        return "index";
    }
    
    @PostMapping("/leave/save")
    @Secured(CommonConst.ROLE_EMP)
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

    @ResponseBody
    @GetMapping("changeStatus/{reqId}/{status}")
    public String changeStatus(@PathVariable("reqId") int reqId, @PathVariable("status") int status){
        if(requestService.changeStatus(reqId, status))
            return String.valueOf(CommonConst.SUCCESS_ACTION_CODE);

        return String.valueOf(CommonConst.ERROR_ACTION_CODE);
    }
    
}
