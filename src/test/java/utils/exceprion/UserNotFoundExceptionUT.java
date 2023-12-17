package utils.exceprion;

import org.example.utils.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserNotFoundExceptionUT {
    @Test
    public void testConstructorWithMessage() {
        String errorMessage = "Trainee not found";
        UserNotFoundException exception = new UserNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
