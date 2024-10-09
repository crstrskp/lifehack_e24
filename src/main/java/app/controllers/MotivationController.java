package app.controllers;

import app.entities.Motivation;
import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.MotivationMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class MotivationController {

    public static void addRoutes(Javalin app, ConnectionPool pool){
        app.get("/motivational", ctx -> showMotivation(ctx,pool));
        app.get("/motivation", ctx -> addMotivation(ctx,pool));

    }


    private static void addMotivation(Context ctx, ConnectionPool pool){

        String quoteTitel = ctx.formParam("motivational_quote");
        String quoteText = ctx.formParam("motivational_quote");

        User user = ctx.sessionAttribute("currentUser");
        if(user == null || quoteTitel == null || quoteText == null) {
            ctx.attribute("message", "Du skal være logget ind");
            ctx.redirect("/motivational");
        }else {
            MotivationMapper.newMotivation(quoteTitel,quoteText,"", pool);
            ctx.redirect("/motivational");
        }

    }


    private static void showMotivation(Context ctx, ConnectionPool pool){

        Motivation quote = MotivationMapper.getMotivation(pool);
        ctx.attribute("motivation", quote);

        ctx.render("/motivational/motivational.html");

    }


}
