package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import utils.SQLConnect;

public class EventOrganizer extends User {
	private String events_created;
	
	private static SQLConnect dbConnect = SQLConnect.getInstance(); 
	
	// Constructor
	public EventOrganizer(String id, String email, String name, String password, String role) {
		super(id, email, name, password, role);
		// TODO Auto-generated constructor stub
	}

	public String getEventsCreated() {
		return events_created;
	}

	public void setEventsCreated(String events_created) {
		this.events_created = events_created;
	}
	
	public String generateId() {
		int nextNum = 1;
		String query = "SELECT COUNT(*) AS event_count FROM event";

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				nextNum = rs.getInt("event_count") + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return String.format("EV%03d", nextNum);
	}
	
	public String createEvent(String eventName, String date, String location, String description, String organizerId) {
		String query = "INSERT INTO event (event_id, event_name, event_date, event_location, event_description, organizer_id) "
					 + "VALUES (? ? ? ? ? ?)";
		
		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
    		statement.setString(1, generateId());
    		statement.setString(2, eventName);
    		statement.setString(3, date);
    		statement.setString(4, location);
    		statement.setString(5, description);
    		statement.setString(6, organizerId);
    		
			int rowsInserted = statement.executeUpdate();

    		if (rowsInserted > 0) {
				System.out.println("Event created successfully");
				return "success";
			} else {
				System.out.println("Event creation failed");
				return "failed";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error occurred while sending invitation: " + e.getMessage());
			return "failed";
		}
	}
	
	public String checkCreateEventInput(String eventName, String eventDate, String eventLocation, String eventDescription) {
		if (eventName.isEmpty() || eventDate.isEmpty() || eventLocation.isEmpty() || eventDescription.isEmpty()) {
			return "All fields are required.";
		}

		try {
			LocalDate date = LocalDate.parse(eventDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			if (!date.isAfter(LocalDate.now())) {
				return "Event date must be in the future.";
			}
		} catch (DateTimeParseException e) {
			return "Invalid date format. Please use yyyy-MM-dd.";
		}

		if (eventLocation.length() < 5) {
			return "Event location must be at least 5 characters long.";
		}

		if (eventDescription.length() > 200) {
			return "Event description must be a maximum of 200 characters.";
		}

		return "success";
	}
	
	public List<Event> viewOrganizedEvents(String organizerId){
    	String query = "SELECT * FROM event WHERE organizer_id = ?";
    	List<Event> events = new ArrayList<>();
    	
    	try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
    		statement.setString(1, organizerId);
 	        try (ResultSet rs = statement.executeQuery()) {
 	            while(rs.next()) {
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

	public Event viewOrganizedEventDetails(String eventId) {
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
	
	public List<Guest> getGuests() throws SQLException {
		String query = "SELECT * FROM user WHERE user_role = 'Guest'";
		List<Guest> guests = new ArrayList<>();
		
		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			try(ResultSet rs = statement.executeQuery()){
				while (rs.next()) {
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
   	
   	public List<Vendor> getVendors() throws SQLException{
   		String query = "SELECT * FROM user WHERE user_role = 'Vendor";
		List<Vendor> vendors = new ArrayList<>();
		
		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Vendor vendor = new Vendor(rs.getString("user_id"),
			  			    		  rs.getString("user_email"),
			  			    		  rs.getString("user_name"),
			  			    		  rs.getString("user_password"),
			  			    		  rs.getString("userRole"));
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
		String query = "INSERT INTO invitations (event_id, user_id, invitation_status, invitation_role) "
					 + "VALUES(?, ?, ?, ?)";
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
			return "An error occurred while sending the guest invitation.";
		}
	}
   	
	public String addGuest(String eventId, String userId) {
		User user = getUserById(userId);

		if (user == null) {
			return "User does not exist";
		}
		String query = "INSERT INTO invitations (event_id, user_id, invitation_status, invitation_role) "
					 + "VALUES(?, ?, ?, ?)";
		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, eventId);
			statement.setString(2, userId);
			statement.setString(3, "pending");
			statement.setString(4, "Guest");

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Guest invitation sent successfully!");
				return "success";
			} else {
				System.out.println("Failed to send invitation.");
				return "failed";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("An error occurred while sending the guest invitation.");
			return "failed";
		}
	}
   	
   	public String editEventName(String eventId, String eventName) {
   		String query = "UPDATE event SET event_name = ? WHERE event_id = ? AND organizer_id = ?";
   		
   		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, eventName);
			statement.setString(2, eventId);
			statement.setString(3, getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return "failed";
		}
		return "success";
    }
	
}
