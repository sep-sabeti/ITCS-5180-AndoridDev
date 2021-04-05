package com.example.inclass08;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Forum implements Serializable {

    String title,owner,description , id , ownerEmail , date;
    ArrayList<String> liked;
    ArrayList<Comment>comments;

    public ArrayList<String> getLiked() {
        return liked;
    }

    public void setLiked(ArrayList<String> liked) {
        this.liked = liked;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(){
        this.comments = new ArrayList<>();
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public Forum(){

    }

    public Forum(String title, String owner, String description, String id, String ownerEmail, String date) {
        this.title = title;
        this.owner = owner;
        this.description = description;
        this.id = id;
        this.ownerEmail = ownerEmail;
        this.date = date;
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
