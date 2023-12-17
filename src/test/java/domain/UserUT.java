package domain;

import org.example.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserUT {

    @Test
    public void testGettersAndSetters() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUsername("john.doe");
        user.setPassword("password");
        user.setActive(true);

        // Act
        Long userId = user.getId();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String username = user.getUsername();
        String password = user.getPassword();
        Boolean isActive = user.getActive();

        // Assert
        assertEquals(1L, userId);
        assertEquals("John", firstName);
        assertEquals("Doe", lastName);
        assertEquals("john.doe", username);
        assertEquals("password", password);
        assertTrue(isActive);
    }

    @Test
    public void testNoArgConstructor() {
        // Act
        User user = new User();

        // Assert
        assertNull(user.getId());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
        assertNull(user.getActive());
    }

    @Test
    public void testToString() {
        // Arrange
        User user = new User(1L, "John", "Doe", "john.doe", "password", true);

        // Act
        String userString = user.toString();

        // Assert
        assertTrue(userString.contains("id=1"));
        assertTrue(userString.contains("firstName='John'"));
        assertTrue(userString.contains("lastName='Doe'"));
        assertTrue(userString.contains("username='john.doe'"));
        assertTrue(userString.contains("password='password'"));
        assertTrue(userString.contains("isActive=true"));
    }
    @Test
    public void testParameterizedConstructor() {
        // Arrange
        User user = new User("John", "Doe", true);

        // Act
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        Boolean isActive = user.getActive();

        // Assert
        assertNull(user.getId()); // Assuming id is not set in the parameterized constructor
        assertEquals("John", firstName);
        assertEquals("Doe", lastName);
        assertTrue(isActive);
    }

}
