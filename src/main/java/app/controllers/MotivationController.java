package app.controllers;

import app.entities.Motivation;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.MotivationMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class MotivationController {

    public static void addRoutes(Javalin app, ConnectionPool pool) {
        app.get("/motivational", ctx -> showMotivation(ctx, pool));
        app.post("/motivation", ctx -> addMotivation(ctx, pool));
        app.post("/motivational/addtofavorites", ctx -> addToFavorites(ctx, pool));
        app.get("/motivational/addmotivation", ctx -> ctx.render("/motivational/newmotivational.html"));

    }


    private static void addMotivation(Context ctx, ConnectionPool pool) {

        String quoteTitel = ctx.formParam("quote-title");
        String quoteText = ctx.formParam("quote-text");

        User author = ctx.sessionAttribute("currentUser");
        if (author == null || quoteTitel == null || quoteText == null) {
            ctx.attribute("message", "Du skal være logget ind");
            ctx.redirect("/motivational");
        } else {
            try {
                MotivationMapper.newMotivation(quoteTitel, quoteText, "", author, pool);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
            ctx.redirect("/motivational");
        }

    }

    private static void addToFavorites(Context ctx, ConnectionPool connectionPool) {
        String msg;
        try {

            User user = ctx.sessionAttribute("currentUser");
            if (user == null) {
                msg = "husk at logge ind for at tilføje til favoritter";
            } else {
                String favoriteInput = ctx.formParam("favorite-id");
                if (favoriteInput == null)
                {
                    msg = "Vælg det citat du vil tilføje til favoritter";
                } else {
                    int favoriteId = Integer.parseInt(favoriteInput);
                    MotivationMapper.addToFavorites(user, favoriteId, connectionPool);
                    msg = "Dit citat er tilføjet til din favoritter";
                }
            }
        } catch (IllegalArgumentException e) {
            msg = "ugyldigt input";

        } catch (DatabaseException e) {
            msg = e.getMessage();
        }
        ctx.attribute("message", msg);
        showMotivation(ctx, connectionPool);

    }


    private static void showMotivation(Context ctx, ConnectionPool pool) {

        Motivation quote;
        try {
            quote = MotivationMapper.getMotivation(pool);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        ctx.attribute("motivation", quote);

        ctx.render("/motivational/motivational.html");

    }


}
