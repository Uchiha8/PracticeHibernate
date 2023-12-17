package utils.exceprion;

import org.example.utils.exception.TrainingTypeNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainingTypeNotFoundExceptionUT {
    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Trainee not found";
        TrainingTypeNotFoundException exception = new TrainingTypeNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
