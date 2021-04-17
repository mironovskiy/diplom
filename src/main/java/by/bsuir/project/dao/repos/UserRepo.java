package by.bsuir.project.dao.repos;

import by.bsuir.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByLogin(String login);
    User findUserByLoginAndPassword(String login, String password);
}
