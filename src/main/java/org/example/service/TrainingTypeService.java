package org.example.service;

import org.example.dao.TrainingTypeDAO;
import org.example.domain.TrainingType;
import org.example.utils.exception.TrainingTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeService implements BaseService<TrainingType> {

    private final TrainingTypeDAO trainingTypeDAO;

    @Autowired
    public TrainingTypeService(TrainingTypeDAO trainingTypeDAO) {
        this.trainingTypeDAO = trainingTypeDAO;
    }


    @Override
    public List<TrainingType> readAll() {
        List<TrainingType> trainingTypes = trainingTypeDAO.readAll();
        if (trainingTypes == null) {
            throw new TrainingTypeNotFoundException("TrainingType not found DB is empty");
        }
        return trainingTypes;
    }

    @Override
    public TrainingType readById(Long id) throws Exception {
        TrainingType trainingType = trainingTypeDAO.readById(id);
        if (trainingType != null) {
            return trainingType;
        }
        throw new TrainingTypeNotFoundException("Training Type is not found with ID: " + id);
    }

    @Override
    public TrainingType create(TrainingType createRequest) {
        if (validParam(createRequest)) {
            return trainingTypeDAO.create(createRequest);
        }
        throw new RuntimeException("Please provide valid params");
    }

    @Override
    public TrainingType update(TrainingType updateRequest) {
        if (validParam(updateRequest)) {
            return trainingTypeDAO.update(updateRequest);
        }
        throw new RuntimeException("Please provide valid params");
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        if (trainingTypeDAO.deleteById(id)) {
            return true;
        }
        throw new TrainingTypeNotFoundException("Training Type is not found with ID: " + id);
    }

    public boolean validParam(TrainingType trainingType) {
        if (trainingType.getName() != null) {
            return true;
        }
        return false;
    }
}
