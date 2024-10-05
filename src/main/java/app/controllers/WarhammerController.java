package app.controllers;

import io.javalin.Javalin;

public class WarhammerController
{
    public static void addRoutes(Javalin app) {
        app.get("/warhammer", ctx -> ctx.redirect("http://bases.tjorne.dk/"));
    }
}
