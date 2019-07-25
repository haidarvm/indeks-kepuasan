package com.haidarvm.indekskepuasan.restControllers;

import com.haidarvm.indekskepuasan.model.Department;
import com.haidarvm.indekskepuasan.repositories.DepartmentRepository;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class DepartmentRestController {

    private final DepartmentRepository departmentRepository;


    public DepartmentRestController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @PutMapping("/department/{id}")
    Department updateDepartment(@RequestBody Department newDepartment, @PathVariable Long id) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setDeviceId(newDepartment.getDeviceId());
                    department.setAvailable(newDepartment.getAvailable());
                    return departmentRepository.save(department);
                })
                .orElseGet(() -> {
                    newDepartment.setId(id);
                    return departmentRepository.save(newDepartment);
                });
    }

}
