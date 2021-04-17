package by.bsuir.project.service.impl;

import by.bsuir.project.dao.repos.DepartmentRepo;
import by.bsuir.project.entity.Department;
import by.bsuir.project.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepo departmentRepo;

    @Override
    public boolean addDepartment(Department department) {

        Department departmentFromDB = departmentRepo.findDepartmentByName(department.getName());

        if (departmentFromDB == null) {
            departmentRepo.save(department);
            return true;
        }

        return false;
    }

    @Override
    public List<Department> getDepartments() {
        List<Department> departments = departmentRepo.findAll();
        return departments;
    }
}
