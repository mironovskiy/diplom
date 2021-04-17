package by.bsuir.project.service.impl;

import by.bsuir.project.dao.repos.MotivationRepo;
import by.bsuir.project.entity.Motivation;
import by.bsuir.project.service.MotivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotivationServiceImpl implements MotivationService {

    @Autowired
    MotivationRepo motivationRepo;

    @Override
    public boolean addMotivation(String name, double efficiency) {
        Motivation motivation = new Motivation();
        motivation.setName(name);
        motivation.setEfficiencyMark(efficiency);

        Motivation motivationFromDB = motivationRepo.findMotivationByName(motivation.getName());

        if (motivationFromDB == null){
            motivationRepo.save(motivation);
            return true;
        }

        return false;
    }

    @Override
    public List<Motivation> getAllMotivations() {
        List<Motivation> motivations = motivationRepo.findAll();
        return motivations;
    }
}
