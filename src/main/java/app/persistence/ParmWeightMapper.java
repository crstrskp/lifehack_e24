package app.persistence;

import app.entities.ParmWeightDTO;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Date;

public class ParmWeightMapper {

    public static ArrayList<ParmWeightDTO> getAllWeightPerUser(int user_id, ConnectionPool connectionPool) throws DatabaseException {
        ArrayList<ParmWeightDTO> parmWeightDTOs = new ArrayList<>();
        String sql = "select * from public.parmweight where user_id=?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, user_id);

            ResultSet rs = ps.executeQuery();
           while (rs.next()) {
               int weightId = rs.getInt("weight_id");
               int userId = rs.getInt("user_id");
               float weight = rs.getFloat("weight");
               Date date = rs.getDate("date");

               parmWeightDTOs.add(new ParmWeightDTO(weightId,userId,weight,date));

            }
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
        return parmWeightDTOs;
    }

    public static void addWeight(int user_id, float weight, ConnectionPool connectionPool) throws DatabaseException {
        String sql ="INSERT INTO public.parmweight (user_id, weight) VALUES (?, ?)";

        try(Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setInt(1, user_id);
            ps.setFloat(2, weight);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny vejning");
            }

        } catch(SQLException e) {
            throw new DatabaseException("DB fejl - fejl i at tilføje vægt", e.getMessage());
        }
    }
}
