package org.example;

import org.example.configuration.AppConfig;
import org.example.utils.Facade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        Facade facade = applicationContext.getBean(Facade.class);
        facade.messageCRUD();
    }
}