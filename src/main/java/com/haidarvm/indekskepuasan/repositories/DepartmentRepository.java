package com.haidarvm.indekskepuasan.repositories;

import com.haidarvm.indekskepuasan.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "score", path = "api/department")
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findByAvailable(Integer available);

    List<Department> findAllById(Long id);
}
