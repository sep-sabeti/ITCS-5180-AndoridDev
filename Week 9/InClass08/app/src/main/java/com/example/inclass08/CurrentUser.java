package com.example.inclass08;

import java.io.Serializable;

public class CurrentUser implements Serializable {
    String name;
    String email;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CurrentUser(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
