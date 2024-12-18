package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.SQLConnect;

public class User {
	private String user_id;
	private String user_email;
	private String user_name;
	private String user_password;
	private String user_role;
	
	private static SQLConnect dbConnect = SQLConnect.getInstance();
	
	// Constructor
	public User(String id, String email, String name, String password, String role) {
		this.user_id = id;
		this.user_email = email;
		this.user_name = name;
		this.user_password = password;
		this.user_role = role;
	}
	
	public User() {
		super();
	}
	
	// Getters and Setters
	public String getId() {
		return user_id;
	}

	public void setId(String user_id) {
		this.user_id = user_id;
	}

	public String getEmail() {
		return user_email;
	}

	public void setEmail(String user_email) {
		this.user_email = user_email;
	}

	public String getName() {
		return user_name;
	}

	public void setName(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return user_password;
	}

	public void setPassword(String user_password) {
		this.user_password = user_password;
	}

	public String getRole() {
		return user_role;
	}

	public void setRole(String user_role) {
		this.user_role = user_role;
	}
	
	public void register(String email, String name, String password, String role) {
		boolean inputTest = checkRegisterInput(email, name, password);
		
		if (inputTest == false) {
			//error log here?
			return;
		}
		
		int nextNum = 0; 

        String query = "SELECT COUNT(*) AS user_count FROM user";

        try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nextNum = rs.getInt("user_count") + 1; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		String currID = String.format("UID%03d", nextNum);
		
        query = "INSERT INTO user (user_id, user_email, user_name, user_password, user_role) "
        		+ "VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
        	statement.setString(1, currID);
            statement.setString(2, email);
            statement.setString(3, name);
            statement.setString(4, password);
            statement.setString(5, role);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User registered successfully!");
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean validateLogin(String name, String password) {
    	if (name.isEmpty() || password.isEmpty()) {
    		return false;
    	}
    	
    	return true;
    }
	
    public User login(String email, String password) {
		String query = "SELECT * FROM user WHERE user_email = ? AND user_password = ?";
		User user = null;
		
		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
	        statement.setString(1, email);
	        statement.setString(2, password);

	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	            	user = new User(rs.getString("user_id"), 
									rs.getString("user_email"),
									rs.getString("user_name"),
									rs.getString("user_password"),
									rs.getString("user_role"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return user;
	}
	
    public boolean checkRegisterInput(String email, String name, String password) {
		if (email.isEmpty()) {
			return false;
		}
		if (name.isEmpty()) {
			return false;
		}
		if (password.length() <= 5) {
			return false;
		}
		int count = 0;

		String query = "SELECT COUNT(*) AS user_count FROM user WHERE user_email = ?";

        try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, email);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                count = rs.getInt("user_count"); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		if (count > 0) {
			return false;
		}

		query = "SELECT COUNT(*) AS user_count FROM user WHERE user_name = ?";

        try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, name);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                count = rs.getInt("user_count"); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		if (count > 0) {
			return false;
		}
		return true;
	}
	
	public User getUserById(String user_id) {
		String query = "SELECT * FROM user WHERE user_id = ?";
		User user = null;

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, user_id);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					user = new User(rs.getString("user_id"), 
									rs.getString("user_email"),
									rs.getString("user_name"),
									rs.getString("user_password"),
									rs.getString("user_role"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User getUserByName(String username) {
	    String query = "SELECT * FROM user WHERE user_name = ?";
	    User user = null;

	    try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
	        statement.setString(1, username);

	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                user = new User(rs.getString("user_id"), 
									rs.getString("user_email"),
									rs.getString("user_name"),
									rs.getString("user_password"),
									rs.getString("user_role"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return user;
	}

	
	public User getUserByEmail(String email) {
		String query = "SELECT * FROM user WHERE user_email = ?";
		User user = null;

	    try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
	        statement.setString(1, email);

	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	            	user = new User(rs.getString("user_id"), 
									rs.getString("user_email"),
									rs.getString("user_name"),
									rs.getString("user_password"),
									rs.getString("user_role"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return user;
	}
	
	public List<User> viewAlluser(){
    	String query = "SELECT * FROM user";
    	List<User> users = new ArrayList<>();
    	User user = null;
    	
    	try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
 	        try (ResultSet rs = statement.executeQuery()) {
 	            while(rs.next()) {
 	            	user = new User(rs.getString("user_id"), 
 	            					rs.getString("user_email"),
 	            					rs.getString("user_name"),
 	            					rs.getString("user_password"),
 	            					rs.getString("user_role"));
 	                users.add(user);

 	            }
 	        }
 	    } catch (SQLException e) {
 	        e.printStackTrace();
 	    }
    	return users;
    }
    
    public String updateProfile(String username, String email, String oldPassword, String newPassword) {
	    User user = getUserByName(username);
	    if (user == null) {
	        return "User not found";
	    }

	    if (!user.getPassword().equals(oldPassword)) {
	        return "Invalid old password";
	    }

	    boolean valid = checkChangeProfile(username, email, oldPassword, newPassword);
	    if (!valid) {
	        return "Cannot update profile due to invalid inputs";
	    }

	    String query = "UPDATE user SET user_name = ?, user_email = ?, user_password = ? WHERE user_name = ?";
	    try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
	        statement.setString(1, username);         
	        statement.setString(2, email);         
	        statement.setString(3, newPassword);  
	        statement.setString(4, user.getName()); 

	        int rowsAffected = statement.executeUpdate();
	        if (rowsAffected > 0) {
	            return "Account successfully updated";
	        } else {
	            return "Failed to update account";
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "An error occurred while updating the profile";
	    }
	}
	
	public boolean checkChangeProfile(String username, String email, String oldPassword, String newPassword) {
		if(email == "" || username == "" || newPassword == "") {
			return false;
		} else if(getUserByName(username)!=null) {
			return false;
		} else if(getUserByEmail(email)!=null) {
			return false;
		} else if(newPassword.length() < 5) {
			return false;
		}
		return true;
	}
    
}
