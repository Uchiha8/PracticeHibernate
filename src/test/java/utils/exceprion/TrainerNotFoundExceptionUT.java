package utils.exceprion;

import org.example.utils.exception.TraineeNotFoundException;
import org.example.utils.exception.TrainerNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainerNotFoundExceptionUT {
    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Trainer not found";
        TrainerNotFoundException exception = new TrainerNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
