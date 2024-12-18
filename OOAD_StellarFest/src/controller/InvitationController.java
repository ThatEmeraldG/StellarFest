package controller;

import java.sql.SQLException;
import java.util.List;

import model.Invitation;

public class InvitationController {
	private Invitation invitation;
	
	public String sendInvitation(String email, String eventId) {
		return invitation.sendInvitation(email, eventId);
	}
	
	public String acceptInvitation(String eventId) {
		return invitation.acceptInvitation(eventId);
	}
	
	public List<Invitation> getInvitations(String email) throws SQLException{
		return invitation.getInvitations(email);
   }
}
