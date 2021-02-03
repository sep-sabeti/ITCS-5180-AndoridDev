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

    public User(){

    }



}
