package dao;

import org.example.dao.TrainerDAO;
import org.example.domain.Trainer;
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

public class TrainerDAOTest {
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Mock
    private Query<Trainer> query;

    @InjectMocks
    private TrainerDAO trainerDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }

    @Test
    void readAll() {
        List<Trainer> trainers = Collections.singletonList(new Trainer());
        when(session.createQuery("FROM Trainer ", Trainer.class)).thenReturn(query);
        when(query.list()).thenReturn(trainers);

        List<Trainer> result = trainerDAO.readAll();

        assertEquals(trainers, result);
        verify(session, times(1)).createQuery("FROM Trainer ", Trainer.class);
        verify(query, times(1)).list();
    }

    @Test
    void readById() {
        long trainerId = 1L;
        Trainer trainer = new Trainer();
        when(session.get(Trainer.class, trainerId)).thenReturn(trainer);

        Trainer result = trainerDAO.readById(trainerId);

        assertEquals(trainer, result);
        verify(session, times(1)).get(Trainer.class, trainerId);
    }

    @Test
    void existByIdTrainerNotExists() {
        long trainerId = 1L;
        when(session.get(Trainer.class, trainerId)).thenReturn(null);
        when(session.getTransaction()).thenReturn(transaction);

        boolean result = trainerDAO.existById(trainerId);

        assertFalse(result);
        verify(session, times(1)).get(Trainer.class, trainerId);
        verify(transaction, times(1)).commit();
        verify(transaction, never()).rollback();
    }
}
