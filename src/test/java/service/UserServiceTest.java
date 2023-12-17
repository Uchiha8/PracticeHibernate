package service;

import org.example.dao.UserDAO;
import org.example.domain.User;
import org.example.service.UserService;
import org.example.utils.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void readAllNotEmpty() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("John", "Doe", true));
        when(userDAO.readAll()).thenReturn(userList);

        List<User> result = userService.readAll();

        assertNotNull(result);
        assertEquals(userList, result);
    }

    @Test
    void readAllEmpty() {
        when(userDAO.readAll()).thenReturn(new ArrayList<>());

        assertThrows(RuntimeException.class, () -> userService.readAll());
    }

    @Test
    void readByIdSuccess() throws Exception {
        User user = new User("John", "Doe", true);
        when(userDAO.readById(1L)).thenReturn(user);

        User result = userService.readById(1L);

        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void readByIdNotFound() {
        when(userDAO.readById(1L)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.readById(1L));
    }

    @Test
    void createSuccess() {
        User userRequest = new User("John", "Doe", true);
        when(userDAO.create(any())).thenReturn(userRequest);

        User result = userService.create(userRequest);

        assertNotNull(result);
        assertEquals(userRequest.getUsername(), result.getUsername());
        assertNotNull(result.getPassword());
    }

    @Test
    void createInvalidParams() {
        User userRequest = new User(null, "Doe", true);

        assertThrows(RuntimeException.class, () -> userService.create(userRequest));
        verify(userDAO, never()).create(any());
    }

    @Test
    void updateSuccess() {
        User userRequest = new User("John", "Doe", true);
        when(userDAO.update(any())).thenReturn(userRequest);

        User result = userService.update(userRequest);

        assertNotNull(result);
        assertEquals(userRequest.getUsername(), result.getUsername());
        assertNotNull(result.getPassword());
    }

    @Test
    void updateInvalidParams() {
        User userRequest = new User(null, "Doe", true);

        assertThrows(RuntimeException.class, () -> userService.update(userRequest));
        verify(userDAO, never()).update(any());
    }

    @Test
    void deleteByIdSuccess() throws Exception {
        when(userDAO.deleteById(1L)).thenReturn(true);
        boolean result = userService.deleteById(1L);
        assertTrue(result);
    }

    @Test
    void deleteByIdNotFound() throws Exception {
        when(userDAO.deleteById(any())).thenReturn(false);
        boolean result = userService.deleteById(1L);
        assertFalse(result);
    }

    @Test
    void usernameGeneratorNoDuplicate() {
        List<User> userList = new ArrayList<>();
        when(userDAO.readAll()).thenReturn(userList);

        String result = userService.usernameGenerator("John", "Doe");

        assertEquals("John.Doe", result);
    }

    @Test
    void usernameGeneratorWithDuplicate() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "John", "Doe", "John.Doe", "password", true));
        when(userDAO.readAll()).thenReturn(userList);

        String result = userService.usernameGenerator("John", "Doe");

        assertEquals("John.Doe_1", result);
    }

    @Test
    void passwordGenerator() {
        String result = userService.passwordGenerator();

        assertNotNull(result);
        assertEquals(10, result.length());
    }

    @Test
    void validParamValid() {
        User user = new User("John", "Doe", true);

        assertTrue(userService.validParam(user));
    }

    @Test
    void validParamInvalid() {
        User user = new User(); // Incomplete user object

        assertFalse(userService.validParam(user));
    }
}
