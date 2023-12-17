package org.example.dao;

import org.example.domain.Trainee;
import org.example.domain.Trainer;
import org.example.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainerDAO implements BaseDAO<Trainer> {
    private final SessionFactory sessionFactory;

    @Autowired
    public TrainerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Trainer> readAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Trainer ", Trainer.class).list();
        }
    }

    @Override
    public Trainer readById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Trainer.class, id);
        }
    }

    public Trainer readByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Trainer> query = session.createQuery(
                    "SELECT t FROM Trainer t JOIN FETCH t.user u WHERE u.username = :username",
                    Trainer.class
            );
            query.setParameter("username", username);
            return query.uniqueResult();
        }
    }
    public Trainer changePassword(String username, String newPassword) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query<Trainer> traineeQuery = session.createQuery(
                    "SELECT t FROM Trainer t JOIN FETCH t.user u WHERE u.username = :username",
                    Trainer.class
            );
            traineeQuery.setParameter("username", username);
            Trainer trainer = traineeQuery.uniqueResult();
            if (trainer != null) {
                User user = trainer.getUser();
                user.setPassword(newPassword);
                session.merge(user);
                transaction.commit();
            }
            return trainer;
        }
    }

    @Override
    public Trainer create(Trainer entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return session.get(Trainer.class, entity.getId());
        }
    }

    @Override
    public Trainer update(Trainer entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            Trainer trainer = session.get(Trainer.class, entity.getId());
            session.getTransaction().commit();
            return trainer;
        }
    }

    public boolean activateDeactivateTrainer(Long id, boolean status) {
        try (Session session = sessionFactory.openSession()) {
            Trainer trainer = readById(id);
            if (trainer != null) {
                trainer.getUser().setActive(status);
                session.merge(trainer);
                return true;
            }
            return false;
        }
    }
    @Override
    public boolean deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Trainer trainer = session.get(Trainer.class, id);
            if (trainer != null) {
                session.remove(trainer);
                return true;
            }
            session.getTransaction().commit();
            return false;
        }
    }

    public boolean deleteByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Trainer> query = session.createQuery(
                    "SELECT t FROM Trainer t JOIN FETCH t.user u WHERE u.username = :username",
                    Trainer.class
            );
            query.setParameter("username", username);
            Trainer trainer = query.uniqueResult();
            if (trainer != null) {
                session.remove(trainer);
                return true;
            }
            return false;
        }
    }
    @Override
    public boolean existById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Trainer trainer = session.get(Trainer.class, id);
            if (trainer != null) {
                return true;
            }
            session.getTransaction().commit();
            return false;
        }
    }
}
