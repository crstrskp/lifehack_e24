package app.controllers;

import app.entities.ParmWeightDTO;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ParmWeightMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.ArrayList;

// TODO: Delete theses imports
import java.util.Calendar;
import java.util.Date;


public class ParmController {


    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/parm", ctx -> showPage(ctx, connectionPool));
        app.post("/parm", ctx -> postWeight(ctx, connectionPool));
    }

    private static void showPage(Context ctx, ConnectionPool connectionPool) {

        User currentUser = ctx.sessionAttribute("currentUser");
        int user_id = currentUser.getUserId();

        // TODO: Delete this code
        Calendar calendar = Calendar.getInstance();

        ArrayList<ParmWeightDTO> weightList = new ArrayList<ParmWeightDTO>();
        calendar.set(2024, Calendar.SEPTEMBER, 4);
        Date specificDate = calendar.getTime();
        weightList.add(new ParmWeightDTO(4,user_id,(float)78,specificDate));
        calendar.set(2024, Calendar.SEPTEMBER, 3);
        specificDate = calendar.getTime();
        weightList.add(new ParmWeightDTO(3,user_id,(float)79,specificDate));
        calendar.set(2024, Calendar.SEPTEMBER, 2);
        specificDate = calendar.getTime();
        weightList.add(new ParmWeightDTO(2,user_id,(float)80,specificDate));
        calendar.set(2024, Calendar.SEPTEMBER, 1);
        specificDate = calendar.getTime();
        weightList.add(new ParmWeightDTO(1,user_id,(float)81,specificDate));

        for(ParmWeightDTO dto: weightList)
            System.out.println(dto);

        ctx.attribute("weightList", weightList);
        ctx.render("/parm/index.html");

//        try {
//            ArrayList<ParmWeightDTO> weightList = ParmWeightMapper.getAllWeightPerUser(user_id, connectionPool);
//            ctx.attribute("weightList", weightList);
//            ctx.render("/parm/index.html");
//        } catch (DatabaseException e) {
//            throw new RuntimeException(e);
//        }

    }

    private static void postWeight(Context ctx, ConnectionPool connectionPool) {

        float weight = Float.parseFloat(ctx.formParam("weight"));
        System.out.println("weight:" + weight);

        User currentUser = ctx.sessionAttribute("currentUser");
        int user_id = currentUser.getUserId();
        System.out.println("user_id: "+user_id);

//        try {
//            ParmWeightMapper.addWeight(user_id, weight, connectionPool);
            showPage(ctx, connectionPool);

//        } catch (DatabaseException e) {
//            throw new RuntimeException(e);
//        }

    }


}
