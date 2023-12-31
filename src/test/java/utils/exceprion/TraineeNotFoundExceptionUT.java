package utils.exceprion;

import org.example.utils.exception.TraineeNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TraineeNotFoundExceptionUT {
    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Trainee not found";
        TraineeNotFoundException exception = new TraineeNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
