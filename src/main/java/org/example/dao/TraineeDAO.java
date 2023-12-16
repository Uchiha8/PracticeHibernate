package org.example.dao;

import org.example.domain.Trainee;
import org.example.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TraineeDAO implements BaseDAO<Trainee> {
    private final SessionFactory sessionFactory;

    @Autowired
    public TraineeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Trainee> readAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Trainee ", Trainee.class).list();
        }
    }

    @Override
    public Trainee readById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Trainee.class, id);
        }
    }

    public Trainee readByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Trainee> query = session.createQuery(
                    "SELECT t FROM Trainee t JOIN FETCH t.user u WHERE u.username = :username",
                    Trainee.class
            );
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }

    public Trainee changePassword(String username, String newPassword) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Trainee> traineeQuery = session.createQuery(
                    "SELECT t FROM Trainee t JOIN FETCH t.user u WHERE u.username = :username",
                    Trainee.class
            );
            traineeQuery.setParameter("username", username);
            Trainee trainee = traineeQuery.uniqueResult();
            if (trainee != null) {
                User user = trainee.getUser();
                user.setPassword(newPassword);
                session.merge(user);
                transaction.commit();
            }
            return trainee;
        }
    }

    @Override
    public Trainee create(Trainee entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return session.get(Trainee.class, entity.getId());
        }
    }

    @Override
    public Trainee update(Trainee entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            Trainee trainee = session.get(Trainee.class, entity.getId());
            session.getTransaction().commit();
            return trainee;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Trainee trainee = session.get(Trainee.class, id);
            if (trainee != null) {
                session.remove(trainee);
                return true;
            }
            session.getTransaction().commit();
            return false;
        }
    }

    @Override
    public boolean existById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Trainee trainee = session.get(Trainee.class, id);
            if (trainee != null) {
                return true;
            }
            session.getTransaction().commit();
            return false;
        }
    }
}
