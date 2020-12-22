package com.personal.requestmanagement.controller;

import com.personal.requestmanagement.constant.CommonConst;
import com.personal.requestmanagement.model.dto.RequestDto;
import com.personal.requestmanagement.model.entity.Role;
import com.personal.requestmanagement.model.entity.User;
import com.personal.requestmanagement.model.search.SearchRequest;
import com.personal.requestmanagement.service.RequestService;
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
import java.util.List;

@Controller
@RequestMapping("/confirmRequest")
public class ConfirmRequestController {
    @Autowired
    RequestService requestService;

    @Autowired
    UserService userService;

    @GetMapping("")
    @Secured(CommonConst.ROLE_MANAGER)
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

    @GetMapping("/save")
    @Secured(CommonConst.ROLE_MANAGER)
    public String confirmLeave(Model model, RequestDto dto, @RequestParam(name = "id") Long id){
        ThymeleafUtil.insertContent(model, "fragments/confirmRequest", "leave", "Đề nghị xin nghỉ phép", "Đề nghị phê duyệt");

        if(id != null)
            dto = requestService.findOneDto(id);

        model.addAttribute("dto", dto == null ? new RequestDto() : dto);

        return "index";
    }

    @PostMapping("/doSave")
    @Secured(CommonConst.ROLE_MANAGER)
    public String leaveSave(Model model, @ModelAttribute @Valid RequestDto dto, Errors errors, RedirectAttributes redirAttrs){
        if(errors != null && errors.getErrorCount() == 0 && requestService.save(dto) != null){
            ThymeleafUtil.successMessage(redirAttrs);
            return "redirect:/request";
        }

        if(errors != null && errors.getErrorCount() > 0){
            ThymeleafUtil.errorMessages(model, errors);
        } else ThymeleafUtil.errorMessage(model);
        dto.setStatus(0);
        return this.confirmLeave(model, dto, null);
    }
}
