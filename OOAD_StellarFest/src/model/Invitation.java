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
	
	public Invitation() {
		super();
	}
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
	
	public static String generateId() {
		String prefix = "IN";
		int nextNum = 1;

		String query = "SELECT COUNT(*) AS invitation_count FROM invitations";

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				nextNum = rs.getInt("invitation_count") + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return String.format("%s%03d", prefix, nextNum);
	}

	public static String sendInvitation(String email, String eventID) {

		String query = "INSERT INTO invitations (invitation_id, event_id, user_id, invitation_role, invitation_status) VALUES (?, ?, ?, ?, ?)";

		User u = User.getUserByEmail(email);

		String invitation_id = Invitation.generateId();
		String user_id = u.getId();
		String role = u.getRole();
		String status = "pending";

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, invitation_id);
			statement.setString(2, eventID);
			statement.setString(3, user_id);
			statement.setString(4, role);
			statement.setString(5, status);

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				return "Invitation sent successfully to " + email;
			} else {
				return "Failed to send invitation to " + email;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error occurred while sending invitation: " + e.getMessage();
		}

	}

	public static String acceptInvitation(String eventId, String userId) {

		String query = "UPDATE invitatons SET invitation_status = ? WHERE user_id = ? AND event_id = ?";

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, "accepted");
			statement.setString(2, userId);
			statement.setString(3, eventId);
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				return "Invitation accepted";
			} else {
				return "Invitation unable to be accepted";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error occured";
		}
	}

	public static List<Invitation> getInvitations(String userId) throws SQLException {

		String query = "SELECT * FROM invitations WHERE user_id = ?";
		List<Invitation> invitations = new ArrayList<>();

		try (PreparedStatement statement = dbConnect.preparedStatement(query)) {
			statement.setString(1, userId);
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					Invitation invitation = new Invitation();
					invitation.setEventId(rs.getString("event_id"));
					invitation.setInvitationId(rs.getString("invitation_id"));
					invitation.setInvitationRole(rs.getString("invitation_role"));
					invitation.setInvitationStatus(rs.getString("invitation_status"));
					invitation.setUserId(rs.getString("user_id"));
					invitations.add(invitation);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return invitations;
	}
	
}
