package app.persistence;

import app.entities.Motivation;
import app.entities.User;
import app.exceptions.DatabaseException;
import java.sql.*;

public class MotivationMapper {

    public static void newMotivation(String motivationTitle, String motivationText, String imageURL, User author, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO motivational_quotes (title, text, image_url, author_id) VALUES (?,?,?,?)";

        try(Connection connection = connectionPool.getConnection()){
            try(PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, motivationTitle);
                ps.setString(2, motivationText);
                ps.setString(3, imageURL);
                ps.setInt(4, author.getUserId());

                int rowsAffected = ps.executeUpdate();

                if(rowsAffected != 1){
                    throw new DatabaseException("Fejl: Motivational text ikke tilføjet. rowsAffected != 1");
                }
            }
        }
        catch(SQLException e){
            throw new DatabaseException("Fejl i opkobling til DB: newMotivation/MotivationMapper");
        }
    }

    public static Motivation getMotivation(ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "SELECT * FROM motivational_quotes ORDER BY RANDOM() LIMIT 1";
        String title;
        String text;
        String imageURL;
        int authorId;
        int motivationId;

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    motivationId = rs.getInt("id");
                    title = rs.getString("title");
                    text = rs.getString("text");
                    imageURL = rs.getString("image_url");
                    authorId = rs.getInt("author_id");
                    return new Motivation(motivationId, title, text, imageURL, authorId);
                }
            }
        } catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
        return null;

    }


    public static Motivation getFavoriteMotivation(User user, ConnectionPool connectionPool){
       String sql = "SELECT * FROM motivational_favorites AS f INNER JOIN motivational_quotes AS q ON f.quote_id = q.id WHERE f.user_id = ?";
       String title;
       String text;
       String imageURL;
       int motivationId;
       int authorId;

       try (Connection connection = connectionPool.getConnection()){
           try (PreparedStatement ps = connection.prepareStatement(sql)){
               ps.setInt(1,user.getUserId());
               ResultSet rs = ps.executeQuery();
               if (rs.next()) {
                   title = rs.getString("title");
                   text = rs.getString("text");
                   imageURL = rs.getString("image_url");
                   motivationId = rs.getInt("id");
                   authorId = rs.getInt("author_id");
                   return new Motivation(motivationId,title,text,imageURL,authorId);
               }
           }
       }

       catch (SQLException e) {
           throw new RuntimeException(e);
       }
        return null;

    }

    public static void addToFavorites(User user, int motivationId, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "INSERT INTO motivational_favorites (user_id, quote_id) VALUES (?, ?)";
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

    public static void deleteFromFavorite(int userId, int motivationId, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "DELETE FROM motivational_favorites WHERE user_id = ? AND quote_id = ?";
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

}
