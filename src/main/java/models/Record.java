package models;

public class Record {

    private int id;
    private int game_id;
    private int saleDate;
    private int price;

    public Record(){
        //for deserialization
    }

    public Record(int game_id, int saleDate, int price) {
        this.game_id = game_id;
        this.saleDate = saleDate;
        this.price = price;
    }

    public Record(int id, int game_id, int saleDate, int price) {
        this.id = id;
        this.game_id = game_id;
        this.saleDate = saleDate;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(int saleDate) {
        this.saleDate = saleDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
