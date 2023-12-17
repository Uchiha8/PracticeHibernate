package domain;

import org.example.domain.Trainee;
import org.example.domain.Trainer;
import org.example.domain.Training;
import org.example.domain.TrainingType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class TrainingUT {
    @Test
    public void testGettersAndSetters() {
        // Arrange
        List<Trainee> trainees = new ArrayList<>();
        Trainer trainer = new Trainer();
        TrainingType trainingType = new TrainingType();
        Date date = new Date();
        Number duration = 2;

        Training training = new Training();
        training.setId(1L);
        training.setTrainees(trainees);
        training.setTrainer(trainer);
        training.setTrainingName("TestTraining");
        training.setTrainingType(trainingType);
        training.setDate(date);
        training.setDuration(duration);

        // Act
        Long trainingId = training.getId();
        List<Trainee> retrievedTrainees = training.getTrainees();
        Trainer retrievedTrainer = training.getTrainer();
        String trainingName = training.getTrainingName();
        TrainingType retrievedTrainingType = training.getTrainingType();
        Date retrievedDate = training.getDate();
        Number retrievedDuration = training.getDuration();

        // Assert
        assertEquals(1L, trainingId);
        assertEquals(trainees, retrievedTrainees);
        assertEquals(trainer, retrievedTrainer);
        assertEquals("TestTraining", trainingName);
        assertEquals(trainingType, retrievedTrainingType);
        assertEquals(date, retrievedDate);
        assertEquals(duration, retrievedDuration);
    }
    @Test
    public void testParameterizedConstructorWithId() {
        // Arrange
        List<Trainee> trainees = new ArrayList<>();
        Trainer trainer = new Trainer();
        TrainingType trainingType = new TrainingType();
        Date date = new Date();
        Number duration = 2;

        Training training = new Training(1L, trainees, trainer, "TestTraining", trainingType, date, duration);

        // Act
        Long trainingId = training.getId();
        List<Trainee> retrievedTrainees = training.getTrainees();
        Trainer retrievedTrainer = training.getTrainer();
        String trainingName = training.getTrainingName();
        TrainingType retrievedTrainingType = training.getTrainingType();
        Date retrievedDate = training.getDate();
        Number retrievedDuration = training.getDuration();

        // Assert
        assertEquals(1L, trainingId);
        assertEquals(trainees, retrievedTrainees);
        assertEquals(trainer, retrievedTrainer);
        assertEquals("TestTraining", trainingName);
        assertEquals(trainingType, retrievedTrainingType);
        assertEquals(date, retrievedDate);
        assertEquals(duration, retrievedDuration);
    }
    @Test
    public void testParameterizedConstructorWithoutId() {
        // Arrange
        List<Trainee> trainees = new ArrayList<>();
        Trainer trainer = new Trainer();
        TrainingType trainingType = new TrainingType();
        Date date = new Date();
        Number duration = 2;

        Training training = new Training(trainees, trainer, "TestTraining", trainingType, date, duration);

        // Act
        Long trainingId = training.getId(); // Assuming id is not set in the constructor
        List<Trainee> retrievedTrainees = training.getTrainees();
        Trainer retrievedTrainer = training.getTrainer();
        String trainingName = training.getTrainingName();
        TrainingType retrievedTrainingType = training.getTrainingType();
        Date retrievedDate = training.getDate();
        Number retrievedDuration = training.getDuration();

        // Assert
        assertNull(trainingId);
        assertEquals(trainees, retrievedTrainees);
        assertEquals(trainer, retrievedTrainer);
        assertEquals("TestTraining", trainingName);
        assertEquals(trainingType, retrievedTrainingType);
        assertEquals(date, retrievedDate);
        assertEquals(duration, retrievedDuration);
    }
    @Test
    public void testToString() {
        // Arrange
        List<Trainee> trainees = new ArrayList<>();
        Trainer trainer = new Trainer();
        TrainingType trainingType = new TrainingType();
        Date date = new Date();
        Number duration = 2;

        Training training = new Training(1L, trainees, trainer, "TestTraining", trainingType, date, duration);

        // Act
        String trainingString = training.toString();

        // Assert
        assertTrue(trainingString.contains("id=1"));
        assertTrue(trainingString.contains("trainees=" + trainees));
        assertTrue(trainingString.contains("trainer=" + trainer));
        assertTrue(trainingString.contains("trainingName='TestTraining'"));
        assertTrue(trainingString.contains("trainingType=" + trainingType));
        assertTrue(trainingString.contains("date=" + date));
        assertTrue(trainingString.contains("duration=" + duration));
    }
}
