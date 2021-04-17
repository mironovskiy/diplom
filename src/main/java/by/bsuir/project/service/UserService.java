package by.bsuir.project.service;

import by.bsuir.project.entity.User;
import org.springframework.stereotype.Service;


public interface UserService {
    void saveUserAsEmployee(User user);
    User authorizationUser(String login, String password);
}
