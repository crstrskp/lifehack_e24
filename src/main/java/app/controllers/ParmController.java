package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class ParmController {


    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/parm", ctx -> index(ctx, connectionPool));
        app.post("/parm", ctx -> postWeight(ctx, connectionPool));
    }

    private static void index(Context ctx, ConnectionPool connectionPool) {
        ctx.render("/parm/index.html");
    }

    private static void postWeight(Context ctx, ConnectionPool connectionPool) {

        float weight = Float.parseFloat(ctx.formParam("weight"));
        System.out.println(weight);

    }


}
