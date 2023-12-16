package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.*;
import org.example.service.*;
import org.example.utils.exception.TraineeNotFoundException;
import org.example.utils.exception.TrainerNotFoundException;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class Facade {
    private static Logger logger = LogManager.getLogger(Facade.class);

    private final UserService userService;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final TrainingTypeService trainingTypeService;

    public Facade(UserService userService, TraineeService traineeService, TrainerService trainerService, TrainingService trainingService, TrainingTypeService trainingTypeService) {
        this.userService = userService;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.trainingTypeService = trainingTypeService;
    }

    public void preparedData() {
        logger.info("======CREATE USERS======");
        try {
            logger.info(userService.create(new User("Ali", "Najimov", true)));
            logger.info(userService.create(new User("Natig", "Kurbanov", true)));
            logger.info(userService.create(new User("Alisher", "Bobojonov", true)));
            logger.info(userService.create(new User("Muhammad", "Najimov", true)));
            logger.info(userService.create(new User("Mirjalol", "Fozilov", true)));
            logger.info(userService.create(new User("SaidUmar", "Dadajonov", true)));
            logger.info(userService.create(new User("Imron", "Umurzakov", true)));
            logger.info(userService.create(new User("Ibrohim", "Soatov", true)));
            logger.info(userService.create(new User("Shohruh", "Alimov", true)));
            logger.info(userService.create(new User("Bilol", "Ahmedov", false)));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        logger.info("=====CREATE TRAINING TYPES=====");
        try {
            logger.info(trainingTypeService.create(new TrainingType("Boxing")));
            logger.info(trainingTypeService.create(new TrainingType("Yoga")));
            logger.info(trainingTypeService.create(new TrainingType("Swimming")));
            logger.info(trainingTypeService.create(new TrainingType("Calisthenics")));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        logger.info("======CREATE TRAINEE======");
        try {
            logger.info(traineeService.create(new Trainee(new Date(), "Samarqand", userService.readById(1L))));
            logger.info(traineeService.create(new Trainee(new Date(), "Buhoro", userService.readById(2L))));
            logger.info(traineeService.create(new Trainee(new Date(), "Qashqadaryo", userService.readById(3L))));
            logger.info(traineeService.create(new Trainee(new Date(), "Qarshi", userService.readById(4L))));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("=====CREATE TRAINERS======");
        try {
            logger.info(trainerService.create(new Trainer(trainingTypeService.readById(1L), userService.readById(6L))));
            logger.info(trainerService.create(new Trainer(trainingTypeService.readById(2L), userService.readById(7L))));
            logger.info(trainerService.create(new Trainer(trainingTypeService.readById(3L), userService.readById(8L))));
            logger.info(trainerService.create(new Trainer(trainingTypeService.readById(4L), userService.readById(9L))));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("======CREATE TRAINING======");
        List<Trainee> trainees = new ArrayList<>();
        try {
            trainees.add(traineeService.readById(1L));
            trainees.add(traineeService.readById(2L));
            trainees.add(traineeService.readById(3L));
            logger.info(trainingService.create(new Training(trainees, trainerService.readById(1L), "Avengers", trainingTypeService.readById(2L), new Date(), 2)));
            logger.info(trainingService.create(new Training(trainees, trainerService.readById(2L), "GoodJob", trainingTypeService.readById(3L), new Date(), 3)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void requiredTasks() {
        logger.info("==========Create Trainer profile.==========");
        try {
            logger.info(trainerService.create(new Trainer(trainingTypeService.readById(1L), userService.readById(10L))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("==========Create Trainee profile.==========");
        try {
            logger.info(traineeService.create(new Trainee(new Date(), "Yakkabog", userService.readById(5L))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("==========Trainee username and password matching.==========");
        User trainee;
        try {
            trainee = userService.readById(2L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (traineeService.matchTraineeCredentials(trainee.getUsername(), trainee.getPassword())) {
            logger.info("The Trainee username and password match!!!");
        } else {
            logger.error("The Trainee with username and password is not found");
        }
        logger.info("==========Trainer username and password matching.==========");
        User trainer;
        try {
            trainer = userService.readById(6L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (trainerService.matchTrainerCredentials(trainer.getUsername(), trainer.getPassword())) {
            logger.info("The Trainer username and password match!!!");
        } else {
            logger.error("The trainee with username and password is not found");
        }
        logger.info("==========Select Trainee profile by username.==========");
        if (traineeService.matchTraineeCredentials(trainee.getUsername(), trainee.getPassword())) {
            try {
                logger.info(traineeService.readByUsername(trainee.getUsername()));
            } catch (TraineeNotFoundException e) {
                logger.error(e.getMessage());
            }
        }
        logger.info("==========Select Trainer profile by username.==========");
        if (trainerService.matchTrainerCredentials(trainer.getUsername(), trainer.getPassword())) {
            try {
                logger.info(trainerService.readByUsername(trainer.getUsername()));
            } catch (TrainerNotFoundException e) {
                logger.error(e.getMessage());
            }
        }
        logger.info("==========Trainee password change.==========");
        User trainee_user;
        try {
            trainee_user = userService.readById(4L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            Trainee passwordChangedTrainee = traineeService.changePassword(trainee_user.getUsername(), trainee_user.getPassword(), "newPassword");
            logger.info(passwordChangedTrainee);
            logger.info("Password changed");
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        logger.info("==========Trainer password change.==========");
        User trainer_user;
        try {
            trainer_user = userService.readById(8L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            Trainer passwordChangedTrainer = trainerService.changePassword(trainer_user.getUsername(), trainer_user.getPassword(), "Ikillyou@13");
            logger.info(passwordChangedTrainer);
            logger.info("Password changed");
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        logger.info("==========Update trainer profile.==========");
        logger.info("==========Update trainee profile.==========");
        logger.info("==========Activate/De-activate trainee.==========");
        logger.info("==========Activate/De-activate trainer.==========");
        logger.info("==========Delete trainee profile by username.==========");
        logger.info("==========Get Trainee Trainings List by trainee username and criteria.==========");
        logger.info("==========Get Trainer Trainings List by trainee username and criteria.==========");
        logger.info("==========Add training.==========");
        logger.info("==========Get not assigned on specific trainee active trainers list.==========");
        logger.info("==========Update Tranee's trainers list==========");


    }
}
