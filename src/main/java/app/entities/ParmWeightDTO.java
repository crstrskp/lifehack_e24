package app.entities;

import java.util.Date;

public class ParmWeightDTO {
    private int weightId;
    private int userId;
    private float weight;
    private Date date;

    // Constructor
    public ParmWeightDTO(int weightId, int userId, float weight, Date date) {
        this.weightId = weightId;
        this.userId = userId;
        this.weight = weight;
        this.date = date;
    }


    public int getWeightId() { return weightId; }
    public void setWeightId(int weightId) { this.weightId = weightId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    @Override
    public String toString() {
        return "weightId: " + weightId + ", userId: " + userId + ", weight: " + weight + ", date: " + date;
    }
}
