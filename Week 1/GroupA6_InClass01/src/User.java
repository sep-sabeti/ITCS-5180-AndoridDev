

import java.util.Comparator;

    //creating a User class
public class User implements Comparable<User>{
    	String firstname; 
    	String lastname; 
    	int age; 
    	String email; 
    	String gender; //could be parsed into char depending on memory limit
    	String city; 
    	String state;
    	
    	//initialization
    	//first/last names, age, email, gender, city, state
    	public User(String first, String last, String age, String email, String gender, String city, String state) {
    		this.firstname = first; 
    		this.lastname = last;
    		this.age = Integer.parseInt(age); //type-casted to int here
    		this.email = email;
    		this.gender = gender;
    		this.city = city;
    		this.state = state;
    	}
    	
    	//all of the gettings for name, age, email, gender, city, state
    	public String getFirst() {
    		return this.firstname; 
    	}
    	public String getLast() {
    		return this.lastname;
    	}
    	public int getAge() {
    		return this.age;
    	}
    	public String getEmail() {
    		return this.email;
    	}
    	public String getGender() {
    		return this.gender;
    	}
    	public String getCity() {
    		return this.city;
    	}
    	public String getState() {
    		return this.state;
    	}
    	
    	//all of the setters for the information
    	public void setFirst(String first) {
    		this.firstname = first; 
    	}
    	public void setLast(String last) {
    		this.lastname = last;
    	}
    	public void setAge(int age) {
    		this.age = age;
    	}
    	public void setEmail(String email) {
    		this.email = email;
    	}
    	public void setGender(String gender) {
    		this.gender = gender;
    	}
    	public void setCity(String city) {
    		this.city = city;
    	}
    	public void setState(String state) {
    		this.state = state;
    	}
    	
    	@Override
    	public String toString() {
    		return(this.firstname + " " + this.lastname + "  " + this.age + "\t" + this.email + "\t" + this.gender + "\t" + this.city + "\t\t" + this.state);
    	}
    	
    	//used to compare two user objects - the higher the negative value, the less alike
    	//the two objects are in attributes
    	//for ex. two completely unlike Users will return -7, for the 7 unalike attributes
    	@Override
    	public int compareTo(User otherUser) {
    		return Comparator.comparing(User::getFirst)
    				.thenComparing(User::getLast)
    				.thenComparing(User::getAge)
    				.thenComparing(User::getEmail)
    				.thenComparing(User::getGender)
    				.thenComparing(User::getCity)
    				.thenComparing(User::getState)
    				.compare(this, otherUser);
    	}
    }

