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
public class Account implements Serializable{
    private static final long serialVersionUID = 1L; 
    String id;
    String account;
    String pass;
    double ammount;
    
    public double getAmmount() {
        return ammount;
    }

    public String getAccount() {
        return account;
    }

    public String getId() {
        return id;
    }
    
    
    public Account(String account, String pass, double ammount) {
        this.id = EncryptSupport.getMD5(account+pass);
        this.account = account;
        this.pass = EncryptSupport.getMD5(pass);
        this.ammount = ammount;
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", account=" + account + ", pass=" + pass + ", ammount=" + ammount + '}';
    }
    
    
}