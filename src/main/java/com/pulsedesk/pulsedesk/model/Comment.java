package com.pulsedesk.pulsedesk.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private String author;

    //Getters and Setters
    public Long getId() { return id; };
    public String getText() { return text; };
    public void setText(String text) { this.text = text; };
    public String getAuthor() {return author; };
    public void setAuthor() { this.author = author; };
}
