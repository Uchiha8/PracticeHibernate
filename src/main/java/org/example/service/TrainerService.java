package org.example.service;

import org.example.dao.TrainerDAO;
import org.example.domain.Trainee;
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

    public Trainer readByUsername(String username) {
        Trainer trainer = trainerDAO.readByUsername(username);
        if (trainer == null) {
            throw new TrainerNotFoundException("Trainer not found ith username: " + username);
        }
        return trainer;
    }

    public Trainer changePassword(String username, String oldPassword, String newPassword) {
        if (matchTrainerCredentials(username, oldPassword)) {
            Trainer trainer = trainerDAO.changePassword(username, newPassword);
            if (trainer != null) {
                return trainer;
            }
        }
        throw new RuntimeException("Trainee password did not changed");
    }

    @Override
    public Trainer create(Trainer createRequest) {
        if (validParams(createRequest)) {
            return trainerDAO.create(createRequest);
        }
        throw new RuntimeException("Please provide valid params");
    }

    public boolean activateDeactivateTrainer(Long id, boolean status) {
        if (trainerDAO.activateDeactivateTrainer(id, status)) {
            return true;
        }
        throw new TrainerNotFoundException("Trainer not found with ID: " + id);
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

    public boolean deleteByUsername(String username) {
        if (trainerDAO.deleteByUsername(username)) {
            return true;
        }
        throw new TrainerNotFoundException("Trainer not found with username: " + username);
    }
    public boolean validParams(Trainer trainer) {
        if (trainer.getUser() == null) {
            return false;
        }
        return true;
    }

    public boolean matchTrainerCredentials(String username, String password) {
        List<Trainer> trainers = readAll();
        for (Trainer t : trainers) {
            if (t.getUser().getUsername().equals(username) && t.getUser().getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
