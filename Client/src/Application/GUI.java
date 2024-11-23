package Application;

import Encrypt.EncryptSupport;
import Entity.Account;
import Entity.Product;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class GUI{
    String idSession;
    JFrame frame;
    JPanel filterPanel;
    JPanel productPanel;
    Button applyButton;
    Button logoutButton;
    JPanel categories;
    JPanel color;
    JPanel size;
    JPanel style;
    JPanel price;
    InputStream input;
    OutputStream output;
    Socket client;
    JLabel amountLabel;
    
    public GUI(String idSession) {
        this.idSession = idSession;
        init();
    }
    
    private void init(){
        open();
        connect();
    }
    
    private void open(){
        frame = new JFrame("Product Catalog");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1366, 768);
        
        filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setPreferredSize(new Dimension(200, 0));
        
        categories = createFilter("Categories", new String[]{"Home_Decor", "Clothing", "Accessories", "Outdoor" ,"Orther"});
        filterPanel.add(categories);
        
        color = createFilter("Color", new String[]{"Red", "Green", "Blue", "Purple", "Other"});
        filterPanel.add(color);
        
        size = createFilter("Size", new String[]{"S", "M", "L", "XL","OneSize","Custom"});
        filterPanel.add(size);
        
        style = createFilter("Style", new String[]{"Modern", "StreetWear", "Colorful", "Vintage", "Bohemian","Patchwork","Other"});
        
        filterPanel.add(style);
        price = createPriceFilter();
        filterPanel.add(price);
        
        amountLabel = new JLabel("Amount: 0.0");
        filterPanel.add(amountLabel);
        
        applyButton = new Button("Apply");
        filterPanel.add(applyButton);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder result = new StringBuilder();

                result.append("Categories:").append(getSelectedOption(categories)).append(",");
                result.append("Color:").append(getSelectedOption(color)).append(",");
                result.append("Size:").append(getSelectedOption(size)).append(",");
                result.append("Style:").append(getSelectedOption(style)).append(",");
//                result.append("Price: ").append(getSelectedPrice(price)).append(",");
                send(result.toString()+idSession);
                System.out.println(result.toString());
            }

        }); 
        logoutButton = new Button("Logout");
        filterPanel.add(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
                frame.setVisible(false);
                Main.function();
            }
        });
        
        productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(3, 3, 10, 10));
        
        JScrollPane productScroll = new JScrollPane(productPanel);
        frame.add(productScroll, BorderLayout.CENTER);
        JScrollPane filterScroll = new JScrollPane(filterPanel);
        frame.add(filterScroll, BorderLayout.WEST);
        
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private String getSelectedOption(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JRadioButton) {
                JRadioButton radioButton = (JRadioButton) comp;
                if (radioButton.isSelected()) {
                    return radioButton.getText(); 
                }
            }
        }
        return "None"; 
    }
    private String getSelectedPrice(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                JTextField textField = (JTextField) comp;
                return textField.getText(); 
            } else if (comp instanceof JSlider) {
                JSlider slider = (JSlider) comp;
                return slider.getMinimum() + " - " + slider.getValue(); 
            }
        }
        return "None"; 
    }
    private JPanel createFilter(String title, String[] option){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(title));
        ButtonGroup buttonGroup = new ButtonGroup();
        for (String string : option) {
            JRadioButton checkBox = new JRadioButton(string);
            buttonGroup.add(checkBox);
            panel.add(checkBox);
        }
        return panel;
    }
    
    private JPanel createPriceFilter(){
        JPanel panel = new JPanel();
        panel.setLayout(new  BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("price"));
        JSlider slider = new JSlider(0, 1000, 250);
        panel.add(slider);
        return panel;
    }
    
    
    public void close(){
        try {
            input.close();
            output.close();
            client.close();
            frame.setVisible(false);
            System.exit(0);
        } catch (Exception e) {
        }
    }
    private void connect(){
        try {
            client = new Socket("localhost", 12345);
            input = client.getInputStream();
            output = client.getOutputStream();
            
            output.write(idSession.getBytes());
            output.flush();
            
            ObjectInputStream ois = new ObjectInputStream(input);
            Account account = (Account) ois.readObject();
            
            if(account != null){
                amountLabel.setText(account.getAccount()+": " + account.getAmmount());
                new Thread(() -> listen()).start();
            }else{
                close();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void send(String mess){
        try {
            output.write(mess.getBytes());
            output.flush();
        } catch (Exception e) {
        }
    }
    
    private void listen() {
        while(true){
            try {
                ObjectInputStream ois = new ObjectInputStream(input);
        //                Set<Product> set = (Set<Product>) ois.readObject();
        //                listProduct = new ArrayList<>(set);
                        List<Product> listProduct = (List<Product>) ois.readObject();
                        productPanel.removeAll();
                        for (Product product : listProduct) {
                            productPanel.add(createProductCard(product));
                        }
                          
                        productPanel.revalidate();  // Cập nhật lại layout của panel
                        productPanel.repaint();     // Vẽ lại panel
            }   catch (IOException | ClassNotFoundException e) {
                        // Xử lý các lỗi khác (nếu có)
                        e.printStackTrace();
                    }

        }
    }


    
    
    private JPanel createProductCard(Product product) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Hình ảnh sản phẩm (giải mã từ picture)
        JPanel imageLabel = EncryptSupport.decode(product.getPicture());
        panel.add(imageLabel, BorderLayout.CENTER);

        // Tên và giá sản phẩm
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(new JLabel("Name: " + product.getName()));
        infoPanel.add(new JLabel("Price: " + product.getPrice()));
        infoPanel.add(new JLabel("Quantity Left: " + product.getQuantity()));
        panel.add(infoPanel, BorderLayout.SOUTH);

        return panel;
    }
    
}