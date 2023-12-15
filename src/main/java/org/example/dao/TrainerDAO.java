package org.example.dao;

import org.example.domain.Trainer;
import org.example.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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