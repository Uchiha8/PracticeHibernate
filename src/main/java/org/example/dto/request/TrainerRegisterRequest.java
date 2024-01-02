package org.example.dto.request;

import jakarta.persistence.Column;
import org.example.domain.TrainingType;

public class TrainerRegisterRequest {
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private TrainingType trainingType;
}
