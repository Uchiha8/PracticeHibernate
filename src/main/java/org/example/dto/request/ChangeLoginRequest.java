package org.example.dto.request;

import jakarta.persistence.Column;

public class ChangeLoginRequest {
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String oldPassword;
    @Column(nullable = false)
    private String newPassword;
}
