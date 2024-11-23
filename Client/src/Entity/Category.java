/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.io.Serializable;

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
    
    
    
}
