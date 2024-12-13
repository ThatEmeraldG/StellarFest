package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.SQLConnect;

public class Event {
	private String EventId;
	private String EventName;
	private String EventDate;
	private String EventLocation;
	private String EventDescription;
	private String OrganizerId;
	
	private static SQLConnect dbConnect = SQLConnect.getInstance(); 
	
	public Event() {
		super();
	}

	public String getEventId() {
		return EventId;
	}

	public void setEventId(String EventId) {
		this.EventId = EventId;
	}

	public String getEventName() {
		return EventName;
	}

	public void setEventName(String EventName) {
		this.EventName = EventName;
	}

	public String getEventDate() {
		return EventDate;
	}

	public void setEventDate(String EventDate) {
		this.EventDate = EventDate;
	}

	public String getEventLocation() {
		return EventLocation;
	}

	public void setEventLocation(String EventLocation) {
		this.EventLocation = EventLocation;
	}

	public String getEventDescription() {
		return EventDescription;
	}

	public void setEventDescription(String EventDescription) {
		this.EventDescription = EventDescription;
	}

	public String getOrganizerId() {
		return OrganizerId;
	}

	public void setOrganizerId(String OrganizerId) {
		this.OrganizerId = OrganizerId;
	}
	
	public static void createEvent(String eventName, String date, String location, String description,
			String organizerId) {
		String query = "INSERT INTO events (EventName, EventDate, EventLocation, EventDescription, OrganizerId) VALUES(?, ?, ?, ?, ?)";
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

	public static List<Event> getAllEvent() {
		String query = "SELECT * FROM events";
		List<Event> events = new ArrayList<>();

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {

			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Event event = new Event();
					event.setEventId(rs.getString("EventId"));
					event.setEventName(rs.getString("EventName"));
					event.setEventDate(rs.getString("EventDate"));
					event.setEventLocation(rs.getString("EventLocation"));
					event.setEventDescription(rs.getString("EventDescription"));
					event.setOrganizerId(rs.getString("OrganizerId"));

					events.add(event);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return events;
	}
	
	public static List<Event> viewOrganizedEvents(String OrganizerId){
    	String query = "SELECT * FROM events WHERE OrganizerId = ?";
    	List<Event> organizedEvents = new ArrayList<>();
    	
    	try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
    		statement.setString(1, OrganizerId);
 	        try (ResultSet rs = statement.executeQuery()) {
 	            while(rs.next()) {
 	                Event event = new Event();
 	                event.setEventDate(rs.getString("EventDate"));
 	                event.setEventDescription(rs.getString("EventDescription"));
 	                event.setEventId(rs.getString("EventId"));
 	                event.setEventLocation(rs.getString("EventLocation"));
 	                event.setEventName(rs.getString("EventName"));
 	                event.setOrganizerId(OrganizerId);
 	                organizedEvents.add(event);
 	            }
 	        }
 	    } catch (SQLException e) {
 	        e.printStackTrace();
 	    }
        return organizedEvents;
    }

	public static Event viewOrganizedEventDetails(String eventID) {
		String query = "SELECT * FROM events WHERE EventId = ?";
		Event event = null;

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, eventID);

			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next()) {
					event = new Event();
					event.setEventName(rs.getString("EventName"));
					event.setEventDate(rs.getString("EventDate"));
					event.setEventDescription(rs.getString("EventDescription"));
					event.setEventId(rs.getString("EventId"));
					event.setOrganizerId(rs.getString("OrganizerId"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return event;
	}

	public static void deleteEvent(String id) {
		String query = "DELETE FROM events WHERE EventId = ?";
		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, id);

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Event deleted successfully!");
			} else {
				System.out.println("Failed to delete event.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	public static String editEventName(String eventId, String EventName) {
		String query = "UPDATE events SET EventName = ? WHERE EventId = ?";
		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, EventName);
			statement.setString(2, eventId);
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				return "Event successfully updated";
			} else {
				return "Failed to update event";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "An error occurred while updating the event";
		}
	}
	
	public static List<Event> viewAcceptedEvents(String email) {
	    String query = "SELECT e.EventId, e.EventDate, e.EventName, e.EventLocation, "
	                 + "e.EventDescription, e.OrganizerId "
	                 + "FROM invitations i "
	                 + "JOIN users u ON i.user_id = u.user_id "
	                 + "JOIN events e ON i.EventId = e.EventId "
	                 + "WHERE u.email = ? AND i.invitation_status = ?";

	    List<Event> events = new ArrayList<>();

	    try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
	        statement.setString(1, email); 
	        statement.setString(2, "accepted"); 
	        try (ResultSet rs = statement.executeQuery()) {
	            while (rs.next()) {
	                Event event = new Event();
	                event.setEventId(rs.getString("EventId"));
	                event.setEventDate(rs.getString("EventDate"));
	                event.setEventName(rs.getString("EventName"));
	                event.setEventLocation(rs.getString("EventLocation"));
	                event.setEventDescription(rs.getString("EventDescription"));
	                event.setOrganizerId(rs.getString("OrganizerId"));
	                events.add(event);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return events;
	}
	
}
