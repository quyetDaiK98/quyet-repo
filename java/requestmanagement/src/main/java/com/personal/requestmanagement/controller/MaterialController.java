package com.personal.requestmanagement.controller;


import com.personal.requestmanagement.model.dto.MaterialDto;
import com.personal.requestmanagement.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/material")
public class MaterialController {
    @Autowired
    MaterialRepository materialRepository;

    @ResponseBody
    @GetMapping("/get/{id}")
    public MaterialDto matSave(@PathVariable("id") int id) throws IOException {

        return materialRepository.findDtoById(id);
    }
}
