package org.example.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private TrainingType trainingType;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public Trainer(Long id, TrainingType trainingType, User user) {
        this.id = id;
        this.trainingType = trainingType;
        this.user = user;
    }

    public Trainer(TrainingType trainingType, User user) {
        this.trainingType = trainingType;
        this.user = user;
    }

    public Trainer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Trainer{" + "id=" + id + ", trainingType=" + trainingType + ", user=" + user + '}';
    }
}
