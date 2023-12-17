package dao;

import org.example.dao.TraineeDAO;
import org.example.domain.Trainee;
import org.example.domain.Trainer;
import org.example.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TraineeDAOTest {
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @InjectMocks
    private TraineeDAO traineeDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    void readAllSuccess() {
        List<Trainee> traineeList = Arrays.asList(new Trainee(), new Trainee());

        Query<Trainee> query = mock(Query.class);
        when(session.createQuery("FROM Trainee ", Trainee.class)).thenReturn(query);
        when(query.list()).thenReturn(traineeList);

        List<Trainee> result = traineeDAO.readAll();

        assertNotNull(result);
        assertEquals(traineeList, result);
    }

    @Test
    void readAllEmpty() {
        Query<Trainee> query = mock(Query.class);
        when(session.createQuery("FROM Trainee ", Trainee.class)).thenReturn(query);
        when(query.list()).thenReturn(List.of());

        List<Trainee> traineeList = traineeDAO.readAll();
        assertEquals(traineeList, List.of());
    }

    @Test
    void readByIdSuccess() {
        long traineeId = 1L;
        Trainee trainee = new Trainee();

        when(session.get(Trainee.class, traineeId)).thenReturn(trainee);

        Trainee result = traineeDAO.readById(traineeId);

        assertNotNull(result);
        assertEquals(trainee, result);
    }

    @Test
    void readByUsernameSuccess() {
        String username = "trainee1";
        Trainee trainee = new Trainee();
        User user = new User();
        user.setUsername(username);
        trainee.setUser(user);

        Query<Trainee> query = mock(Query.class);
        when(session.createQuery(
                "SELECT t FROM Trainee t JOIN FETCH t.user u WHERE u.username = :username", Trainee.class))
                .thenReturn(query);
        when(query.setParameter("username", username)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainee);

        Trainee result = traineeDAO.readByUsername(username);

        assertNotNull(result);
        assertEquals(trainee, result);
    }

    @Test
    void changePasswordSuccess() {
        String username = "trainee1";
        String newPassword = "newPassword";
        Trainee trainee = new Trainee();
        User user = new User();
        user.setUsername(username);
        user.setPassword("oldPassword");
        trainee.setUser(user);

        Query<Trainee> query = mock(Query.class);
        when(session.createQuery(
                "SELECT t FROM Trainee t JOIN FETCH t.user u WHERE u.username = :username", Trainee.class))
                .thenReturn(query);
        when(query.setParameter("username", username)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(trainee);

        Trainee result = traineeDAO.changePassword(username, newPassword);

        assertNotNull(result);
        assertEquals(trainee, result);
        assertEquals(newPassword, trainee.getUser().getPassword());
        verify(session, times(1)).merge(user);
        verify(transaction, times(1)).commit();
    }

    @Test
    void createSuccess() {
        Trainee trainee = new Trainee(new Date(), "Samarqand", new User("Muhammad", "Najimov", true));
        when(session.get(Trainee.class, trainee.getId())).thenReturn(trainee);
        when(session.getTransaction()).thenReturn(transaction);

        Trainee result = traineeDAO.create(trainee);

        assertNotNull(result);
        verify(session, times(1)).beginTransaction();
        verify(session, times(1)).persist(trainee);
        verify(session, times(1)).get(Trainee.class, trainee.getId());
        verify(transaction, times(1)).commit();
    }

    @Test
    void updateSuccess() {
        Trainee trainee = new Trainee(11L, new Date(), "Samarqand", new User("Muhammad", "Najimov", true));

        when(session.get(Trainee.class, trainee.getId())).thenReturn(trainee);
        when(session.getTransaction()).thenReturn(transaction);

        Trainee result = traineeDAO.update(trainee);

        assertNotNull(result);
        verify(session, times(1)).beginTransaction();
        verify(session, times(1)).merge(trainee);
        verify(session, times(1)).get(Trainee.class, trainee.getId());
        verify(transaction, times(1)).commit();
    }

    @Test
    void activateDeactivateTraineeTraineeNotFound() {
        long traineeId = 1L;

        when(session.get(Trainee.class, traineeId)).thenReturn(null);

        boolean result = traineeDAO.activateDeactivateTrainee(traineeId, false);

        assertFalse(result);
        verify(session, never()).merge(any());
        verify(transaction, never()).commit();
    }

    @Test
    void deleteByUsernameTraineeNotFound() {
        String username = "trainee1";

        Query<Trainee> query = mock(Query.class);
        when(session.createQuery(
                "SELECT t FROM Trainee t JOIN FETCH t.user u WHERE u.username = :username", Trainee.class))
                .thenReturn(query);
        when(query.setParameter("username", username)).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);

        boolean result = traineeDAO.deleteByUsername(username);

        assertFalse(result);
        verify(session, never()).remove(any());
        verify(transaction, never()).commit();
    }

    @Test
    void existByIdTraineeNotExists() {
        long traineeId = 1L;

        when(session.get(Trainee.class, traineeId)).thenReturn(null);
        when(session.getTransaction()).thenReturn(transaction);

        boolean result = traineeDAO.existById(traineeId);

        assertFalse(result);
        verify(session, times(1)).get(Trainee.class, traineeId);
        verify(transaction, times(1)).commit();
    }
}
