package app.entities;

import java.util.ArrayList;
import java.util.List;

public class EventPlanner {
    private int eventId;
    private int ownerId;
    private boolean isOwner;
    private String dateAndTime;
    private String location;
    private String title;
    private String description;
    private List<User> participants;


    public EventPlanner(int eventId, User owner, String dateAndTime, String location, String title, String description) {
        this.eventId = eventId;
        this.ownerId = owner.getUserId();
        this.isOwner = true; // Assuming the creator is the owner
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.title = title;
        this.description = description;
        this.participants = new ArrayList<>(); // Initialize with an empty list
    }

    public EventPlanner(String dateAndTime, String location, String title, String description)
    {
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.title = title;
        this.description = description;
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

    public boolean isOwner() {
        return isOwner;
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
