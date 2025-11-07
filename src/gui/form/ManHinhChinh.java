package gui.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.RenderingHints;
import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGUniverse;

import dao.NhanVienDAO;
import entity.TaiKhoan;

public class ManHinhChinh extends JFrame implements ActionListener{
	private JButton btnHoaDon;
	private JButton btnNhanVien;
	private JButton btnKhachHang;
	private JButton btnTaiKhoan;
	private JButton btnThuoc;
	private JPanel pnCenter; 
	private JPanel backgroundPanel;
	private JButton btnNhaCungCap;
	private JButton btnHeThong;
	private TaiKhoan taiKhoan;
	private NhanVienDAO nvDAO;
	private JPanel pnUserInfo;
	private JPanel pnNorth;
	private JPanel pnMenu;
	
	// Biến lưu kích thước ban đầu
	private int baseButtonWidth = 200;
	private int baseButtonHeight = 50;
	private int baseFontSize = 20;
	private int baseUserPanelWidth = 400;
	private int baseUserPanelHeight = 60;
	
	public ManHinhChinh(TaiKhoan tk) throws SQLException {
		nvDAO = new NhanVienDAO();
		taiKhoan = tk;
		this.setTitle("Hiệu Thuốc Tây Nguyên Hưng");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		
		// Panel North - Menu Bar
		pnNorth = new JPanel(new BorderLayout());
		pnNorth.setOpaque(false);
		pnNorth.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		// Panel bên trái chứa các nút menu
		pnMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
		pnMenu.setOpaque(false);
		
		// Khởi tạo các nút menu
		initializeMenuButtons();
		
		// Panel thông tin người vào
		pnUserInfo = createUserInfoPanel(tk);
		
		// Thêm vào pnNorth
		pnNorth.add(pnMenu, BorderLayout.WEST);
		pnNorth.add(pnUserInfo, BorderLayout.EAST);
		
		backgroundPanel = new JPanel() {
            private Image backgroundImage = new ImageIcon("src/img/anhHieuThuoc.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        
        // Form chức năng
        pnCenter = new JPanel(new BorderLayout());
        pnCenter.setOpaque(false);
        
        backgroundPanel.add(pnNorth, BorderLayout.NORTH);
        backgroundPanel.add(pnCenter, BorderLayout.CENTER);
        setContentPane(backgroundPanel);
        
        // Auto Resize
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustSizeBasedOnScreenSize();
            }
        });
        

