package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Message;
import org.example.domain.User;
import org.example.service.MessageService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Facade {
    private static Logger logger = LogManager.getLogger(Facade.class);

    private final MessageService messageService;

    private final UserService userService;

    @Autowired
    public Facade(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    public void messageCRUD() {
        Message message = new Message("Spring Boot");
        try {

            logger.info(messageService.create(message));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }

    }


    public void userCRUD() {
        logger.info("======CREATE USERS======");
        User user1 = new User("Ali", "Najimov", true);
        User user2 = new User("Natig", "Kurbanov", true);
        User user3 = new User("Alisher", "Bobojonov", true);
        User user4 = new User("Natig", "Kurbanov", true);
        try {
            logger.info(userService.create(user1));
            logger.info(userService.create(user2));
            logger.info(userService.create(user3));
            logger.info(userService.create(user4));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
    }
}
