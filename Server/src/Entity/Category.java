/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import Encrypt.EncryptSupport;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thuan
 */

public class Category implements Serializable{
    private static final long serialVersionUID = 1L; 
    int id;
    TypeName name;
    Colors color;
    Styles style;
    Sizes size;
    
    public Category() {
    }

    public Category(int id, TypeName name, Colors color, Styles style, Sizes size) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.style = style;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypeName getName() {
        return name;
    }

    public void setName(TypeName name) {
        this.name = name;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public Styles getStyle() {
        return style;
    }

    public void setStyle(Styles style) {
        this.style = style;
    }

    public Sizes getSize() {
        return size;
    }

    public void setSize(Sizes size) {
        this.size = size;
    }
    
   
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Category> list ;
        int id = 0;
        byte[]data = EncryptSupport.decryptFromFile("Category.dat", EncryptSupport.getMD5("adminadmin"));
        list = (List<Category>) EncryptSupport.byteArrayToObject(data);
        for (Category category : list) {
            System.out.println(category.toString());
        }
//        for (TypeName typeName : TypeName.values()) {
//            for (Colors color : Colors.values()) {
//                for (Styles style : Styles.values()) {
//                    for (Sizes size : Sizes.values()) {
//                        // Thêm từng kết hợp vào danh sách
//                        System.out.println(typeName + " " + color + " " + style + " " + size+" ");
//                    }
//                }
//            }
//        }
//        
//        byte[] buffer;
//        try {
//            buffer = EncryptSupport.objectToByteArray(list);
//            EncryptSupport.encryptSaveToFile( "Category.dat", buffer, EncryptSupport.getMD5("adminadmin"));
//        } catch (IOException ex) {
//            Logger.getLogger(Category.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", name=" + name + ", color=" + color + ", style=" + style + ", size=" + size + '}';
    }
    
    
}
