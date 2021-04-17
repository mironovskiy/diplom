package by.bsuir.project.controller;

import by.bsuir.project.entity.*;
import by.bsuir.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class ManagerController {

    @Autowired
    MotivationService motivationService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    RoleService roleService;

    @Autowired
    RatingService ratingService;

    @GetMapping("/manager")
    public String goToManagerHomePage(Map<String, Object> model, HttpSession session){
        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);
        model.put("accountData", accountData);

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

        return "/manager/manager_home_page";
    }

    @GetMapping("/manager/profile")
    public String goToManagerProfilePage(Map<String, Object> model, HttpSession session){
        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);
        model.put("accountData", accountData);

        return "manager/manager_profile_page";
    }

    @PostMapping("/manager/profile")
    public String updateAccountData(@RequestParam String surname, @RequestParam String name, @RequestParam String email, @RequestParam String password, Map<String, Object> model, HttpSession session){
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
        return "manager/manager_profile_page";
    }

    @GetMapping("/manager/subordinates")
    public String goToSubordinatesPage(Map<String, Object> model, HttpSession session){
        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);
        model.put("accountData", accountData);

        List<Rating> ratings = ratingService.getRatingsByDepartment(accountData.getDepartmentsByDepartmentId());
        Map<Employee, Double> map = new HashMap<>();
        for (int i = 0; i < ratings.size(); i++) {
            map.put(ratings.get(i).getEmployeesByEmployeeId(), ratings.get(i).getManRating());
        }

        model.put("subordinates", map);
        model.put("ratings", ratings);

        return "manager/manager_subordinates_page";
    }

    @GetMapping("/manager/efficiency")
    public String goToManagerEfficiencyPage(Map<String, Object> model, HttpSession session){
        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);
        model.put("accountData", accountData);

        List<Employee> employees = employeeService.getEmployees();

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getDepartmentsByDepartmentId().getName().equals(accountData.getDepartmentsByDepartmentId().getName())){
                if (!employees.get(i).getUsersByUserId().getRolesByRoleId().getRole().equals("employee")){
                    employees.remove(i);
                    i--;
                }
            } else {
                employees.remove(i);
                i--;
            }
        }

        model.put("employees", employees);

        return "manager/manager_efficiency_page";
    }

    @PostMapping("/manager/efficiency")
    public String saveEmployeeRating(@RequestParam String id, @RequestParam String rating, Map<String, Object> model, HttpSession session){
        User currentUser = (User)session.getAttribute("user");
        Employee accountData = employeeService.getAccountData(currentUser);
        model.put("accountData", accountData);

        Employee employee = employeeService.getEmployeeById(new Long(id));
        ratingService.saveRating(employee, new Double(rating));

        List<Employee> employees = employeeService.getEmployees();

        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getDepartmentsByDepartmentId().getName().equals(accountData.getDepartmentsByDepartmentId().getName())){
                if (!employees.get(i).getUsersByUserId().getRolesByRoleId().getRole().equals("employee")){
                    employees.remove(i);
                    i--;
                }
            } else {
                employees.remove(i);
                i--;
            }
        }

        model.put("employees", employees);

        return "manager/manager_efficiency_page";
    }
}
