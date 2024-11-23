package Application;

import Encrypt.EncryptSupport;
import Entity.Account;
import Entity.Category;
import Entity.Product;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 * @author thuan
 */
public class Session {
    static CopyOnWriteArraySet<String>list = new CopyOnWriteArraySet<>();
    static InputStream input;
    static OutputStream output;
    
    public static void func() {
        try (ServerSocket server = new ServerSocket(12345)){
            while(true){
                Socket client = server.accept();
                input = client.getInputStream();
                byte[] buffer = new byte[1024];
                int readByte = input.read(buffer);
                String idSession = new String(buffer, 0, readByte);
                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                byte[] data = EncryptSupport.decryptFromFile(idSession, idSession);
                Account account = (Account) EncryptSupport.byteArrayToObject(data);
                if(account != null){
                    System.out.println(account.toString());
                    oos.writeObject(account);
                    oos.flush();
                    list.add(idSession);
                    sendRandom(idSession, client);
                    new Thread(() -> listen(idSession,client)).start();
                }else{
                    output.write("exit".getBytes());
                    output.flush();
                    try {
                        client.close();
                    } catch (Exception e) {
                    }
                }
                
            }
        } catch (Exception e) {
        }
    }   
    static List<Product> li;
    static List<Category> listCategory ;
    private static void sendRandom(String id, Socket client) {
        try {
            
            byte[] data= EncryptSupport.decryptFromFile("Product.dat", id);
            li = (List<Product>) EncryptSupport.byteArrayToObject(data);
            Set<Product> set = new HashSet<>();
            Random random = new Random();
            
            while(set.size() < 9){
                int idx = random.nextInt(99);
                Product product = li.get(idx);
                
                if(set.contains(product)) continue;
                set.add(product);
                System.out.println(product.toString());
                
            }
                List<Product> result = new ArrayList<>(set);
                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                oos.writeObject(result);
                oos.flush();
                
                byte[]data_2 = EncryptSupport.decryptFromFile("Category.dat", id);
                listCategory = (List<Category>) EncryptSupport.byteArrayToObject(data_2);
                
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   

    private static void listen(String id, Socket client) {
        while(true){
            try {
                byte[] buffer = new byte[1024];
                int readBytes = input.read(buffer);
                if(readBytes == -1){
                    list.remove(id);
                    break;
                }
                String mess = new String(buffer, 0, readBytes);
                if (mess.contains(id)) {
                    String[] parts = mess.split(",");
                    String selectedCategories = parts[0].split(":")[1].trim();
                    String selectedColor = parts[1].split(":")[1].trim();
                    String selectedSize = parts[2].split(":")[1].trim();
                    String selectedStyle = parts[3].split(":")[1].trim();
                    System.out.println(selectedCategories + " " + selectedColor +" " + selectedSize+" " + selectedStyle);
                    Set<Integer> filter = new HashSet<>();
                    for (Category category : listCategory) {
                        
                        String name = category.getName().name();
                        String color = category.getColor().name();
                        String size = category.getSize().name();
                        String style = category.getStyle().name();
                        
                        boolean var_1 = selectedCategories.equals("None") || selectedCategories.toLowerCase().equalsIgnoreCase(name.toLowerCase());
                        boolean var_2 = selectedColor.equals("None") || selectedColor.toLowerCase().equalsIgnoreCase(color.toLowerCase());
                        boolean var_3 = selectedSize.equals("None") || selectedSize.toLowerCase().equalsIgnoreCase(size.toLowerCase());
                        boolean var_4 = selectedStyle.equals("None") || selectedStyle.toLowerCase().equalsIgnoreCase(style.toLowerCase());
                        
                        if(var_1 && var_2 && var_3 && var_4){
                            filter.add(category.getId());
                        }
                    }
                    List<Product> result = new ArrayList<>();
                    for (Product product : li) {
                        if(filter.contains(product.getId())){
                            result.add(product);
                        }
                    }
                    for (Product product : result) {
                        System.out.println(product.toString());
                    }
                    ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                    oos.writeObject(result);
                    oos.flush();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
