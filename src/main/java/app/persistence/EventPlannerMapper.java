package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;

public class EventPlannerMapper {

    public static void createEvent(User owner, String dateAndTime, String location, String title, String decription, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "insert into eventplanner (owner_id, dateandtime, location, title, description, is_owner) values (?,?,?,?,?,true)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, owner.getUserId());
            ps.setString(2, dateAndTime);
            ps.setString(3, location);
            ps.setString(4, title);
            ps.setString(5, decription);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af event");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Eventet findes allerede. Vælg et andet";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }

    public static boolean isEventOwner(int eventId, int userId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT owner_id FROM eventplanner WHERE event_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("ownerid") == userId;
            } else {
                throw new DatabaseException("Event not found");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error", e.getMessage());
        }
    }

    public static void deleteEvent(int eventId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "delete from eventplanner where event_id=?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, eventId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved sletning af event");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Der er sket en fejl. Prøv igen", e.getMessage());
        }
    }

    public static void leaveEvent (int eventId, User user, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "delete from event_participants where event_id=? and user_id=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, eventId);
            ps.setInt(2, user.getUserId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved forlade event");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Der er sket en fejl. Prøv igen", e.getMessage());
        }
    }
    public static void joinEvent (int eventId, User user, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO event_participants (event_id, user_id) VALUES (?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, eventId);
            ps.setInt(2, user.getUserId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved at joine event");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Der er sket en fejl. Prøv igen", e.getMessage());
        }
    }

    public static boolean isUserParticipant(int eventId, int userId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM users_events WHERE event_id = ? AND user_id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DatabaseException("Database error", e.getMessage());
        }
    }

}