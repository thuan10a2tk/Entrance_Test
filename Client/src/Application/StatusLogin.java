/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Application;

import java.io.Serializable;

public class StatusLogin implements Serializable {
    private static final long serialVersionUID = 1L; 
    private String idSession;
    private boolean status;

    public StatusLogin(String idSession, boolean status) {
        this.idSession = idSession;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public String getIdSession() {
        return idSession;
    }
}
