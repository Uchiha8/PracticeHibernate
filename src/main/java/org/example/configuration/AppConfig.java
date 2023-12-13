package org.example.configuration;

import org.example.dao.MessageDAO;
import org.example.service.MessageService;
import org.example.utils.Facade;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("org.example")
public class AppConfig {

    @Bean
    public SessionFactory sessionFactory() {
        return new MetadataSources(
                new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build()
        ).buildMetadata().buildSessionFactory();
    }

    @Bean
    public MessageDAO messageDAO() {
        return new MessageDAO(sessionFactory());
    }

    @Bean
    public MessageService messageService() {
        return new MessageService(messageDAO());
    }

    @Bean
    public Facade facade(MessageService messageService) {
        return new Facade(messageService);
    }

}
