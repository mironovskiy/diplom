package by.bsuir.project.service.impl;

import by.bsuir.project.dao.repos.RatingRepo;
import by.bsuir.project.entity.Department;
import by.bsuir.project.entity.Employee;
import by.bsuir.project.entity.Rating;
import by.bsuir.project.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    RatingRepo ratingRepo;

    @Override
    public void saveRating(Employee employee, Double rating) {
        Rating ratingObj = new Rating();
        ratingObj.setManRating(rating);
        ratingObj.setEmployeesByEmployeeId(employee);
        ratingRepo.save(ratingObj);
    }

    @Override
    public List<Rating> getRatingsByDepartment(Department department) {
        List<Rating> ratings = ratingRepo.findAll();

        for (int i = 0; i < ratings.size(); i++) {
            if (ratings.get(i).getEmployeesByEmployeeId().getDepartmentsByDepartmentId().getName().equals(department.getName())){
                if (!ratings.get(i).getEmployeesByEmployeeId().getUsersByUserId().getRolesByRoleId().getRole().equals("employee")){
                    ratings.remove(i);
                    i--;
                } else {
                    if (ratings.get(i).getManRating() == null){
                        ratings.get(i).setManRating(0.0);
                    }
                }
            } else {
                ratings.remove(i);
                i--;
            }
        }

        return ratings;
    }
}
