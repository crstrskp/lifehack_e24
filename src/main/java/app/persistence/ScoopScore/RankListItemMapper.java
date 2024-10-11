package app.persistence.ScoopScore;

import app.entities.SS.RankListItem;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RankListItemMapper {

    public static List<RankListItem> getAllRankListItemPerUser(int user_id, ConnectionPool connectionPool) throws DatabaseException
    {
        List<RankListItem> rankListItemList = new ArrayList<RankListItem>();
        String sql = "SELECT \n" +
                "    rl.id AS rank_list_id,\n" +
                "    rl.user_id,\n" +
                "    rl.title,\n" +
                "    rl.description,\n" +
                "    rl.is_public,\n" +
                "    rli.id AS rank_list_item_id,\n" +
                "    rli.ice_cream_id,\n" +
                "    rli.tier,\n" +
                "    rli.position\n" +
                "FROM \n" +
                "    public.ss_rank_list AS rl\n" +
                "INNER JOIN \n" +
                "    public.ss_rank_list_item AS rli \n" +
                "ON \n" +
                "    rl.id = rli.rank_list_id\n" +
                "WHERE \n" +
                "    rl.user_id = ? AND \n" +  // Added filter for user_id
                "    rl.is_public = TRUE;";  // Optional filter for public rank lists

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("rank_list_item_id");
                int rank_list_id = rs.getInt("rank_list_id");
                int ice_cream_id = rs.getInt("ice_cream_id");
                String tier = rs.getString("tier");
                int position = rs.getInt("position");

                rankListItemList.add(new RankListItem(id, rank_list_id, ice_cream_id, tier, position));
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl");
        }
        return rankListItemList;
    }
}