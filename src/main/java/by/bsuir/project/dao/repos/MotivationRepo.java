package by.bsuir.project.dao.repos;

import by.bsuir.project.entity.Motivation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotivationRepo extends JpaRepository<Motivation, Long> {
    Motivation findMotivationByName(String name);
}
