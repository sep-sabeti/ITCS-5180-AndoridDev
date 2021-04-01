package com.example.inclass08;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class Forum {

    String title,owner,description , id , ownerEmail , date;
    ArrayList<String> liked;

    public Forum(){

    }

    public Forum(String title, String owner, String description, String id, String ownerEmail, String date) {
        this.title = title;
        this.owner = owner;
        this.description = description;
        this.id = id;
        this.ownerEmail = ownerEmail;
        this.date = date;
        this.liked = new ArrayList<>();
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Forum{" +
                "title='" + title + '\'' +
                ", owner='" + owner + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
