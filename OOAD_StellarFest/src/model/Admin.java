package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.SQLConnect;

public class Admin extends User{
	
	private static SQLConnect dbConnect = SQLConnect.getInstance(); 
	
	public Admin(String id, String email, String name, String password, String role) {
		super(id, email, name, password, role);
		// TODO Auto-generated constructor stub
	}
	
	public List<Event> viewAllEvents() {
		String query = "SELECT * FROM event";
		List<Event> events = new ArrayList<>();

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {

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
    
    public Event viewEventDetails(String eventId) {
    	String query = "SELECT * FROM event WHERE event_id = ?";
		Event event = null;

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, eventId);

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
    
    public void deleteEvent(String eventId) {
    	String query = "DELETE FROM event WHERE event_id = ?";
		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, eventId);

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Event successfully deleted.");
			} else {
				System.out.println("Failed to delete event.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void deleteUser(String user_id) {
    	String query = "DELETE FROM event WHERE user_id = ?";
    	try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
	        statement.setString(1, user_id);         
   
	        int rowsAffected = statement.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("User successfully deleted.");
	        } else {
	            System.out.println("Failed to delete user.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
    }
    
    public List<User> getAllUsers() {
        String query = "SELECT * FROM user";
        List<User> users = new ArrayList<>();
        
        try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	            	User user = new User(rs.getString("user_id"), 
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
    
    public List<Event> getAllEvents() {
		String query = "SELECT * FROM event";
		List<Event> events = new ArrayList<>();

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
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
    
//    public static List<Guest> getGuestsByTransactionId(String eventId){
//		String query = "SELECT i.user_id "
//	 				 + "FROM invitation i "
//	 				 + "JOIN user u ON i.user_id = u.user_id ";
//	 				 + "WHERE event_id = ? AND user_role = 'Guest'";
//    	List<Guest> guests = new ArrayList<>();
//    	
//    	try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
//			statement.setString(1, eventId);
//
//			try (ResultSet rs = statement.executeQuery()) {
//				while (rs.next()) {
//					Guest guest = new Guest(rs.getString("user_id"), 
//									rs.getString("user_email"),
//									rs.getString("user_name"),
//									rs.getString("user_password"),
//									rs.getString("user_role"));
//					guests.add(guest);
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return guests;
//    }
//    
//    public static List<Vendor> getVendorsByTransactionId(String eventId){
//    	String query = "SELECT i.user_id "
//    				 + "FROM invitation i "
//    				 + "JOIN user u ON i.user_id = u.user_id ";
//    				 + "WHERE event_id = ? AND user_role = 'Vendor'";
//    	List<Vendor> vendors = new ArrayList<>();
//    	
//    	try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
//			statement.setString(1, eventId);
//
//			try (ResultSet rs = statement.executeQuery()) {
//				while (rs.next()) {
//					Vendor vendor = new Vendor(rs.getString("user_id"), 
//									rs.getString("user_email"),
//									rs.getString("user_name"),
//									rs.getString("user_password"),
//									rs.getString("user_role"));
//					vendors.add(vendor);
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return vendors;
//    }
}
