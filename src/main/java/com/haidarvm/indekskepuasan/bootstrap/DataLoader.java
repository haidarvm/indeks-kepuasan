package com.haidarvm.indekskepuasan.bootstrap;

import com.haidarvm.indekskepuasan.model.Department;
import com.haidarvm.indekskepuasan.repositories.DepartmentRepository;
import com.haidarvm.indekskepuasan.repositories.ScoreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {


    private final DepartmentRepository departmentRepository;
    private final ScoreRepository scoreRepository;


    public DataLoader(DepartmentRepository departmentRepository, ScoreRepository scoreRepository) {
        this.departmentRepository = departmentRepository;
        this.scoreRepository = scoreRepository;
    }

    private void loadData(){
        Department gigi = new Department();
        gigi.setName("Layanan Gigi");
        Department savedGigi = departmentRepository.save(gigi);

        Department kia = new Department();
        kia.setName("Layanan KIA");
        Department savedKia = departmentRepository.save(kia);

        System.out.println(" Loaded Department ...");

    }

    @Override
    public void run(String... args) throws Exception {

       loadData();
    }
}
