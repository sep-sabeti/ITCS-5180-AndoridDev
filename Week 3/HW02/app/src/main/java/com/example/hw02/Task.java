package com.example.hw02;

import java.io.Serializable;
import java.util.Calendar;

public class Task implements Serializable {
    String name, priority;
    Calendar date;
    int id;
    public Task(){

    }

    public Task(String name, Calendar date, String priority){
        //constructor for the Task
        this.name = name;
        this.date = date;
        this.priority = priority;
    }

    public String getName(){
        return name;
    }

    public Calendar getDate(){
        return date;
    }

    public String getPriority(){
        return priority;
    }
    public int getID(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPriority(String priority){
        this.priority = priority;
    }

    public void setDate(Calendar date){
        this.date = date;
    }
    public void setId(int id){this.id = id;};

    @Override
    public String toString() {
        return (date.get(Calendar.MONTH) + "/" + date.get(Calendar.DAY_OF_MONTH) + "/" + date.get(Calendar.YEAR));
    }
}
