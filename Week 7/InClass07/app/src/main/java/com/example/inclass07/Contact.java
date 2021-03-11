package com.example.inclass07;

import java.io.Serializable;

public class Contact implements Serializable {
    String id;
    String email;
    String numberBase;
    String name;
    String number;


    public Contact(String id, String name, String email, String number , String numberBase){
        this.name = name;
        this.number = number;
        this.id = id;
        this.email = email;
        this.numberBase = numberBase;
    }

}
