package app.persistence.ScoopScore;

import app.entities.SS.SSIceCream;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class IceCreamMapper
{


    public static List<SSIceCream> iceCreamMapper(ConnectionPool connectionPool) throws DatabaseException
    {

        List<SSIceCream> iceCreamList = new ArrayList<>();
        String sql = "SELECT * FROM ss_ice_cream";

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
                String description = rs.getString("description");
                iceCreamList.add(new SSIceCream(id, brandId, name, description));
            }
        } catch (SQLException e)
        {
            throw new DatabaseException("Der skete en fejl!");
        }

        return iceCreamList;

    }
}
