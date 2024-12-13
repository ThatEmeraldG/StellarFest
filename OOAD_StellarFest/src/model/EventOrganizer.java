package model;

public class EventOrganizer extends User {
	private String events_created;

	public EventOrganizer() {
		super();
	}

	public String getEventsCreated() {
		return events_created;
	}

	public void setEventsCreated(String events_created) {
		this.events_created = events_created;
	}
	
	
}
