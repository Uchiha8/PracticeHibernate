package org.example.service;

import org.example.dao.MessageDAO;
import org.example.domain.Message;
import org.example.utils.exception.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements BaseService<Message> {
    @Autowired
    private final MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }


    @Override
    public List<Message> readAll() {
        List<Message> messages = messageDAO.readAll();
        if (messages.isEmpty()) {
            throw new MessageNotFoundException("DB of Messages is empty!!!");
        }
        return messages;
    }

    @Override
    public Message readById(Long id) throws Exception {
        Message message = messageDAO.readById(id);
        if (message == null) {
            throw new MessageNotFoundException("Message not found with ID: " + id);
        }
        return message;
    }

    @Override
    public Message create(Message createRequest) {
        if (validParams(createRequest)) {
            return messageDAO.create(createRequest);
        }
        throw new RuntimeException("Provided Message unsuccessfully saved. Please provide valid params");
    }

    @Override
    public Message update(Message updateRequest) {
        if (validParams(updateRequest)) {
            return messageDAO.update(updateRequest);
        }
        throw new RuntimeException("Provided Message unsuccessfully updated. Please provide valid params");

    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        if (messageDAO.deleteById(id)) {
            return true;
        }
        throw new MessageNotFoundException("Message not found with ID: " + id);
    }

    public boolean validParams(Message message) {
        if (message.getMessage() != null) {
            return true;
        }
        return false;
    }
}
