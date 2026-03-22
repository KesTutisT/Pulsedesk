package com.pulsedesk.pulsedesk.model;

import jakarta.persistence.*;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String author;

    @Enumerated(EnumType.STRING)
    private Priority priority;
    private Category category;

    //Getters and Setters
    public Long getId() { return id; };
    public String getText() { return text; };
    public void setText(String text) { this.text = text; };
    public String getAuthor() { return author; };
    public void setAuthor(String author) { this.author = author; };
    public Priority getPriority() { return priority; };
    public void setPriority(Priority priority) { this.priority = priority; };
    public Category getCategory() { return category; };
    public void setCategory(Category category) { this.category = category; };
}
