package app.persistence.ScoopScore;

import app.entities.SS.RankList;
import app.persistence.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RankListMapper
{
    public static List<RankList> getAllPublicRankList(boolean is_public, ConnectionPool connectionPool)
    {
        List<RankList> rankLists = new ArrayList<>();
        String sql = "SELECT * FROM ss_rank_list WHERE is_public=true";

        try (
                ConnectionPool connection = connectionPool.getConnection();
                PreparedStatement ps = connection.getConnection().prepareStatement(sql);
                )
        {
            ps.setBoolean(1, is_public);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                
            }

        }
    }
}
