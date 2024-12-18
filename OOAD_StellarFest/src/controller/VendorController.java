package controller;

import java.util.List;

import model.Event;
import model.Invitation;
import model.Vendor;

public class VendorController {
	private Vendor vendor;
	
	public String acceptInvitation(String eventId) {
		return vendor.acceptInvitation(eventId);
	}

	public List<Event> viewAcceptedEvents(String email) {
		return vendor.viewAcceptedEvents(email);
	}

	public String manageVendor(String description, String product) {
		String valid = checkManageVendorInput(description, product);

		if (valid.equals("success")) {
			return vendor.updateVendor(description, product);
		} else {
			return valid;
		}
	}

	public String checkManageVendorInput(String description, String product) {
		if (description.isEmpty() || product.isEmpty()) {
			return "All fields must be filled!";
		}
		if (description.length() > 200) {
			return "Maximum Description length exceeded 200 characters!";
		}
		return "success";
	}
}
