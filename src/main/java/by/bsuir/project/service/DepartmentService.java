package by.bsuir.project.service;

import by.bsuir.project.entity.Department;

import java.util.List;

public interface DepartmentService {
    boolean addDepartment(Department department);
    List<Department> getDepartments();
}
