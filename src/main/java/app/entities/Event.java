package app.entities;

import java.util.List;

public class Event {
    private int eventId;
    private int ownerId;
    private String dateAndTime;
    private String location;
    private String title;
    private String description;
    private List<User> participants;

    public Event(int eventId, int ownerId, String dateAndTime, String location, String title, String description, List<User> participants) {
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

    public int getOwnerId() {
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
