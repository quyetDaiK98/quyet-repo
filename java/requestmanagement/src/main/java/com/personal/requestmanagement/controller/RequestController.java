package com.personal.requestmanagement.controller;

import com.personal.requestmanagement.constant.CommonConst;
import com.personal.requestmanagement.constant.PdfNote;
import com.personal.requestmanagement.model.dto.RequestDto;
import com.personal.requestmanagement.model.dto.UserDto;
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
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
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
    public String list(Model model, @Valid SearchRequest searchDto, Errors errors) {
        ThymeleafUtil.insertContent(model, "fragments/request", "list", "Danh sách đề nghị", "Đề nghị cá nhân");

        model.addAttribute("searchDto", searchDto);
        
        model.addAttribute("status", CommonConst.REQUEST_STATUS);

        model.addAttribute("type", CommonConst.REQUEST_TYPE);

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
    public String info(Model model, RedirectAttributes redirAttrs, @RequestParam(name = "type") Integer type, @RequestParam(name = "id") Long id) {
        if (type == 1)
            ThymeleafUtil.insertContent(model, "fragments/request", "info", "Chi tiết đề nghị xin nghỉ phép", "Chi tiết đề nghị");
        else if (type == 2)
            ThymeleafUtil.insertContent(model, "fragments/request", "matInfo", "Chi tiết đề nghị vật tư", "Chi tiết đề nghị");

        id = id == null ? 0 : id;
        RequestDto dto = requestService.findOneDto(id);
        model.addAttribute("dto", dto == null ? new RequestDto() : dto);
        if (dto != null)
            model.addAttribute("listMat", dto.getRequestMaterialDtos() == null ? new ArrayList<>() : dto.getRequestMaterialDtos());

        model.addAttribute("statusStr", dto != null ? dto.getStatusStr() : "");

        return "index";
    }

    @GetMapping("/del")
    @Secured(CommonConst.ROLE_EMP)
    public String delete(Model model, RedirectAttributes redirAttrs, @RequestParam(name = "id") Long id) {
        id = id == null ? 0 : id;
        if (requestService.changeStatus(Math.toIntExact(id), 6))
            ThymeleafUtil.successMessage(redirAttrs);
        else
            ThymeleafUtil.errorMessage(redirAttrs);

        return "redirect:/request";
    }

    @GetMapping("/leave")
    @Secured(CommonConst.ROLE_EMP)
    public String leave(Model model, RequestDto dto) {
        ThymeleafUtil.insertContent(model, "fragments/request", "leave", "Đề nghị xin nghỉ phép", "Đề nghị cá nhân");

        if (dto.getId() != 0)
            dto = requestService.findOneDto(dto.getId());

        model.addAttribute("dto", dto == null ? new RequestDto() : dto);

        return "index";
    }

    @PostMapping("/leave/save")
    @Secured(CommonConst.ROLE_EMP)
    public String leaveSave(Model model, @ModelAttribute @Valid RequestDto dto, Errors errors, RedirectAttributes redirAttrs) {
        if (errors != null && errors.getErrorCount() == 0 && requestService.save(dto) != null) {
            ThymeleafUtil.successMessage(redirAttrs);
            return "redirect:/request";
        }

        if (errors != null && errors.getErrorCount() > 0) {
            ThymeleafUtil.errorMessages(model, errors);
        } else ThymeleafUtil.errorMessage(model);
        dto.setStatus(0);
        return this.leave(model, dto);

    }

    @GetMapping("/mat")
    @Secured(CommonConst.ROLE_EMP)
    public String mat(Model model, RequestDto dto) {
        ThymeleafUtil.insertContent(model, "fragments/request", "mat", "Đề nghị vật tư", "Đề nghị cá nhân");

        if (dto.getId() != 0)
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
        if (requestDto != null) {
            Template template = new Template();
            template.setDtos(requestDto.getRequestMaterialDtos());
            List<PdfObject> lst = new ArrayList<>();
            lst.add(new PdfObject(PdfNote.CREATEDDATE.getValue(), DateUtil.convertDatetoString(DateUtil.now(), DateUtil.FORMAT_DATE_YYYY_MM_DD_HH_mm_ss)));
            lst.add(new PdfObject(PdfNote.USERNAME.getValue(), userDto.getUserName()));
            lst.add(new PdfObject(PdfNote.DEPTNAME.getValue(), userDto.getDepartment().getDeptName()));
            lst.add(new PdfObject(PdfNote.REASON.getValue(), requestDto.getReason()));

            template.setData(lst);
            //Write text to Pdf template
            String strDate = DateUtil.convertDatetoString(DateUtil.now(), DateUtil.FORMAT_YMDHM);
            String filePath = CommonConst.DOC_STORE_PATH + "\\" + userDto.getUserName() + "\\temp.pdf";
            PdfUtil.copyFile(CommonConst.TEMPLATE_PATH, filePath);
            File filePdf = new File(filePath);
            PdfUtil.fillTemplate(filePdf, template);
            return String.valueOf(CommonConst.SUCCESS_ACTION_CODE);
        }

        return String.valueOf(CommonConst.ERROR_ACTION_CODE);
    }

    @ResponseBody
    @GetMapping("changeStatus/{reqId}/{status}")
    public String changeStatus(@PathVariable("reqId") int reqId, @PathVariable("status") int status) throws IOException {
        if (requestService.changeStatus(reqId, status)) {
            UserDto userDto = new UserDto(userService.getCurrentUser());

            if (!userDto.getRoleCode().equals(CommonConst.ROLE_EMP)) {
                RequestDto requestDto = requestService.findOneDto(reqId);

                File signFile = new File(CommonConst.DOC_STORE_PATH + "\\" + requestDto.getUser().getUserName() + "\\temp.pdf");
                File signImage = new File(CommonConst.DOC_STORE_PATH + "\\" + userDto.getUserName() + "\\" + userDto.getSign());

                if (userDto.getRoleCode().equals(CommonConst.ROLE_OPERATOR))
                    PdfUtil.signPdf(signFile, "operator", signImage);
                else if (userDto.getRoleCode().equals(CommonConst.ROLE_MANAGER))
                    PdfUtil.signPdf(signFile, "manager", signImage);
            }
            return String.valueOf(CommonConst.SUCCESS_ACTION_CODE);
        }

        return String.valueOf(CommonConst.ERROR_ACTION_CODE);
    }

    @ResponseBody
    @GetMapping("/download/{reqId}")
    public ResponseEntity<InputStreamResource> download(HttpServletRequest request, @PathVariable("reqId") int reqId) throws IOException {
        HttpHeaders responseHeader = new HttpHeaders();
        try {
            RequestDto requestDto = requestService.findOneDto(reqId);
            File file = new File(CommonConst.DOC_STORE_PATH + "\\" + requestDto.getUser().getUserName() + "\\temp.pdf");
            byte[] data = FileUtils.readFileToByteArray(file);
            // Set mimeType trả về
            responseHeader.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // Thiết lập thông tin trả về
            responseHeader.set("Content-disposition", "attachment; filename=" + file.getName());
            responseHeader.setContentLength(data.length);
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
            return new ResponseEntity<InputStreamResource>(inputStreamResource, responseHeader, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<InputStreamResource>(null, responseHeader, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
