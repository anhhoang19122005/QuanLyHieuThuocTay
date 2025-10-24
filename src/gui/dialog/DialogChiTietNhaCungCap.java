package gui.dialog;

import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.border.*;

import entity.NhaCungCap;

public class DialogChiTietNhaCungCap extends JDialog {
    
    private NhaCungCap nhaCungCap;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    
    public DialogChiTietNhaCungCap(Frame parent, NhaCungCap ncc) {
        super(parent, "Thông tin chi tiết nhà cung cấp", true);
        this.nhaCungCap = ncc;
        
        initComponents();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setSize(600, 450);
        setResizable(false);
        getContentPane().setBackground(new Color(230, 245, 245));
        setLayout(new BorderLayout(10, 10));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 153, 153));
        headerPanel.setPreferredSize(new Dimension(600, 60));
        
        JLabel lblTitle = new JLabel("THÔNG TIN CHI TIẾT NHÀ CUNG CẤP");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        contentPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 10, 15, 10);
        
        Font labelFont = new Font("Roboto", Font.BOLD, 15);
        Font valueFont = new Font("Roboto", Font.PLAIN, 15);
        
        int row = 0;
        
        // Mã NCC
        addInfoRow(contentPanel, gbc, row++, "Mã nhà cung cấp:", nhaCungCap.getMaNCC(), labelFont, valueFont);
        
        // Tên NCC
        addInfoRow(contentPanel, gbc, row++, "Tên nhà cung cấp:", nhaCungCap.getTenNCC(), labelFont, valueFont);
        
        // Số điện thoại
        addInfoRow(contentPanel, gbc, row++, "Số điện thoại:", nhaCungCap.getSoDienThoai(), labelFont, valueFont);
        
        // Công nợ
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        JLabel lblCongNo = new JLabel("Công nợ:");
        lblCongNo.setFont(labelFont);
        contentPanel.add(lblCongNo, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        String congNoStr = df.format(nhaCungCap.getCongNo()) + " đ";
        JLabel lblCongNoValue = new JLabel(congNoStr);
        lblCongNoValue.setFont(valueFont);
        
        // Màu sắc theo mức công nợ
        if (nhaCungCap.getCongNo() > 0) {
            lblCongNoValue.setForeground(new Color(255, 87, 34)); // Màu cam - có công nợ
        } else {
            lblCongNoValue.setForeground(new Color(46, 125, 50)); // Màu xanh lá - không công nợ
        }
        contentPanel.add(lblCongNoValue, gbc);
        
        add(contentPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setBackground(new Color(230, 245, 245));
        
        JButton btnDong = new JButton("ĐÓNG");
        btnDong.setFont(new Font("Roboto", Font.BOLD, 14));
        btnDong.setPreferredSize(new Dimension(140, 40));
        btnDong.setBackground(new Color(0, 153, 153));
        btnDong.setForeground(Color.WHITE);
        btnDong.setFocusPainted(false);
        btnDong.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDong.setBorder(new LineBorder(new Color(0, 153, 153), 2, true));
        btnDong.addActionListener(e -> dispose());
        
        buttonPanel.add(btnDong);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void addInfoRow(JPanel panel, GridBagConstraints gbc, int row, 
                           String labelText, String valueText, Font labelFont, Font valueFont) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        
        JLabel value = new JLabel(valueText);
        value.setFont(valueFont);
        panel.add(value, gbc);
    }
}