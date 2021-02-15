package com.example.inclass03;

import java.io.Serializable;

public class User implements Serializable {
    String name;
    String email;
    String id;
    String department;

    public User(String name, String email, String id, String department){
        this.name = name;
        this.email = email;
        this.id = id;
        this.department = department;
    }


    public String getName(){
        return this.name;
    }


    public String getEmail(){
        return this.email;
    }



    public String getId(){
        return this.id;
    }


    public String getDepartment(){
        return this.department;
    }

    public User(){

    }



}
