package app.entities.SS;

public class RankListItem {

    private int id;
    private int rank_list_id;
    private int ice_cream_id;
    private String tier;
    private int position;

    public RankListItem(int id, int rank_list_id, int ice_cream_id, String tier, int position) {
        this.id = id;
        this.rank_list_id = rank_list_id;
        this.ice_cream_id = ice_cream_id;
        this.tier = tier;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public int getRank_list_id() {
        return rank_list_id;
    }

    public int getIce_cream_id() {
        return ice_cream_id;
    }

    public String getTier() {
        return tier;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "RankListItem{" +
                "id=" + id +
                ", rank_list_id=" + rank_list_id +
                ", ice_cream_id=" + ice_cream_id +
                ", tier=" + tier +
                ", position=" + position +
                '}';
    }

}