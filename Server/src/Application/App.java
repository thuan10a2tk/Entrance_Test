/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application;

import java.awt.Label;
import javax.swing.JFrame;

/**
 *
 * @author thuan
 */
public class App {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Server");
        frame.add(new Label("Sever running..."));
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(300,200);
          frame.setLocationRelativeTo(null);  
        frame.setVisible(true);
        
        new Thread(()-> Main.func()).start();
        new Thread(()->Session.func()).start();
        
    }
}
