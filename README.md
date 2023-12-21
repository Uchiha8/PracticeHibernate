# Hibernate ORM Demo Gym Project

## Configuration

### AppConfiguration.java

This configuration class sets up essential beans for the application, including data sources, DAOs, and services.

#### Beans:

1. **sessionFactory():** Provides an instance of `SessionFactory`.

2. **userDAO():** Creates a `UserDAO` bean with a specified `DataSource`.

3. **userService():** Creates a `UserService` bean.

4. **traineeDAO():** Creates a `TraineeDAO` bean with a specified `DataSource`.

5. **traineeService():** Creates a `TraineeService` bean.

6. **trainerDAO():** Creates a `TrainerDAO` bean with a specified `DataSource`.

7. **trainerService():** Creates a `TrainerService` bean.

8. **trainingDAO():** Creates a `TrainingDAO` bean with a specified `DataSource`.

9. **trainingService():** Creates a `TrainingService` bean.

10. **facade(UserService, TraineeService, TrainerService, TrainingService):** Creates a `Facade` bean, injecting dependencies for user, trainee, trainer, and training services.

---

## Domain

### User.java

The `User` class represents a user entity in the system.

#### Attributes:

- **id (Long):** Unique identifier for the user.
- **firstName (String):** First name of the user.
- **lastName (String):** Last name of the user.
- **username (String):** Unique username for the user.
- **password (String):** User's password.
- **isActive (Boolean):** Indicates whether the user is active.

#### Constructors:

1. **User():** Default constructor.
2. **User(Long id, String firstName, String lastName, String username, String password, Boolean isActive):** Parameterized constructor.

#### Methods:

- **Getter and Setter methods for all attributes.**

#### Additional Methods:

- **toString(): String:** Provides a string representation of the user object.

---

### Trainee.java

The `Trainee` class represents a trainee entity in the system.

#### Attributes:

- **id (Long):** Unique identifier for the trainee.
- **dof (LocalDate):** Date of birth of the trainee.
- **address (String):** Address of the trainee.
- **userID (Long):** User ID associated with the trainee.

#### Constructors:

1. **Trainee():** Default constructor.
2. **Trainee(Long id, LocalDate dof, String address, Long userID):** Parameterized constructor.

#### Methods:

- **Getter and Setter methods for all attributes.**

#### Additional Methods:

- **toString(): String:** Provides a string representation of the trainee object.
---
### Trainer.java

The `Trainer` class represents a trainer entity in the system.

#### Attributes:

- **id (Long):** Unique identifier for the trainer.
- **trainingType (Long):** Identifier for the training type associated with the trainer.
- **userId (Long):** User ID associated with the trainer.

#### Constructors:

1. **Trainer():** Default constructor.
2. **Trainer(Long id, Long trainingType, Long userId):** Parameterized constructor.

#### Methods:

- **Getter and Setter methods for all attributes.**

#### Additional Methods:

- **toString(): String:** Provides a string representation of the trainer object.

---

### Training.java

The `Training` class represents a training entity in the system.

#### Attributes:

- **id (Long):** Unique identifier for the training.
- **traineeID (Long):** User ID associated with the trainee receiving the training.
- **trainerID (Long):** User ID associated with the trainer providing the training.
- **trainingName (String):** Name of the training.
- **trainingTypeID (Long):** Identifier for the type of training.
- **trainingDate (LocalDate):** Date of the training.
- **duration (Number):** Duration of the training.

#### Constructors:

1. **Training():** Default constructor.
2. **Training(Long id, Long traineeID, Long trainerID, String trainingName, Long trainingTypeID, LocalDate trainingDate, Number duration):** Parameterized constructor.

#### Methods:

- **Getter and Setter methods for all attributes.**

#### Additional Methods:

- **toString(): String:** Provides a string representation of the training object.

---

### TrainingType.java

The `TrainingType` class represents a training type entity in the system.

#### Attributes:

- **id (Long):** Unique identifier for the training type.
- **name (String):** Name of the training type.

#### Constructors:

1. **TrainingType():** Default constructor.
2. **TrainingType(Long id, String name):** Parameterized constructor.

#### Methods:

- **Getter and Setter methods for all attributes.**

## Hibernate

### Hibernate Configuration - AppConfig Class

The AppConfig class serves as the configuration hub for Hibernate integration. It defines a singleton SessionFactory bean, a critical component for managing database connections and interactions.

### Hibernate Implementation

The UserDAO class is a Hibernate Data Access Object (DAO) implementing CRUD operations for the User entity. It utilizes the SessionFactory to manage database sessions, encapsulating transactional and session logic.

Also, the same implementations on TrainerDAO, TraineeDAO, TrainingDAO, and TrainingTypeDAO.

This project implements a Training Management System with various functionalities related to trainer and trainee profiles, password management, profile updates, activation/deactivation, training sessions, and more.

## Implemented Functionalities

1. **Create Profiles**
   - Create trainer and trainee profiles associating them with training types and user details.

2. **Username and Password Matching**
   - Verify the matching of usernames and passwords for trainees and trainers.

3. **Select Profiles by Username**
   - Retrieve trainer and trainee profiles based on their respective usernames.

4. **Password Change**
   - Allow secure password changes for both trainees and trainers.

5. **Update Profiles**
   - Modify trainer and trainee profiles while ensuring credentials are valid.

6. **Activate/De-activate Profiles**
   - Enable/disable trainee and trainer profiles.

7. **Delete Profiles by Username**
   - Delete trainee and trainer profiles based on their usernames, with credential validation.

8. **Add Training**
   - Add training sessions with associated trainees, trainers, and other details.

9. **Get Trainings List by Username and Criteria**
   - Retrieve training sessions associated with a specific trainee.

10. **Get Trainer Trainings List by Username and Criteria**
    - Retrieve training sessions associated with a specific trainer.

11. **Get Not Assigned Active Trainers List**
    - Retrieve a list of active trainers not assigned to specific trainees.
      
## How to Use

1. **Profile Creation**
   - Use provided methods to create trainer and trainee profiles.

2. **Username and Password Matching**
   - Verify username and password combinations for trainees and trainers.

3. **Profile Selection**
   - Retrieve profiles by providing usernames.

4. **Password Change**
   - Change passwords for trainees and trainers.

5. **Profile Update**
   - Modify profiles as needed.

6. **Activation/Deactivation**
   - Activate or deactivate profiles based on criteria.

7. **Profile Deletion**
   - Delete profiles by specifying usernames.

8. **Training Management**
   - Add training sessions and retrieve training lists for trainees and trainers.

9. **Trainer Assignment**
   - Update the list of trainers associated with a specific trainee.

10. **List Not Assigned Active Trainers**
    - Retrieve a list of active trainers not assigned to specific trainees.

## Dependencies

Ensure the necessary dependencies, such as the logger and service classes, are configured in the project.

## How to Run the Project

To execute the Java application project utilizing a PostgreSQL relational database, follow these steps:

### Database Configuration

Create a PostgreSQL database named "hibernate" to facilitate the proper functioning of the application.

### Running the Project

After configuring the database, proceed to execute the Java application.
