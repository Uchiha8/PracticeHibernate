package org.example.domain;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date birthOfDate;
    private String address;
    @OneToOne
    private User user;

    public Trainee(Date birthOfDate, String address, User user) {
        this.birthOfDate = birthOfDate;
        this.address = address;
        this.user = user;
    }

    public Trainee(Long id, Date birthOfDate, String address, User user) {
        this.id = id;
        this.birthOfDate = birthOfDate;
        this.address = address;
        this.user = user;
    }

    public Trainee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(Date birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "id=" + id +
                ", birthOfDate=" + birthOfDate +
                ", address='" + address + '\'' +
                ", user=" + user +
                '}';
    }
}
