package org.example.dao;

import org.example.domain.Trainee;
import org.example.domain.TrainingType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingTypeDAO implements BaseDAO<TrainingType> {
    private final SessionFactory sessionFactory;

    @Autowired
    public TrainingTypeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<TrainingType> readAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM TrainingType ", TrainingType.class).list();
        }
    }

    @Override
    public TrainingType readById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(TrainingType.class, id);
        }
    }

    @Override
    public TrainingType create(TrainingType entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return session.get(TrainingType.class, entity.getId());
        }
    }

    @Override
    public TrainingType update(TrainingType entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            TrainingType trainingType = session.get(TrainingType.class, entity.getId());
            session.getTransaction().commit();
            return trainingType;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            TrainingType trainingType = session.get(TrainingType.class, id);
            if (trainingType != null) {
                session.remove(trainingType);
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
            TrainingType trainingType = session.get(TrainingType.class, id);
            if (trainingType != null) {
                return true;
            }
            session.getTransaction().commit();
            return false;
        }
    }
}
