package service;

import org.example.dao.TrainingTypeDAO;
import org.example.domain.TrainingType;
import org.example.service.TrainingTypeService;
import org.example.utils.exception.TrainingTypeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TrainingTypeServiceTest {
    @Mock
    private TrainingTypeDAO trainingTypeDAO;

    @InjectMocks
    private TrainingTypeService trainingTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void readAllNotEmpty() {
        List<TrainingType> trainingTypes = new ArrayList<>();
        trainingTypes.add(new TrainingType("Type 1"));

        when(trainingTypeDAO.readAll()).thenReturn(trainingTypes);

        List<TrainingType> result = trainingTypeService.readAll();

        assertNotNull(result);
        assertEquals(trainingTypes, result);
    }

    @Test
    void readAllEmpty() {
        when(trainingTypeDAO.readAll()).thenReturn(null);

        assertThrows(TrainingTypeNotFoundException.class, () -> trainingTypeService.readAll());
    }

    @Test
    void readByIdFound() throws Exception {
        long typeId = 1L;
        TrainingType trainingType = new TrainingType("Type 1");
        when(trainingTypeDAO.readById(typeId)).thenReturn(trainingType);

        TrainingType result = trainingTypeService.readById(typeId);

        assertNotNull(result);
        assertEquals(trainingType, result);
    }

    @Test
    void readByIdNotFound() {
        long typeId = 1L;
        when(trainingTypeDAO.readById(typeId)).thenReturn(null);

        assertThrows(TrainingTypeNotFoundException.class, () -> trainingTypeService.readById(typeId));
    }
    @Test
    void createValidParam() {
        TrainingType trainingType = new TrainingType("Type 1");

        when(trainingTypeDAO.create(trainingType)).thenReturn(trainingType);

        TrainingType result = trainingTypeService.create(trainingType);

        assertNotNull(result);
        assertEquals(trainingType, result);
    }

    @Test
    void createInvalidParam() {
        TrainingType invalidTrainingType = new TrainingType(null);

        assertThrows(RuntimeException.class, () -> trainingTypeService.create(invalidTrainingType));
    }

    @Test
    void updateValidParam() {
        TrainingType trainingType = new TrainingType("Type 1");

        when(trainingTypeDAO.update(trainingType)).thenReturn(trainingType);

        TrainingType result = trainingTypeService.update(trainingType);

        assertNotNull(result);
        assertEquals(trainingType, result);
    }

    @Test
    void updateInvalidParam() {
        TrainingType invalidTrainingType = new TrainingType(null);

        assertThrows(RuntimeException.class, () -> trainingTypeService.update(invalidTrainingType));
    }

    @Test
    void deleteByIdSuccess() throws Exception {
        long typeId = 1L;
        when(trainingTypeDAO.deleteById(typeId)).thenReturn(true);

        boolean result = trainingTypeService.deleteById(typeId);

        assertTrue(result);
    }

    @Test
    void deleteByIdNotFound() {
        long typeId = 1L;
        when(trainingTypeDAO.deleteById(typeId)).thenReturn(false);

        assertThrows(TrainingTypeNotFoundException.class, () -> trainingTypeService.deleteById(typeId));
    }

    @Test
    void validParamValid() {
        TrainingType validTrainingType = new TrainingType("Type 1");

        assertTrue(trainingTypeService.validParam(validTrainingType));
    }

    @Test
    void validParamInvalid() {
        TrainingType invalidTrainingType = new TrainingType(null);

        assertFalse(trainingTypeService.validParam(invalidTrainingType));
    }

}
