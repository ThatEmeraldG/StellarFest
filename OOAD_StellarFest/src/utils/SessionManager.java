package utils;

import model.User;

public class SessionManager {
	private static SessionManager instance;
	private User currUser;
	
	private SessionManager(User user) {
		this.currUser = user;
	}
	
	public static SessionManager getInstance() throws Error {
		if(instance == null) {
			throw new Error("You are not logged in!");
		}
		return instance;
	}
	
	public static void authenticate(User user) {
		instance = new SessionManager(user);
	}

	public User getCurrUser() {
		return currUser;
	}

	public void setCurrUser(User currUser) {
		this.currUser = currUser;
	}
	
	public void logout() {
		currUser = null;
	}
}
