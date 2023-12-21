package org.example.dao;

import jakarta.persistence.criteria.*;
import org.example.domain.Trainee;
import org.example.domain.Trainer;
import org.example.domain.Training;
import org.example.domain.User;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingDAO implements BaseDAO<Training> {
    private final SessionFactory sessionFactory;

    @Autowired
    public TrainingDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Training> readAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Training ", Training.class).list();
        }
    }

    @Override
    public Training readById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Training.class, id);
        }
    }

    @Override
    public Training create(Training entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return session.get(Training.class, entity.getId());
        }
    }

    @Override
    public Training update(Training entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            Training training = session.get(Training.class, entity.getId());
            session.getTransaction().commit();
            return training;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Training training = session.get(Training.class, id);
            if (training != null) {
                session.remove(training);
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
            Training training = session.get(Training.class, id);
            if (training != null) {
                return true;
            }
            session.getTransaction().commit();
            return false;
        }
    }

    public List<Training> getTrainingsByTraineeUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Training> cr = cb.createQuery(Training.class);
            Root<Training> root = cr.from(Training.class);
            Join<Training, Trainee> traineeJoin = root.join("trainees");
            Join<Trainee, User> userJoin = traineeJoin.join("user");
            Predicate condition = cb.and(
                    cb.equal(userJoin.get("username"), username)
            );
            cr.select(root).where(condition);
            Query<Training> query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    public List<Training> getTrainingsByTrainerUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Training> cr = cb.createQuery(Training.class);
            Root<Training> root = cr.from(Training.class);
            Join<Training, Trainer> traineeJoin = root.join("trainer");
            Join<Trainer, User> userJoin = traineeJoin.join("user");
            Predicate condition = cb.and(
                    cb.equal(userJoin.get("username"), username)
            );
            cr.select(root).where(condition);
            Query<Training> query = session.createQuery(cr);
            return query.getResultList();
        }
    }

    public void deleteTrainingTrainees(Trainee trainee) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Training> trainings = readAll();
            for (Training t : trainings) {
                t.getTrainees().remove(trainee);
                session.merge(t);
            }
            session.getTransaction().commit();
        }
    }
}
