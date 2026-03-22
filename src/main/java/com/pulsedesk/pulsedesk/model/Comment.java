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
    public Long GetId() { return id; };
    public String GetText() { return text; };
    public void SetText(String text) { this.text = text; };
    public String GetAuthor() {return author; };
    public void SetAuthor() { this.author = author; };
}
