package campustv.session;

import java.io.Serializable;

public class Session implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String sessionID;   
    private long userID;      
    private boolean active;
    private boolean isSecure;

	private String createTime;    
    private String lastAccessedTime;
    
    
	public Session() {
		super();
	}


	public String getSessionID() {
		return sessionID;
	}


	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}


	public long getUserId() {
		return userID;
	}


	public void setUserID(long userID) {
		this.userID = userID;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}
	
    public boolean isSecure() {
		return isSecure;
	}


	public void setSecure(boolean isSecure) {
		this.isSecure = isSecure;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastAccessedTime() {
		return lastAccessedTime;
	}


	public void setLastAccessedTime(String lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}  
 
	
    

}
