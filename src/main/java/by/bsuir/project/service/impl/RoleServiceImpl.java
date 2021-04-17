package by.bsuir.project.service.impl;

import by.bsuir.project.dao.repos.RoleRepo;
import by.bsuir.project.entity.Role;
import by.bsuir.project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepo roleRepo;

    @Override
    public List<Role> findAllRoles() {
        return roleRepo.findAll();
    }
}
