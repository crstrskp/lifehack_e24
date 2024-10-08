package app.controllers;

import app.entities.Motivation;
import app.persistence.ConnectionPool;
import app.persistence.MotivationMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class MotivationController {

    public static void addRoutes(Javalin app, ConnectionPool pool){
        app.get("/motivational", ctx -> showMotivation(ctx,pool));

    }


    private void addMotivation(){

    }


    private static void showMotivation(Context ctx, ConnectionPool pool){

        Motivation quote = MotivationMapper.getMotivation(pool);
        ctx.attribute("motivation", quote);

        ctx.render("motivational/motivational.html");

    }


}
