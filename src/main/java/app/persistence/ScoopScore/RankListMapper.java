package app.persistence.ScoopScore;

import app.entities.SS.RankList;
import app.entities.SS.RankListItem;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RankListMapper
{
    public static List<RankList> getAllPublicRankList(boolean isPublic, ConnectionPool connectionPool) throws DatabaseException
    {
        List<RankList> rankLists = new ArrayList<>();
        String sql = "SELECT * FROM ss_rank_list WHERE is_public =true";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        )
        {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                List<RankListItem> rankListItemPerUser;
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                boolean is_public = rs.getBoolean("is_public");
                rankListItemPerUser = RankListItemMapper.getAllRankListItemPerUser(user_id, connectionPool);
                rankLists.add(new RankList(id, user_id, title, description, is_public, rankListItemPerUser));
            }
        } catch (SQLException e)
        {
            throw new DatabaseException("Fejl!", e.getMessage());
        }
        return rankLists;
    }
}
