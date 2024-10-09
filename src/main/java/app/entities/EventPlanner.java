package app.entities;

import java.util.List;

public class EventPlanner {
    private int eventId;
    private boolean ownerId;
    private String dateAndTime;
    private String location;
    private String title;
    private String description;
    private List<User> participants;

    public EventPlanner(int eventId, boolean ownerId, String dateAndTime, String location, String title, String description, List<User> participants) {
        this.eventId = eventId;
        this.ownerId = ownerId;
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.title = title;
        this.description = description;
        this.participants = participants;
    }

    public String getTitle() {
        return title;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public boolean getOwnerId() {
        return ownerId;
    }

    public String getLocation() {
        return location;
    }

    public int getEventId() {
        return eventId;
    }

    public String getDescription() {
        return description;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }
}
