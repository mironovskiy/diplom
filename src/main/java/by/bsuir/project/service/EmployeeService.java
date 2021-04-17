package by.bsuir.project.service;

import by.bsuir.project.entity.Department;
import by.bsuir.project.entity.Employee;
import by.bsuir.project.entity.User;

import java.util.List;

public interface EmployeeService {
    Employee getAccountData(User user);
    void updateEmployee(Employee employee);
    boolean saveEmployee(String login, String password, String role, String email, String surname, String name, String department, double salary);
    List<Employee> getEmployees();
    void updateEmployee(String login, String salary, String role, String department);
    int countNumberOfEmployeesInDepartment(Department department);
    List<Employee> getEmployeesInDepartment(Department department);
    Employee getEmployeeById(Long id);
}
