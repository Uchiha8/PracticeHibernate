package org.example.dao;

import org.example.domain.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageDAO implements BaseDAO<Message> {


    @Autowired
    private final SessionFactory sessionFactory;

    public MessageDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Message> readAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Message ", Message.class).list();
        }
    }

    @Override
    public Message readById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Message.class, id);
        }
    }

    @Override
    public Message create(Message entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return session.get(Message.class, entity.getId());
        }
    }

    @Override
    public Message update(Message entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(entity);
            Message message = session.get(Message.class, entity.getId());
            session.getTransaction().commit();
            return message;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Message message = session.get(Message.class, id);
            if (message != null) {
                session.remove(message);
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
            Message message = session.get(Message.class, id);
            if (message != null) {
                return true;
            }
            session.getTransaction().commit();
            return false;
        }
    }
}
