package com.pulsedesk.pulsedesk.model;

import jakarta.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private String author;

    private String title;
    private String summary;

    private String priority;
    private String category;

    //Getters and Setters
    public Long getId() { return id; };
    public String getText() { return text; };
    public void setText(String text) { this.text = text; };
    public String getAuthor() { return author; };
    public void setAuthor(String author) { this.author = author; };
    public String getPriority() { return priority; };
    public void setPriority(String priority) { this.priority = priority; };
    public String getCategory() { return category; };
    public void setCategory(String category) { this.category = category; };

    public String getTitle() { return title; };
    public void setTitle(String title) { this.title = title; };
    public String getSummary() { return summary; };
    public void setSummary(String summary) { this.summary = summary; };
}
