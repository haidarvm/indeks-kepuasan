package com.haidarvm.indekskepuasan.controllers;


import com.haidarvm.indekskepuasan.Services.StorageService;
import com.haidarvm.indekskepuasan.model.Department;
import com.haidarvm.indekskepuasan.repositories.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    private static final String VIEWS_DEPARTMENT_CREATE_OR_UPDATE_FORM = "department/form";

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    private final DepartmentRepository departmentRepository;
    private final StorageService storageService;


    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository, StorageService storageService) {
        this.departmentRepository = departmentRepository;
        this.storageService = storageService;
    }

    @RequestMapping({"", "index"})
    public String listDepartment(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
        logger.debug("mana yaa Allah Ryzen 3900x  terbaik please yaa sangat puas yaa Allah {}", departmentRepository.findById(1L).get().getTextService());
        return "department/index";
    }


    @GetMapping("/insert")
    public String insertDeaprtment(Model model) {
        model.addAttribute("department", Department.builder().build());
        return "department/form-insert";
    }

    @PostMapping("/insert")
    public String processInsertDepartment(@Valid Department department, BindingResult result) {
        if(result.hasErrors()) {
            return "department/form-insert";
        } else {
            Department savedDepartment = departmentRepository.save(department);
            return "redirect:/department";
        }
    }

    @GetMapping("update/{departmentId}")
    public String getDepartmentForm(@PathVariable Long departmentId, Model model) {
        model.addAttribute("department", departmentRepository.findById(departmentId));
        return "department/form-update";
    }

    @PostMapping("update/{departmentId}")
    public String processUpdateDepartmentForm(@Valid Department department, BindingResult result, @PathVariable Long departmentId, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "department/form-update";
        } else {
            storageService.store(file);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
            department.setId(departmentId);
            departmentRepository.save(department);
            return "redirect:/department";
        }
    }


}
