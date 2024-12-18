package controller;

import java.util.List;

import model.Admin;
import model.User;
import model.Guest;
import model.Event;
import model.Vendor;

public class AdminController {
	private Admin admin;
	
	public List<Event> viewAllEvents() {
        return admin.viewAllEvents();
    }
    
    public Event viewEventDetails(String eventId) {
       	return admin.viewEventDetails(eventId);
    }
    
    public void deleteEvent(String eventId) {
    	admin.deleteEvent(eventId);
    }
    
    public void deleteUser(String userId) {
    	admin.deleteUser(userId);
    }
    
    public List<User> getAllUsers(){
    	return admin.getAllUsers();
    }
    
    public List<Event> getAllEvents(){
    	return admin.getAllEvents();
    }
    
//    public List<Guest> getGuestsByTransactionId(String eventId){
//    	return admin.getGuestsByTransactionId(eventId);
//    }
//    
//    public List<Vendor> getVendorsByTransactionId(String eventId){
//    	return admin.getVendorsByTransactionId(eventId);
//    }
}
