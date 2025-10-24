package gui.form;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import dao.TaiKhoanDAO;
import entity.TaiKhoan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class Login extends JFrame implements ActionListener 
{
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnExit;
    private TaiKhoanDAO dsTK;
    private JCheckBox chkShowPassword;
    
    public Login() 
    {
        initComponents();
        dsTK = new TaiKhoanDAO();
    }
    
    private void initComponents() 
    {
        setTitle("Đăng Nhập - Hệ Thống Quản Lý Hiệu Thuốc Tây");
        setSize(450, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 153, 153));
        headerPanel.setPreferredSize(new Dimension(450, 150));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        // Logo/Icon (nếu có)
        JLabel lblIcon = new JLabel("💊");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        lblIcon.setForeground(Color.WHITE);
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ HIỆU THUỐC TÂY");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitle = new JLabel("Đăng nhập để tiếp tục");
        lblSubtitle.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(230, 245, 245));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(Box.createVerticalGlue());
        headerPanel.add(lblIcon);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(lblTitle);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerPanel.add(lblSubtitle);
        headerPanel.add(Box.createVerticalGlue());
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(40, 50, 40, 50));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        
        // Username Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblUsername = new JLabel("Tên đăng nhập");
        lblUsername.setFont(new Font("Roboto", Font.BOLD, 13));
        lblUsername.setForeground(new Color(60, 60, 60));
        formPanel.add(lblUsername, gbc);
        
        // Username Panel with Icon
        gbc.gridy = 1;
        JPanel usernamePanel = new JPanel(new BorderLayout(8, 0));
        usernamePanel.setBackground(Color.WHITE);
        usernamePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        
        JLabel iconUsername = new JLabel(new FlatSVGIcon("./img/username.svg", 20, 20));
        txtUsername = new JTextField();
        txtUsername.setBorder(null);
        txtUsername.setFont(new Font("Roboto", Font.PLAIN, 14));
        txtUsername.setBackground(Color.WHITE);
        
        usernamePanel.add(iconUsername, BorderLayout.WEST);
        usernamePanel.add(txtUsername, BorderLayout.CENTER);
        formPanel.add(usernamePanel, gbc);
        
        // Password Label
        gbc.gridy = 2;
        gbc.insets = new Insets(20, 0, 8, 0);
        JLabel lblPassword = new JLabel("Mật khẩu");
        lblPassword.setFont(new Font("Roboto", Font.BOLD, 13));
        lblPassword.setForeground(new Color(60, 60, 60));
        formPanel.add(lblPassword, gbc);
        
        // Password Panel with Icon
        gbc.gridy = 3;
        gbc.insets = new Insets(8, 0, 8, 0);
        JPanel passwordPanel = new JPanel(new BorderLayout(8, 0));
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            new EmptyBorder(8, 12, 8, 12)
        ));
        
        JLabel iconPassword = new JLabel(new FlatSVGIcon("./img/password.svg", 20, 20));
        txtPassword = new JPasswordField();
        txtPassword.setBorder(null);
        txtPassword.setFont(new Font("Roboto", Font.PLAIN, 14));
        txtPassword.setBackground(Color.WHITE);
        
        passwordPanel.add(iconPassword, BorderLayout.WEST);
        passwordPanel.add(txtPassword, BorderLayout.CENTER);
        formPanel.add(passwordPanel, gbc);
        
        // Show Password Checkbox
        gbc.gridy = 4;
        gbc.insets = new Insets(8, 0, 8, 0);
        chkShowPassword = new JCheckBox("Hiển thị mật khẩu");
        chkShowPassword.setFont(new Font("Roboto", Font.PLAIN, 12));
        chkShowPassword.setBackground(Color.WHITE);
        chkShowPassword.setFocusPainted(false);
        chkShowPassword.addActionListener(e -> {
            if(chkShowPassword.isSelected()) 
            {
                txtPassword.setEchoChar((char) 0);
            } 
            else 
            {
                txtPassword.setEchoChar('•');
            }
        });
        formPanel.add(chkShowPassword, gbc);
        
        // Login Button
        gbc.gridy = 5;
        gbc.insets = new Insets(25, 0, 8, 0);
        btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setFont(new Font("Roboto", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(300, 45));
        btnLogin.setBackground(new Color(0, 153, 153));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBorderPainted(false);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(this);
        
        // Hover effect
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt) 
            {
                btnLogin.setBackground(new Color(0, 133, 133));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) 
            {
                btnLogin.setBackground(new Color(0, 153, 153));
            }
        });
        
        formPanel.add(btnLogin, gbc);
        
        // Exit Button
        gbc.gridy = 6;
        gbc.insets = new Insets(8, 0, 8, 0);
        btnExit = new JButton("THOÁT");
        btnExit.setFont(new Font("Roboto", Font.BOLD, 14));
        btnExit.setPreferredSize(new Dimension(300, 45));
        btnExit.setBackground(new Color(240, 240, 240));
        btnExit.setForeground(new Color(60, 60, 60));
        btnExit.setBorderPainted(false);
        btnExit.setFocusPainted(false);
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExit.addActionListener(e -> System.exit(0));
        
        // Hover effect
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            public void mouseEntered(java.awt.event.MouseEvent evt) 
            {
                btnExit.setBackground(new Color(220, 220, 220));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) 
            {
                btnExit.setBackground(new Color(240, 240, 240));
            }
        });
        
        formPanel.add(btnExit, gbc);
        
        // Footer Label
        gbc.gridy = 7;
        gbc.insets = new Insets(20, 0, 0, 0);
        JLabel lblFooter = new JLabel("© 2025 Nguyên Hưng Management System");
        lblFooter.setFont(new Font("Roboto", Font.ITALIC, 11));
        lblFooter.setForeground(new Color(150, 150, 150));
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(lblFooter, gbc);
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        setContentPane(mainPanel);
        
        // Enter key listener
        KeyAdapter enterKeyListener = new KeyAdapter() 
        {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    btnLogin.doClick();
                }
            }
        };
        
        txtUsername.addKeyListener(enterKeyListener);
        txtPassword.addKeyListener(enterKeyListener);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object obj = e.getSource();
        if(obj.equals(btnLogin)) 
        {
            login();
        }
    }
    
    private void login() 
    {
        String username = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword());
        
        // Validation
        if(username.isEmpty()) 
        {
            showError("Vui lòng nhập tên đăng nhập!");
            txtUsername.requestFocus();
            return;
        }
        
        if(pass.isEmpty()) 
        {
            showError("Vui lòng nhập mật khẩu!");
            txtPassword.requestFocus();
            return;
        }
        
        // Show loading
        btnLogin.setEnabled(false);
        btnLogin.setText("Đang đăng nhập...");
        
        // Perform login in background thread
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            private TaiKhoan foundAccount = null;
            
            @Override
            protected Boolean doInBackground() throws Exception {
                ArrayList<TaiKhoan> ds = dsTK.dsTaiKhoan();
                
                for (TaiKhoan t : ds) {
                    if (username.equalsIgnoreCase(t.getTenDangNhap().trim())) {
                        foundAccount = t;
                        break;
                    }
                }
                
                if (foundAccount == null) {
                    return false;
                }
                
                String validPass = foundAccount.getMatKhau().trim();
                return validPass.equals(pass);
            }
            
            @Override
            protected void done() {
                try {
                    boolean success = get();
                    
                    btnLogin.setEnabled(true);
                    btnLogin.setText("ĐĂNG NHẬP");
                    
                    if (foundAccount == null) {
                        showError("Tài khoản không tồn tại!");
                        txtUsername.requestFocus();
                        txtUsername.selectAll();
                    } else if (!success) {
                        showError("Mật khẩu không chính xác!");
                        txtPassword.requestFocus();
                        txtPassword.selectAll();
                    } else {
                        // Login successful
                        showSuccess("Đăng nhập thành công!");
                        System.out.println("Đang đăng nhập user " + foundAccount.getTenDangNhap());
                        
                        // Close login and open main window
                        SwingUtilities.invokeLater(() -> {
                            try {
                                dispose();
                                new ManHinhChinh(foundAccount).setVisible(true);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                                showError("Lỗi khi mở màn hình chính: " + e1.getMessage());
                            }
                        });
                    }
                } catch (Exception ex) {
                    btnLogin.setEnabled(true);
                    btnLogin.setText("ĐĂNG NHẬP");
                    ex.printStackTrace();
                    showError("Lỗi khi đăng nhập: " + ex.getMessage());
                }
            }
        };
        
        worker.execute();
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, 
            message, 
            "Lỗi đăng nhập", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, 
            message, 
            "Thành công", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            
            // Custom UI properties
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}