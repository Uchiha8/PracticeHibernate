package dao;

import org.example.dao.TrainingTypeDAO;
import org.example.domain.TrainingType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainingTypeDAOTest {
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @InjectMocks
    private TrainingTypeDAO trainingTypeDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void readAllTest() {
        when(sessionFactory.openSession()).thenReturn(session);
        org.hibernate.query.Query<TrainingType> mockQuery = Mockito.mock(org.hibernate.query.Query.class);
        when(session.createQuery("FROM TrainingType ", TrainingType.class)).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(new ArrayList<>());
        List<TrainingType> result = trainingTypeDAO.readAll();
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).createQuery("FROM TrainingType ", TrainingType.class);
        verify(mockQuery, times(1)).list();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void readByIdTest() {
        // Mocking behavior
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.get(TrainingType.class, 1L)).thenReturn(new TrainingType("Type 1"));

        // Method invocation
        TrainingType result = trainingTypeDAO.readById(1L);

        // Verify interactions
        verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).get(TrainingType.class, 1L);

        // Assertion
        assertNotNull(result);
        assertEquals("Type 1", result.getName());
    }
}
