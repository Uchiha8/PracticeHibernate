package domain;
import org.example.domain.TrainingType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrainingTypeUT {
    @Test
    public void testGettersAndSetters() {
        // Arrange
        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setName("Boxing");

        // Act
        Long typeId = trainingType.getId();
        String typeName = trainingType.getName();

        // Assert
        assertEquals(1L, typeId);
        assertEquals("Boxing", typeName);
    }
    @Test
    public void testParameterizedConstructor() {
        // Arrange
        TrainingType trainingType = new TrainingType("Boxing");

        // Act
        String typeName = trainingType.getName();

        // Assert
        assertNull(trainingType.getId()); // Assuming id is not set in the parameterized constructor
        assertEquals("Boxing", typeName);
    }
    @Test
    public void testToString() {
        // Arrange
        TrainingType trainingType = new TrainingType(1L, "Boxing");

        // Act
        String typeString = trainingType.toString();

        // Assert
        assertTrue(typeString.contains("id=1"));
        assertTrue(typeString.contains("name='Boxing'"));
    }
}
