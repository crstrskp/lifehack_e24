package app.entities.SS;

public class SSIceCream {

    private int id;
    private int brandId;
    private String name;
    private String discription;

    public SSIceCream(int id, int brandId, String name, String discription) {
        this.id = id;
        this.brandId = brandId;
        this.name = name;
        this.discription = discription;
    }

    public int getId() {
        return id;
    }

    public int getBrandId() {
        return brandId;
    }

    public String getName() {
        return name;
    }

    public String getDiscription() {
        return discription;
    }

    @Override
    public String toString() {
        return "SSIceCream{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", name='" + name + '\'' +
                ", discription='" + discription + '\'' +
                '}';
    }

}