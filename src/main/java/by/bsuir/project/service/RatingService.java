package by.bsuir.project.service;

import by.bsuir.project.entity.Department;
import by.bsuir.project.entity.Employee;
import by.bsuir.project.entity.Rating;

import java.util.List;

public interface RatingService {
    void saveRating(Employee employee, Double rating);
    List<Rating> getRatingsByDepartment(Department department);
}
