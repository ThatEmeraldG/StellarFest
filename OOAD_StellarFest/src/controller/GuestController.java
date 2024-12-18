package controller;

import java.util.List;

import model.Event;
import model.Guest;
import model.Invitation;

public class GuestController {
	private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String acceptInvitation(String eventId) {
		return Guest.acceptInvitation(eventId);
	}
    
    public List<Event> viewAcceptedEvents(String email) {
        return Guest.viewAcceptedEvents(email);
    }
    
    public Event viewAcceptedEventDetails(String eventId) {
        return Guest.viewAcceptedEventDetails(eventId);
    }
    
    public List<Invitation> viewInvitations(){
    	return Guest.viewInvitations(userId);
    }
}
