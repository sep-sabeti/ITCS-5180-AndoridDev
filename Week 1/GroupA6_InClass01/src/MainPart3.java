/*
 * Assignment #1 : In Class Assignment 1
 * MainPart3.java
 * Group 6A : Naima Karzouz and Sepehr Sabeti
 */


import java.util.*;

public class MainPart3 {
    /*
    * Question 3:
    * The goal is to print out the users that exist in both the Data.users and 
    * Data.otherUsers. Two users are equal if all their attributes are equal.
    * 
    * Print out the list of users which exist in both Data.users and
    * Data.otherUsers. The printed list of useres should be sorted by state
    * in descending order.
    * */

    public static void main(String[] args) {
    	ArrayList<User> users = new ArrayList<User>(); 
    	ArrayList<User> otherUsers = new ArrayList<User>();

    	//assemble users data into arraylist users
        for(String str : Data.users) {
        	String[] tempData = (str.split(","));
        	//parsing all parameters for each user
        	User tempUser = new User(tempData[0], tempData[1], tempData[2], tempData[3], tempData[4], tempData[5], tempData[6]); 

        	users.add(tempUser); //insert each of the users in a list
        }
        
        //assemble otherusers data into arraylist otherusers
        for(String str : Data.otherUsers) {
        	String[] tempData = (str.split(","));
        	//parsing all parameters for each user
        	User tempUser = new User(tempData[0], tempData[1], tempData[2], tempData[3], tempData[4], tempData[5], tempData[6]); 

        	otherUsers.add(tempUser); //insert each of the users in a list
        }
        
        //casting users and otherusers into a hashmap
        //use key = First/Last Name and value = the actual User Object
		HashMap<String, User> usersHash = new HashMap<String, User>();//all states - unique
		//first String is Key/state, second is Integer/number of population
		
		for(User use : users) {
			//populates hashmap with first/last as the key, followed by the user object address
			usersHash.put((use.getFirst() + " " + use.getLast()), use);
		}
		
		HashMap<String, User> otherUsersHash = new HashMap<String, User>();//all states - unique
		//first String is Key/state, second is Integer/number of population
		
		for(User use : otherUsers) {
			//populates hashmap with first/last as the key, followed by the user object address
			otherUsersHash.put((use.getFirst() + " " + use.getLast()), use);
		}
		
		//integer is the address of the User Object
		HashMap<String, Object> sortedUsersHash = returnSorted(usersHash, 1);
        HashMap<String, Object> sortedOtherUsersHash = returnSorted(otherUsersHash, 1);
		                
        //get object using key from the sorted hash from users and otherusers
        //usersObj.compareTo(otherUsers)
        //if == 0, the same, print out
        ArrayList<User> finalList = new ArrayList<User>(); 
        
        //for every string in the sorted users, search through the otherUsers
        //as they're both in alphabetical, should take less time
        //if match, exit inner loop and add to list of final duplicates
        for(String s : sortedUsersHash.keySet()) {
        	for(String x : sortedOtherUsersHash.keySet()) {
        		if(usersHash.get(s).compareTo(otherUsersHash.get(x)) == 0) {
        			finalList.add(usersHash.get(s));
        			break; //if even one match is found, then there's a duplicate. 
        					//to reduce complexity, exit the inner loop
        		}
        	}
        }
        //sort the list according to the state, which requires writing a new comparator
        Collections.sort(finalList, new newComparator());
        
        //iterates through the final list in order to print them out
        for(int i = 0; i <finalList.size(); i++) {
        	System.out.println(finalList.get(i).toString());
        }
    }
    
    //used for sorting by unique string-key and an object
    public static HashMap<String, Object> returnSorted(HashMap hash, int flip) {
		//copy over the hashmap into a list to sort
		List tempList = new LinkedList(hash.entrySet());
		
		//sorts using Collections.sort() but first has to redefine the compare() method - 
		//it gets the value of the key (map.entry) to compare to the second value (manually casting it as a comparable)
		Collections.sort(tempList, new Comparator() {
			public int compare(Object obj1, Object obj2) {return((Comparable) ((Map.Entry)(obj1)).getValue()).compareTo(((Map.Entry)(obj2)).getValue());}
		});
		
		if(flip == 0) {
			Collections.reverse(tempList); //now that it's sorted, just flip it
		}
		
		HashMap sortedMap = new LinkedHashMap();

		for(Iterator it = tempList.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			//key from the now-sorted list and matched up with the 
			//proper object from the original list
			sortedMap.put(entry.getKey(), hash.get(entry.getKey()));
		}
		
		return sortedMap;
	}
}

//comparator used to compare two states
class newComparator implements Comparator<User>{
	@Override
    public int compare(User u1, User u2) {
    	return u1.state.compareTo(u2.state);
    }
}