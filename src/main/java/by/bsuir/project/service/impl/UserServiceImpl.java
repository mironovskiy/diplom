package by.bsuir.project.service.impl;

import by.bsuir.project.dao.repos.RoleRepo;
import by.bsuir.project.dao.repos.UserRepo;
import by.bsuir.project.entity.User;
import by.bsuir.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;

    @Override
    public void saveUserAsEmployee(User user) {
        user.setRolesByRoleId(roleRepo.findRoleByRole("employee"));

        userRepo.save(user);
    }

    @Override
    public User authorizationUser(String login, String password) {
        User userFromDB = userRepo.findUserByLoginAndPassword(login, password);

        if (userFromDB != null){
            return userFromDB;
        }

        return null;
    }
}
