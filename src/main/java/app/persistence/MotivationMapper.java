package app.persistence;

import app.entities.Motivation;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;
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


    public static Motivation getFavoriteMotivation(User user, ConnectionPool connectionPool){
       String sql = "SELECT * FROM motivational_favorite AS f INNER JOIN motivational_quotes AS q ON f.motivation_id = q.id WHERE f.user_id = ?";
       String title;
       String text;
       String imageURL;
       int motivationId;

       try (Connection connection = connectionPool.getConnection()){
           try (PreparedStatement ps = connection.prepareStatement(sql)){
               ps.setInt(1,user.getUserId());
               ResultSet rs = ps.executeQuery();
               if (rs.next()) {
                   title = rs.getString("title");
                   text = rs.getString("text");
                   imageURL = rs.getString("image_url");
                   motivationId = rs.getInt("id");
                   return new Motivation(motivationId,title,text,imageURL);
               }
           }
       }

       catch (SQLException e) {
           throw new RuntimeException(e);
       }
        return null;

    }

    public void addFavorite(User user, int motivationId, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "INSERT INTO motivational_favorite (user_id, motivation_id) VALUES (?, ?)";
        try (var connection = connectionPool.getConnection())
        {
            try (var prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                prepareStatement.setInt(1, user.getUserId());
                prepareStatement.setInt(2, motivationId);
                prepareStatement.executeUpdate();
                prepareStatement.getGeneratedKeys();

            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not create user in the database");
        }
    }

    public void deleteFromFavorite(int userId, int motivationId, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "DELETE FROM motivational_favorite WHERE user_id = ? AND quote_id = ?";
        try (var connection = connectionPool.getConnection())
        {
            try (var prepareStatement = connection.prepareStatement(sql))
            {
                prepareStatement.setInt(1, userId);
                prepareStatement.setInt(2, motivationId);
                prepareStatement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not delete user from the database");
        }
    }


/*
    public static List<Motivation> getMotivations(ConnectionPool connectionPool){

    }*/
}
