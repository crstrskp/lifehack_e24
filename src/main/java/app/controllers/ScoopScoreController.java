package app.controllers;

import app.entities.SS.RankList;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ScoopScore.RankListMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class ScoopScoreController
{
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {
        app.get("/scoopscore", ctx -> index(ctx, connectionPool));
    }

    private static void index(Context ctx, ConnectionPool connectionPool)
    {
        ctx.render("/scoopscore/index.html");
    }

    public static void allRankLists(Context ctx, ConnectionPool connectionPool)
    {
        try {
            List<RankList> allRankLists = RankListMapper.getAllPublicRankList(true, connectionPool);
            ctx.attribute("allRankLists", allRankLists);
            ctx.render("/scoopscore/pages/allranklists.html");
        } catch (DatabaseException e)
        {
            ctx.attribute("message", e.getMessage());
            ctx.render("../index.html");
        }
    }

}