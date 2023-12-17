package dao;

import org.example.dao.TrainingDAO;
import org.example.domain.Trainee;
import org.example.domain.Trainer;
import org.example.domain.Training;
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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainingDAOTest {
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Mock
    private Query<Training> query;

    @InjectMocks
    private TrainingDAO trainingDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    void readAll() {
        List<Training> trainings = Collections.singletonList(new Training());
        when(session.createQuery("FROM Training ", Training.class)).thenReturn(query);
        when(query.list()).thenReturn(trainings);

        List<Training> result = trainingDAO.readAll();

        assertEquals(trainings, result);
        verify(session, times(1)).createQuery("FROM Training ", Training.class);
        verify(query, times(1)).list();
    }

    @Test
    void readById() {
        long trainingId = 1L;
        Training training = new Training();
        when(session.get(Training.class, trainingId)).thenReturn(training);

        Training result = trainingDAO.readById(trainingId);

        assertEquals(training, result);
        verify(session, times(1)).get(Training.class, trainingId);
    }

    @Test
    void create() {
        Training training = new Training();
        when(session.get(Training.class, training.getId())).thenReturn(training);
        when(session.getTransaction()).thenReturn(transaction);

        Training result = trainingDAO.create(training);

        assertEquals(training, result);
        verify(session, times(1)).persist(training);
        verify(session, times(1)).get(Training.class, training.getId());
        verify(transaction, times(1)).commit();
    }

    @Test
    void update() {
        Training training = new Training();
        when(session.get(Training.class, training.getId())).thenReturn(training);
        when(session.getTransaction()).thenReturn(transaction);

        Training result = trainingDAO.update(training);

        assertEquals(training, result);
        verify(session, times(1)).merge(training);
        verify(session, times(1)).get(Training.class, training.getId());
        verify(transaction, times(1)).commit();
    }


    @Test
    void existByIdTrainingNotExists() {
        long trainingId = 1L;
        when(session.get(Training.class, trainingId)).thenReturn(null);
        when(session.getTransaction()).thenReturn(transaction);
        when(session.beginTransaction()).thenReturn(transaction);
        boolean result = trainingDAO.existById(trainingId);

        assertFalse(result);
        verify(session, times(1)).get(Training.class, trainingId);
        verify(transaction, times(1)).commit();
        verify(transaction, never()).rollback();
    }
}
