package com.haidarvm.indekskepuasan.restControllers;

import com.haidarvm.indekskepuasan.model.Department;
import com.haidarvm.indekskepuasan.repositories.DepartmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class DepartmentRestController {

    private final DepartmentRepository departmentRepository;


    public DepartmentRestController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @PutMapping("/department")
    Department updateDepartment(@RequestBody Department newDepartment) {
        Long deptId = newDepartment.getId();
        return departmentRepository.findById(deptId)
                .map(department -> {
                    department.setDeviceId(newDepartment.getDeviceId());
                    department.setAvailable(newDepartment.getAvailable());
                    return departmentRepository.save(department);
                })
                .orElseGet(() -> {
                    newDepartment.setId(deptId);
                    return departmentRepository.save(newDepartment);
                });
    }

    @GetMapping("/department")
    List<Department> selectAvailableDepartment() {
        return departmentRepository.findByAvailable(1);
    }

    @GetMapping("/department/{departmentId}")
    List<Department> selectDepartmentId(@PathVariable Long departmentId) {
        return departmentRepository.findAllById(departmentId);
    }


}
