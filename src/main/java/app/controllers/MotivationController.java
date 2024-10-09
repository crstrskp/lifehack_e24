package app.controllers;

import app.entities.Motivation;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.MotivationMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class MotivationController {

    public static void addRoutes(Javalin app, ConnectionPool pool){
        app.get("/motivational", ctx -> showMotivation(ctx,pool));
        app.post("/motivation", ctx -> addMotivation(ctx,pool));

    }


    private static void addMotivation(Context ctx, ConnectionPool pool){

        String quoteTitel = ctx.formParam("motivational_quote");
        String quoteText = ctx.formParam("motivational_quote");

        User author = ctx.sessionAttribute("currentUser");
        if(author == null || quoteTitel == null || quoteText == null) {
            ctx.attribute("message", "Du skal være logget ind");
            ctx.redirect("/motivational");
        }else {
            try
            {
                MotivationMapper.newMotivation(quoteTitel,quoteText,"",author, pool);
            } catch (DatabaseException e)
            {
                throw new RuntimeException(e);
            }
            ctx.redirect("/motivational");
        }

    }


    private static void showMotivation(Context ctx, ConnectionPool pool){

        Motivation quote = null;
        try
        {
            quote = MotivationMapper.getMotivation(pool);
        } catch (DatabaseException e)
        {
            throw new RuntimeException(e);
        }
        ctx.attribute("motivation", quote);

        ctx.render("/motivational/motivational.html");

    }


}
