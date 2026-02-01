package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Message {
    @Id
    int id ;
    String text ;


    public Message(String text,int id) {
        this.id = id ;
        this.text = text;
    }

    public int getId(){
        return id ;
    }

    public String getText() {
        return text;
    }
}
