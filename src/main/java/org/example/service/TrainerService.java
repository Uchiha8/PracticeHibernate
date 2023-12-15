package org.example.service;

import org.example.dao.TrainerDAO;
import org.example.domain.Trainer;
import org.example.utils.exception.TraineeNotFoundException;
import org.example.utils.exception.TrainerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService implements BaseService<Trainer> {
    private final TrainerDAO trainerDAO;
    @Autowired
    public TrainerService(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Override
    public List<Trainer> readAll() {
        List<Trainer> trainers = trainerDAO.readAll();
        if (trainers == null) {
            throw new TraineeNotFoundException("Trainers DB is empty");
        }
        return trainers;
    }

    @Override
    public Trainer readById(Long id) throws Exception {
        Trainer trainer = trainerDAO.readById(id);
        if (trainer == null) {
            throw new TrainerNotFoundException("Trainer not found ith ID: " + id);
        }
        return trainer;
    }

    @Override
    public Trainer create(Trainer createRequest) {
        if (validParams(createRequest)) {
            return trainerDAO.create(createRequest);
        }
        throw new RuntimeException("Please provide valid params");
    }

    @Override
    public Trainer update(Trainer updateRequest) {
        if (validParams(updateRequest)) {
            return trainerDAO.update(updateRequest);
        }
        throw new RuntimeException("Please provide valid params");
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        if (trainerDAO.deleteById(id)) {
            return true;
        }
        throw new TraineeNotFoundException("Trainer is not found with ID: " + id);
    }

    public boolean validParams(Trainer trainer) {
        if (trainer.getUser() == null) {
            return false;
        }
        return true;
    }
}
