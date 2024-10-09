package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class ParmController {


    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/parm", ctx -> showPage(ctx, connectionPool));
        app.post("/parm", ctx -> postWeight(ctx, connectionPool));
    }

    private static void showPage(Context ctx, ConnectionPool connectionPool) {




        ctx.render("/parm/index.html");
    }

    private static void postWeight(Context ctx, ConnectionPool connectionPool) {

        float weight = Float.parseFloat(ctx.formParam("weight"));
        System.out.println(weight);

    }


}
