package domain;

import org.example.domain.Trainee;
import org.example.domain.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TraineeUT {
    @Test
    public void testGettersAndSetters() {
        // Arrange
        User user = new User();
        Date birthOfDate = new Date();

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setBirthOfDate(birthOfDate);
        trainee.setAddress("TestAddress");
        trainee.setUser(user);

        // Act
        Long traineeId = trainee.getId();
        Date retrievedBirthOfDate = trainee.getBirthOfDate();
        String address = trainee.getAddress();
        User retrievedUser = trainee.getUser();

        // Assert
        assertEquals(1L, traineeId);
        assertEquals(birthOfDate, retrievedBirthOfDate);
        assertEquals("TestAddress", address);
        assertEquals(user, retrievedUser);
    }
    @Test
    public void testParameterizedConstructorWithId() {
        // Arrange
        User user = new User();
        Date birthOfDate = new Date();

        Trainee trainee = new Trainee(1L, birthOfDate, "TestAddress", user);

        // Act
        Long traineeId = trainee.getId();
        Date retrievedBirthOfDate = trainee.getBirthOfDate();
        String address = trainee.getAddress();
        User retrievedUser = trainee.getUser();

        // Assert
        assertEquals(1L, traineeId);
        assertEquals(birthOfDate, retrievedBirthOfDate);
        assertEquals("TestAddress", address);
        assertEquals(user, retrievedUser);
    }
    @Test
    public void testParameterizedConstructorWithoutId() {
        // Arrange
        User user = new User();
        Date birthOfDate = new Date();

        Trainee trainee = new Trainee(birthOfDate, "TestAddress", user);

        // Act
        Long traineeId = trainee.getId(); // Assuming id is not set in the constructor
        Date retrievedBirthOfDate = trainee.getBirthOfDate();
        String address = trainee.getAddress();
        User retrievedUser = trainee.getUser();

        // Assert
        assertNull(traineeId);
        assertEquals(birthOfDate, retrievedBirthOfDate);
        assertEquals("TestAddress", address);
        assertEquals(user, retrievedUser);
    }
    @Test
    public void testToString() {
        // Arrange
        User user = new User();
        Date birthOfDate = new Date();

        Trainee trainee = new Trainee(1L, birthOfDate, "TestAddress", user);

        // Act
        String traineeString = trainee.toString();

        // Assert
        assertTrue(traineeString.contains("id=1"));
        assertTrue(traineeString.contains("birthOfDate=" + birthOfDate));
        assertTrue(traineeString.contains("address='TestAddress'"));
        assertTrue(traineeString.contains("user=" + user));
    }
}
