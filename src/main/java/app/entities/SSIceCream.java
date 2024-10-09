package app.entities;

public class SSIceCream {

    private int id;
    private String name;

    public SSIceCream(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SSIceCream{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}