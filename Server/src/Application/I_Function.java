/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author thuan
 */
public interface I_Function extends Remote{
    StatusLogin login(String idSession) throws RemoteException; 
}
