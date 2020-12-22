package com.personal.requestmanagement.controller;

import com.personal.requestmanagement.constant.CommonConst;
import com.personal.requestmanagement.constant.PdfNote;
import com.personal.requestmanagement.model.dto.RequestDto;
import com.personal.requestmanagement.model.dto.UserDto;
import com.personal.requestmanagement.model.entity.Material;
import com.personal.requestmanagement.model.entity.Role;
import com.personal.requestmanagement.model.entity.User;
import com.personal.requestmanagement.model.pdf.PdfObject;
import com.personal.requestmanagement.model.pdf.Template;
import com.personal.requestmanagement.model.search.SearchRequest;
import com.personal.requestmanagement.repository.MaterialRepository;
import com.personal.requestmanagement.repository.RequestMaterialRepo;
import com.personal.requestmanagement.service.RequestService;
import com.personal.requestmanagement.service.UserService;
import com.personal.requestmanagement.utils.DateUtil;
import com.personal.requestmanagement.utils.PdfUtil;
import com.personal.requestmanagement.utils.ThymeleafUtil;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/request")
public class RequestController {
	
	@Autowired
	RequestService requestService;

	@Autowired
    UserService userService;

	@Autowired
    MaterialRepository materialRepository;

	@Autowired
    RequestMaterialRepo requestMaterialRepo;
	
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
        ThymeleafUtil.insertContent(model, "fragments/request", "info", "Chi tiết đề nghị xin nghỉ phép", "Chi tiết đề nghị");

        id = id == null ? 0 : id;
        RequestDto dto = requestService.findOneDto(id);
        model.addAttribute("dto", dto == null ? new RequestDto() : dto);

        model.addAttribute("statusStr", dto != null ? dto.getStatusStr() : "");

        return "index";
    }

    @GetMapping("/del")
    @Secured(CommonConst.ROLE_EMP)
    public String delete(Model model, RedirectAttributes redirAttrs, @RequestParam(name = "id") Long id){
        id = id == null ? 0 : id;
        if(requestService.changeStatus(Math.toIntExact(id), 6))
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
    	if(errors != null && errors.getErrorCount() == 0 && requestService.save(dto) != null){
            ThymeleafUtil.successMessage(redirAttrs);
            return "redirect:/request";
        }

    	if(errors != null && errors.getErrorCount() > 0){
    		ThymeleafUtil.errorMessages(model, errors);
    	} else ThymeleafUtil.errorMessage(model);
        dto.setStatus(0);
        return this.leave(model, dto);

    }

    @GetMapping("/mat")
    @Secured(CommonConst.ROLE_EMP)
    public String mat(Model model, RequestDto dto){
        ThymeleafUtil.insertContent(model, "fragments/request", "mat", "Đề nghị vật tư", "Đề nghị cá nhân");

        if(dto.getId() != 0)
            dto = requestService.findOneDto(dto.getId());

        if (dto != null) {
            model.addAttribute("listMat", dto.getRequestMaterialDtos() == null ? new ArrayList<>() : dto.getRequestMaterialDtos());
            model.addAttribute("status", dto == null ? 0 : dto.getStatus());
            model.addAttribute("list", materialRepository.findAllDto());
        }

        model.addAttribute("dto", dto == null ? new RequestDto() : dto);

        return "index";
    }

    @PostMapping("/mat/save")
    @ResponseBody
    public String matSave(@RequestBody RequestDto param) throws IOException {
        UserDto userDto = new UserDto(userService.getCurrentUser());
        param.setUser(userDto);
        RequestDto requestDto = requestService.save(param);
        if(requestDto != null){
            Template template = new Template();
            template.setDtos(requestMaterialRepo.findAllDto(requestDto.getId()));
            List<PdfObject> lst = new ArrayList<>();
            lst.add(new PdfObject(PdfNote.CREATEDDATE.getValue(), DateUtil.convertDatetoString(DateUtil.now(), DateUtil.FORMAT_DATE_YYYY_MM_DD_HH_mm_ss)));
            lst.add(new PdfObject(PdfNote.USERNAME.getValue(), userDto.getUserName()));
            lst.add(new PdfObject(PdfNote.DEPTNAME.getValue(), userDto.getDepartment().getDeptName()));
            lst.add(new PdfObject(PdfNote.REASON.getValue(), requestDto.getReason()));

            template.setData(lst);
            //Write text to Pdf template
            String strDate = DateUtil.convertDatetoString(DateUtil.now(), DateUtil.FORMAT_YMDHM);
            String templateFile = CommonConst.TEMPLATE_PATH;
            String filePath =  CommonConst.DOC_STORE_PATH + "\\" + userDto.getUserName() + "\\temp.pdf";
            PdfUtil.copyFile(CommonConst.TEMPLATE_PATH, filePath);
            File filePdf = new File(templateFile);
            PdfUtil.fillTemplate(filePdf, template);
            return String.valueOf(CommonConst.SUCCESS_ACTION_CODE);
        }

        return String.valueOf(CommonConst.ERROR_ACTION_CODE);
    }

    @ResponseBody
    @GetMapping("changeStatus/{reqId}/{status}")
    public String changeStatus(@PathVariable("reqId") int reqId, @PathVariable("status") int status){
        if(requestService.changeStatus(reqId, status))
            return String.valueOf(CommonConst.SUCCESS_ACTION_CODE);

        return String.valueOf(CommonConst.ERROR_ACTION_CODE);
    }
    
}
