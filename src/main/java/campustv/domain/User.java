package campustv.domain;

import java.util.Set;

public class User implements java.security.Principal{

	public enum Role {
	        PRODUCER, CONSUMER, VISITOR, ADMIN
	    };

    private long userID;          
    private String name;            
    private String email;   
    private Set<Role> roles;      
	    
	public User(long userID, String name, String email) {
		super();
		this.userID = userID;
		this.name = name;
		this.email = email;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	

}
