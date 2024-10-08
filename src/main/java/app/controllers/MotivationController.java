package app.controllers;

import app.entities.User;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import javax.naming.Context;
import java.util.List;


public class MotivationController {





    public void addRoutes(Javalin app, ConnectionPool pool){
        app.get("/motivational", ctx -> showMotivation(ctx,pool));

    }


    public void addMotivation(){

    }


    public void showMotivation(Context ctx, ConnectionPool pool){

        List<Motivation> quotes = MotivationMapper.getMotivation();

        ctx.render("motivational.html");

    }


}
