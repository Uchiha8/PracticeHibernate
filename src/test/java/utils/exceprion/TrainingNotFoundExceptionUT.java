package utils.exceprion;

import org.example.utils.exception.TrainingNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TrainingNotFoundExceptionUT {
    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Trainee not found";
        TrainingNotFoundException exception = new TrainingNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
