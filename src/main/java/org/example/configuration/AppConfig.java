package org.example.configuration;

import org.example.dao.*;
import org.example.service.*;
import org.example.utils.Facade;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("org.example")
public class AppConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SessionFactory sessionFactory() {
        return new MetadataSources(
                new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build()
        ).buildMetadata().buildSessionFactory();
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAO(sessionFactory());
    }

    @Bean
    public UserService userService() {
        return new UserService(userDAO());
    }

    @Bean
    public TraineeDAO traineeDAO() {
        return new TraineeDAO(sessionFactory());
    }

    @Bean
    public TraineeService traineeService() {
        return new TraineeService(traineeDAO());
    }

    @Bean
    public TrainerDAO trainerDAO() {
        return new TrainerDAO(sessionFactory());
    }

    @Bean
    public TrainerService trainerService() {
        return new TrainerService(trainerDAO());
    }

    @Bean
    public TrainingTypeDAO trainingTypeDAO() {
        return new TrainingTypeDAO(sessionFactory());
    }

    @Bean
    public TrainingTypeService trainingTypeService() {
        return new TrainingTypeService(trainingTypeDAO());
    }

    @Bean
    public TrainingDAO trainingDAO() {
        return new TrainingDAO(sessionFactory());
    }

    @Bean
    public TrainingService trainingService() {
        return new TrainingService(trainingDAO());
    }

    @Bean
    public Facade facade(UserService userService, TraineeService traineeService, TrainerService trainerService, TrainingService trainingService, TrainingTypeService trainingTypeService) {
        return new Facade(userService, traineeService, trainerService, trainingService, trainingTypeService);
    }

}
