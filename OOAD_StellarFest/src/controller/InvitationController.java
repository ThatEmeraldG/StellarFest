package controller;

import java.sql.SQLException;
import java.util.List;

import model.Invitation;

public class InvitationController {
	private String userId;
	
    public InvitationController(String userId) {
    	this.userId = userId;
    }
	
	public String sendInvitation(String email, String eventID) {
		
		return Invitation.sendInvitation(email, eventID);
		
	}
	
	public String acceptInvitation(String eventId) {
		return Invitation.acceptInvitation(eventId, eventId);
	}
	
	public List<Invitation> getInvitations(String email) throws SQLException{
		return Invitation.getInvitations(userId);
   }
}
