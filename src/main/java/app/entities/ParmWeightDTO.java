package app.entities;

import java.util.Date;

public class ParmWeightDTO {
    private int weightId;
    private int userId;
    private float weight;
    private Date date;

    public ParmWeightDTO(int weightId, int userId, float weight, Date date) {
        this.weightId = weightId;
        this.userId = userId;
        this.weight = weight;
        this.date = date;
    }
    public float getWeight() { return weight; }
}