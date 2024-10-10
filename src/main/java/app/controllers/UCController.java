package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UCController
{
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {
        app.get("UC/index", ctx -> index(ctx, connectionPool));
    }

    private static void index(Context ctx, ConnectionPool connectionPool)
    {
        ctx.render("/UC/index.html");
    }
}