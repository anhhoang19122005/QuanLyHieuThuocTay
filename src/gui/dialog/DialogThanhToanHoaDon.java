package gui.dialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import dao.ThueDAO;
import dao.ThuocDAO;
import dao.ChiTietHoaDonDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.KhuyenMaiDAO;
import dao.NhanVienDAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.Thue;
import gui.form.formLapHoaDon;
import entity.NhanVien;


public class DialogThanhToanHoaDon extends JDialog  {
    
    private JLabel lblMaHoaDon;
    private JLabel lblNgayLap;
    private JLabel lblNhanVien;
    private JLabel lblKhachHang;
    private JLabel lblSoDienThoai;
    private JTable tableChiTiet;
    private DefaultTableModel tableModel;
    private JTextField txtTongTien;
    private JTextField txtThue;
    private JTextField txtGiamGia;
    private JTextField txtThanhTien;
    private JTextField txtTienNhanVao;
    private JTextField txtTienThua;
    private JButton btnXacNhan;
    private JButton btnHuy;
    private boolean confirmed = false;
    private ThueDAO thueDAO;
    private NhanVienDAO nhanVienDAO;
	private KhuyenMaiDAO khuyenMaiDAO;
	private HoaDonDAO hdDAO;
	private ChiTietHoaDonDAO cthdDAO;
	private ThuocDAO thuocDAO;
	private KhachHangDAO khDAO;
	private JPanel mainPanel;
	private boolean isThanhToan = false;
    /**
     * Constructor
     */
    public DialogThanhToanHoaDon(Frame parent, String maHoaDon, String tenKhachHang, 
                               String soDienThoai, ArrayList<ChiTietHoaDon> dsChiTietHoaDon, 
                               double tongTien, double tienNhanVao, String maNhanVien) {
        super(parent, "Chi Tiết Hóa Đơn", true);
        thueDAO = new ThueDAO();
        thuocDAO = new ThuocDAO();
        nhanVienDAO = new NhanVienDAO();
        cthdDAO = new ChiTietHoaDonDAO();
        hdDAO = new HoaDonDAO();
        khDAO = new KhachHangDAO();
        initComponents(maHoaDon, tenKhachHang, soDienThoai, dsChiTietHoaDon, tongTien, tienNhanVao, maNhanVien);
        setLocationRelativeTo(parent);
    }
    
