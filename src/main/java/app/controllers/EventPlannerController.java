package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.EventPlannerMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class EventPlannerController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/eventplanner", ctx -> index(ctx, connectionPool));
        app.post("/eventplanner/create", ctx -> createEvent(ctx, connectionPool));
        app.post("/eventplanner/delete", ctx -> deleteEvent(ctx, connectionPool));
        app.post("/eventplanner/leave", ctx -> leaveEvent(ctx, connectionPool));
    }

    private static void index(Context ctx, ConnectionPool connectionPool) {
        ctx.render("/eventplanner/index.html");
    }

    private static void createEvent(Context ctx, ConnectionPool connectionPool) {
        Boolean ownerId = Boolean.valueOf(ctx.formParam("ownerid"));
        String eventDate = ctx.formParam("eventdateandtime");
        String eventName = ctx.formParam("eventname");
        String eventLocation = ctx.formParam("eventlocation");
        String eventDescription = ctx.formParam("eventdescription");

        if (eventName.length() > 3) {
            try {
                EventPlannerMapper.createEvent(ownerId, eventDate, eventName, eventLocation, eventDescription, connectionPool);
                ctx.attribute("message", "Event created.");
                ctx.render("/eventplanner/index.html");
            } catch (DatabaseException e) {
                ctx.attribute("message", "Something went wrong, Try again");
                ctx.render("/eventplanner/index.html");
            }
        } else {
            ctx.attribute("message", "Event name must be at least 3 characters long");
            ctx.render("/eventplanner/index.html");
        }
    }

    public static void deleteEvent(Context ctx, ConnectionPool connectionPool) {
        int eventId = Integer.parseInt(ctx.formParam("eventid"));
        try {
            EventPlannerMapper.deleteEvent(eventId, connectionPool);
            ctx.attribute("message", "Event deleted.");
            ctx.render("/eventplanner/index.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Something went wrong, Try again");
            ctx.render("/eventplanner/index.html");
        }
    }

    public static void leaveEvent(Context ctx, ConnectionPool connectionPool) {
        int eventId = Integer.parseInt(ctx.formParam("eventid"));
        User currentUser = ctx.sessionAttribute("currentUser");

        if(currentUser != null) {
            try {
                EventPlannerMapper.leaveEvent(eventId, currentUser, connectionPool);
                ctx.attribute("message", "You have left the event.");
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

}