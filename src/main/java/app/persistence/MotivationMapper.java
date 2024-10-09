package app.persistence;

import app.entities.Motivation;
import app.entities.User;

import java.sql.*;
import java.util.List;

public class MotivationMapper {

    public static void newMotivation(String motivationTitle, String motivationText, String imageURL, User user, ConnectionPool connectionPool) throws SQLException {
        String sql = "INSERT INTO motivational_quotes (title, text, image_url, author_id) VALUES (?,?,?)";

        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, motivationTitle);
                ps.setString(2, motivationText);
                ps.setString(3, imageURL);
                ps.setInt(4, user.getUserId());

                int rowsAffected = ps.executeUpdate();

                if(rowsAffected != 1){
                    throw new SQLException("Fejl: Motivational text ikke tilføjet. rowsAffected != 1");
                }
            }
        }
        catch(SQLException e){
            throw new SQLException("Fejl i opkobling til DB: newMotivation/MotivationMapper");
        }
    }

    public static void deleteMotivation(int motivationId, ConnectionPool connectionPool){

    }

    public static Motivation getMotivation(ConnectionPool connectionPool){
        String sql = "SELECT * FROM motivational_quotes ORDER BY RANDOM() LIMIT 1";
        String title;
        String text;
        String imageURL;
        int authorId;
        int motivationId;

        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    motivationId = rs.getInt("id");
                    title = rs.getString("title");
                    text = rs.getString("text");
                    imageURL = rs.getString("image_url");
                    authorId = rs.getInt("author_id");
                    return new Motivation(motivationId,title,text,imageURL, authorId);
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
