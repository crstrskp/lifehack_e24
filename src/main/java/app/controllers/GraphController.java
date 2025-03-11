package app.controllers;

import io.javalin.Javalin;

public class GraphController {

    public static void addRoutes(Javalin app) {
        app.get("/graph", ctx -> ctx.redirect("http://graph.tjorne.dk/"));
    }
}
