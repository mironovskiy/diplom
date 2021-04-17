package by.bsuir.project.dao.repos;

import by.bsuir.project.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
    Department findDepartmentByName(String name);
}
