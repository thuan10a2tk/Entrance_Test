/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Encrypt.EncryptSupport;
import java.io.Serializable;

/**
 *
 * @author thuan
 */
public class Product implements Serializable{
    private static final long serialVersionUID = 1L; 
    int id;
    String name;
    double price;
    int quantity;
    Category category;
    String picture;
    public Product() {
    }

    public String getPicture() {
        return picture;
    }
    
    public Product(int id, String name, double price, int quantity,String path, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        picture = EncryptSupport.encode(path);
        this.category = category;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
}
