package app.controllers;

import app.entities.EventPlanner;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.EventPlannerMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import jdk.jfr.Event;

import java.util.List;

public class EventPlannerController
{
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {
        app.get("/eventplanner", ctx -> index(ctx, connectionPool));
        app.get("/eventplanner/create", ctx -> ctx.render("/eventplanner/createevent.html"));
        app.post("/eventplanner/create", ctx -> createEvent(ctx, connectionPool));
        app.post("/eventplanner/delete", ctx -> deleteEvent(ctx, connectionPool));
        app.post("/eventplanner/leave", ctx -> leaveEvent(ctx, connectionPool));
        app.post("/eventplanner/join", ctx -> joinEvent(ctx, connectionPool));
    }

    private static void index(Context ctx, ConnectionPool connectionPool)
    {
        ctx.render("/eventplanner/index.html");
    }

    private static void createEvent(Context ctx, ConnectionPool connectionPool)
    {
        User currentuser = ctx.sessionAttribute("currentuser");
        String eventDate = ctx.formParam("eventdateandtime");
        String eventName = ctx.formParam("eventname");
        String eventLocation = ctx.formParam("eventlocation");
        String eventDescription = ctx.formParam("eventdescription");

        if (eventName.length() > 3) {
            try {
                if (eventName.length() > 3) {
                    EventPlanner newEvent = EventPlannerMapper.createEvent(currentuser, eventDate, eventLocation, eventName, eventDescription, connectionPool);
                    ctx.attribute("message", "Event created.");
                } else {
                    ctx.attribute("message", "Event must be at least 3 characters long");
                }
                List<EventPlanner> eventPlannerList = EventPlannerMapper.getAllEvents(currentuser.getUserName(), connectionPool);
                ctx.attribute("eventPlannerList", eventPlannerList);
                ctx.render("/eventplanner/index.html");

            } catch (DatabaseException e) {
                ctx.attribute("message", "Something went wrong, Try again");
                ctx.render("/eventplanner/createevent.html");
            }
        } else {
            ctx.attribute("message", "Event name must be at least 3 characters long");
            ctx.render("/eventplanner/createevent.html");
        }
    }

        public static void deleteEvent (Context ctx, ConnectionPool connectionPool){
        User currentUser = ctx.sessionAttribute("currentuser");
        int eventId = Integer.parseInt(ctx.formParam("eventid"));

        if (currentUser != null) {
            try {
                boolean isOwner = EventPlannerMapper.isEventOwner(eventId, currentUser.getUserId(), connectionPool);
                if (isOwner) {
                    EventPlannerMapper.deleteEvent(eventId, connectionPool);
                    ctx.attribute("message", "Event deleted.");
                } else {
                    ctx.attribute("message", "You are not the owner of this event.");
                }
            } catch (DatabaseException e) {
                ctx.attribute("message", "Something went wrong, try again.");
            }
        } else {
            ctx.attribute("message", "You must be logged in to delete an event.");
        }
        ctx.render("/eventplanner/index.html");
    }

        public static void leaveEvent (Context ctx, ConnectionPool connectionPool){
        int eventId = Integer.parseInt(ctx.formParam("eventid"));
        User currentUser = ctx.sessionAttribute("currentuser");

        if (currentUser != null) {
            try {
                boolean isParticipant = EventPlannerMapper.isUserParticipant(eventId, currentUser.getUserId(), connectionPool);
                if(isParticipant){
                    EventPlannerMapper.leaveEvent(eventId, currentUser, connectionPool);
                    ctx.attribute("message", "You have left the event.");
                } else {
                    ctx.attribute("message", "You are not a participant of this event.");
                }
                ctx.render("/eventplanner/index.html");
            } catch (DatabaseException e) {
                ctx.attribute("message", "Something went wrong, Try again");
                ctx.render("/eventplanner/index.html");
            }
        } else {
            ctx.attribute("message", "You must be logged in to leave an event.");
            ctx.render("/eventplanner/index.html");
        }
    }

        public static void joinEvent (Context ctx, ConnectionPool connectionPool){

        User currentUser = ctx.sessionAttribute("currentUser");
        int eventId = Integer.parseInt(ctx.formParam("eventid"));

        if (currentUser != null) {

            try {
                EventPlannerMapper.joinEvent(eventId, currentUser, connectionPool);
                ctx.attribute("message", "Event joined.");
                ctx.render("/eventplanner/index.html");
            } catch (DatabaseException e) {
                ctx.attribute("message", "Something went wrong, try again.");
                ctx.render("/eventplanner/index.html");
            }
        } else {

            ctx.attribute("message", "You must be logged in to join an event.");
            ctx.render("/eventplanner/index.html");
        }
    }

    }