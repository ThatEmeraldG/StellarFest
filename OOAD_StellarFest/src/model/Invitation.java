package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.SQLConnect;

public class Invitation {
	private String invitation_id;
	private String event_id;
	private String user_id;
	private String invitation_status;
	private String invitation_role;
	
	private static SQLConnect dbConnect = SQLConnect.getInstance(); 
	
	// Constructor
	public Invitation(String invitation_id, String event_id, String user_id, String invitation_status,
			String invitation_role) {
		super();
		this.invitation_id = invitation_id;
		this.event_id = event_id;
		this.user_id = user_id;
		this.invitation_status = invitation_status;
		this.invitation_role = invitation_role;
	}
	
	// Getters and Setters
	public String getInvitationId() {
		return invitation_id;
	}
	
	public void setInvitationId(String invitation_id) {
		this.invitation_id = invitation_id;
	}
	public String getEventId() {
		return event_id;
	}
	public void setEventId(String event_id) {
		this.event_id = event_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUserId(String user_id) {
		this.user_id = user_id;
	}
	public String getInvitationStatus() {
		return invitation_status;
	}
	public void setInvitationStatus(String invitation_status) {
		this.invitation_status = invitation_status;
	}
	public String getInvitationRole() {
		return invitation_role;
	}
	public void setInvitationRole(String invitation_role) {
		this.invitation_role = invitation_role;
	}
	
	public String generateId() {
		int nextNum = 1;
		String query = "SELECT COUNT(*) AS invitation_count FROM invitation";

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				nextNum = rs.getInt("invitation_count") + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return String.format("INV%03d", nextNum);
	}

	public String sendInvitation(String email, String eventID) {

		String query = "INSERT INTO invitation (invitation_id, event_id, user_id, "
					 + "invitation_role, invitation_status) "
					 + "VALUES (?, ?, ?, ?, ?)";

		User user = new User();
		user.getUserByEmail(email);

		String invitationId = generateId();
		String userId = user.getId();
		String role = user.getRole();
		String status = "pending";

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, invitationId);
			statement.setString(2, eventID);
			statement.setString(3, userId);
			statement.setString(4, role);
			statement.setString(5, status);

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				System.out.println("Invitation sent successfully to " + email);
				return "success";
			} else {
				System.out.println("Failed to send invitation to " + email);
				return "failed";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error occurred while sending invitation: " + e.getMessage());
			return "failed";
		}
	}

	public String acceptInvitation(String eventId) {
		String query = "UPDATE invitation SET invitation_status = ? "
						+ "WHERE event_id = ? AND user_id = ?";

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, "accepted");
			statement.setString(2, eventId);
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Invitation accepted");
				return "success";
			} else {
				System.out.println("Invitation unable to be accepted");
				return "failed";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error occured");
			return "failed";
		}
	}

	public List<Invitation> getInvitations(String email) throws SQLException {
		String query = "SELECT * FROM invitation WHERE user_email = ?";
		List<Invitation> invitations = new ArrayList<>();

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, email);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Invitation invitation = new Invitation(rs.getString("invitation_id"),
														   rs.getString("event_id"),
														   rs.getString("user_id"),
														   rs.getString("invitation_status"),
														   rs.getString("invitation_role"));
					invitations.add(invitation);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return invitations;
	}
	
}