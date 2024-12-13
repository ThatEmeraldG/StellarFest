package controller;

import java.util.List;

import model.Event;

public class EventController {
	public void createEvent(String eventName, String date, String location, String description, String organizerId) {
   	 	Event.createEvent(eventName, date, location, description, organizerId);
	}
	
	public List<Event> getAllEvent(){
		return Event.getAllEvent();
	}
	
	public Event viewOrganizedEventDetails(String eventID) {
   		return Event.viewOrganizedEventDetails(eventID);
	}
   
	public void deleteEvent(String id) {
		Event.deleteEvent(id);
	}
}
