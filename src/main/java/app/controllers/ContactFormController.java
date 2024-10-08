package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class ContactFormController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/gruppeg/contact", ctx -> ctx.render("/gruppeg/contact"));
        app.post("/gruppeg/contact", ctx -> contactHandler(ctx));
    }

    private static void contactHandler(Context ctx) {
        String name = ctx.formParam("name");
        String email = ctx.formParam("email");
        String message = ctx.formParam("message");

        if (name == null || name.isEmpty()) {
            ctx.attribute("error", "Name is required.");
            ctx.render("/gruppeg/contact");
            return;
        }

        if (email == null || email.isEmpty()) {
            ctx.attribute("error", "Email is required.");
            ctx.render("/gruppeg/contact");
            return;
        }

        if (message == null || message.length() < 20 || message.length() > 250) {
            ctx.attribute("error", "Message must be between 20 and 250 characters.");
            ctx.render("/gruppeg/contact");
            return;
        }

        ctx.attribute("error", null);
        ctx.attribute("success", "Your message has been sent successfully!");

        ctx.render("/gruppeg/contact");

    }
}