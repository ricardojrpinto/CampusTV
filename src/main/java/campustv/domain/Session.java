package campustv.domain;

import java.io.Serializable;

public class Session implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String sessionID;   
    private long userID;   
    private String accessToken;
    private String email;
	private String createTime;    
    private String tokenTTL;
    
    
	public Session() {
		super();
	}

	public Session(String sessionID, long userID, String accessToken,
			String email, String createTime, String tokenTTL) {
		this.sessionID = sessionID;
		this.userID = userID;
		this.accessToken = accessToken;
		this.email = email;
		this.createTime = createTime;
		this.tokenTTL = tokenTTL;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTokenTTL() {
		return tokenTTL;
	}


	public void setTokenTTL(String tokenTTL) {
		this.tokenTTL = tokenTTL;
	}


	public long getUserID() {
		return userID;
	}



	
    

}
