/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author thuan
 */
public class Authentication {
    public static StatusLogin login(String idSession){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 12346);
            I_Function func = (I_Function) registry.lookup("login");
            
            return func.login(idSession);
        } catch (NotBoundException | RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
