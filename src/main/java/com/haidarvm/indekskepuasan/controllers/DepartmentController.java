package com.haidarvm.indekskepuasan.controllers;


import com.haidarvm.indekskepuasan.model.Department;
import com.haidarvm.indekskepuasan.repositories.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    private static final String VIEWS_DEPARTMENT_CREATE_OR_UPDATE_FORM = "department/form";

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    private final DepartmentRepository departmentRepository;
    //    private final StorageService storageService;
    private static String BASE_URL = System.getProperty("user.dir");
    private static String UPLOADED_FOLDER = BASE_URL + "/dept/";

    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @RequestMapping({"", "index"})
    public String listDepartment(Model model) {
        model.addAttribute("departments", departmentRepository.findAll());
//        logger.debug("mana yaa Allah Ryzen 3900x  terbaik please yaa sangat puas yaa Allah {}", departmentRepository.findById(1L).get().getTextService());
        return "department/index";
    }


    @GetMapping("/insert")
    public String insertDeaprtment(Model model) {
        model.addAttribute("department", Department.builder().build());
        return "department/form-insert";
    }

    @PostMapping("/insert")
    public String processInsertDepartment(@Valid Department department, BindingResult result) {
        if (result.hasErrors()) {
            return "department/form-insert";
        } else {
            Department savedDepartment = departmentRepository.save(department);
            return "redirect:/department";
        }
    }

    @GetMapping("update/{departmentId}")
    public String getDepartmentForm(@PathVariable Long departmentId, Model model, HttpServletRequest request) {
        model.addAttribute("department", departmentRepository.findById(departmentId));
        model.addAttribute("id", departmentId);
        String baseUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + "/dept";
//        System.out.println("baseurl =" + baseUrl);
        model.addAttribute("baseurl", baseUrl);
        return "department/form-update";
    }

    @PostMapping("update/{departmentId}")
    public String processUpdateDepartmentForm(@Valid Department department, BindingResult result, @PathVariable Long departmentId, MultipartHttpServletRequest multipartRequest, RedirectAttributes redirectAttributes) throws IOException {
        if (result.hasErrors()) {
            return "department/form-update";
        } else {
                Iterator<String> it = multipartRequest.getFileNames();

                while (it.hasNext()) {
                    String fileControlName = it.next();
                    MultipartFile srcFile = multipartRequest.getFile(fileControlName);
                    if(srcFile.getSize() > 1) {
                        String uploadName = srcFile.getName();
                        logger.debug("Upload filename {}", uploadName);
                        String satisfaction = "satisfy".equals(uploadName) ? "satisfy/" : "dissatisfy/";
                        String destFilePath = UPLOADED_FOLDER + satisfaction + departmentId + ".png";
                        File destFile = new File(destFilePath);
                        srcFile.transferTo(destFile);
                    }
                }

            department.setId(departmentId);
            departmentRepository.save(department);
            return "redirect:/department";
        }
    }


}
