package org.example.service;

import org.example.dao.TraineeDAO;
import org.example.domain.Trainee;
import org.example.utils.exception.TraineeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean deleteById(Long id) throws Exception {
        if (traineeDAO.deleteById(id)) {
            return true;
        }
        throw new TraineeNotFoundException("Trainee not found with ID: " + id);
    }

    public boolean validParams(Trainee trainee) {
        if (trainee.getAddress() == null || trainee.getBirthOfDate() == null) {
            return false;
        }
        return true;
    }

}
