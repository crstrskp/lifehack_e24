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

        if (currentUser == null) {
            ctx.attribute("errorMessage", "Please log in, in order to use this app.");
            ctx.render("/parm/error.html");
            return;
        }


        int user_id = currentUser.getUserId();

        try {
            ArrayList<ParmWeightDTO> weightList = ParmWeightMapper.getAllWeightPerUser(user_id, connectionPool);

            for (ParmWeightDTO dto : weightList)
                System.out.println(dto);

            ctx.attribute("weightList", weightList);
            ctx.render("/parm/index.html");

        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", "There was a problem retrieving your weight data. Please try again later.");
            ctx.render("/parm/error.html");
            throw new RuntimeException(e);
        }

    }

    private static void postWeight(Context ctx, ConnectionPool connectionPool) {

        float weight = Float.parseFloat(ctx.formParam("weight"));
        System.out.println("weight:" + weight);

        User currentUser = ctx.sessionAttribute("currentUser");
        int user_id = currentUser.getUserId();
        System.out.println("user_id: " + user_id);

        try {
            ParmWeightMapper.addWeight(user_id, weight, connectionPool);
            showPage(ctx, connectionPool);

        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", "There was a problem submitting your weight data. Please try again later.");
            ctx.render("/parm/error.html");
            throw new RuntimeException(e);
        }

    }


}
