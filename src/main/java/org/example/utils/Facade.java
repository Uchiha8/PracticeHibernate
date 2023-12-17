package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.*;
import org.example.service.*;
import org.example.utils.exception.TraineeNotFoundException;
import org.example.utils.exception.TrainerNotFoundException;
import org.example.utils.exception.TrainingNotFoundException;
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
        logger.info("==========Update trainee profile.==========");
        try {
            Trainee updateTrainee = new Trainee(3L, new Date(), "New York", userService.readById(3L));
            if (traineeService.matchTraineeCredentials(updateTrainee.getUser().getUsername(), updateTrainee.getUser().getPassword())) {
                logger.info(traineeService.update(updateTrainee));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("==========Update trainer profile.==========");
        try {
            Trainer updateTrainer = new Trainer(3L, trainingTypeService.readById(1L), userService.readById(3L));
            if (trainerService.matchTrainerCredentials(updateTrainer.getUser().getUsername(), updateTrainer.getUser().getPassword())) {
                logger.info(trainerService.update(updateTrainer));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("==========Activate/De-activate trainee.==========");
        try {
            Trainee activateTrainee = traineeService.readById(5L);
            if (traineeService.matchTraineeCredentials(activateTrainee.getUser().getUsername(), activateTrainee.getUser().getPassword())) {
                try {
                    logger.info(traineeService.activateDeactivateTrainee(activateTrainee.getId(), false));
                } catch (TraineeNotFoundException e) {
                    logger.error(e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("==========Activate/De-activate trainer.==========");
        try {
            Trainer activateTrainer = trainerService.readById(5L);
            if (trainerService.matchTrainerCredentials(activateTrainer.getUser().getUsername(), activateTrainer.getUser().getPassword())) {
                try {
                    logger.info(trainerService.activateDeactivateTrainer(activateTrainer.getId(), true));
                } catch (TrainerNotFoundException e) {
                    logger.error(e.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("==========Delete trainee profile by username.==========");
        Trainee deletedTrainee = null;
        try {
            deletedTrainee = traineeService.readById(3L);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (traineeService.matchTraineeCredentials(deletedTrainee.getUser().getUsername(), deletedTrainee.getUser().getPassword())) {
            try {
                logger.info(traineeService.deleteByUsername(deletedTrainee.getUser().getUsername()));
            } catch (TraineeNotFoundException e) {
                logger.error(e.getMessage());
            }
        }
        logger.info("==========Delete trainer profile by username.==========");
        Trainer deletedTrainer = null;
        try {
            deletedTrainer = trainerService.readById(3L);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (trainerService.matchTrainerCredentials(deletedTrainer.getUser().getUsername(), deletedTrainee.getUser().getPassword())) {
            try {
                logger.info(trainerService.deleteByUsername(deletedTrainer.getUser().getUsername()));
            } catch (TrainerNotFoundException e) {
                logger.error(e.getMessage());
            }
        }
        logger.info("==========Add training.==========");
        List<Trainee> traineeList = new ArrayList<>();
        try {
            traineeList.add(traineeService.readById(1L));
            traineeList.add(traineeService.readById(2L));
            traineeList.add(traineeService.readById(3L));
            logger.info(trainingService.create(new Training(traineeList, trainerService.readById(3L), "EpamLab", trainingTypeService.readById(4L), new Date(), 1)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("==========Get Trainee Trainings List by trainee username and criteria.==========");
        Trainee traineeTrainings = null;
        try {
            traineeTrainings = traineeService.readById(1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (traineeService.matchTraineeCredentials(traineeTrainings.getUser().getUsername(), traineeTrainings.getUser().getPassword())) {
            try {
                List<Training> trainingList = trainingService.readTrainingsByTraineeUsername(traineeTrainings.getUser().getUsername());
                for (Training t : trainingList) {
                    logger.info(t.toString());
                }
            } catch (TrainingNotFoundException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.error("There is no training with provided username of trainee!!!");
        }
        logger.info("==========Get Trainer Trainings List by trainee username and criteria.==========");
        Trainer trainerTrainings = null;
        try {
            trainerTrainings = trainerService.readById(2L);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (trainerService.matchTrainerCredentials(trainerTrainings.getUser().getUsername(), trainerTrainings.getUser().getPassword())) {
            try {
                List<Training> trainings = trainingService.readTrainingsByTrainerUsername(trainerTrainings.getUser().getUsername());
                for (Training t : trainings) {
                    logger.info(t.toString());
                }
            } catch (TrainingNotFoundException e) {
                logger.error(e.getMessage());
            }
        }
        logger.info("==========Get not assigned on specific trainee active trainers list.==========");
        logger.info("==========Update Tranee's trainers list==========");


    }
}
