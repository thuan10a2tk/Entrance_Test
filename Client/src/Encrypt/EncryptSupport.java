package Encrypt;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EncryptSupport {

    public static byte[] objectToByteArray(Object obj) throws IOException {
        if (obj == null) {
            return null;
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            return bos.toByteArray();
        }
    }

    public static Object byteArrayToObject(byte[] arrBytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(arrBytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return ois.readObject();
        }
    }

    public static boolean encryptQuestionsSaveToFile(String fname, byte[] data, String key) {
        try {
            SecretKey secretKey = getKey(key);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            try (FileOutputStream fos = new FileOutputStream(fname);
                 CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {
                cos.write(data);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] decryptQuestionsFromFile(String fname, String key) {
        try {
            SecretKey secretKey = getKey(key);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            try (FileInputStream fis = new FileInputStream(fname);
                 CipherInputStream cis = new CipherInputStream(fis, cipher);
                 ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = cis.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }
                return bos.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryption(byte[] data, String key) {
        try {
            SecretKey secretKey = getKey(key);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedData = cipher.doFinal(data);
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryption(byte[] data, String key) {
        try {
            SecretKey secretKey = getKey(key);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedData = cipher.doFinal(data);
            return new String(decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMD5(String msg) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(msg.getBytes("UTF-16LE"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static SecretKey getKey(String key) throws Exception {
        byte[] keyBytes = key.getBytes("ASCII");
        DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        return keyFactory.generateSecret(desKeySpec);
    }
    public static String encode(String path) {
        try {
            File file = new File(path);
            byte[] fileContent = new byte[(int) file.length()];
            try (FileInputStream fis = new FileInputStream(file)) {
                fis.read(fileContent);
            }

            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

     public static JPanel decode(String encodedString) {
         // Giải mã Base64 thành byte array
         byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
         // Chuyển byte array thành ImageIcon
         ImageIcon imageIcon = new ImageIcon(decodedBytes);
         // Tạo JLabel chứa ImageIcon
         JLabel imageLabel = new JLabel(imageIcon);
         // Tạo JPanel và thêm JLabel vào đó
         JPanel panel = new JPanel();
         panel.setLayout(new BorderLayout());
         panel.add(imageLabel, BorderLayout.CENTER);
         return panel;
    }
}
