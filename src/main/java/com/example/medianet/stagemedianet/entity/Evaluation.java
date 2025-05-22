package com.example.medianet.stagemedianet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Evaluation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evaluated_user_id")
    private User evaluatedUser;

    @ManyToOne
    @JoinColumn(name = "evaluator_id")
    private User evaluator;

    private String title;
    private String comments;
    private int score;

    public Evaluation() {}


    public Evaluation(Long id, User evaluatedUser, User evaluator, String title, String comments, int score) {
        this.id = id;
        this.evaluatedUser = evaluatedUser;
        this.evaluator = evaluator;
        this.title = title;
        this.comments = comments;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getEvaluatedUser() {
        return evaluatedUser;
    }

    public void setEvaluatedUser(User evaluatedUser) {
        this.evaluatedUser = evaluatedUser;
    }

    public User getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(User evaluator) {
        this.evaluator = evaluator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
