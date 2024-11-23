package Application;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import Encrypt.EncryptSupport;
import Entity.Account;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
/**
 *
 * @author thuan
 */
public class Impl_I_Function extends UnicastRemoteObject implements I_Function{
    Impl_I_Function () throws RemoteException{
        super();
    }
    @Override
    public StatusLogin login(String idSession) {
        try {
            byte[] buffer = EncryptSupport.decryptFromFile(idSession, idSession);
            Account account = (Account) EncryptSupport.byteArrayToObject(buffer);
            if(idSession.equalsIgnoreCase(account.getId())){
                return new StatusLogin(idSession, true);
            }
        } catch (Exception e) {
              return new StatusLogin(idSession, false);
        }
        return new StatusLogin(idSession, false);
    }
    
}
