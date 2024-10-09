package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventPlannerMapper {

        public static void createEvent(Boolean ownerId, String dateAndTime, String location, String title , String decription, ConnectionPool connectionPool) throws DatabaseException
        {
            String sql = "insert into eventplanner (ownerid, dateandtime, location, title, decription) values (true, ?,?,?,?)";

            try (
                    Connection connection = connectionPool.getConnection();
                    PreparedStatement ps = connection.prepareStatement(sql)
            )
            {
                ps.setBoolean(1, ownerId);
                ps.setString(2, dateAndTime);
                ps.setString(3, location);
                ps.setString(4, title);
                ps.setString(5, decription);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1)
                {
                    throw new DatabaseException("Fejl ved oprettelse af event");
                }
            }
            catch (SQLException e)
            {
                String msg = "Der er sket en fejl. Prøv igen";
                if (e.getMessage().startsWith("ERROR: duplicate key value "))
                {
                    msg = "Eventet findes allerede. Vælg et andet";
                }
                throw new DatabaseException(msg, e.getMessage());
            }
        }

        public static void deleteEvent(int eventId, ConnectionPool connectionPool) throws DatabaseException
        {
            String sql = "delete from eventplanner where eventid=?";

            try (
                    Connection connection = connectionPool.getConnection();
                    PreparedStatement ps = connection.prepareStatement(sql)
            )
            {
                ps.setInt(1, eventId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected != 1)
                {
                    throw new DatabaseException("Fejl ved sletning af event");
                }
            }
            catch (SQLException e)
            {
                throw new DatabaseException("Der er sket en fejl. Prøv igen", e.getMessage());
            }
        }
    }