package app.controllers;

import io.javalin.Javalin;

public class GameDevController {

    public static void addRoutes(Javalin app) {
        app.get("/unity", ctx -> ctx.redirect("http://gamedev.tjorne.dk/"));
    }
}
