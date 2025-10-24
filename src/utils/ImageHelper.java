package utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class ImageHelper {
    
    /**
     * Load hình ảnh từ thư mục img_product
     * Tự động tìm đường dẫn đúng
     */
    public static void setImageToLabel(JLabel label, String imagePath, int width, int height) {
        try {
            if (imagePath == null || imagePath.trim().isEmpty()) {
                setNoImage(label);
                return;
            }
            
            // Loại bỏ "img_product/" nếu có trong path
            String fileName = imagePath.replace("img_product/", "").replace("img_product\\", "");
            
            // Thử load từ resources trước
            URL imgURL = ImageHelper.class.getResource("/img_product/" + fileName);
            
            ImageIcon icon = null;
            
            if (imgURL != null) {
                // Load từ resources
                icon = new ImageIcon(imgURL);
                System.out.println("✓ Load hình từ resources: " + fileName);
            } else {
                // Thử load từ file system
                // Tìm đường dẫn project root
                String projectPath = System.getProperty("user.dir");
                String fullPath = projectPath + "/img_product/" + fileName;
                
                File imgFile = new File(fullPath);
                if (imgFile.exists()) {
                    icon = new ImageIcon(fullPath);
                    System.out.println("✓ Load hình từ file: " + fullPath);
                } else {
                    // Thử đường dẫn khác (trong src/)
                    fullPath = projectPath + "/src/img_product/" + fileName;
                    imgFile = new File(fullPath);
                    if (imgFile.exists()) {
                        icon = new ImageIcon(fullPath);
                        System.out.println("✓ Load hình từ src: " + fullPath);
                    } else {
                        System.err.println("✗ Không tìm thấy file: " + fileName);
                        System.err.println("  Đã thử: ");
                        System.err.println("  - Resources: /img_product/" + fileName);
                        System.err.println("  - File: " + projectPath + "/img_product/" + fileName);
                        System.err.println("  - Src: " + projectPath + "/src/img_product/" + fileName);
                        setNoImage(label);
                        return;
                    }
                }
            }
            
            if (icon != null) {
                Image image = icon.getImage();
                Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImage));
                label.setText("");
            } else {
                setNoImage(label);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            setErrorImage(label);
        }
    }
    
    /**
     * Set hình ảnh với tỷ lệ giữ nguyên
     */
    public static void setImageKeepRatio(JLabel label, String imagePath, int maxWidth, int maxHeight) {
        try {
            if (imagePath == null || imagePath.trim().isEmpty()) {
                setNoImage(label);
                return;
            }
            
            String fileName = imagePath.replace("img_product/", "").replace("img_product\\", "");
            
            URL imgURL = ImageHelper.class.getResource("/img_product/" + fileName);
            
            ImageIcon originalIcon = null;
            
            if (imgURL != null) {
                originalIcon = new ImageIcon(imgURL);
            } else {
                String projectPath = System.getProperty("user.dir");
                String fullPath = projectPath + "/img_product/" + fileName;
                
                File imgFile = new File(fullPath);
                if (imgFile.exists()) {
                    originalIcon = new ImageIcon(fullPath);
                } else {
                    fullPath = projectPath + "/src/img_product/" + fileName;
                    imgFile = new File(fullPath);
                    if (imgFile.exists()) {
                        originalIcon = new ImageIcon(fullPath);
                    }
                }
            }
            
            if (originalIcon != null && originalIcon.getIconWidth() > 0) {
                Image originalImage = originalIcon.getImage();
                
                int originalWidth = originalIcon.getIconWidth();
                int originalHeight = originalIcon.getIconHeight();
                
                double widthRatio = (double) maxWidth / originalWidth;
                double heightRatio = (double) maxHeight / originalHeight;
                double ratio = Math.min(widthRatio, heightRatio);
                
                int newWidth = (int) (originalWidth * ratio);
                int newHeight = (int) (originalHeight * ratio);
                
                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImage));
                label.setText("");
            } else {
                setNoImage(label);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            setErrorImage(label);
        }
    }
    
    private static void setNoImage(JLabel label) {
        label.setIcon(null);
        label.setText("No Image");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.GRAY);
    }
    
    private static void setErrorImage(JLabel label) {
        label.setIcon(null);
        label.setText("Error Loading Image");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.RED);
    }
}