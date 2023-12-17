package domain;

import org.example.domain.Trainer;
import org.example.domain.TrainingType;
import org.example.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerUT {
    @Test
    public void testGettersAndSetters() {
        // Arrange
        TrainingType trainingType = new TrainingType();
        User user = new User();
        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setTrainingType(trainingType);
        trainer.setUser(user);
        Long trainerId = trainer.getId();
        TrainingType retrievedTrainingType = trainer.getTrainingType();
        User retrievedUser = trainer.getUser();
        assertEquals(1L, trainerId);
        assertEquals(trainingType, retrievedTrainingType);
        assertEquals(user, retrievedUser);
    }


    @Test
    public void testParameterizedConstructorWithId() {
        // Arrange
        TrainingType trainingType = new TrainingType();
        User user = new User();
        Trainer trainer = new Trainer(1L, trainingType, user);
        Long trainerId = trainer.getId();
        TrainingType retrievedTrainingType = trainer.getTrainingType();
        User retrievedUser = trainer.getUser();
        assertEquals(1L, trainerId);
        assertEquals(trainingType, retrievedTrainingType);
        assertEquals(user, retrievedUser);
    }

    @Test
    public void testParameterizedConstructorWithoutId() {
        TrainingType trainingType = new TrainingType();
        User user = new User();
        Trainer trainer = new Trainer(trainingType, user);
        Long trainerId = trainer.getId(); // Assuming id is not set in the constructor
        TrainingType retrievedTrainingType = trainer.getTrainingType();
        User retrievedUser = trainer.getUser();
        assertNull(trainerId);
        assertEquals(trainingType, retrievedTrainingType);
        assertEquals(user, retrievedUser);
    }

    @Test
    public void testToString() {
        TrainingType trainingType = new TrainingType();
        User user = new User();
        Trainer trainer = new Trainer(1L, trainingType, user);
        String trainerString = trainer.toString();
        assertTrue(trainerString.contains("id=1"));
        assertTrue(trainerString.contains("trainingType=" + trainingType));
        assertTrue(trainerString.contains("user=" + user));
    }
}
