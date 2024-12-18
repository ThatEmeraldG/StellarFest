package controller;

import java.sql.SQLException;
import java.util.List;

import model.Event;
import model.EventOrganizer;
import model.Guest;
import model.User;
import model.Vendor;

public class EventOrganizerController {
	private EventOrganizer eventOrganizer;
	
	public String createEvent(String eventName, String date, String location, String description, String organizerId) {
   	 	return eventOrganizer.createEvent(eventName, date, location, description, organizerId);
    }
	
	public List<Event> viewOrganizedEvents(String userId){
	   	return eventOrganizer.viewOrganizedEvents(userId);
	}
   
	public Event viewOrganizedEventDetails(String eventId) {
   		return eventOrganizer.viewOrganizedEventDetails(eventId);
   	}
   
   	public List<Guest> getGuests() throws SQLException {
       	return eventOrganizer.getGuests();
   	}
   	
   	public List<Vendor> getVendors() throws SQLException{
   		return eventOrganizer.getVendors();
   	}
   	
   	public String addVendor(String eventId, String userId) {
       	return eventOrganizer.addVendor(eventId, userId);
   	}
   
   	public String addGuest(String event_id, String user_id) {
   		return eventOrganizer.addGuest(event_id, user_id);
	}
   	
   	public String editEventName(String eventId, String eventName) {
    	return eventOrganizer.editEventName(eventId, eventName);
    }
   	
   	public void checkCreateEventInput(String eventName, String date, String location, String description) {
   		eventOrganizer.checkCreateEventInput(eventName, date, location, description);
   	}
   	
   	public void checkAddVendorInput(String vendorId) {
   		
   	}
   	
   	public void checkAddGuestInput(String vendorId) {
   		
   	}
    
}
