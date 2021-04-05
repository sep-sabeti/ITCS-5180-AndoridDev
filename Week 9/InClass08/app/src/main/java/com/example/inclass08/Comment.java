package com.example.inclass08;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Comment implements Serializable {

    String Name, Description;
    String Date;
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Comment(String name, String description, String date, String email) {
        Name = name;
        Description = description;
        Date = date;
        this.email = email;
    }

    public Comment(Map<String,String> map){
        this.Name =  map.get("name");
        this.Description =  map.get("description");
        this.Date =  map.get("date");
        this.email = map.get("email");
    }

    public Comment(){

    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }


}

