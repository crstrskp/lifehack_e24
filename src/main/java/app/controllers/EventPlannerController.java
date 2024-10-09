package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class EventPlannerController
{
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {
        app.get("/eventplanner", ctx -> index(ctx, connectionPool));
    }

    private static void index(Context ctx, ConnectionPool connectionPool)
    {
        ctx.render("/eventplanner/index.html");
    }
}