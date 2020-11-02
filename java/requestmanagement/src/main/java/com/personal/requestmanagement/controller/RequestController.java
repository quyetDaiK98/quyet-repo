package com.personal.requestmanagement.controller;

import com.personal.requestmanagement.constant.CommonConst;
import com.personal.requestmanagement.model.dto.RequestDto;
import com.personal.requestmanagement.model.search.SearchRequest;
import com.personal.requestmanagement.service.RequestService;
import com.personal.requestmanagement.utils.ThymeleafUtil;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/request")
public class RequestController {
	
	@Autowired
	RequestService requestService;
	
    @GetMapping("")
    public String list(Model model, SearchRequest searchDto){
        ThymeleafUtil.insertContent(model, "fragments/request", "list", "Danh sách đề nghị", "Đề nghị cá nhân");

        model.addAttribute("status", CommonConst.REQUEST_STATUS);
        
        model.addAttribute("searchDto", searchDto);

        List<RequestDto> list = requestService.getAllDto(searchDto);
        model.addAttribute("list", list);

        return "index";
    }

    @GetMapping("/leave")
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

        ThymeleafUtil.errorMessages(model, errors);
        return this.leave(model, dto);

    }
}
