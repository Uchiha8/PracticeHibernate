package org.example.service;

import org.example.dao.TraineeDAO;
import org.example.domain.Trainee;
import org.example.utils.exception.TraineeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TraineeService implements BaseService<Trainee> {
    private final TraineeDAO traineeDAO;

    @Autowired
    public TraineeService(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Override
    public List<Trainee> readAll() {
        List<Trainee> trainees = traineeDAO.readAll();
        if (trainees.isEmpty()) {
            throw new RuntimeException("Trainees DB is empty");
        }
        return trainees;
    }

    @Override
    public Trainee readById(Long id) throws Exception {
        Trainee trainee = traineeDAO.readById(id);
        if (trainee == null) {
            throw new TraineeNotFoundException("Trainee not found with ID: " + id);
        }
        return trainee;
    }

    public Trainee changePassword(String username, String oldPassword, String newPassword) {
        if (matchTraineeCredentials(username, oldPassword)) {
            Trainee trainee = traineeDAO.changePassword(username, newPassword);
            if (trainee != null) {
                return trainee;
            }
        }
        throw new RuntimeException("Trainee password did not changed");
    }

    public Trainee readByUsername(String username) {
        Trainee trainee = traineeDAO.readByUsername(username);
        if (trainee == null) {
            throw new TraineeNotFoundException("Trainee not found with username: " + username);
        }
        return trainee;
    }

    @Override
    public Trainee create(Trainee createRequest) {
        if (validParams(createRequest)) {
            return traineeDAO.create(createRequest);
        }
        throw new RuntimeException("Please provide valid params");
    }

    @Override
    public Trainee update(Trainee updateRequest) {
        if (validParams(updateRequest)) {
            return traineeDAO.update(updateRequest);
        }
        throw new RuntimeException("Please provide valid params");
    }

    public boolean activateDeactivateTrainee(Long id, boolean status) {
        if (traineeDAO.activateDeactivateTrainee(id, status)) {
            return true;
        }
        throw new TraineeNotFoundException("Trainee not found with ID: " + id);
    }
    @Override
    public boolean deleteById(Long id) throws Exception {
        if (traineeDAO.deleteById(id)) {
            return true;
        }
        throw new TraineeNotFoundException("Trainee not found with ID: " + id);
    }

    public boolean deleteByUsername(String username) {
        if (traineeDAO.deleteByUsername(username)) {
            return true;
        }
        throw new TraineeNotFoundException("Trainee not found with username: " + username);
    }

    public boolean validParams(Trainee trainee) {
        if (trainee.getAddress() == null || trainee.getBirthOfDate() == null) {
            return false;
        }
        return true;
    }

    public boolean matchTraineeCredentials(String username, String password) {
        List<Trainee> trainees = readAll();
        for (Trainee t : trainees) {
            if (t.getUser().getUsername().equals(username) && t.getUser().getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }


}