    /**
     * Khởi tạo các components
     */
    private void initComponents(String maHoaDon, String tenKhachHang, String soDienThoai,
                               ArrayList<ChiTietHoaDon> dsChiTietHoaDon, double tongTien, 
                               double tienNhanVao, String maNhanVien) {
        
        setSize(900, 800);
        setResizable(false);
        getContentPane().setBackground(new Color(230, 245, 245));
        setLayout(new BorderLayout(10, 10));
        
        // ===== TITLE PANEL =====
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 153, 153));
        titlePanel.setPreferredSize(new Dimension(900, 60));
        
        JLabel lblTitle = new JLabel("CHI TIẾT HÓA ĐƠN");
        lblTitle.setFont(new Font("Roboto", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);
        
        add(titlePanel, BorderLayout.NORTH);
        
        // ===== MAIN PANEL =====
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // ===== THÔNG TIN HÓA ĐƠN - LAYOUT NGANG =====
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setMaximumSize(new Dimension(840, 120));
        
        Font labelFont = new Font("Roboto", Font.BOLD, 14);
        Font valueFont = new Font("Roboto", Font.PLAIN, 14);
        
        // Lấy thông tin nhân viên từ mã nhân viên
        String tenNhanVien = maNhanVien;
        try {
            NhanVien nv = nhanVienDAO.getNhanVienTheoMa(maNhanVien);
            if (nv != null) {
                tenNhanVien = nv.getTenNV() + " (" + maNhanVien + ")";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("✗ Lỗi khi lấy thông tin nhân viên: " + e.getMessage());
        }
        
        // Dòng 1: Mã hóa đơn + Ngày lập
        JPanel row1 = new JPanel(new GridLayout(1, 4, 15, 0));
        row1.setBackground(Color.WHITE);
        row1.setMaximumSize(new Dimension(840, 35));
        
        JLabel lblMaHDLabel = new JLabel("Mã hóa đơn:");
        lblMaHDLabel.setFont(labelFont);
        row1.add(lblMaHDLabel);
        
        lblMaHoaDon = new JLabel(maHoaDon);
        lblMaHoaDon.setFont(valueFont);
        lblMaHoaDon.setForeground(new Color(0, 153, 153));
        row1.add(lblMaHoaDon);
        
        JLabel lblNgayLabel = new JLabel("Ngày lập:");
        lblNgayLabel.setFont(labelFont);
        row1.add(lblNgayLabel);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        lblNgayLap = new JLabel(sdf.format(new Date()));
        lblNgayLap.setFont(valueFont);
        row1.add(lblNgayLap);
        
        infoPanel.add(row1);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Dòng 2: Nhân viên + Khách hàng
        JPanel row2 = new JPanel(new GridLayout(1, 4, 15, 0));
        row2.setBackground(Color.WHITE);
        row2.setMaximumSize(new Dimension(840, 35));
        
        JLabel lblNVLabel = new JLabel("Nhân viên:");
        lblNVLabel.setFont(labelFont);
        row2.add(lblNVLabel);
        
        lblNhanVien = new JLabel(tenNhanVien);
        lblNhanVien.setFont(valueFont);
        lblNhanVien.setForeground(new Color(0, 102, 204));
        row2.add(lblNhanVien);
        
        JLabel lblKHLabel = new JLabel("Khách hàng:");
        lblKHLabel.setFont(labelFont);
        row2.add(lblKHLabel);
        
        lblKhachHang = new JLabel(tenKhachHang.isEmpty() ? "Khách lẻ" : tenKhachHang);
        lblKhachHang.setFont(valueFont);
        row2.add(lblKhachHang);
        
        infoPanel.add(row2);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Dòng 3: Số điện thoại
        JPanel row3 = new JPanel(new GridLayout(1, 4, 15, 0));
        row3.setBackground(Color.WHITE);
        row3.setMaximumSize(new Dimension(840, 35));
        
        JLabel lblSDTLabel = new JLabel("Số điện thoại:");
        lblSDTLabel.setFont(labelFont);
        row3.add(lblSDTLabel);
        
        lblSoDienThoai = new JLabel(soDienThoai.isEmpty() ? "N/A" : soDienThoai);
        lblSoDienThoai.setFont(valueFont);
        row3.add(lblSoDienThoai);
        
        // Ô trống để cân đối layout
        row3.add(new JLabel(""));
        row3.add(new JLabel(""));
        
        infoPanel.add(row3);
        
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // ===== SEPARATOR =====
        JSeparator separator1 = new JSeparator();
        separator1.setMaximumSize(new Dimension(840, 2));
        mainPanel.add(separator1);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // ===== TABLE CHI TIẾT
        JLabel lblChiTiet = new JLabel("Chi tiết sản phẩm:");
        lblChiTiet.setFont(new Font("Roboto", Font.BOLD, 16));
        lblChiTiet.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblChiTiet);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        String[] columns = {"STT", "Tên thuốc", "Số lượng", "Đơn giá", "Thành tiền"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableChiTiet = new JTable(tableModel);
        tableChiTiet.setFont(new Font("Roboto", Font.PLAIN, 14));
        tableChiTiet.setRowHeight(35);
        tableChiTiet.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        tableChiTiet.getTableHeader().setBackground(new Color(0, 153, 153));
        tableChiTiet.getTableHeader().setForeground(Color.WHITE);
        tableChiTiet.getTableHeader().setPreferredSize(new Dimension(840, 40));
        
        // Set column width
        tableChiTiet.getColumnModel().getColumn(0).setPreferredWidth(60);
        tableChiTiet.getColumnModel().getColumn(1).setPreferredWidth(350);
        tableChiTiet.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableChiTiet.getColumnModel().getColumn(3).setPreferredWidth(130);
        tableChiTiet.getColumnModel().getColumn(4).setPreferredWidth(140);
        
        // Cell renderers
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableChiTiet.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tableChiTiet.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableChiTiet.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tableChiTiet.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        
        // Load data
        int stt = 1;
        double tongTienTruocThue = 0;
        for (ChiTietHoaDon item : dsChiTietHoaDon) {
            tableModel.addRow(new Object[]{
                stt++,
                item.getThuoc().getTenThuoc(),
                item.getSoLuong(),
                String.format("%,.0f VNĐ", item.getDonGia()),
                String.format("%,.0f VNĐ", item.getThanhTien()),
                tongTienTruocThue += item.getThanhTien()
            });
        }
        
        JScrollPane scrollPane = new JScrollPane(tableChiTiet);
        scrollPane.setMaximumSize(new Dimension(840, 300));
        scrollPane.setPreferredSize(new Dimension(840, 300));
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // ===== SEPARATOR =====
        JSeparator separator2 = new JSeparator();
        separator2.setMaximumSize(new Dimension(840, 2));
        mainPanel.add(separator2);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // ===== PAYMENT INFO =====
        JPanel paymentPanel = new JPanel(new GridLayout(6, 2, 15, 12));
        paymentPanel.setBackground(Color.WHITE);
        paymentPanel.setMaximumSize(new Dimension(780, 200));
        
        Font paymentFont = new Font("Roboto", Font.PLAIN, 14);
        
        // Tổng tiền
        JLabel lblTongTienLabel = new JLabel("Tổng tiền:");
        lblTongTienLabel.setFont(paymentFont);
        paymentPanel.add(lblTongTienLabel);
        
        txtTongTien = new JTextField(String.format("%,.0f VNĐ", tongTienTruocThue));
        txtTongTien.setEditable(false);
        txtTongTien.setFont(new Font("Roboto", Font.BOLD, 15));
        paymentPanel.add(txtTongTien);
        
        // ===== THUẾ =====
        AtomicReference<Thue> thueInfo = new AtomicReference<>();
        double tienThue = 0;
        double phanTramThue = 0;
        String tenThue = "Thuế (0%)";
        ArrayList<Thue> dsThue = null;

        try {
            dsThue = thueDAO.getDsThue();
            if (dsThue != null && !dsThue.isEmpty()) {
                Thue thue = dsThue.get(0);
                thueInfo.set(thue);

                phanTramThue = thue.getPhanTramThue();
                tenThue = thue.getTenThue() + " (" + String.format("%.0f%%", phanTramThue) + ")";
                tienThue = tongTienTruocThue * phanTramThue / 100;

                System.out.println("✓ Áp dụng thuế: " + tenThue);
                System.out.println("  Tổng tiền: " + String.format("%,.0f", tongTien));
                System.out.println("  Tiền thuế: " + String.format("%,.0f", tienThue));
            } else {
                System.out.println("⚠ Không có thông tin thuế, mặc định 0%");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("✗ Lỗi khi lấy thông tin thuế: " + e.getMessage());
        }
        
        JLabel lblThueLabel = new JLabel(tenThue + ":");
        lblThueLabel.setFont(paymentFont);
        paymentPanel.add(lblThueLabel);
        
        txtThue = new JTextField(String.format("%,.0f VNĐ", tienThue));
        txtThue.setEditable(false);
        txtThue.setFont(paymentFont);
        paymentPanel.add(txtThue);
        
     // ===== GIẢM GIÁ TỪ KHUYẾN MÃI =====
        double giamGia = 0;
        double phanTramGiamGia = 0;
        AtomicReference<KhuyenMai> khuyenMaiInfo = new AtomicReference<>(); // Changed from KhuyenMai to AtomicReference<KhuyenMai>
        try {
            khuyenMaiDAO = new KhuyenMaiDAO();
            ArrayList<KhuyenMai> dsKhuyenMai = khuyenMaiDAO.getDsKhuyenMai();
            Date now = new Date();
            
            for (KhuyenMai km : dsKhuyenMai) {
                if (km.getNgayBatDau() != null && km.getNgayKetThuc() != null) {
                    if (isInDateRange(now, km.getNgayBatDau(), km.getNgayKetThuc())) {
                        khuyenMaiInfo.set(km);
                        phanTramGiamGia += km.getPhanTramGiamGia();
                    }
                }
            }
            
            if (phanTramGiamGia > 0) {
                giamGia = tongTien * phanTramGiamGia / 100;
                System.out.println("  % Giảm: " + phanTramGiamGia + "%");
                System.out.println("  Tiền giảm: " + String.format("%,.0f VNĐ", giamGia));
            } else {
                System.out.println("⚠ Không có khuyến mãi hiệu lực");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("✗ Lỗi khi lấy khuyến mãi: " + e.getMessage());
        }
        
        String labelGiamGia = phanTramGiamGia > 0 
            ? "Giảm giá (" + " -" + String.format("%.0f%%", phanTramGiamGia) + "):"
            : "Giảm giá:";
        
        JLabel lblGiamGiaLabel = new JLabel(labelGiamGia);
        lblGiamGiaLabel.setFont(paymentFont);
        paymentPanel.add(lblGiamGiaLabel);
        
        txtGiamGia = new JTextField(String.format("%,.0f VNĐ", giamGia));
        txtGiamGia.setEditable(false);
        txtGiamGia.setFont(paymentFont);
        if (giamGia > 0) {
            txtGiamGia.setForeground(new Color(0, 153, 0));
            txtGiamGia.setFont(new Font("Roboto", Font.BOLD, 14));
        }
        paymentPanel.add(txtGiamGia);
        
        // Thành tiền = Tổng tiền + Thuế - Giảm giá
        double thanhTien = tongTienTruocThue + tienThue - giamGia;
        
        JLabel lblThanhTienLabel = new JLabel("Thành tiền:");
        lblThanhTienLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        lblThanhTienLabel.setForeground(new Color(255, 51, 0));
        paymentPanel.add(lblThanhTienLabel);
        
        txtThanhTien = new JTextField(String.format("%,.0f VNĐ", thanhTien));
        txtThanhTien.setEditable(false);
        txtThanhTien.setFont(new Font("Roboto", Font.BOLD, 16));
        txtThanhTien.setForeground(new Color(255, 51, 0));
        paymentPanel.add(txtThanhTien);
        
        // Tiền nhận vào
        JLabel lbltienNhanVaoLabel = new JLabel("Tiền nhận vào:");
        lbltienNhanVaoLabel.setFont(paymentFont);
        paymentPanel.add(lbltienNhanVaoLabel);
        
        txtTienNhanVao = new JTextField(String.format("%,.0f VNĐ", tienNhanVao));
        txtTienNhanVao.setEditable(false);
        txtTienNhanVao.setFont(new Font("Roboto", Font.BOLD, 15));
        paymentPanel.add(txtTienNhanVao);
        
        // Tiền thừa = Tiền nhận vào - Thành tiền
        double tienThua = 0;
        if (tienNhanVao != 0) {
        	tienThua = tienNhanVao - thanhTien;
        } 
        JLabel lblTienThuaLabel = new JLabel("Tiền thừa:");
        lblTienThuaLabel.setFont(paymentFont);
        paymentPanel.add(lblTienThuaLabel);
        
        txtTienThua = new JTextField(String.format("%,.0f VNĐ", tienThua));
        txtTienThua.setEditable(false);
        txtTienThua.setFont(new Font("Roboto", Font.BOLD, 15));
        txtTienThua.setForeground(tienThua >= 0 ? new Color(0, 153, 0) : Color.RED);
        paymentPanel.add(txtTienThua);
        
        mainPanel.add(paymentPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(230, 245, 245));
        buttonPanel.setPreferredSize(new Dimension(850, 80));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        
        btnHuy = new JButton("HỦY BỎ");
        btnHuy.setFont(new Font("Roboto", Font.BOLD, 14));
        btnHuy.setPreferredSize(new Dimension(150, 40));
        btnHuy.setBackground(new Color(255, 102, 102));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFocusPainted(false);
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHuy.setBorder(new LineBorder(new Color(255, 102, 102), 2, true));
        btnHuy.addActionListener(e -> {
        	int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn huỷ ?", "Xác nhận" ,JOptionPane.YES_NO_OPTION);
        	if (choice == JOptionPane.YES_OPTION) {
        		this.dispose();
        	}
        });
        
        btnXacNhan = new JButton("XÁC NHẬN THANH TOÁN");
        btnXacNhan.setFont(new Font("Roboto", Font.BOLD, 14));
        btnXacNhan.setPreferredSize(new Dimension(220, 40));
        btnXacNhan.setBackground(new Color(0, 204, 51));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setFocusPainted(false);
        btnXacNhan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnXacNhan.setBorder(new LineBorder(new Color(0, 204, 51), 2, true));
        btnXacNhan.addActionListener(e -> {
        	if (tienNhanVao == 0) {
	        	try {
					taoMaQrCode();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	} else {
        		isThanhToan = true;
        	}
        	if (isThanhToan) {
        		KhachHang kh = null;
        		if (tenKhachHang.isEmpty() && soDienThoai.isEmpty()) {
        			kh = new KhachHang();
        		} else {
        			try {
						kh = khDAO.getKhachHangTheoSDT(soDienThoai);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        		}
	            HoaDon hd = new HoaDon(maHoaDon, new Date(),
	                thueInfo.get(),
	                new NhanVien(maNhanVien),
	                kh,
	                khuyenMaiInfo.get(),
	                null);
	            try {
	            	if (hdDAO.themHoaDon(hd)) {
	            	    boolean allSuccess = true;
	            	    for (ChiTietHoaDon cthd : dsChiTietHoaDon) {
	            	        if (!cthdDAO.themChiTietHoaDon(cthd)) {
	            	            allSuccess = false;
	            	            break;
	            	        } else {
		        	            // Cập nhật tồn kho
		        	            String maThuoc = cthd.getThuoc().getMaThuoc();
		        	            int soLuongBan = cthd.getSoLuong();
		
		        	            // Lấy số lượng tồn hiện tại
		        	            int soLuongTonCu = thuocDAO.getSoLuongTonTheoMaThuoc(maThuoc);
		        	            int soLuongMoi = Math.max(0, soLuongTonCu - soLuongBan);
		
		        	            if (thuocDAO.updateSoLuongTonTheoMaThuoc(maThuoc, soLuongMoi)) {
		        	                System.out.println("✓ Cập nhật tồn kho thuốc " + maThuoc + ": " + soLuongMoi);
		        	            }
	            	        }
	            	    }
	            	    if (allSuccess) {
	            	    	
	            	        JOptionPane.showMessageDialog(this, "✅ Thanh toán thành công");
	            	        confirmed = true;
	            	        int choice = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hoá đơn không ?");
	            	        
	            	        if (choice == JOptionPane.YES_OPTION) {
	            	        	inHoaDon();
	            	        } else {
	            	        	this.dispose();
	            	        }
	            	      
	            	    } else {
	            	        JOptionPane.showMessageDialog(this, "❌ Lỗi khi thêm chi tiết hóa đơn");
	            	    }
	            	} else {
	            	    JOptionPane.showMessageDialog(this, "❌ Lỗi khi thêm hóa đơn");
	            	}
	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });
        
        buttonPanel.add(btnHuy);
        buttonPanel.add(btnXacNhan);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        System.out.println("=== TỔNG KẾT HÓA ĐƠN ===");
        System.out.println("Tổng tiền: " + String.format("%,.0f VNĐ", tongTien));
        System.out.println("Thuế: " + String.format("%,.0f VNĐ (%.0f%%)", tienThue, phanTramThue));
        System.out.println("Giảm giá: " + String.format("%,.0f VNĐ (%.0f%%)", giamGia, phanTramGiamGia));
        System.out.println("Thành tiền: " + String.format("%,.0f VNĐ", thanhTien));
        System.out.println("Tiền nhận vào: " + String.format("%,.0f VNĐ", tienNhanVao));
        System.out.println("Tiền thừa: " + String.format("%,.0f VNĐ", tienThua));
        System.out.println("========================");
    }
    
private void taoMaQrCode() {
    try {
        String bank = "mbbank";
        String account = "0389470120";
        String amountStr = txtThanhTien.getText().trim()
                .replace(",", "")
                .replace("VNĐ", "")
                .replace(".", "")
                .trim();
        
        String noiDung = "Thanh toan san pham"; 
        
        // Tạo URL cho QR code
        String qrUrl = String.format(
            "https://img.vietqr.io/image/%s-%s-compact.png?amount=%s&addInfo=%s",
            bank, account, amountStr, URLEncoder.encode(noiDung, "UTF-8")
        );
        
        System.out.println("QR URL: " + qrUrl);
        
        // Tạo dialog
        JDialog dlQrCode = new JDialog(this, "Quét mã QR để thanh toán", true);
        dlQrCode.setSize(800, 600);
        dlQrCode.setLayout(new BorderLayout(10, 10));
        dlQrCode.setLocationRelativeTo(this);
        dlQrCode.getContentPane().setBackground(Color.WHITE);
        
        // Panel chứa thông tin
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel lblAmount = new JLabel("Số tiền: " + txtThanhTien.getText());
        lblAmount.setFont(new Font("Roboto", Font.BOLD, 18));
        lblAmount.setForeground(new Color(255, 51, 0));
        lblAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblBank = new JLabel("Ngân hàng: MB Bank");
        lblBank.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblBank.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblAccount = new JLabel("Số tài khoản: " + account);
        lblAccount.setFont(new Font("Roboto", Font.PLAIN, 14));
        lblAccount.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(lblAmount);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(lblBank);
        infoPanel.add(lblAccount);
        
        dlQrCode.add(infoPanel, BorderLayout.NORTH);
        
        // Qr Code
        JPanel qrPanel = new JPanel(new BorderLayout());
        qrPanel.setBackground(Color.WHITE);
        qrPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        URL url = new URL(qrUrl);
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        icon.setImage(scaledImg);
        JLabel lblQrCode = new JLabel(icon);
        lblQrCode.setFont(new Font("Roboto", Font.ITALIC, 14));
        lblQrCode.setForeground(Color.GRAY);
        qrPanel.add(lblQrCode, BorderLayout.CENTER);
        
        dlQrCode.add(qrPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnDaNhanTien = new JButton("ĐÃ NHẬN TIỀN");
        btnDaNhanTien.setFont(new Font("Roboto", Font.BOLD, 14));
        btnDaNhanTien.setPreferredSize(new Dimension(180, 40));
        btnDaNhanTien.setBackground(new Color(0, 204, 51));
        btnDaNhanTien.setForeground(Color.WHITE);
        btnDaNhanTien.setFocusPainted(false);
        btnDaNhanTien.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDaNhanTien.setBorder(new LineBorder(new Color(0, 204, 51), 2, true));
        
        JButton btnHuyBo = new JButton("HỦY BỎ");
        btnHuyBo.setFont(new Font("Roboto", Font.BOLD, 14));
        btnHuyBo.setPreferredSize(new Dimension(120, 40));
        btnHuyBo.setBackground(new Color(255, 102, 102));
        btnHuyBo.setForeground(Color.WHITE);
        btnHuyBo.setFocusPainted(false);
        btnHuyBo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHuyBo.setBorder(new LineBorder(new Color(255, 102, 102), 2, true));
        
        buttonPanel.add(btnHuyBo);
        buttonPanel.add(btnDaNhanTien);
        
        dlQrCode.add(buttonPanel, BorderLayout.SOUTH);
        
        // Event handlers
        btnDaNhanTien.addActionListener(e -> {
            txtTienNhanVao.setText(txtThanhTien.getText());
            
            // Tính tiền thừa
            String thanhTienStr = txtThanhTien.getText()
                    .replace(",", "")
                    .replace("VNĐ", "")
                    .replace(".", "")
                    .trim();
            txtTienThua = new JTextField();
            txtTienThua.setText("0 VND");
            txtTienThua.setForeground(new Color(0, 153, 0));
            int choice = JOptionPane.showConfirmDialog(this, "Xác nhận đã nhận tiền ?");
            if (choice == JOptionPane.YES_OPTION) {
	            JOptionPane.showMessageDialog(dlQrCode, 
	                "✓ Đã xác nhận nhận tiền qua chuyển khoản", 
	                "Thông báo", 
	                JOptionPane.INFORMATION_MESSAGE);
	            isThanhToan = true;
	            dlQrCode.dispose();
            }
        });
        
        btnHuyBo.addActionListener(e -> {
            dlQrCode.dispose();
        });
        
        dlQrCode.setVisible(true);
        
        
    } catch (Exception e) {
        e.printStackTrace();
        System.err.println("✗ Lỗi tạo QR code: " + e.getMessage());
        JOptionPane.showMessageDialog(this, 
            "Lỗi tạo mã QR: " + e.getMessage(), 
            "Lỗi", 
            JOptionPane.ERROR_MESSAGE);
    }
}
	private void inHoaDon() {
        try {
            // Đường dẫn
        	String folderPath = "src/LuuHoaDonInRa";
        	String fileName = "HoaDon_" + lblMaHoaDon.getText() + "_" +
        	        new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";
        	String outputPath = folderPath + "/" + fileName;

            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs(); // 
            }
            // === TẠO TÀI LIỆU PDF ===
            com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4, 40, 40, 40, 40);
            com.itextpdf.text.pdf.PdfWriter.getInstance(document, new java.io.FileOutputStream(outputPath));
            document.open();

            // Font 
            com.itextpdf.text.pdf.BaseFont bf = com.itextpdf.text.pdf.BaseFont.createFont(
                    "C:/Windows/Fonts/arial.ttf", com.itextpdf.text.pdf.BaseFont.IDENTITY_H, com.itextpdf.text.pdf.BaseFont.EMBEDDED);
            com.itextpdf.text.Font fontTitle = new com.itextpdf.text.Font(bf, 18, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font fontNormal = new com.itextpdf.text.Font(bf, 12);
            com.itextpdf.text.Font fontBold = new com.itextpdf.text.Font(bf, 12, com.itextpdf.text.Font.BOLD);

            // Title
            com.itextpdf.text.Paragraph title = new com.itextpdf.text.Paragraph("HÓA ĐƠN BÁN HÀNG", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new com.itextpdf.text.Paragraph(" "));

            // Thông tin chung
            document.add(new com.itextpdf.text.Paragraph("Mã hóa đơn: " + lblMaHoaDon.getText(), fontNormal));
            document.add(new com.itextpdf.text.Paragraph("Ngày lập: " + lblNgayLap.getText(), fontNormal));
            document.add(new com.itextpdf.text.Paragraph("Nhân viên: " + lblNhanVien.getText(), fontNormal));
            document.add(new com.itextpdf.text.Paragraph("Khách hàng: " + lblKhachHang.getText(), fontNormal));
            document.add(new com.itextpdf.text.Paragraph("Số điện thoại: " + lblSoDienThoai.getText(), fontNormal));
            document.add(new com.itextpdf.text.Paragraph(" "));
            
            // Chi tiết sản phẩm
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1f, 3f, 1.5f, 1.5f, 2f});

            String[] headers = {"STT", "Tên thuốc", "Số lượng", "Đơn giá", "Thành tiền"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }
            
            DefaultTableModel model = (DefaultTableModel) tableChiTiet.getModel();
            
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    PdfPCell cell = new PdfPCell(new Phrase(model.getValueAt(i, j).toString(), fontNormal));
                    cell.setHorizontalAlignment(j == 1 ? Element.ALIGN_LEFT : Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }

            document.add(table);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Tổng tiền: " + txtTongTien.getText(), fontNormal));
            document.add(new Paragraph("Thuế: " + txtThue.getText(), fontNormal));
            document.add(new Paragraph("Giảm giá: " + txtGiamGia.getText(), fontNormal));
            document.add(new Paragraph("Thành tiền: " + txtThanhTien.getText(), fontBold));
            document.add(new Paragraph("Tiền nhận vào: " + txtTienNhanVao.getText(), fontNormal));
            document.add(new Paragraph("Tiền thừa: " + txtTienThua.getText(), fontNormal));

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Cảm ơn quý khách đã mua hàng!", fontBold));
            document.close();
            
            JOptionPane.showMessageDialog(this, "✅ In hóa đơn thành công!\nĐã lưu tại: " + outputPath);
            Desktop.getDesktop().open(new java.io.File(outputPath));
            this.dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Lỗi khi in hóa đơn: " + e.getMessage());
        }
    }


	/**
     * Kiểm tra ngày hiện tại có nằm trong khoảng ngày bắt đầu - ngày kết thúc không
     */
    private boolean isInDateRange(Date currentDate, Date startDate, Date endDate) {
        if (currentDate == null || startDate == null || endDate == null) {
            return false;
        }
        
        java.util.Calendar calCurrent = java.util.Calendar.getInstance();
        calCurrent.setTime(currentDate);
        calCurrent.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calCurrent.set(java.util.Calendar.MINUTE, 0);
        calCurrent.set(java.util.Calendar.SECOND, 0);
        calCurrent.set(java.util.Calendar.MILLISECOND, 0);
        Date current = calCurrent.getTime();
        
        java.util.Calendar calStart = java.util.Calendar.getInstance();
        calStart.setTime(startDate);
        calStart.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calStart.set(java.util.Calendar.MINUTE, 0);
        calStart.set(java.util.Calendar.SECOND, 0);
        calStart.set(java.util.Calendar.MILLISECOND, 0);
        Date start = calStart.getTime();
        
        java.util.Calendar calEnd = java.util.Calendar.getInstance();
        calEnd.setTime(endDate);
        calEnd.set(java.util.Calendar.HOUR_OF_DAY, 23);
        calEnd.set(java.util.Calendar.MINUTE, 59);
        calEnd.set(java.util.Calendar.SECOND, 59);
        calEnd.set(java.util.Calendar.MILLISECOND, 999);
        Date end = calEnd.getTime();
        
        return (current.equals(start) || current.after(start)) && 
               (current.equals(end) || current.before(end));
    }
    
    /**
     * Kiểm tra user có xác nhận thanh toán không
     */
    public boolean isConfirmed() {
        return confirmed;
    }

}
