package by.bsuir.project.service.impl;

import by.bsuir.project.dao.repos.DepartmentRepo;
import by.bsuir.project.dao.repos.EmployeeRepo;
import by.bsuir.project.dao.repos.RoleRepo;
import by.bsuir.project.dao.repos.UserRepo;
import by.bsuir.project.entity.Department;
import by.bsuir.project.entity.Employee;
import by.bsuir.project.entity.Role;
import by.bsuir.project.entity.User;
import by.bsuir.project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    @Override
    public Employee getAccountData(User user) {

        user = userRepo.findUserByLogin(user.getLogin());

        Employee employee = employeeRepo.findEmployeeByUsersByUserId(user);

        return employee;
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeRepo.save(employee);
    }

    @Override
    public boolean saveEmployee(String login, String password, String role, String email, String surname, String name, String department, double salary) {
        Role roleObj = roleRepo.findRoleByRole(role);
        Department departmentObj = departmentRepo.findDepartmentByName(department);
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRolesByRoleId(roleObj);

        Employee employee = new Employee();
        employee.setSurname(surname);
        employee.setName(name);
        employee.setEmail(email);
        employee.setSalary(salary);
        employee.setUsersByUserId(user);
        employee.setDepartmentsByDepartmentId(departmentObj);

        User uniqueUser = userRepo.findUserByLogin(login);
        if (uniqueUser == null){
            employeeRepo.save(employee);
            return true;
        }

        return false;
    }

    @Override
    public List<Employee> getEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        return employees;
    }

    @Override
    public void updateEmployee(String login, String salary, String role, String department) {
        User user = userRepo.findUserByLogin(login);
        Employee employee = employeeRepo.findEmployeeByUsersByUserId(user);

        Department departmentObj = departmentRepo.findDepartmentByName(department);
        employee.setDepartmentsByDepartmentId(departmentObj);

        Role roleObj = roleRepo.findRoleByRole(role);
        employee.getUsersByUserId().setRolesByRoleId(roleObj);

        employee.setSalary(new Double(salary));

        employeeRepo.save(employee);
    }

    @Override
    public int countNumberOfEmployeesInDepartment(Department department) {
        return getEmployeesInDepartment(department).size();
    }

    @Override
    public List<Employee> getEmployeesInDepartment(Department department) {
        List<Employee> employees = employeeRepo.findEmployeesByDepartmentsByDepartmentId(department);
        return employees;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepo.findEmployeesById(id);
    }

}
