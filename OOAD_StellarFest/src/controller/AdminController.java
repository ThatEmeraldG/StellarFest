package controller;

import java.util.List;

import model.Event;
import model.User;

public class AdminController {
	public List<Event> viewAllEvents() {
    	return Event.getAllEvent();
    }
    
    public Event viewEventDetails(String eventID) {
       	return Event.viewOrganizedEventDetails(eventID);
    }
    
    public String deleteEvent(String eventId) {
    	return User.deleteEvent(eventId);
    }
    
    public List<User> viewAllUsers(){
    	return User.viewAllUsers();
    }
    
    public String deleteUser(String user_id) {
    	return User.deleteUser(user_id);
    }
}
