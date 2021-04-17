package by.bsuir.project.dao.repos;

import by.bsuir.project.entity.Department;
import by.bsuir.project.entity.Employee;
import by.bsuir.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Employee findEmployeeByUsersByUserId(User user);

    List<Employee> findEmployeesByDepartmentsByDepartmentId(Department department);

    Employee findEmployeesById(Long id);
}
