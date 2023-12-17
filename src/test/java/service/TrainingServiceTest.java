package service;


import org.example.dao.TrainingDAO;
import org.example.domain.*;
import org.example.service.TrainingService;
import org.example.utils.exception.TrainingNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TrainingServiceTest {

    @Mock
    private TrainingDAO trainingDAO;

    @InjectMocks
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void readAllSuccess() {
        List<Training> trainingList = Arrays.asList(new Training(), new Training());

        when(trainingDAO.readAll()).thenReturn(trainingList);

        List<Training> result = trainingService.readAll();

        assertNotNull(result);
        assertEquals(trainingList, result);
    }

    @Test
    void readAllEmpty() {
        when(trainingDAO.readAll()).thenReturn(null);

        assertThrows(TrainingNotFoundException.class, () -> trainingService.readAll());
    }

    @Test
    void readByIdSuccess() throws Exception {
        long trainingId = 1L;
        Training training = new Training();
        when(trainingDAO.readById(trainingId)).thenReturn(training);

        Training result = trainingService.readById(trainingId);

        assertNotNull(result);
        assertEquals(training, result);
    }

    @Test
    void readByIdNotFound() {
        long trainingId = 1L;
        when(trainingDAO.readById(trainingId)).thenReturn(null);

        assertThrows(TrainingNotFoundException.class, () -> trainingService.readById(trainingId));
    }

    @Test
    void createValidParam() {
        List<Trainee> traineeList = new ArrayList<>();
        traineeList.add(new Trainee(1L, new Date(), "Address1", new User("Muhammad", "Najimov", true)));
        Training training = new Training(1L, traineeList, new Trainer(), "Avengers", new TrainingType(), new Date(), 2);

        when(trainingDAO.create(training)).thenReturn(training);

        Training result = trainingService.create(training);

        assertNotNull(result);
        assertEquals(training, result);
    }

    @Test
    void createInvalidParam() {
        Training invalidTraining = null;
        assertThrows(RuntimeException.class, () -> trainingService.create(invalidTraining));
    }
    @Test
    void updateValidParam() {
        List<Trainee> traineeList = new ArrayList<>();
        traineeList.add(new Trainee(1L, new Date(), "Address1", new User("Muhammad", "Najimov", true)));
        Training training = new Training(1L, traineeList, new Trainer(), "Avengers", new TrainingType(), new Date(), 2);

        when(trainingDAO.update(training)).thenReturn(training);

        Training result = trainingService.update(training);

        assertNotNull(result);
        assertEquals(training, result);
    }

    @Test
    void updateInvalidParam() {
        Training invalidTraining = new Training();

        assertThrows(RuntimeException.class, () -> trainingService.update(invalidTraining));
    }
    @Test
    void deleteByIdSuccess() throws Exception {
        long trainingId = 1L;
        when(trainingDAO.deleteById(trainingId)).thenReturn(true);

        boolean result = trainingService.deleteById(trainingId);

        assertTrue(result);
    }

    @Test
    void deleteByIdNotFound() {
        long trainingId = 1L;
        when(trainingDAO.deleteById(trainingId)).thenReturn(false);

        assertThrows(TrainingNotFoundException.class, () -> trainingService.deleteById(trainingId));
    }
    @Test
    void validParamValid() {
        List<Trainee> traineeList = new ArrayList<>();
        traineeList.add(new Trainee(1L, new Date(), "Address1", new User("Muhammad", "Najimov", true)));
        Training validTraining = new Training(1L, traineeList, new Trainer(), "Avengers", new TrainingType(), new Date(), 2);

        assertTrue(trainingService.validParam(validTraining));
    }

    @Test
    void validParamInvalid() {
        Training invalidTraining = new Training();

        assertFalse(trainingService.validParam(invalidTraining));
    }

    @Test
    void readTrainingsByTraineeUsernameSuccess() {
        String username = "trainee1";
        List<Training> trainingList = Arrays.asList(new Training(), new Training());

        when(trainingDAO.getTrainingsByTraineeUsername(username)).thenReturn(trainingList);

        List<Training> result = trainingService.readTrainingsByTraineeUsername(username);

        assertNotNull(result);
        assertEquals(trainingList, result);
    }

    @Test
    void readTrainingsByTraineeUsernameNotFound() {
        String username = "nonexistent_trainee";
        when(trainingDAO.getTrainingsByTraineeUsername(username)).thenReturn(Collections.emptyList());

        assertThrows(TrainingNotFoundException.class, () -> trainingService.readTrainingsByTraineeUsername(username));
    }

    @Test
    void readTrainingsByTrainerUsernameSuccess() {
        String username = "trainer1";
        List<Training> trainingList = Arrays.asList(new Training(), new Training());

        when(trainingDAO.getTrainingsByTrainerUsername(username)).thenReturn(trainingList);

        List<Training> result = trainingService.readTrainingsByTrainerUsername(username);

        assertNotNull(result);
        assertEquals(trainingList, result);
    }

    @Test
    void readTrainingsByTrainerUsernameNotFound() {
        String username = "nonexistent_trainer";
        when(trainingDAO.getTrainingsByTrainerUsername(username)).thenReturn(Collections.emptyList());

        assertThrows(TrainingNotFoundException.class, () -> trainingService.readTrainingsByTrainerUsername(username));
    }
}
