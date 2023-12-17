package service;

import org.example.dao.TrainerDAO;
import org.example.domain.Trainer;
import org.example.domain.TrainingType;
import org.example.domain.User;
import org.example.service.TrainerService;
import org.example.utils.exception.TraineeNotFoundException;
import org.example.utils.exception.TrainerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TrainerServiceTest {
    @Mock
    private TrainerDAO trainerDAO;

    @InjectMocks
    private TrainerService trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void readAllSuccess() {
        List<Trainer> trainerList = Arrays.asList(new Trainer(), new Trainer());

        when(trainerDAO.readAll()).thenReturn(trainerList);

        List<Trainer> result = trainerService.readAll();

        assertNotNull(result);
        assertEquals(trainerList, result);
    }

    @Test
    void readAllEmpty() {
        when(trainerDAO.readAll()).thenReturn(null);

        assertThrows(TraineeNotFoundException.class, () -> trainerService.readAll());
    }

    @Test
    void readByIdSuccess() throws Exception {
        long trainerId = 1L;
        Trainer trainer = new Trainer();
        when(trainerDAO.readById(trainerId)).thenReturn(trainer);

        Trainer result = trainerService.readById(trainerId);

        assertNotNull(result);
        assertEquals(trainer, result);
    }

    @Test
    void readByIdNotFound() {
        long trainerId = 1L;
        when(trainerDAO.readById(trainerId)).thenReturn(null);

        assertThrows(TrainerNotFoundException.class, () -> trainerService.readById(trainerId));
    }

    @Test
    void readByUsernameSuccess() {
        String username = "trainer1";
        Trainer trainer = new Trainer();

        when(trainerDAO.readByUsername(username)).thenReturn(trainer);

        Trainer result = trainerService.readByUsername(username);

        assertNotNull(result);
        assertEquals(trainer, result);
    }

    @Test
    void readByUsernameNotFound() {
        String username = "nonexistent_trainer";
        when(trainerDAO.readByUsername(username)).thenReturn(null);

        assertThrows(TrainerNotFoundException.class, () -> trainerService.readByUsername(username));
    }

    @Test
    void changePasswordSuccess() {
        String username = "Ali.Najimov";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        Trainer trainer = new Trainer();

        when(trainerDAO.changePassword(username, newPassword)).thenReturn(trainer);
        when(trainerDAO.readAll()).thenReturn(List.of(new Trainer(new TrainingType(), new User(1L, "Ali", "Najimov", "Ali.Najimov", "oldPassword", true))));

        Trainer result = trainerService.changePassword(username, oldPassword, newPassword);

        assertNotNull(result);
        assertEquals(trainer, result);
    }

    @Test
    void changePasswordInvalidCredentials() {
        String username = "trainer1";
        String oldPassword = "wrongOldPassword";
        String newPassword = "newPassword";

        when(trainerDAO.readAll()).thenReturn(List.of(new Trainer(new TrainingType(), new User(1L, "Ali", "Najimov", "Ali.Najimov", "password", true))));

        assertThrows(RuntimeException.class, () -> trainerService.changePassword(username, oldPassword, newPassword));
    }

    @Test
    void createValidParams() {
        Trainer trainer = new Trainer(10L, new TrainingType(), new User());

        when(trainerDAO.create(trainer)).thenReturn(trainer);

        Trainer result = trainerService.create(trainer);

        assertNotNull(result);
        assertEquals(trainer, result);
    }

    @Test
    void createInvalidParams() {
        Trainer invalidTrainer = new Trainer();

        assertThrows(RuntimeException.class, () -> trainerService.create(invalidTrainer));
    }

    @Test
    void activateDeactivateTrainerSuccess() {
        long trainerId = 1L;

        when(trainerDAO.activateDeactivateTrainer(trainerId, true)).thenReturn(true);

        boolean result = trainerService.activateDeactivateTrainer(trainerId, true);

        assertTrue(result);
    }

    @Test
    void activateDeactivateTrainerNotFound() {
        long trainerId = 1L;

        when(trainerDAO.activateDeactivateTrainer(trainerId, true)).thenReturn(false);

        assertThrows(TrainerNotFoundException.class, () -> trainerService.activateDeactivateTrainer(trainerId, true));
    }

    @Test
    void updateValidParams() {
        Trainer trainer = new Trainer(8L, new TrainingType(), new User());

        when(trainerDAO.update(trainer)).thenReturn(trainer);

        Trainer result = trainerService.update(trainer);

        assertNotNull(result);
        assertEquals(trainer, result);
    }

    @Test
    void updateInvalidParams() {
        Trainer invalidTrainer = new Trainer();

        assertThrows(RuntimeException.class, () -> trainerService.update(invalidTrainer));
    }

    @Test
    void deleteByIdSuccess() throws Exception {
        long trainerId = 1L;

        when(trainerDAO.deleteById(trainerId)).thenReturn(true);

        boolean result = trainerService.deleteById(trainerId);

        assertTrue(result);
    }

    @Test
    void deleteByIdNotFound() {
        long trainerId = 1L;

        when(trainerDAO.deleteById(trainerId)).thenReturn(false);

        assertThrows(TraineeNotFoundException.class, () -> trainerService.deleteById(trainerId));
    }

    @Test
    void deleteByUsernameSuccess() {
        String username = "trainer1";

        when(trainerDAO.deleteByUsername(username)).thenReturn(true);

        boolean result = trainerService.deleteByUsername(username);

        assertTrue(result);
    }

    @Test
    void deleteByUsernameNotFound() {
        String username = "nonexistent_trainer";

        when(trainerDAO.deleteByUsername(username)).thenReturn(false);

        assertThrows(TrainerNotFoundException.class, () -> trainerService.deleteByUsername(username));
    }

    @Test
    void validParamsValid() {
        Trainer validTrainer = new Trainer(20L, new TrainingType(), new User("Bilol", "Ahmedov", true));

        assertTrue(trainerService.validParams(validTrainer));
    }

    @Test
    void validParamsInvalid() {
        Trainer invalidTrainer = new Trainer();

        assertFalse(trainerService.validParams(invalidTrainer));
    }

    @Test
    void matchTrainerCredentialsMatch() {
        String username = "Ali.Najimov";
        String password = "password";
        List<Trainer> trainers = Arrays.asList(new Trainer(new TrainingType(), new User(1L, "Ali", "Najimov", "Ali.Najimov", "password", true)));

        when(trainerDAO.readAll()).thenReturn(trainers);

        assertTrue(trainerService.matchTrainerCredentials(username, password));
    }

    @Test
    void matchTrainerCredentialsNoMatch() {
        String username = "trainer1";
        String password = "wrongPassword";
        List<Trainer> trainers = Arrays.asList(new Trainer(new TrainingType(), new User(1L, "Ali", "Najimov", "Ali.Najimov", "password", true)));

        when(trainerDAO.readAll()).thenReturn(trainers);

        assertFalse(trainerService.matchTrainerCredentials(username, password));
    }

    @Test
    void readActiveUnassignedTrainersSuccess() {
        List<Trainer> trainerList = Arrays.asList(new Trainer(), new Trainer());

        when(trainerDAO.readActiveUnassignedTrainers()).thenReturn(trainerList);

        List<Trainer> result = trainerService.readActiveUnassignedTrainers();

        assertNotNull(result);
        assertEquals(trainerList, result);
    }

    @Test
    void readActiveUnassignedTrainersEmpty() {
        when(trainerDAO.readActiveUnassignedTrainers()).thenReturn(Collections.emptyList());

        assertThrows(TrainerNotFoundException.class, () -> trainerService.readActiveUnassignedTrainers());
    }
}
