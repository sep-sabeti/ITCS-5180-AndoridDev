/*
 * Assignment #1 : In Class Assignment 1
 * MainPart2.java
 * Group 6A : Naima Karzouz and Sepehr Sabeti
 */

import java.util.*; 


public class MainPart2 {
   /*
    * Question 2:
    * - In this question you will use the Data.users array that includes
    * a list of users. Formatted as : firstname,lastname,age,email,gender,city,state
    * - Create a User class that should parse all the parameters for each user.
    * - The goal is to count the number of users living each state.
    * - Print out the list of State, Count order in ascending order by count.
    * */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		//The goal is to count the number of users living each state.
		//Print out the list of State
		//Count order in ascending order by count.
		
		ArrayList<User> users = new ArrayList<User>();
		// Using the same Data.users array:
		//The goal is to count the number of users living in each state.
		//Print out the list of State, Count order in ascending order by Count
		for(String str : Data.users) {
	    	String[] tempData = (str.split(","));
	    	//parsing all parameters for each user
	    	User tempUser = new User(tempData[0], tempData[1], tempData[2], tempData[3], tempData[4], tempData[5], tempData[6]);
	
	    	users.add(tempUser); //insert each of the users in a list
	    }
		
		HashMap<String, Integer> state = new HashMap<String, Integer>();//all states - unique
		//first String is Key/state, second is Integer/number of population
		
		//first get all of the states
		for(User use : users) {
			if(state.keySet().contains(use.getState())) {
				int tempval = state.get(use.getState()) + 1;
				state.put(use.getState(), tempval);
			}
			else {
				state.put(use.getState(), 1); //first time val = 0; next time val+=1
			}
		}
		
//		for(String i : state.keySet()) {
//			System.out.println(i + " " + state.get(i));
//		}
		//NOTE: need to sort largest to the top
		
		//starts off with list from state hashmap
		Object[] list = state.entrySet().toArray();
		//uses Arrays.sort() and overwrites the compare() method to directly compare the values for the sort() method
		Arrays.sort(list, new Comparator<Object>() {
			public int compare(Object object1, Object object2) {
		        return ((Map.Entry<String, Integer>) object2).getValue()
		                   .compareTo(((Map.Entry<String, Integer>) object1).getValue());
		    }
		});
		
		//prints out values
		for (Object e : list) {
		    System.out.println(((Map.Entry<String, Integer>) e).getKey() + " : "
		            + ((Map.Entry<String, Integer>) e).getValue());
		}
		
	}
}