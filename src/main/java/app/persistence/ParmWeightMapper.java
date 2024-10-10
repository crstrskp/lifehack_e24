package app.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.entities.ParmWeightDTO;
import app.exceptions.DatabaseException;

public class ParmWeightMapper {

    public static ArrayList<ParmWeightDTO> getAllWeightPerUser(int userId, ConnectionPool connectionPool) throws DatabaseException {
        ArrayList<ParmWeightDTO> parmWeightDTOs = new ArrayList<>();
        String sql = "select * from parmweight " +
                     "where user_id=? " +
                     "ORDER BY weight_id DESC";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int weightId = rs.getInt("weight_id");
                int userIdSQL = rs.getInt("user_id");
                float weight = rs.getFloat("weight");
                Date date = rs.getDate("date");

                parmWeightDTOs.add(new ParmWeightDTO(weightId, userIdSQL, weight, date));
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
        return parmWeightDTOs;
    }

    public static Boolean addWeight(int userId, float weight, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO parmweight (user_id, weight) VALUES (?, ?)";
        boolean result = false;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setFloat(2, weight);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Error, occured when adding a weight");
            } else {
                result = true;
            }

        } catch (SQLException e) {
            throw new DatabaseException("Database error", e.getMessage());
        }
        return result;
    }
}