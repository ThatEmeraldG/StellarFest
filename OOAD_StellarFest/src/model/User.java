package model;

public class User {
	private String user_id;
	private String user_email;
	private String user_name;
	private String user_password;
	private String user_role;
	
	// Constructor
	public User(String user_id, String user_email, String user_name, String user_password, String user_role) {
		super();
		this.user_id = user_id;
		this.user_email = user_email;
		this.user_name = user_name;
		this.user_password = user_password;
		this.user_role = user_role;
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
	
	
}
