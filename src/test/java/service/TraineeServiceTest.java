package service;

import org.example.dao.TraineeDAO;
import org.example.domain.Trainee;
import org.example.domain.User;
import org.example.service.TraineeService;
import org.example.utils.exception.TraineeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TraineeServiceTest {
    @Mock
    private TraineeDAO traineeDAO;

    @InjectMocks
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void readAllSuccess() {
        List<Trainee> traineeList = Arrays.asList(new Trainee(), new Trainee());

        when(traineeDAO.readAll()).thenReturn(traineeList);

        List<Trainee> result = traineeService.readAll();

        assertNotNull(result);
        assertEquals(traineeList, result);
    }

    @Test
    void readAllEmpty() {
        when(traineeDAO.readAll()).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> traineeService.readAll());
    }

    @Test
    void readByIdSuccess() throws Exception {
        long traineeId = 1L;
        Trainee trainee = new Trainee();
        when(traineeDAO.readById(traineeId)).thenReturn(trainee);

        Trainee result = traineeService.readById(traineeId);

        assertNotNull(result);
        assertEquals(trainee, result);
    }

    @Test
    void readByIdNotFound() {
        long traineeId = 1L;
        when(traineeDAO.readById(traineeId)).thenReturn(null);

        assertThrows(TraineeNotFoundException.class, () -> traineeService.readById(traineeId));
    }

    @Test
    void changePasswordSuccess() {
        String username = "Ali.Najimov";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        Trainee trainee = new Trainee();

        when(traineeDAO.changePassword(username, newPassword)).thenReturn(trainee);
        when(traineeDAO.readAll()).thenReturn(List.of(new Trainee(1L, new Date(), "address1", new User(1L, "Ali", "Najimov", "Ali.Najimov", "oldPassword", true))));

        Trainee result = traineeService.changePassword(username, oldPassword, newPassword);

        assertNotNull(result);
        assertEquals(trainee, result);
    }

    @Test
    void changePasswordInvalidCredentials() {
        String username = "trainee1";
        String oldPassword = "wrongOldPassword";
        String newPassword = "newPassword";

        when(traineeDAO.changePassword(username, oldPassword)).thenReturn(null);
        when(traineeDAO.readAll()).thenReturn(List.of(new Trainee(1L, new Date(), "address1", new User("Ali", "Najimov", true))));

        assertThrows(RuntimeException.class, () -> traineeService.changePassword(username, oldPassword, newPassword));
    }

    @Test
    void readByUsernameSuccess() {
        String username = "trainee1";
        Trainee trainee = new Trainee();

        when(traineeDAO.readByUsername(username)).thenReturn(trainee);

        Trainee result = traineeService.readByUsername(username);

        assertNotNull(result);
        assertEquals(trainee, result);
    }

    @Test
    void readByUsernameNotFound() {
        String username = "nonexistent_trainee";
        when(traineeDAO.readByUsername(username)).thenReturn(null);

        assertThrows(TraineeNotFoundException.class, () -> traineeService.readByUsername(username));
    }

    @Test
    void createValidParams() {
        Trainee trainee = new Trainee(2L, new Date(), "NYC", new User(4L, "Ali", "Najimov", "Ali.Najimov", "vertushka", true));

        when(traineeDAO.create(trainee)).thenReturn(trainee);
        when(traineeDAO.readAll()).thenReturn(List.of(trainee));

        Trainee result = traineeService.create(trainee);

        assertNotNull(result);
        assertEquals(trainee, result);
    }

    @Test
    void createInvalidParams() {
        Trainee invalidTrainee = new Trainee();

        assertThrows(RuntimeException.class, () -> traineeService.create(invalidTrainee));
    }

    @Test
    void updateValidParams() {
        Trainee trainee = new Trainee(2L, new Date(), "NYC", new User(4L, "Ali", "Najimov", "Ali.Najimov", "vertushka", true));

        when(traineeDAO.update(trainee)).thenReturn(trainee);
        when(traineeDAO.readAll()).thenReturn(List.of(trainee));

        Trainee result = traineeService.update(trainee);

        assertNotNull(result);
        assertEquals(trainee, result);
    }

    @Test
    void updateInvalidParams() {
        Trainee invalidTrainee = new Trainee();

        assertThrows(RuntimeException.class, () -> traineeService.update(invalidTrainee));
    }

    @Test
    void activateDeactivateTraineeSuccess() {
        long traineeId = 1L;

        when(traineeDAO.activateDeactivateTrainee(traineeId, true)).thenReturn(true);

        boolean result = traineeService.activateDeactivateTrainee(traineeId, true);

        assertTrue(result);
    }

    @Test
    void activateDeactivateTraineeNotFound() {
        long traineeId = 1L;

        when(traineeDAO.activateDeactivateTrainee(traineeId, true)).thenReturn(false);

        assertThrows(TraineeNotFoundException.class, () -> traineeService.activateDeactivateTrainee(traineeId, true));
    }

    @Test
    void deleteByIdSuccess() throws Exception {
        long traineeId = 1L;

        when(traineeDAO.deleteById(traineeId)).thenReturn(true);

        boolean result = traineeService.deleteById(traineeId);

        assertTrue(result);
    }

    @Test
    void deleteByIdNotFound() {
        long traineeId = 1L;

        when(traineeDAO.deleteById(traineeId)).thenReturn(false);

        assertThrows(TraineeNotFoundException.class, () -> traineeService.deleteById(traineeId));
    }

    @Test
    void deleteByUsernameSuccess() {
        String username = "trainee1";

        when(traineeDAO.deleteByUsername(username)).thenReturn(true);

        boolean result = traineeService.deleteByUsername(username);

        assertTrue(result);
    }

    @Test
    void deleteByUsernameNotFound() {
        String username = "nonexistent_trainee";

        when(traineeDAO.deleteByUsername(username)).thenReturn(false);

        assertThrows(TraineeNotFoundException.class, () -> traineeService.deleteByUsername(username));
    }

    @Test
    void validParamsValid() {
        Trainee validTrainee = new Trainee(new Date(), "Address", new User());

        assertTrue(traineeService.validParams(validTrainee));
    }

    @Test
    void validParamsInvalid() {
        Trainee invalidTrainee = new Trainee();

        assertFalse(traineeService.validParams(invalidTrainee));
    }

    @Test
    void matchTraineeCredentialsMatch() {
        String username = "trainee1";
        String password = "password";
        List<Trainee> trainees = Arrays.asList(new Trainee(2L, new Date(), "NYC", new User(4L, "Ali", "Najimov", "trainee1", "password", true)));

        when(traineeDAO.readAll()).thenReturn(trainees);

        assertTrue(traineeService.matchTraineeCredentials(username, password));
    }

    @Test
    void matchTraineeCredentialsNoMatch() {
        String username = "trainee1";
        String password = "wrongPassword";
        List<Trainee> trainees = Arrays.asList(new Trainee(2L, new Date(), "NYC", new User(4L, "Ali", "Najimov", "trainee1", "vertushka", true)));
        when(traineeDAO.readAll()).thenReturn(trainees);

        assertFalse(traineeService.matchTraineeCredentials(username, password));
    }


}
