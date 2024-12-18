package controller;

import model.User;

public class UserController {
	private User user;
	
	public void register(String email, String name, String password, String role) {
		user.register(email, name, password, role);
	}
	
	public User login(String email, String password) {
		return user.login(email, password);
	}
	
    public String validateRegister(String username, String email, String password, String role) {
    	if (username.isEmpty() || password.isEmpty() || email.isEmpty() || role.isEmpty() ) {
    		return "All fields must be filled!";
    	}
    	if (password.length() < 5) {
    		return "Password length must be at least 5 letters!";
    	}
    	if (getUserByEmail(email) != null) {
    		return "Email already registered!";
    	}
    	if (getUserByName(username) != null) {
    		return "Username must be unique!";
    	}
    	return "failed";
    }

    public String validateLogin(String username, String password) {
    	if (username.isEmpty() || password.isEmpty()) {
    		return "All fields must be filled!";
    	}
    	return "failed";
    }
	
	public String updateProfile(String username, String email, String oldPassword, String newPassword) {
	    return user.updateProfile(username, email, oldPassword, newPassword);
	}
	
	public User getUserByName(String username) {
	    return user.getUserByName(username);
	}

	public User getUserByEmail(String email) {
		return user.getUserByEmail(email);
	}
	
	public boolean checkRegisterInput(String email, String name, String password) {
		return user.checkRegisterInput(email, name, password);
	}
	
	public boolean checkChangeProfile(String username, String email, String oldPassword, String newPassword) {
		if(email.isEmpty() || username.isEmpty() || newPassword.isEmpty()) {
			return false;
		} else if(getUserByName(username) != null) {
			return false;
		} else if(getUserByEmail(email) != null) {
			return false;
		} else if(newPassword.length() < 5) {
			return false;
		} else if(newPassword.equals(oldPassword)){
			return false;
		}
		return true;
	}
}
