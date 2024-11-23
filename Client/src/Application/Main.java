package Application;

import Encrypt.EncryptSupport;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        function();
    }
    public static void function(){
        SwingUtilities.invokeLater(()->{
            JFrame loginFrame = new JFrame("Login");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.setSize(300,200);
            loginFrame.setLayout(new BorderLayout());
            
            JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
            
            JLabel userLable = new JLabel("Username:");
            JTextField userField = new JTextField();
            
            JLabel passLable = new JLabel("Password:");
            JPasswordField passField = new JPasswordField();
            
            JButton loginButton = new JButton("Login");
            
            panel.add(userLable);
            panel.add(userField);
            panel.add(passLable);
            panel.add(passField);
            panel.add(new JLabel());
            panel.add(loginButton);
            loginFrame.add(panel, BorderLayout.CENTER);
            
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = userField.getText();
                    String password = new String(passField.getPassword());
                    String idSession = EncryptSupport.getMD5(username+password);
                    StatusLogin status = Authentication.login(idSession);
                    if(status == null){
                        JOptionPane.showMessageDialog(loginFrame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        if (!status.isStatus()) {
                            JOptionPane.showMessageDialog(loginFrame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            loginFrame.setVisible(false);
                            GUI gui = new GUI(idSession);
                        }
                    }
                    
                }
            });
            
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);
            
        });
    }
}
