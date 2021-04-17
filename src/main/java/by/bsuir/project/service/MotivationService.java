package by.bsuir.project.service;

import by.bsuir.project.entity.Motivation;

import java.util.List;

public interface MotivationService {
    boolean addMotivation(String name, double efficiency);
    List<Motivation> getAllMotivations();
}
