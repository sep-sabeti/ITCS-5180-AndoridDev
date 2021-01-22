/*
 * Assignment #1 : In Class Assignment 1
 * MainPart1.java
 * Group 6A : Naima Karzouz and Sepehr Sabeti
 */

import java.util.*;

public class MainPart1 {
    /*
    * Question 1:
    * - In this question you will use the Data.users array that includes
    * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
    * - Create a User class that should parse all the parameters for each user.
    * - Insert each of the users in a list.
    * - Print out the TOP 10 oldest users.
    * */

    public static void main(String[] args) {
    	ArrayList<User> users = new ArrayList<User>(); 
    	
        //access the Data.users array.
        for(String str : Data.users) {
        	String[] tempData = (str.split(","));
        	//parsing all parameters for each user
        	User tempUser = new User(tempData[0], tempData[1], tempData[2], tempData[3], tempData[4], tempData[5], tempData[6]); 

        	users.add(tempUser); //insert each of the users in a list
        }
        
        //print out the top 10 oldest users
        //we go thru temp age array, get the oldest, pop that out of array
        int tempAge = 0; 
        int counter = 0;
        //parallel arrays for printing out the eldest and their names
        ArrayList<Integer> eldest = new ArrayList<Integer>(); //the top 10 eldest
        ArrayList<String> eldestNames = new ArrayList<String>(); //their names
        
        
        //for each user...
        for(User use : users) {
        	//get age of current user
        	int usersAge = use.getAge();
        	//get the user's name
        	String usersName = (use.getFirst() + " " + use.getLast());
        			
        	//if the user's age is either greater-than or equal to the current age
        	//... then add them to the eldest and update the counter
        	if(usersAge >= tempAge && (counter < 10)) {
        		tempAge = usersAge; //update current maxAge
        		eldest.add(tempAge);
        		eldestNames.add(usersName);
        		counter++; 
        	}
        }
        
        //clean it up so the output looks better
        for(int i = 0; i < eldest.size(); i++) {
        	System.out.println(eldestNames.get(i) + " " + eldest.get(i));
        }
    }
    
}