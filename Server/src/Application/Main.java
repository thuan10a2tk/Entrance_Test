/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**
 *
 * @author thuan
 */
public class Main {
   
    public static void func() {
        try {
            Impl_I_Function func = new  Impl_I_Function();
            Registry registry = LocateRegistry.createRegistry(12346);
            System.out.println("Server is running...");
            registry.bind("login", func);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
