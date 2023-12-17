package org.example.service;

import org.example.dao.TrainingDAO;
import org.example.domain.Training;
import org.example.domain.TrainingType;
import org.example.service.BaseService;
import org.example.utils.exception.TrainingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService implements BaseService<Training> {
    private final TrainingDAO trainingDAO;

    @Autowired
    public TrainingService(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public List<Training> readAll() {
        List<Training> trainings = trainingDAO.readAll();
        if (trainings == null) {
            throw new TrainingNotFoundException("Training not found DB is empty");
        }
        return trainings;
    }

    @Override
    public Training readById(Long id) throws Exception {
        Training training = trainingDAO.readById(id);
        if (training == null) {
            throw new TrainingNotFoundException("Training is not found with ID: " + id);
        }
        return training;
    }

    @Override
    public Training create(Training createRequest) {
        if (validParam(createRequest)) {
            return trainingDAO.create(createRequest);
        }
        throw new RuntimeException("Please provide valid params");
    }

    @Override
    public Training update(Training updateRequest) {
        if (validParam(updateRequest)) {
            return trainingDAO.update(updateRequest);
        }
        throw new RuntimeException("Please provide valid params");
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        if (trainingDAO.deleteById(id)) {
            return true;
        }
        throw new TrainingNotFoundException("Training is not found with ID: " + id);
    }

    public boolean validParam(Training training) {
        if (training.getTrainingName() == null) {
            return false;
        }
        return true;
    }

    public List<Training> readTrainingsByTraineeUsername(String username) {
        List<Training> trainings = trainingDAO.getTrainingsByTraineeUsername(username);
        if (trainings.isEmpty()) {
            throw new TrainingNotFoundException("There is no training with trainee username: " + username);
        }
        return trainings;
    }

    public List<Training> readTrainingsByTrainerUsername(String username) {
        List<Training> trainings = trainingDAO.getTrainingsByTrainerUsername(username);
        if (trainings.isEmpty()) {
            throw new TrainingNotFoundException("There is no training with trainer username: " + username);
        }
        return trainings;
    }
}
