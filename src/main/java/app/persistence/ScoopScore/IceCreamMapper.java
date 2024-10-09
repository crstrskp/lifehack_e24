package app.persistence.ScoopScore;

import app.entities.SS.SSIceCream;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class IceCreamMapper
{


    public static List<SSIceCream> IceCreamMapper(ConnectionPool connectionPool) throws DatabaseException
    {

        List<SSIceCream> iceCreamList = new ArrayList<>();
        String sql = "SELECT * FROM ice_creams";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                int id = rs.getInt("id");
                int brandId = rs.getInt("brand_id");
                String name = rs.getString("name");
                String discription = rs.getString("description");
                iceCreamList.add(new SSIceCream(id, brandId, name, discription));
            }
        } catch (SQLException e)
        {
            throw new DatabaseException("Der skete en fejl!");
        }

        return iceCreamList;

    }
}
