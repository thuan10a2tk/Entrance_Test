/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Encrypt.EncryptSupport;
import java.io.IOException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", category=" + category + ", picture=" + picture + '}';
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
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        List<Product> list ;
//        int id = 0;
//        byte[]data = EncryptSupport.decryptFromFile("Product.dat", EncryptSupport.getMD5("adminadmin"));
//        list = (List<Product>) EncryptSupport.byteArrayToObject(data);
//        for (Product category : list) {
//            System.out.println(category.toString());
//        }
//    }
 public static void main(String[] args) {
        Random random = new Random();
        List<Product> list = new ArrayList<>();

        // Tạo ngẫu nhiên 100 sản phẩm
        for (int idx = 0; idx < 99; idx++) {
            // Tạo ngẫu nhiên tên sản phẩm
            String name = "Product " + (idx + 1);

            // Tạo giá ngẫu nhiên (ví dụ từ 10 đến 1000)
            double price = 10 + (1000 - 10) * random.nextDouble();

            // Tạo số lượng ngẫu nhiên (ví dụ từ 1 đến 100)
            int quantity = random.nextInt(100) + 1;

            // Tạo đường dẫn ảnh ngẫu nhiên (ví dụ chỉ là chuỗi giả)
            String path = "C:\\Users\\thuan\\Desktop\\Submit\\Server\\product_images\\" + (idx + 1) + ".png";


            // Tạo danh mục ngẫu nhiên (random từ các enum)
            Category category = new Category(
                    idx, 
                    TypeName.values()[random.nextInt(TypeName.values().length)],
                    Colors.values()[random.nextInt(Colors.values().length)],
                    Styles.values()[random.nextInt(Styles.values().length)],
                    Sizes.values()[random.nextInt(Sizes.values().length)]
            );

            // Thêm sản phẩm vào danh sách
            list.add(new Product(idx, name, price, quantity, path, category));
        }

        byte[] data;
        try {
            data = EncryptSupport.objectToByteArray(list);
        EncryptSupport.encryptSaveToFile("Product.dat", data, EncryptSupport.getMD5("adminadmin"));

        } catch (IOException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
