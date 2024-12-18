package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.SQLConnect;

public class Vendor extends User{
	// asumsi: accepted_invitations adalah list invitation_id dengan status "accepted"
	private List<String> accepted_invitations;
	
	private static SQLConnect dbConnect = SQLConnect.getInstance(); 

	public Vendor(String id, String email, String name, String password, String role) {
		super(id, email, name, password, role);
		this.accepted_invitations = new ArrayList<>();
	}

	public List<String> getAcceptedInvitations() {
		return accepted_invitations;
	}

	public void setAcceptedInvitations(List<String> accepted_invitations) {
		this.accepted_invitations = accepted_invitations;
	}
	
	public List<Vendor> getVendors() throws SQLException {
		List<Vendor> vendors = new ArrayList<>();
		String query = "SELECT * FROM vendors";

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Vendor vendor = new Vendor(rs.getString("user_id"),
											   rs.getString("user_name"),
											   rs.getString("user_email"),
											   rs.getString("user_password"),
											   rs.getString("user_role"));
					vendors.add(vendor);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vendors;
	}

	public String addVendor(String eventId, String userId) {
		User user = getUserById(userId);

		if (user == null) {
			return "User does not exist";
		}

		String query = "INSERT INTO invitation (event_id, user_id, invitation_status, invitation_role) VALUES(?, ?, ?, ?)";
		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, eventId);
			statement.setString(2, userId);
			statement.setString(3, "pending");
			statement.setString(4, "Vendor");

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				return "Vendor invitation sent successfully!";
			} else {
				return "Failed to send vendor invitation.";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "An error occurred while sending the vendor invitation.";
		}
	}

	public String updateVendor(String description, String product) {
		String query = "UPDATE vendors SET product_name = ?, product_description = ?";
		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, product);
			statement.setString(2, description);

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				return "Product successfully updated";
			} else {
				return "Failed to update account";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "An error occurred while updating the profile";
		}
	}
	
	public static String acceptInvitation(String eventId) {
		String query = "UPDATE invitaton SET invitation_status = ? "
					 + "WHERE event_id = ?";
			
		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, "accepted"); 
			statement.setString(2, eventId);
			int rowsAffected = statement.executeUpdate();
		    if (rowsAffected > 0) {
		    	System.out.println("Invitation accepted");
		        return "success";
		    } else {
		    	System.out.println("Failed to accept invitation");
		        return "failed";
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		    System.out.println("Error occured.");
		    return "failed";
		}
	}
	
	public List<Event> viewAcceptedEvents(String email) {
	    String query = "SELECT e.event_id, e.event_date, e.event_name, "
	    			 + "e.event_location, e.event_description, e.organizer_id "
	                 + "FROM invitation i "
	                 + "JOIN user u ON i.user_id = u.user_id "
	                 + "JOIN event e ON i.event_id = e.event_id "
	                 + "WHERE u.email = ? AND i.invitation_status = ?";

	    List<Event> events = new ArrayList<>();

	    try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
	        statement.setString(1, email);
	        statement.setString(2, "accepted");
	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	            	Event event = new Event(rs.getString("event_id"),
	            							rs.getString("event_name"),
	            							rs.getString("event_date"),
	            							rs.getString("event_location"),
	            							rs.getString("event_description"),
	            							rs.getString("organizer_id"));
	                events.add(event);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return events;
	}
}
