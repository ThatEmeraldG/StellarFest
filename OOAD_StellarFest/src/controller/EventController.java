package controller;

import model.Event;

public class EventController {
	public static void createEvent(String eventName, String date, String location, String description, String organizerId){
   	 	Event.createEvent(eventName, date, location, description, organizerId);
	}
	
	public static Event viewEventDetails(String eventID){
   		return Event.viewEventDetails(eventID);
	}
	
}
