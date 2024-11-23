import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class CreateImageFolder {
    public static void main(String[] args) {
        String folderPath = "product_images"; // Đường dẫn thư mục chứa ảnh
        File folder = new File(folderPath);

        // Tạo thư mục nếu chưa có
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Tạo 100 ảnh từ product0.jpg đến product99.jpg
        for (int i = 0; i < 100; i++) {
            String fileName = folderPath + "/product" + i + ".jpg";
            createRandomImage(fileName);
        }

        System.out.println("Thư mục chứa ảnh đã được tạo tại: " + folderPath);
    }

    // Phương thức tạo ảnh giả
    private static void createRandomImage(String fileName) {
        Random random = new Random();
        byte[] imageData = new byte[100]; // 100 bytes làm dữ liệu ngẫu nhiên đại diện cho ảnh
        random.nextBytes(imageData); // Tạo dữ liệu ngẫu nhiên

        // Tạo file ảnh trong thư mục
        try (OutputStream out = new FileOutputStream(fileName)) {
            out.write(imageData); // Ghi dữ liệu vào file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
