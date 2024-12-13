package controller;

import java.sql.SQLException;
import java.util.List;

import model.Event;
import model.Invitation;
import model.Vendor;

public class VendorController {
	public String acceptInvitation(String eventId, String userId) {

		return Invitation.acceptInvitation(eventId, userId);
	}

	public List<Invitation> viewInvitations(String user_id) throws SQLException {
		return Invitation.getInvitations(user_id);
	}

	public List<Event> viewAcceptedEvents(String email) {
		return Event.viewAcceptedEvents(email);

	}

	public String manageVendor(String description, String product) {
		String valid = checkManageVendorInput(description, product);

		if (valid == "Success") {
			return Vendor.updateVendor(description, product);
		} else {
			return valid;
		}
	}

	public String checkManageVendorInput(String description, String product) {
		if (description == "" || product == "") {
			return "All fields must be filled!";
		}
		if (description.length() > 200) {
			return "Maximum Description length exceeded 200 characters!";
		}
		return "Success";
	}

	public Event viewAcceptedEventDetails(String eventId) {
		return Event.viewOrganizedEventDetails(eventId);
	}
}
