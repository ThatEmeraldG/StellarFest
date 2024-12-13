package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.SQLConnect;

public class Vendor extends User{
	private String accepted_invitations;
	
	private static SQLConnect dbConnect = SQLConnect.getInstance(); 

	public Vendor() {
		super();
	}

	public String getAccepted_invitations() {
		return accepted_invitations;
	}

	public void setAccepted_invitations(String accepted_invitations) {
		this.accepted_invitations = accepted_invitations;
	}
	
	public static List<Vendor> getVendors() throws SQLException {
		List<Vendor> vendors = new ArrayList<>();
		String query = "SELECT * FROM vendors";

		try (PreparedStatement stmt = dbConnect.preparedStatement(query)) {

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Vendor vendor = new Vendor();
					vendor.setId(rs.getString("user_id"));
					vendor.setName(rs.getString("user_name"));
					vendor.setEmail(rs.getString("user_email"));
					vendor.setPassword(rs.getString("user_password"));
					vendor.setRole("user_role");
					vendors.add(vendor);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vendors;
	}

	public static String addVendor(String eventId, String userId) {
		User user = User.getUserById(userId);

		if (user == null) {
			return "Vendor does not exist";
		}

		String query = "INSERT INTO invitations (event_id, user_id, invitation_status, invitation_role) VALUES(?, ?, ?, ?)";
		try (PreparedStatement stmt = dbConnect.preparedStatement(query)) {
			stmt.setString(1, eventId);
			stmt.setString(2, userId);
			stmt.setString(3, "pending");
			stmt.setString(4, "Vendor");

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				return "Vendor invitation sent successfully!";
			} else {
				return "Failed to send vendor invitation.";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "An error occurred while sending the vendor invitation.";
		}
	}

	public static String updateVendor(String description, String product) {
		String query = "UPDATE vendors SET product_name = ?, product_description = ?";
		try (PreparedStatement stmt = dbConnect.preparedStatement(query)) {
			stmt.setString(1, product);
			stmt.setString(2, description);

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				return "Product successfully updated";
			} else {
				return "Failed to update account";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "An error occurred while updating the profile";
		}
	}
}