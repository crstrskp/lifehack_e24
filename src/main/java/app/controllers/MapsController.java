package app.controllers;

import io.javalin.Javalin;

public class MapsController {
    public static void addRoutes(Javalin app) {
        app.get("/pacman", ctx -> ctx.redirect("http://pacman.tjorne.dk/"));
    }
}
