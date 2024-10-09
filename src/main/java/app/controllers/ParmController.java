package app.controllers;

import app.entities.ParmWeightDTO;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ParmWeightMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;


public class ParmController {


    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/parm", ctx -> showPage(ctx, connectionPool));
        app.post("/parm", ctx -> postWeight(ctx, connectionPool));
    }

    private static void showPage(Context ctx, ConnectionPool connectionPool) {

        User currentUser = ctx.sessionAttribute("currentUser");
        int user_id = currentUser.getUserId();

        try {
            ArrayList<ParmWeightDTO> allWeights = ParmWeightMapper.getAllWeightPerUser(user_id, connectionPool);
            ctx.attribute("allWeights", allWeights);
            ctx.render("/parm/index.html");
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void postWeight(Context ctx, ConnectionPool connectionPool) {

        float weight = Float.parseFloat(ctx.formParam("weight"));
        System.out.println(weight);

        User currentUser = ctx.sessionAttribute("currentUser");
        int user_id = currentUser.getUserId();

        try {
            ParmWeightMapper.addWeight(user_id, weight, connectionPool);

            showPage(ctx, connectionPool);

        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

    }


}
