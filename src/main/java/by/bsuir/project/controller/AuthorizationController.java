package by.bsuir.project.controller;

import by.bsuir.project.entity.User;
import by.bsuir.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class AuthorizationController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String greeting(Map<String, Object> model, HttpSession session) {
        return "redirect:/auth";
    }

    @GetMapping("/auth")
    public String authorization(Map<String, Object> model){
        model.put("message", "");
        return "login.html";
    }



    @PostMapping("/auth")
    public String checkUser(@RequestParam String login, @RequestParam String password, Map<String, Object> model, HttpSession session){
        User user = userService.authorizationUser(login, password);

        if (user != null){
            user.setPassword("");
            session.setAttribute("user", user);

            if (user.getRolesByRoleId().getRole().equals("admin")){
                return "redirect:/admin";
            }

            if (user.getRolesByRoleId().getRole().equals("manager")){
                return "redirect:/manager";
            }

            return "greeting.html";
        }

        model.put("message", "Неверный логин или пароль");
        return "login.html";
    }

}
