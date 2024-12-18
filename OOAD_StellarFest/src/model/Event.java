package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.SQLConnect;

public class Event {
	private String event_id;
	private String event_name;
	private String event_date;
	private String event_location;
	private String event_description;
	private String organizer_id;
	
	private static SQLConnect dbConnect = SQLConnect.getInstance(); 
	
	public Event(String event_id, String event_name, String event_date, String event_location, String event_description,
			String organizer_id) {
		this.event_id = event_id;
		this.event_name = event_name;
		this.event_date = event_date;
		this.event_location = event_location;
		this.event_description = event_description;
		this.organizer_id = organizer_id;
	}

	public String getEventId() {
		return event_id;
	}

	public void setEventId(String EventId) {
		this.event_id = EventId;
	}

	public String getEventName() {
		return event_name;
	}

	public void setEventName(String EventName) {
		this.event_name = EventName;
	}

	public String getEventDate() {
		return event_date;
	}

	public void setEventDate(String EventDate) {
		this.event_date = EventDate;
	}

	public String getEventLocation() {
		return event_location;
	}

	public void setEventLocation(String EventLocation) {
		this.event_location = EventLocation;
	}

	public String getEventDescription() {
		return event_description;
	}

	public void setEventDescription(String EventDescription) {
		this.event_description = EventDescription;
	}

	public String getOrganizerId() {
		return organizer_id;
	}

	public void setOrganizerId(String OrganizerId) {
		this.organizer_id = OrganizerId;
	}
	
	public static void createEvent(String eventName, String date, String location, String description, String organizerId) {
		String query = "INSERT INTO event (event_name, event_date, event_location, event_description, organizer_id) "
				+ "VALUES(?, ?, ?, ?, ?)";
		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, eventName);
			statement.setString(2, date);
			statement.setString(3, location);
			statement.setString(4, description);
			statement.setString(5, organizerId);

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Event created successfully!");
			} else {
				System.out.println("Failed to create event.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Event viewEventDetails(String eventId) {
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
	
//	public static List<Event> viewOrganizedEvent(String OrganizerId){
//    	String query = "SELECT * FROM event WHERE organizer_id = ?";
//    	List<Event> organizedevent = new ArrayList<>();
//    	
//    	try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
//    		statement.setString(1, OrganizerId);
// 	        try (ResultSet rs = statement.executeQuery()) {
// 	            while(rs.next()) {
// 	            	Event event = new Event(rs.getString("event_id"),
//			  			    				rs.getString("event_name"),
//			  			    				rs.getString("event_date"),
//			  			    				rs.getString("event_location"),
//			  			    				rs.getString("event_description"),
//			  			    				rs.getString("organizer_id"));
// 	                organizedevent.add(event);
// 	            }
// 	        }
// 	    } catch (SQLException e) {
// 	        e.printStackTrace();
// 	    }
//        return organizedevent;
//    }
//
//	public static Event viewOrganizedEventDetails(String eventId) {
//		String query = "SELECT * FROM event WHERE event_id = ?";
//		Event event = null;
//
//		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
//			statement.setString(1, eventId);
//
//			try (ResultSet rs = statement.executeQuery()) {
//				if (rs.next()) {
//					event = new Event(rs.getString("event_id"),
//			  			    		  rs.getString("event_name"),
//			  			    		  rs.getString("event_date"),
//			  			    		  rs.getString("event_location"),
//			  			    		  rs.getString("event_description"),
//			  			    		  rs.getString("organizer_id"));
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return event;
//	}
	
//	public static List<Event> viewAcceptedEvent(String email) {
//	    String query = "SELECT e.event_id, e.event_date, e.event_name, "
//	    			 + "e.event_location, e.event_description, e.organizer_id "
//	                 + "FROM invitations i "
//	                 + "JOIN users u ON i.user_id = u.user_id "
//	                 + "JOIN event e ON i.event_id = e.event_id "
//	                 + "WHERE u.email = ? AND i.invitation_status = ?";
//
//	    List<Event> events = new ArrayList<>();
//
//	    try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
//	        statement.setString(1, email); 
//	        statement.setString(2, "accepted"); 
//	        try (ResultSet rs = statement.executeQuery()) {
//	            while (rs.next()) {
//	            	Event event = new Event(rs.getString("event_id"),
//	            							rs.getString("event_name"),
//	            							rs.getString("event_date"),
//	            							rs.getString("event_location"),
//	            							rs.getString("event_description"),
//	            							rs.getString("organizer_id"));
//	                events.add(event);
//	            }
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    return events;
//	}
	
}
