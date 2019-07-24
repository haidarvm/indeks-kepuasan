package com.haidarvm.indekskepuasan.repositories;

import com.haidarvm.indekskepuasan.model.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Long> {

    List<Department> findByAvailable(Integer available);

}
