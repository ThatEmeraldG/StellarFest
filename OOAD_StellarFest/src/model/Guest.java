package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.SQLConnect;

public class Guest extends User {
	// asumsi: accepted_invitations adalah list invitation_id dengan status "accepted"
	private List<String> accepted_invitations;
	
	private static SQLConnect dbConnect = SQLConnect.getInstance(); 

	public Guest(String id, String email, String name, String password, String role) {
		super(id, email, name, password, role);
		this.accepted_invitations = new ArrayList<>();
	}

	public List<String> getAcceptedInvitations() {
		return accepted_invitations;
	}

	public void setAcceptedInvitations(List<String> accepted_invitations) {
		this.accepted_invitations = accepted_invitations;
	}
	
	public static List<Guest> getGuests() throws SQLException {
        List<Guest> guests = new ArrayList<>();
        String query = "SELECT * FROM user "
        			 + "WHERE user_role = 'Guest'";
        
        try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
 	        try (ResultSet rs = statement.executeQuery()) {
 	            while(rs.next()) {
 	                Guest guest = new Guest(rs.getString("user_id"),
 	                						rs.getString("user_email"),
 	                						rs.getString("user_name"),
 	                						rs.getString("user_password"),
 	                						rs.getString("user_role"));
 	                guests.add(guest);
 	            }
 	        }
 	    } catch (SQLException e) {
 	        e.printStackTrace();
 	    }
        return guests;
    }
	
	public static List<Invitation> viewInvitations(String userId){
    	List<Invitation> invitations = new ArrayList<>();
    	
    	String query = "SELECT * FROM invitation "
    				 + "WHERE user_id = ? AND invitation_status = ?";
    	
    	 try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
 	    	statement.setString(1, userId);
 	    	statement.setString(2, "pending");
  	        try (ResultSet rs = statement.executeQuery()) {
  	            while(rs.next()) {
  	                Invitation invitation = new Invitation(rs.getString("invitation_id"),
  	                									   rs.getString("event_id"),
  	                									   rs.getString("user_id"),
  	                									   rs.getString("invitation_status"),
  	                									   rs.getString("invitation_role"));
  	                invitations.add(invitation);
  	            }
  	        }
  	    } catch (SQLException e) {
  	        e.printStackTrace();
  	    }
 	    return invitations;
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
	
    public static List<Event> viewAcceptedEvents(String email) {
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
    
    public static Event viewAcceptedEventDetails(String eventId) {
    	String query = "SELECT e.event_id, e.event_date, e.event_name, "
   			 		 + "e.event_location, e.event_description, e.organizer_id "
   			 		 + "FROM invitation i "
   			 		 + "JOIN event e ON i.event_id = e.event_id "
             		 + "WHERE e.event_id = ? AND i.invitation_status = ?";
    	
    	Event event = null;
    	try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
	        statement.setString(1, eventId); 
	        statement.setString(2, "accepted"); 
	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	            	event = new Event(rs.getString("event_id"),
	            					  rs.getString("event_name"),
	            					  rs.getString("event_date"),
	            					  rs.getString("event_location"),
	            					  rs.getString("event_description"),
	            					  rs.getString("organizer_id"));
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return event;
    }
}
