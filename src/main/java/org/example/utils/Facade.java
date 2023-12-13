package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Message;
import org.example.service.MessageService;
import org.example.utils.exception.MessageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Facade {
    private static Logger logger = LogManager.getLogger(Facade.class);
    @Autowired
    private final MessageService messageService;

    public Facade(MessageService messageService) {
        this.messageService = messageService;
    }

    public void messageCRUD() {
        logger.info("======CREATE MESSAGES=======");
        Message creatRequest1 = new Message("Spring core");
        Message creatRequest3 = new Message("Spring Boot");
        Message creatRequest2 = new Message("Spring MVC");
        try {
            messageService.create(creatRequest1);
            logger.info(creatRequest1.toString());
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        try {
            messageService.create(creatRequest2);
            logger.info(creatRequest2.toString());

        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        try {
            messageService.create(creatRequest3);
            logger.info(creatRequest3.toString());
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        logger.info("======READ ALL=======");
        List<Message> messageList;
        try {
            messageList = messageService.readAll();
            for (Message message : messageList) {
                logger.info(message.toString());
            }
        } catch (MessageNotFoundException e) {
            logger.error(e.getMessage());
        }
    }
}
