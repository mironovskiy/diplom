package by.bsuir.project.controller;

import by.bsuir.project.entity.*;
import by.bsuir.project.service.DepartmentService;
import by.bsuir.project.service.EmployeeService;
import by.bsuir.project.service.MotivationService;
import by.bsuir.project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class AdminController {

    @Autowired
    MotivationService motivationService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    RoleService roleService;

    @GetMapping("/admin")
    public String goToAdminHomePage(Map<String, Object> model, HttpSession session){
        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);
        model.put("accountData", accountData);

        //dbar and pie
        Map<String, Integer> departmentGraphData = new TreeMap<>();
        List<Department> departments = departmentService.getDepartments();
        for (Department d: departments
        ) {
            departmentGraphData.put(d.getName(), employeeService.countNumberOfEmployeesInDepartment(d));
        }


        List<Role> roles = roleService.findAllRoles();
        List<Employee> employees = employeeService.getEmployees();

        Map<String, Integer> numberRoleEmployee = new HashMap<>();

        for (int i = 0; i < roles.size(); i++) {
            int count = 0;
            for (int j = 0; j < employees.size(); j++) {
                if (employees.get(j).getUsersByUserId().getRolesByRoleId().getRole().equals(roles.get(i).getRole())){
                    count++;
                }
            }
            numberRoleEmployee.put(roles.get(i).getRole(), count);
        }

        model.put("chartData", departmentGraphData);
        model.put("employeechartData", numberRoleEmployee);

        return "admin/admin_home_page";
    }

    @GetMapping("/admin/profile")
    public String goToAdminProfilePage(Map<String, Object> model, HttpSession session){

        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);

        model.put("accountData", accountData);

        return "admin/admin_profile_page";
    }

    @PostMapping("/admin/profile")
    public String updateAccountData(@RequestParam String surname, @RequestParam String name, @RequestParam String email, @RequestParam String password, Map<String, Object> model, HttpSession session) {
        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);


        if (surname.isEmpty() && name.isEmpty() && email.isEmpty() && password.isEmpty()){

        } else {
            if (!password.isEmpty()) {
                currentUser.setPassword(password);
                accountData.setUsersByUserId(currentUser);
            }

            if (!surname.isEmpty()){
                accountData.setSurname(surname);
            }

            if (!name.isEmpty()){
                accountData.setName(name);
            }

            if (!email.isEmpty()){
                accountData.setEmail(email);
            }

            employeeService.updateEmployee(accountData);
            accountData = employeeService.getAccountData(currentUser);
        }


        model.put("accountData", accountData);
        return "admin/admin_profile_page";
    }

    @GetMapping("/admin/departments/add")
    public String goToAddDepartmentPage(Map<String, Object> model, HttpSession session){
        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);

        model.put("accountData", accountData);
        model.put("message", "");

        return "admin/admin_add_department_page";
    }

    @PostMapping("/admin/departments/add")
    public String addDepartment(@RequestParam String name, Map<String, Object> model, HttpSession session){

        Department department = new Department();
        department.setName(name);

        if (departmentService.addDepartment(department)){
            model.put("message", "");
        }
        else {
            model.put("message", "Такой отдел уже существует");
        }

        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);

        model.put("accountData", accountData);

        return "admin/admin_add_department_page";
    }

    @GetMapping("/admin/departments/list")
    public String goToDepartmentsPage(Map<String, Object> model, HttpSession session){
        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);
        model.put("accountData", accountData);

        List<Department> departments = departmentService.getDepartments();
        model.put("departments", departments);

        return "/admin/admin_departments_list_page";
    }

    @GetMapping("/admin/employees/add")
    public String goToAddEmployeesPage(Map<String, Object> model, HttpSession session){
        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);

        List<Department> departments = departmentService.getDepartments();

        model.put("accountData", accountData);
        model.put("departments", departments);
        model.put("message", "");

        return "admin/admin_add_employee_page";
    }

    @PostMapping("/admin/employees/add")
    public String addEmployee(@RequestParam String surname, @RequestParam String name, @RequestParam String login, @RequestParam String password, @RequestParam String email, @RequestParam String salary, @RequestParam String role, @RequestParam String department, Map<String, Object> model, HttpSession session){
        if (employeeService.saveEmployee(login, password, role, email, surname, name, department, new Double(salary))) {
            model.put("message", "");
        } else {
            model.put("message", "Логин уже занят");
        }

        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);

        List<Department> departments = departmentService.getDepartments();

        model.put("accountData", accountData);
        model.put("departments", departments);

        return "admin/admin_add_employee_page";
    }

    @GetMapping("/admin/employees/list")
    public String goToEmployeesPage(Map<String, Object> model, HttpSession session){
        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);
        model.put("accountData", accountData);

        List<Employee> employees = employeeService.getEmployees();
        model.put("employees", employees);

        List<Department> departments = departmentService.getDepartments();
        model.put("departments", departments);

        return "admin/admin_employees_list_page";
    }

    @PostMapping("/admin/employees/list")
    public String updateEmployeeData(@RequestParam String login, @RequestParam String salary, @RequestParam String role, @RequestParam String department, Map<String, Object> model, HttpSession session){
        employeeService.updateEmployee(login, salary, role, department);

        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);
        model.put("accountData", accountData);

        List<Employee> employees = employeeService.getEmployees();
        model.put("employees", employees);

        List<Department> departments = departmentService.getDepartments();
        model.put("departments", departments);

        return "admin/admin_employees_list_page";
    }

    @GetMapping("/admin/motivation/add")
    public String goToAddMotivationPage(Map<String, Object> model, HttpSession session){
        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);

        model.put("accountData", accountData);
        model.put("message", "");

        return "admin/admin_add_motivation_page";
    }

    @PostMapping("/admin/motivation/add")
    public String saveMotivation(@RequestParam String name, @RequestParam String efficiency, Map<String, Object> model, HttpSession session){

        if (motivationService.addMotivation(name, new Double(efficiency))){
            model.put("message", "");
        } else {
            model.put("message", "такой способ уже существует");
        }

        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);
        model.put("accountData", accountData);

        return "admin/admin_add_motivation_page";
    }

    @GetMapping("/admin/motivation/list")
    public String goToMotivationsPage(Map<String, Object> model, HttpSession session){
        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);
        model.put("accountData", accountData);

        List<Motivation> motivations = motivationService.getAllMotivations();
        model.put("motivations", motivations);
        System.out.println(motivations.size());

        return "admin/admin_motivation_list_page";
    }

}
