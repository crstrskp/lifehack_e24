package app;

import app.config.ThymeleafConfig;
import app.controllers.TimeZonesController;
import app.controllers.UCController;
import app.controllers.UserController;
import app.controllers.WarhammerController;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;


public class Main
{

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://ep-calm-mountain-a2qbwmjf.eu-central-1.aws.neon.tech/licensekey?user=licensekey_owner&password=jx47yzLvATmo&sslmode=require";
    private static final String DB = "postgres";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args)
    {
        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
            config.staticFiles.add("/templates");
        }).start(7070);

        // Routing
        app.get("/", ctx -> ctx.render("index.html"));

        UserController.addRoutes(app, connectionPool);
        WeightConversion.addRoutes(app);
        TimeZonesController.addRoutes(app);
        WarhammerController.addRoutes(app);
        UCController.addRoutes(app, connectionPool);
    }
}