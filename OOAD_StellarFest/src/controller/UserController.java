package controller;

import java.util.List;
import model.User;

public class UserController {
	
	public static void register(String username, String pass, String email, String role) {
		User.register(username, pass, email, role);
	}
	
	public void deleteUser(String id) {
        User.deleteUser(id);
    }
    
    public List<User> getAllUser() {
    	return User.getAllUser();
    }
    
    public String validateRegister(String name, String password, String email, String role) {
    	if (name.isEmpty() || password.isEmpty() || email.isEmpty() || role.isEmpty() ) {
    		return "All field must be filled!";
    	}
    	
    	if (password.length() < 5) {
    		return "Password length must be at least 5 letters!";
    	}
    	
    	if (getUserByEmail(email) != null) {
    		return "Email must be unique!";
    	}
    	
    	if (getUserByName(name) != null) {
    		return "Username must be unique!";
    	}
    	
    	return "";
    }

    public boolean validateLogin(String name, String password) {
    	if (name.isEmpty() || password.isEmpty()) {
    		return false;
    	}
    	
    	return true;
    }

	
	public User login(String name, String password) {
		return User.login(name, password);
	}
	
	public String updateProfile(String email, String name, String oldPassword, String newPassword) {
	    return User.updateProfile(email, name, oldPassword, newPassword);
	}

	
	public User getUserByName(String name) {
	    return User.getUserByName(name);
	}

	
	public User getUserByEmail(String email) {
		return User.getUserByEmail(email);
	}
	
	public boolean checkRegisterInput(String email, String name, String password) {
		if(email == "" || name == "" || password == "") {
			return false;
		} else if(getUserByName(name)!= null) {
			return false;
		} else if(getUserByEmail(email)!= null) {
			return false;
		} else if(password.length() < 5) {
			return false;
		}
			
		return true;
	}
	
	public boolean checkChangeProfile(String email, String name, String oldPassword, String newPassword) {
		if(email == "" || name == "" || newPassword == "") {
			return false;
		} else if(getUserByName(name)!= null) {
			return false;
		} else if(getUserByEmail(email)!= null) {
			return false;
		} else if(newPassword.length() < 5) {
			return false;
		}
		return true;
	}
}
