package app.entities.SS;

public class SSIceCream {

    private int id;
    private int brandId;
    private String name;
    private String description;

    public SSIceCream(int id, int brandId, String name, String description) {
        this.id = id;
        this.brandId = brandId;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "SSIceCream{" +
                "id=" + id +
                ", brandId=" + brandId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}