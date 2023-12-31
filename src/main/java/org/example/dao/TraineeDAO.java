package org.example.dao;

import org.example.domain.Trainee;
import org.example.domain.Training;
import org.example.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TraineeDAO implements BaseDAO<Trainee> {
    private final SessionFactory sessionFactory;

    @Autowired
    private TrainingDAO trainingDAO;

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

    @Transactional
    public Trainee changePassword(String username, String newPassword) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            Trainee trainee = null;

            try {
                transaction = session.beginTransaction();

                Query<Trainee> traineeQuery = session.createQuery(
                        "SELECT t FROM Trainee t JOIN FETCH t.user u WHERE u.username = :username",
                        Trainee.class
                );
                traineeQuery.setParameter("username", username);
                trainee = traineeQuery.uniqueResult();

                if (trainee != null) {
                    User user = trainee.getUser();
                    user.setPassword(newPassword);
                    session.merge(user);
                }

                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                return null;
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

    public boolean activateDeactivateTrainee(Long id, boolean status) {
        try (Session session = sessionFactory.openSession()) {
            Trainee trainee = readById(id);
            if (trainee != null) {
                trainee.getUser().setActive(status);
                session.merge(trainee);
                return true;
            }
            return false;
        }
    }


    @Override
    public boolean deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Trainee trainee = session.get(Trainee.class, id);

            if (trainee != null) {
                trainingDAO.deleteTrainingTrainees(trainee);
                session.remove(trainee);
                transaction.commit();
                return true;
            }
            transaction.rollback();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Trainee> query = session.createQuery(
                    "SELECT t FROM Trainee t JOIN FETCH t.user u WHERE u.username = :username",
                    Trainee.class
            );
            query.setParameter("username", username);
            Trainee trainee = query.uniqueResult();
            if (trainee != null) {
                trainingDAO.deleteTrainingTrainees(trainee);
                session.remove(trainee);
                return true;
            }
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
