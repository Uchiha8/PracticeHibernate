package org.example.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Trainee> trainees;
    @ManyToOne
    private Trainer trainer;
    @Column(nullable = false)
    private String trainingName;
    @ManyToOne
    private TrainingType trainingType;
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private Number duration;

    public Training(Long id, List<Trainee> trainees, Trainer trainer, String trainingName, TrainingType trainingType, Date date, Number duration) {
        this.id = id;
        this.trainees = trainees;
        this.trainer = trainer;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.date = date;
        this.duration = duration;
    }

    public Training() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Number getDuration() {
        return duration;
    }

    public void setDuration(Number duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", trainees=" + trainees +
                ", trainer=" + trainer +
                ", trainingName='" + trainingName + '\'' +
                ", trainingType=" + trainingType +
                ", date=" + date +
                ", duration=" + duration +
                '}';
    }
}
