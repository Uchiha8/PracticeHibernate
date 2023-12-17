package dao;

import org.example.dao.UserDAO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDAOTest {
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @InjectMocks
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
    }
    @Test
    void readAllTest() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("John", "Doe", true));

        Query<User> mockQuery = mock(Query.class);
        when(session.createQuery(anyString(), eq(User.class))).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(userList);

        List<User> result = userDAO.readAll();

        assertNotNull(result);
        assertEquals(userList, result);
    }

    @Test
    void readByIdTest() {
        User expectedUser = new User("John", "Doe", true);
        when(session.get(User.class, 1L)).thenReturn(expectedUser);
        User result = userDAO.readById(1L);
        assertNotNull(result);
        assertEquals(expectedUser, result);
    }
}
