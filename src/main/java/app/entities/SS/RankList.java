package app.entities.SS;

import java.util.ArrayList;
import java.util.List;

public class RankList {

    private int id;
    private int user_id;
    private String title;
    private String description;
    private boolean is_public;

    private List<RankListItem>items;

    public RankList(int id, int user_id, String title, String description, boolean is_public, List<RankListItem> items) {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.is_public = is_public;
        this.items = items;
    }

    public void addItem(RankListItem item) {
        this.items.add(item);
    }

    public List<RankListItem> getItems() {
        return items;
    }

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isIs_public() {
        return is_public;
    }

    @Override
    public String toString() {
        return "ListsOfRankLists{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", is_public=" + is_public +
                ", items=" + items +
                '}';
    }

}
