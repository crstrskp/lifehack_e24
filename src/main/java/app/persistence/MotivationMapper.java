package app.persistence;

import app.entities.Motivation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotivationMapper {

    public static void newMotivation(String motivationTitle, String motivationText, String imageURL, ConnectionPool connectionPool){

    }

    public static void deleteMotivation(int motivationId, ConnectionPool connectionPool){

    }

    public static Motivation getMotivation(ConnectionPool connectionPool){
        String sql = "SELECT * FROM motivational_quotes ORDER BY RANDOM() LIMIT 1";
        String title;
        String text;
        String imageURL;
        int authorId;

        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    title = rs.getString("title");
                    text = rs.getString("text");
                    imageURL = rs.getString("image_url");
                    authorId = rs.getInt("author_id");
                    return new Motivation(authorId,title,text,imageURL);
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;

    }


/*
    public static List<Motivation> getMotivations(ConnectionPool connectionPool){

    }*/
}