        adjustSizeBasedOnScreenSize();
	}
	
	private void initializeMenuButtons() {
		String vaiTro = taiKhoan.getVaiTro();
		// Hệ thống
		btnHeThong = taoButtonDep("Hệ thống", "src/img/hethong.svg", baseButtonWidth, baseButtonHeight);
		JPopupMenu dropMenuHeThong = new JPopupMenu();
		String[] chucNangHeThong = {"Trang chủ", "Đăng xuất", "Thoát"};
		themMenuItem(dropMenuHeThong, chucNangHeThong, this);
		btnHeThong.addActionListener(e -> {
			dropMenuHeThong.show(btnHeThong, 0, btnHeThong.getHeight());
		});
		
		// Hoá Đơn
		btnHoaDon = taoButtonDep("Hoá Đơn", "src/img/bill.svg", baseButtonWidth, baseButtonHeight);
		JPopupMenu dropMenuHoaDon = new JPopupMenu();
		String[] chucNangHoaDon = {"Lập hoá đơn","Quản lý hoá đơn", "Tìm kiếm hoá đơn"};
		themMenuItem(dropMenuHoaDon, chucNangHoaDon, this);
		btnHoaDon.addActionListener(e -> {
			dropMenuHoaDon.show(btnHoaDon, 0, btnHoaDon.getHeight());
		});
		
		// Nhân Viên
		
		btnNhanVien = taoButtonDep("Nhân Viên", "src/img/employee.svg", baseButtonWidth, baseButtonHeight);
		JPopupMenu dropMenuNhanVien = new JPopupMenu();
		if (vaiTro.equals("Nhân viên quản lý")) {
			String[] chucNangNhanVien = {"Quản lý nhân viên","Tìm kiếm nhân viên", "Khuyến mãi", "Thuế", "Phiếu Đặt Thuốc", "Phiếu Nhập Thuốc", "Thống kê"};
			themMenuItem(dropMenuNhanVien, chucNangNhanVien, this);
		} else {
			String[] chucNangNhanVien = {"Khuyến mãi", "Thuế", "Phiếu Đặt Thuốc", "Phiếu Nhập Thuốc", "Thống kê"};
			themMenuItem(dropMenuNhanVien, chucNangNhanVien, this);
		}
		
		btnNhanVien.addActionListener(e -> {
			dropMenuNhanVien.show(btnNhanVien,0,btnNhanVien.getHeight());
		});
		
		// Khách Hàng
		btnKhachHang = taoButtonDep("Khách Hàng", "src/img/customer_52.svg", baseButtonWidth, baseButtonHeight);
		JPopupMenu dropMenuKhachHang = new JPopupMenu();
		String[] chucNangKhachHang = {"Quản lý khách hàng", "Tìm kiếm khách hàng"};
		themMenuItem(dropMenuKhachHang, chucNangKhachHang, this);
		btnKhachHang.addActionListener(e -> {
			dropMenuKhachHang.show(btnKhachHang,0,btnKhachHang.getHeight());
		});
		
		// Tài Khoản
		if (vaiTro.equals("Nhân viên quản lý")) {
			btnTaiKhoan = taoButtonDep("Tài Khoản", "src/img/security.svg", baseButtonWidth, baseButtonHeight);
			JPopupMenu dropMenuTaiKhoan = new JPopupMenu();
			String[] chucNangTaiKhoan = {"Quản lý tài khoản"};
			themMenuItem(dropMenuTaiKhoan, chucNangTaiKhoan, this);
			btnTaiKhoan.addActionListener(e -> {
				dropMenuTaiKhoan.show(btnTaiKhoan,0,btnTaiKhoan.getHeight());
			});
		}
		
		// Thuốc
		btnThuoc = taoButtonDep("Thuốc", "src/img/medicine.svg", baseButtonWidth, baseButtonHeight);
		JPopupMenu dropMenuThuoc = new JPopupMenu();
		String[] chucNangThuoc = {"Quản lý thuốc", "Tìm kiếm thuốc", "Danh Mục Thuốc", "Quản lý đổi/trả thuốc"};
		themMenuItem(dropMenuThuoc, chucNangThuoc, this);
		btnThuoc.addActionListener(e -> {
			dropMenuThuoc.show(btnThuoc,0,btnThuoc.getHeight());
		});
		
		if (vaiTro.equals("Nhân viên quản lý")) {
			btnNhaCungCap = taoButtonDep("Nhà Cung Cấp", "src/img/trucks.svg", baseButtonWidth, baseButtonHeight);
			JPopupMenu dropMenuNhaCungCap = new JPopupMenu();
			String[] chucNangNhaCungCap = {"Quản lý nhà cung cấp", "Tìm kiếm nhà cung cấp"};
			themMenuItem(dropMenuNhaCungCap, chucNangNhaCungCap, this);
			btnNhaCungCap.addActionListener(e -> {
				dropMenuNhaCungCap.show(btnNhaCungCap,0,btnNhaCungCap.getHeight());
			});
		}
		
		pnMenu.add(btnHeThong);
		pnMenu.add(btnHoaDon);
		pnMenu.add(btnThuoc);
		pnMenu.add(btnNhanVien);
		pnMenu.add(btnKhachHang);
		if (vaiTro.equals("Nhân viên quản lý")) {
			pnMenu.add(btnNhaCungCap);
			pnMenu.add(btnTaiKhoan);
		}
	}
	
	private JPanel createUserInfoPanel(TaiKhoan tk) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));
		panel.setOpaque(true);
		panel.setBackground(new Color(41, 128, 185, 200));
		panel.setBorder(new LineBorder(Color.WHITE, 2, true));
		panel.setPreferredSize(new Dimension(baseUserPanelWidth, baseUserPanelHeight));
		
		// Icon user
		JLabel lblUserIcon = new JLabel("👤");
		lblUserIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
		lblUserIcon.setForeground(Color.WHITE);
		lblUserIcon.setName("userIcon"); // Đặt tên để tìm sau này
		
		// Panel chứa thông tin text
		JPanel pnTextInfo = new JPanel();
		pnTextInfo.setLayout(new BoxLayout(pnTextInfo, BoxLayout.Y_AXIS));
		pnTextInfo.setOpaque(false);
		
		// Tên nhân viên
		String tenNV = "";
		try {
			tenNV = nvDAO.getNhanVienTheoMa(tk.getNhanVien().getMaNV()).getTenNV();
		} catch (SQLException e) {
			tenNV = "N/A";
			e.printStackTrace();
		}
		
		JLabel lblTenNV = new JLabel(tenNV);
		lblTenNV.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTenNV.setForeground(Color.WHITE);
		lblTenNV.setAlignmentX(LEFT_ALIGNMENT);
		lblTenNV.setName("tenNV"); // Đặt tên
		
		// Vai trò
		JPanel pnVaiTro = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		pnVaiTro.setOpaque(false);
		
		JLabel lblStatus = new JLabel("●");
		lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblStatus.setForeground(new Color(46, 204, 113));
		
		JLabel lblVaiTro = new JLabel(tk.getVaiTro());
		lblVaiTro.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblVaiTro.setForeground(new Color(236, 240, 241));
		lblVaiTro.setName("vaiTro"); 
		
		pnVaiTro.add(lblStatus);
		pnVaiTro.add(lblVaiTro);
		
		pnTextInfo.add(lblTenNV);
		pnTextInfo.add(Box.createVerticalStrut(3));
		pnTextInfo.add(pnVaiTro);
		
		panel.add(lblUserIcon);
		panel.add(pnTextInfo);
		
		return panel;
	}
	
	/**
	 * Điều chỉnh kích thước các thành phần dựa trên kích thước màn hình hiện tại
	 */
	private void adjustSizeBasedOnScreenSize() {
		int screenWidth = getWidth();
		int screenHeight = getHeight();
		
		// Tính scale factor dựa trên độ rộng màn hình (chuẩn 1920x1080)
		double widthScale = screenWidth / 1920.0;
		double heightScale = screenHeight / 1080.0;
		double scale = Math.min(widthScale, heightScale);
		
		// Đảm bảo scale không quá nhỏ hoặc quá lớn
		scale = Math.max(0.7, Math.min(scale, 1.5));
		
		// Điều chỉnh kích thước button
		int newButtonWidth = (int)(baseButtonWidth * scale);
		int newButtonHeight = (int)(baseButtonHeight * scale);
		int newFontSize = (int)(baseFontSize * scale);
		
		// Cập nhật kích thước các button
		updateButtonSize(btnHeThong, newButtonWidth, newButtonHeight, newFontSize);
		updateButtonSize(btnHoaDon, newButtonWidth, newButtonHeight, newFontSize);
		updateButtonSize(btnNhanVien, newButtonWidth, newButtonHeight, newFontSize);
		updateButtonSize(btnKhachHang, newButtonWidth, newButtonHeight, newFontSize);
		updateButtonSize(btnTaiKhoan, newButtonWidth, newButtonHeight, newFontSize);
		updateButtonSize(btnThuoc, newButtonWidth, newButtonHeight, newFontSize);
		updateButtonSize(btnNhaCungCap, 180, newButtonHeight, newFontSize);
		
		// Điều chỉnh kích thước user info panel
		int newUserPanelWidth = (int)(baseUserPanelWidth * scale);
		int newUserPanelHeight = (int)(baseUserPanelHeight * scale);
		pnUserInfo.setPreferredSize(new Dimension(newUserPanelWidth, newUserPanelHeight));
		
		// Điều chỉnh font size trong user info panel
		updateUserInfoFontSize((int)(28 * scale), (int)(16 * scale), (int)(13 * scale));
		
		// Điều chỉnh padding của pnNorth
		int padding = (int)(10 * scale);
		pnNorth.setBorder(new EmptyBorder(padding, padding, padding, padding));
		
		// Điều chỉnh spacing của pnMenu
		FlowLayout layout = (FlowLayout) pnMenu.getLayout();
		layout.setHgap((int)(15 * scale));
		
		// Refresh UI
		pnNorth.revalidate();
		pnNorth.repaint();
	}
	
	/**
	 * Cập nhật kích thước và font của button
	 */
	private void updateButtonSize(JButton btn, int width, int height, int fontSize) {
		if (btn != null) {
			btn.setPreferredSize(new Dimension(width, height));
			Font currentFont = btn.getFont();
			btn.setFont(new Font(currentFont.getName(), currentFont.getStyle(), fontSize));
			
			// Cập nhật icon size
			if (btn.getIcon() != null) {
				int iconSize = (int)(20 * (fontSize / 20.0));
				ImageIcon originalIcon = (ImageIcon) btn.getIcon();
				Image scaledImage = originalIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
				btn.setIcon(new ImageIcon(scaledImage));
			}
		}
	}
	
	/**
	 * Cập nhật font size trong user info panel
	 */
	private void updateUserInfoFontSize(int iconSize, int nameSize, int roleSize) {
		for (java.awt.Component comp : pnUserInfo.getComponents()) {
			if (comp instanceof JLabel) {
				JLabel label = (JLabel) comp;
				if ("userIcon".equals(label.getName())) {
					label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, iconSize));
				}
			} else if (comp instanceof JPanel) {
				JPanel textPanel = (JPanel) comp;
				for (java.awt.Component innerComp : textPanel.getComponents()) {
					if (innerComp instanceof JLabel) {
						JLabel label = (JLabel) innerComp;
						if ("tenNV".equals(label.getName())) {
							label.setFont(new Font("Segoe UI", Font.BOLD, nameSize));
						}
					} else if (innerComp instanceof JPanel) {
						JPanel rolePanel = (JPanel) innerComp;
						for (java.awt.Component roleComp : rolePanel.getComponents()) {
							if (roleComp instanceof JLabel) {
								JLabel label = (JLabel) roleComp;
								if ("vaiTro".equals(label.getName())) {
									label.setFont(new Font("Segoe UI", Font.PLAIN, roleSize));
								}
							}
						}
					}
				}
			}
		}
	}
	
	// Phương thức để thêm MenuItem với ActionListener
	private void themMenuItem(JPopupMenu menu, String[] names, ActionListener listener) {
	    for (String name : names) {
	        JMenuItem item = new JMenuItem(name);
	        item.setFont(new Font("Segoe UI", Font.BOLD, 20));
	        item.setForeground(new Color(41, 128, 185)); 
	        Color baseColor = new Color(236, 240, 241);  
	        Color hoverColor = new Color(52, 152, 219);  
	        Color hoverTextColor = Color.WHITE;
	        item.setBackground(baseColor);
	        item.setPreferredSize(new Dimension(250, 50));
	        
	        item.setBorderPainted(false);   
	        item.setFocusPainted(false);    
	        item.setContentAreaFilled(true);
	        
	        // SET BORDER BAN ĐẦU
	        EmptyBorder originalBorder = new EmptyBorder(10, 0, 10, 20);
	        item.setBorder(originalBorder);
	        
	        item.addActionListener(e -> {
	            // Reset màu sắc khi click
	            item.setBackground(baseColor);
	            item.setForeground(new Color(41, 128, 185));
	            item.setBorder(originalBorder);
	            listener.actionPerformed(e);
	        });

	        item.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                item.setBackground(hoverColor);
	                item.setForeground(hoverTextColor);
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                item.setBackground(baseColor);
	                item.setForeground(new Color(41, 128, 185));
	            }
	        });
	        menu.add(item);
	    }
	}
	
	public static JButton taoButtonDep(String text, String iconPath, int width, int height) {
        JButton btn = new JButton(text);

        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setForeground(new Color(41, 128, 185));
        Color baseColor = new Color(236, 240, 241);
        Color hoverColor = new Color(52, 152, 219);
        Color hoverTextColor = Color.WHITE;
        btn.setBackground(baseColor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setBorder(new LineBorder(new Color(189, 195, 199), 2, true));
        btn.setContentAreaFilled(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(width, height));
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));

        // --- Xử lý icon ---
        if (iconPath != null && !iconPath.isEmpty()) {
            try {
                ImageIcon icon = null;
                String resourcePath = iconPath;
                if (resourcePath.startsWith("src/")) {
                    resourcePath = resourcePath.substring(4); 
                }
                if (!resourcePath.startsWith("/")) {
                    if (!resourcePath.contains("/")) {
                        resourcePath = "/img/" + resourcePath;
                    } else {
                        resourcePath = "/" + resourcePath;
                    }
                }

                URL resUrl = ManHinhChinh.class.getResource(resourcePath);
                if (resUrl != null) {
                    if (iconPath.toLowerCase().endsWith(".svg")) {
                        icon = renderSVG(resUrl, 24, 24);
                    } else {
                        icon = new ImageIcon(resUrl);
                        Image scaled = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                        icon = new ImageIcon(scaled);
                    }
                } else {
                    File f = new File(iconPath);
                    if (f.exists()) {
                        if (iconPath.toLowerCase().endsWith(".svg")) {
                            icon = renderSVG(f.toURI().toURL(), 24, 24);
                        } else {
                            ImageIcon raw = new ImageIcon(iconPath);
                            Image scaled = raw.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                            icon = new ImageIcon(scaled);
                        }
                    } else {
                        System.err.println("Không tìm thấy icon: (resource) " + resourcePath + " hoặc (file) " + iconPath);
                    }
                }

                if (icon != null) {
                    btn.setIcon(icon);
                    btn.setHorizontalTextPosition(SwingConstants.RIGHT);
                    btn.setIconTextGap(8);
                }
            } catch (Exception e) {
                System.err.println("⚠️ Không thể tải icon: " + iconPath);
                e.printStackTrace();
            }
        }

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hoverColor);
                btn.setForeground(hoverTextColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(baseColor);
                btn.setForeground(new Color(41, 128, 185));
            }
        });

        return btn;
    }

    private static ImageIcon renderSVG(URL url, int w, int h) throws Exception {
        SVGUniverse universe = new SVGUniverse();
        URI handle = universe.loadSVG(url);
        SVGDiagram diagram = universe.getDiagram(handle);
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double scaleX = w / diagram.getWidth();
        double scaleY = h / diagram.getHeight();
        g2.scale(scaleX, scaleY);
        diagram.render(g2);
        g2.dispose();
        return new ImageIcon(img);
    }
	
	public void hienThiForm(JPanel formMoi) {
		pnCenter.removeAll(); 
		pnCenter.add(formMoi, BorderLayout.CENTER); 
		pnCenter.revalidate(); 
		pnCenter.repaint(); 
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		// Xử lý cho các chức năng Hoá Đơn
		if(command.equals("Quản lý hoá đơn")) {
			hienThiForm(new FormQuanLyHoaDon(this,taiKhoan));
		}
		else if(command.equals("Tìm kiếm hoá đơn")) {
			System.out.println("Hiển thị form tìm kiếm hóa đơn");
			hienThiForm(new FormTimKiemHoaDon());
		}
		else if (command.equals("Lập hoá đơn")) {
		    System.out.println("Hiển thị form lập hóa đơn");
		    try {
				hienThiForm(new formLapHoaDon(taiKhoan));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		// Xử lý cho các chức năng Nhân Viên
		else if(command.equals("Quản lý nhân viên")) {
			hienThiForm(new formQuanLyNhanVien());
		}
		else if(command.equals("Tìm kiếm nhân viên")) {
			hienThiForm(new formTimKiemNV());
		}
		else if(command.equals("Khuyến mãi")) {
			hienThiForm(new formKhuyenMai());
		}
		else if(command.equals("Thuế")) {
			hienThiForm(new formThue());
		}
		else if(command.equals("Phiếu Đặt Thuốc")) {
			hienThiForm(new formPhieuDatThuoc(taiKhoan));
		}
		else if(command.equals("Phiếu Nhập Thuốc")) {
			hienThiForm(new FormPhieuNhapThuoc());
		}
		else if(command.equals("Thống kê")) {
			hienThiForm(new FormThongKeLoiNhuan());
		}
		
		// Xử lý cho các chức năng Khách Hàng
		else if(command.equals("Quản lý khách hàng")) {
			hienThiForm(new FormQuanLyKhachHang());
		}
		else if(command.equals("Tìm kiếm khách hàng")) {
			hienThiForm(new formTimKiemKH());
		}
		
		// Xử lý cho các chức năng Tài Khoản
		else if(command.equals("Quản lý tài khoản")) {
			hienThiForm(new formQuanLyTK());
		}
		
		// Xử lý cho các chức năng Thuốc
		else if(command.equals("Quản lý thuốc")) {
			hienThiForm(new formQuanLyThuoc());
		}
		else if(command.equals("Tìm kiếm thuốc")) {
			hienThiForm(new formTimKiemThuoc());
		}
		else if(command.equals("Danh Mục Thuốc")) {
			try {
				hienThiForm(new formDanhMucThuoc());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		else if(command.equals("Quản lý đổi/trả thuốc")) {
			hienThiForm(new formQuanLyDoiTraThuoc());
		}
		
		// Xử lý cho các chức năng nhà cung cấp
		else if(command.equals("Quản lý nhà cung cấp")) {
			hienThiForm(new FormQuanLyNhaCungCap());
		} 
		else if (command.equals("Tìm kiếm nhà cung cấp")) {
			hienThiForm(new TimKiemNhaCungCap());
		}
		
		// Xử lý cho các chức năng hệ thống
		else if (command.equals("Trang chủ")) {
			pnCenter.removeAll();
			pnCenter.revalidate();
			pnCenter.repaint();
		}
		else if (command.equals("Đăng xuất")) {
			new Login().setVisible(true);
			this.dispose();
		}
		else if (command.equals("Thoát")) {
			System.exit(0);
		}
	}
}