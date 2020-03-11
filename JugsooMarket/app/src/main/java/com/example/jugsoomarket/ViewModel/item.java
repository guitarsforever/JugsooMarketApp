package com.example.jugsoomarket.ViewModel;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_table")
public class item {
    @PrimaryKey(autoGenerate = true)
    int id;
    String qrUrl;
    String name;
    int price;
    int totalPrice;
    String thumbnail;
    int quantity;

    public item(int id, String qrUrl, String name, int price, String thumbnail, int quantity) {
        this.id = id;
        this.qrUrl = qrUrl;
        this.name = name;
        this.price = price;
        this.totalPrice = price*quantity;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "item{" +
                "id=" + id +
                ", qrUrl='" + qrUrl + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", thumbnail='" + thumbnail + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
