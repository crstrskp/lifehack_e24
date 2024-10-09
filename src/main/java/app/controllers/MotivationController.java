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
        app.post("/motivational/addtofavorites", ctx -> addToFavorites(ctx, pool) );

    }


    private void addMotivation(){

    }

    private static void addToFavorites(Context ctx, ConnectionPool connectionPool)
    {
        String f = ctx.formParam("favorite-id");
        int favoriteId = Integer.parseInt(f);

        User user = ctx.sessionAttribute("currentUser");
        MotivationMapper.addToFavorites(user, favoriteId, connectionPool);
        String msg = "Dit citat er tilføjet til din favoritter";

        ctx.attribute("motivation", msg);
    }




    private static void showMotivation(Context ctx, ConnectionPool pool){

        Motivation quote = MotivationMapper.getMotivation(pool);
        ctx.attribute("motivation", quote);

        ctx.render("/motivational/motivational.html");

    }


}
