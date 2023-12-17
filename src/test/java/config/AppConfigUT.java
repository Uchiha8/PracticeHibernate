package config;

import org.example.configuration.AppConfig;
import org.example.dao.*;
import org.example.service.*;
import org.example.utils.Facade;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringJUnitConfig(classes = AppConfig.class)
@ContextConfiguration(classes = AppConfig.class)
public class AppConfigUT {
    @Autowired
    private ApplicationContext context;

    @Test
    public void testSessionFactoryBean() {
        SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        assertNotNull(sessionFactory);
    }

    @Test
    public void testUserDAOBean() {
        UserDAO userDAO = context.getBean(UserDAO.class);
        assertNotNull(userDAO);
    }

    @Test
    public void testUserServiceBean() {
        UserService userService = context.getBean(UserService.class);
        assertNotNull(userService);
    }

    @Test
    public void testTraineeDAOBean() {
        TraineeDAO traineeDAO = context.getBean(TraineeDAO.class);
        assertNotNull(traineeDAO);
    }

    @Test
    public void testTraineeServiceBean() {
        TraineeService traineeService = context.getBean(TraineeService.class);
        assertNotNull(traineeService);
    }

    @Test
    public void testTrainerDAOBean() {
        TrainerDAO trainerDAO = context.getBean(TrainerDAO.class);
        assertNotNull(trainerDAO);
    }

    @Test
    public void testTrainerServiceBean() {
        TrainerService trainerService = context.getBean(TrainerService.class);
        assertNotNull(trainerService);
    }

    @Test
    public void testTrainingTypeDAOBean() {
        TrainingTypeDAO trainingTypeDAO = context.getBean(TrainingTypeDAO.class);
        assertNotNull(trainingTypeDAO);
    }

    @Test
    public void testTrainingTypeServiceBean() {
        TrainingTypeService trainingTypeService = context.getBean(TrainingTypeService.class);
        assertNotNull(trainingTypeService);
    }

    @Test
    public void testTrainingDAOBean() {
        TrainingDAO trainingDAO = context.getBean(TrainingDAO.class);
        assertNotNull(trainingDAO);
    }

    @Test
    public void testTrainingServiceBean() {
        TrainingService trainingService = context.getBean(TrainingService.class);
        assertNotNull(trainingService);
    }

    @Test
    public void testFacadeBean() {
        Facade facade = context.getBean(Facade.class);
        assertNotNull(facade);
    }
}
