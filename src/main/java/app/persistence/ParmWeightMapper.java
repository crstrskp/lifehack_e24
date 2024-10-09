package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ParmWeightMapper {

    public static void getAllWeightPerUser(int user_id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "select * from public.parmweight where user_id=?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, user_id);

            ResultSet rs = ps.executeQuery();
           while (rs.next()) {

            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }
}
