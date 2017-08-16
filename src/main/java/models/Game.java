package models;

public class Game {

    private int id;
    private String name;
    private String genre;
    private int stock;
    private int price;
    private int releaseDate;

    public Game(){
        //for deserialization
    }

    public Game(int id, String name, String genre, int stock, int price, int releaseDate) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.stock = stock;
        this.price = price;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }
}

