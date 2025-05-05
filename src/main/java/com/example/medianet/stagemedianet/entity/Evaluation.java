package com.example.medianet.stagemedianet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class Evaluation {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String comments;
    private int score; // par exemple : sur 10 ou sur 100
    private LocalDate date;

    @ManyToOne
    private User user;

    public Evaluation() {
    }
    public Evaluation(String title, String comments, int score, LocalDate date, User user) {
        this.title = title;
        this.comments = comments;
        this.score = score;
        this.date = date;
        this.user = user;

    }

    public Evaluation(Long id, String title, String comments, int score, LocalDate date, User user) {
        this.id = id;
        this.title = title;
        this.comments = comments;
        this.score = score;
        this.date = date;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
