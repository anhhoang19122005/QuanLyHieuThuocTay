package gui.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.security.PublicKey;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import dao.DanhMucThuocDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.KhuyenMaiDAO;
import dao.ThueDAO;
import dao.ThuocDAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.TaiKhoan;
import entity.Thue;
import entity.Thuoc;
import gui.dialog.DialogThanhToanHoaDon;
import gui.dialog.DialogThemKhachHang;
//import utils.TableSorter;
import utils.ImageHelper;

public class formLapHoaDon extends JPanel {
	// Trong phần khai báo components
	private javax.swing.JPanel khuyenMaiPanel;
	private javax.swing.JScrollPane jScrollPaneKhuyenMai;
	private javax.swing.JPanel khuyenMaiContentPanel;
	    private KhachHangDAO khachHangDAO;
	    private HoaDonDAO hoaDonDAO;
	    private KhuyenMaiDAO khuyenMaiDAO;
	    private javax.swing.JPanel actionPanel;
	    private javax.swing.JPanel billInfoPanel;
	    private javax.swing.JPanel billPanel;
	    private javax.swing.JButton btnAddCart;
	    private javax.swing.JButton btnAddCustomer;
	    private javax.swing.JButton btnDeleteCartItem;
	    private javax.swing.JButton btnHuy;
	    private javax.swing.JButton btnReload;
	    private javax.swing.JButton btnSearchKH;
	    private javax.swing.JButton btnThanhToan;
	    private javax.swing.JPanel cardPanel;
	    private javax.swing.JComboBox<String> cboxGioiTinhKH;
	    private javax.swing.JComboBox<String> cboxSearch;
	    private javax.swing.JLabel jLabel1;
	    private javax.swing.JLabel jLabel10;
	    private javax.swing.JLabel jLabel11;
	    private javax.swing.JLabel jLabel12;
	    private javax.swing.JLabel jLabel14;
	    private javax.swing.JLabel lblHoaDon;
	    private javax.swing.JLabel jLabel3;
	    private javax.swing.JLabel jLabel4;
	    private javax.swing.JLabel jLabel5;
	    private javax.swing.JLabel jLabel6;
	    private javax.swing.JLabel jLabel7;
	    private javax.swing.JLabel jLabel8;
	    private javax.swing.JPanel jPanel10;
	    private javax.swing.JPanel jPanel11;
	    private javax.swing.JPanel jPanel12;
	    private javax.swing.JPanel jPanel13;
	    private javax.swing.JPanel jPanel14;
	    private javax.swing.JPanel jPanel15;
	    private javax.swing.JPanel jPanel16;
	    private javax.swing.JPanel jPanel17;
	    private javax.swing.JPanel jPanel18;
	    private javax.swing.JPanel jPanel19;
	    private javax.swing.JPanel jPanel2;
	    private javax.swing.JPanel jPanel20;
	    private javax.swing.JPanel jPanel21;
	    private javax.swing.JPanel jPanel22;
	    private javax.swing.JPanel jPanel23;
	    private javax.swing.JPanel jPanel24;
	    private javax.swing.JPanel jPanel25;
	    private javax.swing.JPanel jPanel26;
	    private javax.swing.JPanel jPanel3;
	    private javax.swing.JPanel jPanel4;
	    private javax.swing.JPanel jPanel5;
	    private javax.swing.JPanel jPanel6;
	    private javax.swing.JPanel jPanel7;
	    private javax.swing.JPanel pnBtnHoaDon;
	    private javax.swing.JPanel jPanel9;
	    private javax.swing.JScrollPane jScrollPane1;
	    private javax.swing.JScrollPane jScrollPane2;
	    private javax.swing.JScrollPane jScrollPane3;
	    private javax.swing.JSeparator jSeparator1;
	    private javax.swing.JLabel lblThuoc;
	    private javax.swing.JPanel mainPanel;
	    private javax.swing.JPanel sanPhamPanel;
	    private javax.swing.JTable table;
	    private javax.swing.JTable tableCart;
	    private javax.swing.JPanel tablePanel;
	    private javax.swing.JTextField txtDonGia;
	    private javax.swing.JLabel txtHinhAnh;
	    private javax.swing.JTextField txtHoTenKH;
	    private javax.swing.JTextField txtMaHoaDon;
	    private javax.swing.JTextField txtMaThuoc;
	    private javax.swing.JTextField txtSdtKH;
	    private javax.swing.JTextField txtSearch;
	    private javax.swing.JTextField txtSoLuong;
	    private javax.swing.JTextField txtTenThuoc;
	    private javax.swing.JTextArea txtThanhPhan;
	    private javax.swing.JTextField txtTienNhanVao;
	    private javax.swing.JTextField txtTienThua;
	    private javax.swing.JTextField txtTong;
	    private DefaultTableModel tableModel;
		private DefaultTableModel tableCartModel;
		private ThuocDAO thuocDAO;
	    private ArrayList<ChiTietHoaDon> dsChiTietHoaDon;
	    private String currentMaThuoc = "";
	    private double tongTien = 0;
	    private TaiKhoan taiKhoan;
	    private DanhMucThuocDAO dmtDAO;
	    private ThueDAO thueDAO;
	    public formLapHoaDon(TaiKhoan tk) throws SQLException {
	    	taiKhoan = tk;
	    	dsChiTietHoaDon = new ArrayList<>();
	        thuocDAO = new ThuocDAO();
	        khachHangDAO = new KhachHangDAO();
	        hoaDonDAO = new HoaDonDAO();
	        dmtDAO = new DanhMucThuocDAO();
	        thueDAO = new ThueDAO();
	        initComponents();
	        pruductLayout();
	        generateMaHoaDon();
	    }
	    
	    /**
	     * Tự động phát sinh mã hóa đơn
	     */
	    private void generateMaHoaDon() {
	        try {
	            String maHD = hoaDonDAO.generateMaHD();
	            txtMaHoaDon.setText(maHD);
	        } catch (Exception e) {
	            e.printStackTrace();
	            txtMaHoaDon.setText("HD00001");
	        }
	    }
	    
	    private void pruductLayout() {
	        txtSoLuong.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Số lượng...");
	        btnReload.putClientProperty(FlatClientProperties.STYLE, "arc: 15");

	        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm...");
	        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("./img/search.svg"));

	        String[] searchType = {"Tất cả", "Mã", "Tên"};
	        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(searchType);
	        cboxSearch.setModel(model);
	    }
	    private void initComponents() throws SQLException {
	    	thuocDAO = new ThuocDAO();
	        khuyenMaiDAO = new KhuyenMaiDAO();
	        
	        // ===== KHỞI TẠO CÁC COMPONENTS =====
	        mainPanel = new javax.swing.JPanel();
	        sanPhamPanel = new javax.swing.JPanel(); 
	        jPanel15 = new javax.swing.JPanel();
	        lblThuoc = new javax.swing.JLabel();
	        jPanel16 = new javax.swing.JPanel();
	        jPanel22 = new javax.swing.JPanel();
	        txtHinhAnh = new javax.swing.JLabel();
	        jPanel24 = new javax.swing.JPanel();
	        jPanel17 = new javax.swing.JPanel();
	        jLabel10 = new javax.swing.JLabel();
	        txtMaThuoc = new javax.swing.JTextField();
	        jPanel18 = new javax.swing.JPanel();
	        jLabel11 = new javax.swing.JLabel();
	        txtTenThuoc = new javax.swing.JTextField();
	        jPanel19 = new javax.swing.JPanel();
	        jLabel12 = new javax.swing.JLabel();
	        jScrollPane3 = new javax.swing.JScrollPane();
	        txtThanhPhan = new javax.swing.JTextArea();
	        jPanel21 = new javax.swing.JPanel();
	        jLabel14 = new javax.swing.JLabel();
	        txtDonGia = new javax.swing.JTextField();
	        jPanel4 = new javax.swing.JPanel();
	        actionPanel = new javax.swing.JPanel();
	        jPanel12 = new javax.swing.JPanel();
	        jPanel14 = new javax.swing.JPanel();
	        cboxSearch = new javax.swing.JComboBox<>();
	        txtSearch = new javax.swing.JTextField();
	        btnReload = new javax.swing.JButton();
	        jPanel13 = new javax.swing.JPanel();
	        txtSoLuong = new javax.swing.JTextField();
	        btnAddCart = new javax.swing.JButton();
	        tablePanel = new javax.swing.JPanel();
	        jScrollPane1 = new javax.swing.JScrollPane();
	        table = new javax.swing.JTable();
	        billPanel = new javax.swing.JPanel();
	        cardPanel = new javax.swing.JPanel();
	        jScrollPane2 = new javax.swing.JScrollPane();
	        tableCart = new javax.swing.JTable();
	        jPanel3 = new javax.swing.JPanel();
	        jLabel1 = new javax.swing.JLabel();
	        jPanel20 = new javax.swing.JPanel();
	        btnDeleteCartItem = new javax.swing.JButton();
	        billInfoPanel = new javax.swing.JPanel();
	        jPanel5 = new javax.swing.JPanel();
	        lblHoaDon = new javax.swing.JLabel();
	        jPanel6 = new javax.swing.JPanel();
	        jPanel23 = new javax.swing.JPanel();
	        jPanel7 = new javax.swing.JPanel();
	        jLabel4 = new javax.swing.JLabel();
	        txtMaHoaDon = new javax.swing.JTextField();
	        jPanel25 = new javax.swing.JPanel();
	        jLabel8 = new javax.swing.JLabel();
	        txtSdtKH = new javax.swing.JTextField();
	        btnSearchKH = new javax.swing.JButton();
	        btnAddCustomer = new javax.swing.JButton();
	        jPanel2 = new javax.swing.JPanel();
	        jLabel3 = new javax.swing.JLabel();
	        txtHoTenKH = new javax.swing.JTextField();
	        cboxGioiTinhKH = new javax.swing.JComboBox<>();
	        jSeparator1 = new javax.swing.JSeparator();
	        jPanel26 = new javax.swing.JPanel();
	        jPanel11 = new javax.swing.JPanel();
	        jLabel7 = new javax.swing.JLabel();
	        txtTong = new javax.swing.JTextField();
	        jPanel10 = new javax.swing.JPanel();
	        jLabel6 = new javax.swing.JLabel();
	        txtTienNhanVao = new JTextField();
	        jPanel9 = new javax.swing.JPanel();
	        jLabel5 = new javax.swing.JLabel();
	        txtTienThua = new javax.swing.JTextField();
	        pnBtnHoaDon = new javax.swing.JPanel();
	        btnHuy = new javax.swing.JButton();
	        btnThanhToan = new javax.swing.JButton();


	        // ===== SETUP LAYOUT CHÍNH =====
	        setBackground(new java.awt.Color(230, 245, 245));
	        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 245, 245), 6, true));
	        setLayout(new java.awt.BorderLayout(5, 0));

	        mainPanel.setBackground(new java.awt.Color(230, 245, 245));
	        mainPanel.setLayout(new java.awt.BorderLayout(5, 5));

	        // ===== SETUP SẢN PHẨM PANEL (THÔNG TIN THUỐC) =====
	        sanPhamPanel.setBackground(new java.awt.Color(255, 255, 255));
	        sanPhamPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(237, 237, 237), 2, true));
	        sanPhamPanel.setPreferredSize(new java.awt.Dimension(832, 300));
	        sanPhamPanel.setLayout(new java.awt.BorderLayout());

	        jPanel15.setBackground(new java.awt.Color(0, 153, 153));
	        jPanel15.setMinimumSize(new java.awt.Dimension(100, 60));
	        jPanel15.setPreferredSize(new java.awt.Dimension(500, 30));
	        jPanel15.setLayout(new java.awt.BorderLayout());

	        lblThuoc.setFont(new java.awt.Font("Roboto Medium", 0, 14));
	        lblThuoc.setForeground(new java.awt.Color(255, 255, 255));
	        lblThuoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        lblThuoc.setText("Thông tin thuốc");
	        jPanel15.add(lblThuoc, java.awt.BorderLayout.CENTER);

	        sanPhamPanel.add(jPanel15, java.awt.BorderLayout.NORTH);

	        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel16.setLayout(new java.awt.BorderLayout(16, 16));

	        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel22.setPreferredSize(new java.awt.Dimension(300, 200));
	        jPanel22.setLayout(new java.awt.BorderLayout(20, 20));

	        txtHinhAnh.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 230, 230), 4, true));
	        txtHinhAnh.setPreferredSize(new java.awt.Dimension(300, 200));
	        txtHinhAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        txtHinhAnh.setText("Chọn thuốc để xem");
	        jPanel22.add(txtHinhAnh, java.awt.BorderLayout.CENTER);

	        jPanel16.add(jPanel22, java.awt.BorderLayout.WEST);

	        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

	        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel17.setPreferredSize(new java.awt.Dimension(215, 40));
	        jPanel17.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 0));

	        jLabel10.setFont(new java.awt.Font("Roboto", 0, 14));
	        jLabel10.setText("Mã thuốc:");
	        jLabel10.setMaximumSize(new java.awt.Dimension(44, 40));
	        jLabel10.setPreferredSize(new java.awt.Dimension(90, 40));
	        jPanel17.add(jLabel10);

	        txtMaThuoc.setEditable(false);
	        txtMaThuoc.setFocusable(false);
	        txtMaThuoc.setPreferredSize(new java.awt.Dimension(120, 40));
	        jPanel17.add(txtMaThuoc);

	        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel18.setPreferredSize(new java.awt.Dimension(340, 40));
	        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 0));

	        jLabel11.setFont(new java.awt.Font("Roboto", 0, 14));
	        jLabel11.setText("Tên thuốc:");
	        jLabel11.setMaximumSize(new java.awt.Dimension(44, 40));
	        jLabel11.setPreferredSize(new java.awt.Dimension(90, 40));
	        jPanel18.add(jLabel11);

	        txtTenThuoc.setEditable(false);
	        txtTenThuoc.setFocusable(false);
	        txtTenThuoc.setPreferredSize(new java.awt.Dimension(350, 40));
	        jPanel18.add(txtTenThuoc);

	        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel19.setPreferredSize(new java.awt.Dimension(215, 40));
	        jPanel19.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 0));

	        jLabel12.setFont(new java.awt.Font("Roboto", 0, 14));
	        jLabel12.setText("Thành phần:");
	        jLabel12.setMaximumSize(new java.awt.Dimension(44, 40));
	        jLabel12.setPreferredSize(new java.awt.Dimension(90, 40));
	        jPanel19.add(jLabel12);

	        jScrollPane3.setPreferredSize(new java.awt.Dimension(350, 100));

	        txtThanhPhan.setEditable(false);
	        txtThanhPhan.setColumns(20);
	        txtThanhPhan.setLineWrap(true);
	        txtThanhPhan.setRows(5);
	        txtThanhPhan.setFocusable(false);
	        txtThanhPhan.setPreferredSize(new java.awt.Dimension(320, 40));
	        jScrollPane3.setViewportView(txtThanhPhan);

	        jPanel19.add(jScrollPane3);

	        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel21.setPreferredSize(new java.awt.Dimension(215, 40));
	        jPanel21.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 0));

	        jLabel14.setFont(new java.awt.Font("Roboto", 0, 14));
	        jLabel14.setText("Đơn giá:");
	        jLabel14.setMaximumSize(new java.awt.Dimension(44, 40));
	        jLabel14.setPreferredSize(new java.awt.Dimension(90, 40));
	        jPanel21.add(jLabel14);

	        txtDonGia.setEditable(false);
	        txtDonGia.setFont(new java.awt.Font("Roboto Mono Medium", 0, 14));
	        txtDonGia.setFocusable(false);
	        txtDonGia.setPreferredSize(new java.awt.Dimension(120, 40));
	        jPanel21.add(txtDonGia);

	        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
	        jPanel24.setLayout(jPanel24Layout);
	        jPanel24Layout.setHorizontalGroup(
	            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(jPanel24Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(jPanel24Layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                        .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                        .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	                    .addContainerGap()))
	        );
	        jPanel24Layout.setVerticalGroup(
	            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
	                .addContainerGap(205, Short.MAX_VALUE)
	                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addGap(21, 21, 21))
	            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(jPanel24Layout.createSequentialGroup()
	                    .addContainerGap()
	                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addContainerGap(66, Short.MAX_VALUE)))
	        );

	        jPanel16.add(jPanel24, java.awt.BorderLayout.CENTER);
	        sanPhamPanel.add(jPanel16, java.awt.BorderLayout.CENTER);

	        // ===== KHUYẾN MÃI PANEL - BÊN TRÁI =====
	        khuyenMaiPanel = new javax.swing.JPanel();
	        khuyenMaiPanel.setBackground(new java.awt.Color(255, 255, 255));
	        khuyenMaiPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(237, 237, 237), 2, true));
	        khuyenMaiPanel.setPreferredSize(new java.awt.Dimension(320, 300));
	        khuyenMaiPanel.setLayout(new java.awt.BorderLayout());

	        // Header
	        javax.swing.JPanel khuyenMaiHeaderPanel = new javax.swing.JPanel();
	        khuyenMaiHeaderPanel.setBackground(new java.awt.Color(255, 153, 0));
	        khuyenMaiHeaderPanel.setPreferredSize(new java.awt.Dimension(320, 45));
	        khuyenMaiHeaderPanel.setLayout(new java.awt.BorderLayout());

	        javax.swing.JLabel lblKhuyenMaiHeader = new javax.swing.JLabel();
	        lblKhuyenMaiHeader.setFont(new java.awt.Font("Roboto", Font.BOLD, 15));
	        lblKhuyenMaiHeader.setForeground(new java.awt.Color(255, 255, 255));
	        lblKhuyenMaiHeader.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        lblKhuyenMaiHeader.setText("🎁 KHUYẾN MÃI ĐANG CÓ");
	        khuyenMaiHeaderPanel.add(lblKhuyenMaiHeader, java.awt.BorderLayout.CENTER);

	        khuyenMaiPanel.add(khuyenMaiHeaderPanel, java.awt.BorderLayout.NORTH);

	        // Content panel chứa các card khuyến mãi
	        khuyenMaiContentPanel = new javax.swing.JPanel();
	        khuyenMaiContentPanel.setLayout(new javax.swing.BoxLayout(khuyenMaiContentPanel, javax.swing.BoxLayout.Y_AXIS));
	        khuyenMaiContentPanel.setBackground(new java.awt.Color(245, 245, 245));
	        khuyenMaiContentPanel.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));

	        // Load dữ liệu khuyến mãi
	        loadKhuyenMai();

	        jScrollPaneKhuyenMai = new javax.swing.JScrollPane(khuyenMaiContentPanel);
	        jScrollPaneKhuyenMai.setBorder(null);
	        jScrollPaneKhuyenMai.getVerticalScrollBar().setUnitIncrement(16);
	        jScrollPaneKhuyenMai.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	        khuyenMaiPanel.add(jScrollPaneKhuyenMai, java.awt.BorderLayout.CENTER);

	        // ===== WRAPPER PANEL: KHUYẾN MÃI + SẢN PHẨM =====
	        javax.swing.JPanel topWrapperPanel = new javax.swing.JPanel();
	        topWrapperPanel.setBackground(new java.awt.Color(230, 245, 245));
	        topWrapperPanel.setLayout(new java.awt.BorderLayout(5, 5));
	        topWrapperPanel.add(khuyenMaiPanel, java.awt.BorderLayout.WEST);
	        topWrapperPanel.add(sanPhamPanel, java.awt.BorderLayout.CENTER);

	        mainPanel.add(topWrapperPanel, java.awt.BorderLayout.PAGE_START);
	        jPanel4.setBackground(new java.awt.Color(230, 245, 245));
	        jPanel4.setPreferredSize(new java.awt.Dimension(832, 400));
	        jPanel4.setLayout(new java.awt.BorderLayout(0, 5));

	        actionPanel.setBackground(new java.awt.Color(255, 255, 255));
	        actionPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(237, 237, 237), 2, true));
	        actionPanel.setPreferredSize(new java.awt.Dimension(605, 60));
	        actionPanel.setLayout(new java.awt.BorderLayout());

	        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 8));

	        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));
	        jPanel12.add(jPanel14);

	        cboxSearch.setToolTipText("");
	        cboxSearch.setPreferredSize(new java.awt.Dimension(100, 40));
	        jPanel12.add(cboxSearch);

	        txtSearch.setToolTipText("Tìm kiếm");
	        txtSearch.setPreferredSize(new java.awt.Dimension(200, 40));
	        txtSearch.setSelectionColor(new java.awt.Color(230, 245, 245));
	        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
	            public void keyReleased(java.awt.event.KeyEvent evt) {
	                txtSearchKeyReleased(evt);
	            }
	        });
	        jPanel12.add(txtSearch);

	        btnReload.setIcon(new FlatSVGIcon("./img/reload.svg"));
	        btnReload.setToolTipText("Làm mới");
	        btnReload.setBorder(null);
	        btnReload.setBorderPainted(false);
	        btnReload.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        btnReload.setFocusPainted(false);
	        btnReload.setFocusable(false);
	        btnReload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
	        btnReload.setPreferredSize(new java.awt.Dimension(40, 40));
	        btnReload.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnReloadActionPerformed(evt);
	            }
	        });
	        jPanel12.add(btnReload);

	        actionPanel.add(jPanel12, java.awt.BorderLayout.CENTER);

	        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel13.setPreferredSize(new java.awt.Dimension(260, 60));
	        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 8));

	        txtSoLuong.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
	        txtSoLuong.setPreferredSize(new java.awt.Dimension(120, 40));
	        jPanel13.add(txtSoLuong);

	        btnAddCart.setBackground(new java.awt.Color(0, 179, 246));
	        btnAddCart.setFont(new java.awt.Font("Roboto Black", 0, 16)); // NOI18N
	        btnAddCart.setForeground(new java.awt.Color(255, 220, 0));
	        btnAddCart.setIcon(new FlatSVGIcon("./img/add-to-cart.svg"));
	        btnAddCart.setText("THÊM");
	        btnAddCart.setBorderPainted(false);
	        btnAddCart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        btnAddCart.setFocusPainted(false);
	        btnAddCart.setFocusable(false);
	        btnAddCart.setPreferredSize(new java.awt.Dimension(120, 40));
	        btnAddCart.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnAddCartActionPerformed(evt);
	            }
	        });
	        jPanel13.add(btnAddCart);

	        actionPanel.add(jPanel13, java.awt.BorderLayout.EAST);

	        jPanel4.add(actionPanel, java.awt.BorderLayout.PAGE_START);

	        tablePanel.setBackground(new java.awt.Color(255, 255, 255));
	        tablePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(237, 237, 237), 2, true));
	        tablePanel.setLayout(new java.awt.BorderLayout());

	        jScrollPane1.setBorder(null);
	        
	        String[] title =  new String [] {
	                "STT", "Mã thuốc", "Tên thuốc", "Danh mục", "Xuất xứ", "Đơn vị tính", "Số lượng tồn", "Đơn giá"};
	        tableModel = new DefaultTableModel(title,0);
	        table.setFocusable(false);
	        table.setModel(tableModel);
	        table.getTableHeader().setFont(new Font("Time New Roman",Font.BOLD,20));
	        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
	        table.setShowGrid(true);
	        table.setGridColor(Color.BLACK);
	        table.setIntercellSpacing(new Dimension(2, 2));
	        table.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent evt) {
	                tableMouseClicked(evt);
	            }

	            private void tableMouseClicked(MouseEvent evt) {
	                int index = table.getSelectedRow();
	                
	                if (index < 0) {
	                    return;
	                }
	                
	                System.out.println("Đang chọn dòng: " + index);
	                String maThuoc = (String) table.getValueAt(index, 1);
	                
	                try {
	                    Thuoc thuoc = thuocDAO.getThuocTheoMaThuoc(maThuoc);
	                    
	                    if (thuoc != null) {
	                        
	                        txtMaThuoc.setText(thuoc.getMaThuoc());
	                        txtTenThuoc.setText(thuoc.getTenThuoc());
	                        txtThanhPhan.setText(thuoc.getThanhPhan());
	                        txtDonGia.setText(String.format("%,.0f", thuoc.getGiaBan()));
	                        
	                        
	                        String imagePath = thuoc.getHinhAnh();
	                        System.out.println("Đường dẫn hình ảnh: " + imagePath);
	                        
	                       
	                        ImageHelper.setImageKeepRatio(txtHinhAnh, imagePath, 280, 180);
	                    }
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                    
	                }
	            }
	        });
	        loadDataThuoc();
	        jScrollPane1.setViewportView(table);

	        tablePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

	        jPanel4.add(tablePanel, java.awt.BorderLayout.CENTER);

	        mainPanel.add(jPanel4, java.awt.BorderLayout.CENTER);

	        add(mainPanel, java.awt.BorderLayout.CENTER);

	        billPanel.setBackground(new java.awt.Color(230, 245, 245));
	        billPanel.setPreferredSize(new java.awt.Dimension(460, 800));
	        billPanel.setLayout(new java.awt.BorderLayout(0, 5));

	        cardPanel.setBackground(new java.awt.Color(255, 255, 255));
	        cardPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(238, 238, 238), 2, true));
	        cardPanel.setPreferredSize(new java.awt.Dimension(600, 500));
	        cardPanel.setLayout(new java.awt.BorderLayout());

	        jScrollPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 230, 230), 1, true));

	        String[] tableCartTitle = {"STT", "Tên thuốc", "Số lượng", "Đơn giá", "Thành tiền"};
	        tableCartModel = new DefaultTableModel(tableCartTitle,0);
	        tableCart.setModel(tableCartModel);
	        tableCart.setFocusable(false);
	        jScrollPane2.setViewportView(tableCart);
	        tableCart.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
	        tableCart.setFont(new Font("Arial", Font.PLAIN, 14));

	        cardPanel.add(jScrollPane2, java.awt.BorderLayout.CENTER);

	        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
	        jPanel3.setMinimumSize(new java.awt.Dimension(100, 60));
	        jPanel3.setPreferredSize(new java.awt.Dimension(500, 30));
	        jPanel3.setLayout(new java.awt.BorderLayout());

	        jLabel1.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
	        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
	        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        jLabel1.setText("Giỏ hàng");
	        jPanel3.add(jLabel1, java.awt.BorderLayout.CENTER);

	        cardPanel.add(jPanel3, java.awt.BorderLayout.NORTH);

	        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel20.setForeground(new java.awt.Color(255, 255, 255));
	        jPanel20.setPreferredSize(new java.awt.Dimension(456, 42));
	        jPanel20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 6, 2));

	        btnDeleteCartItem.setBackground(new java.awt.Color(255, 102, 102));
	        btnDeleteCartItem.setFont(new java.awt.Font("Roboto Mono", 1, 14)); // NOI18N
	        btnDeleteCartItem.setForeground(new java.awt.Color(255, 255, 255));
	        btnDeleteCartItem.setIcon(new FlatSVGIcon("./img/trash-cart.svg"));
	        btnDeleteCartItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        btnDeleteCartItem.setPreferredSize(new java.awt.Dimension(50, 38));
	        btnDeleteCartItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnDeleteCartItemActionPerformed(evt);
	            }
	        });
	        jPanel20.add(btnDeleteCartItem);

	        cardPanel.add(jPanel20, java.awt.BorderLayout.PAGE_END);

	        billPanel.add(cardPanel, java.awt.BorderLayout.CENTER);

	        billInfoPanel.setBackground(new java.awt.Color(255, 255, 255));
	        billInfoPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(238, 238, 238), 2, true));
	        billInfoPanel.setPreferredSize(new java.awt.Dimension(500, 400));
	        billInfoPanel.setLayout(new java.awt.BorderLayout());

	        jPanel5.setBackground(new java.awt.Color(0, 153, 153));
	        jPanel5.setMinimumSize(new java.awt.Dimension(100, 60));
	        jPanel5.setPreferredSize(new java.awt.Dimension(500, 30));
	        jPanel5.setLayout(new java.awt.BorderLayout());

	        lblHoaDon.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
	        lblHoaDon.setForeground(new java.awt.Color(255, 255, 255));
	        lblHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        lblHoaDon.setText("Hóa đơn");
	        jPanel5.add(lblHoaDon, java.awt.BorderLayout.CENTER);

	        billInfoPanel.add(jPanel5, java.awt.BorderLayout.NORTH);

	        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 8));

	        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel23.setPreferredSize(new java.awt.Dimension(440, 140));
	        jPanel23.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

	        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

	        jLabel4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
	        jLabel4.setText("Mã hóa đơn ");
	        jLabel4.setPreferredSize(new java.awt.Dimension(120, 40));
	        jPanel7.add(jLabel4);

	        txtMaHoaDon.setEditable(false);
	        txtMaHoaDon.setFont(new java.awt.Font("Roboto Mono", 1, 14)); // NOI18N
	        txtMaHoaDon.setFocusable(false);
	        txtMaHoaDon.setPreferredSize(new java.awt.Dimension(200, 40));
	        jPanel7.add(txtMaHoaDon);

	        jPanel23.add(jPanel7);

	        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel25.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

	        jLabel8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
	        jLabel8.setText("Số điện thoại:");
	        jLabel8.setPreferredSize(new java.awt.Dimension(120, 40));
	        jPanel25.add(jLabel8);

	        txtSdtKH.setPreferredSize(new java.awt.Dimension(200, 40));
	        jPanel25.add(txtSdtKH);

	        btnSearchKH.setIcon(new FlatSVGIcon("./img/search.svg"));
	        btnSearchKH.setBorderPainted(false);
	        btnSearchKH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        btnSearchKH.setFocusPainted(false);
	        btnSearchKH.setFocusable(false);
	        btnSearchKH.setPreferredSize(new java.awt.Dimension(40, 40));
	        btnSearchKH.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnSearchKHActionPerformed(evt);
	            }
	        });
	        jPanel25.add(btnSearchKH);

	        btnAddCustomer.setIcon(new FlatSVGIcon("./img/add-customer.svg"));
	        btnAddCustomer.setBorderPainted(false);
	        btnAddCustomer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        btnAddCustomer.setFocusPainted(false);
	        btnAddCustomer.setFocusable(false);
	        btnAddCustomer.setPreferredSize(new java.awt.Dimension(40, 40));
	        btnAddCustomer.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                try {
						btnAddCustomerActionPerformed(evt);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }

	        });
	        jPanel25.add(btnAddCustomer);

	        jPanel23.add(jPanel25);

	        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

	        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
	        jLabel3.setText("Tên khách hàng");
	        jLabel3.setMaximumSize(new java.awt.Dimension(44, 40));
	        jLabel3.setPreferredSize(new java.awt.Dimension(120, 40));
	        jPanel2.add(jLabel3);

	        txtHoTenKH.setPreferredSize(new java.awt.Dimension(200, 40));
	        jPanel2.add(txtHoTenKH);

	        jPanel23.add(jPanel2);

	        cboxGioiTinhKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
	        cboxGioiTinhKH.setPreferredSize(new java.awt.Dimension(90, 40));
	        jPanel23.add(cboxGioiTinhKH);

	        jPanel6.add(jPanel23);

	        jSeparator1.setPreferredSize(new java.awt.Dimension(400, 3));
	        jPanel6.add(jSeparator1);

	        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel26.setPreferredSize(new java.awt.Dimension(440, 150));
	        jPanel26.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

	        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

	        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
	        jLabel7.setForeground(new java.awt.Color(255, 51, 0));
	        jLabel7.setText("Tổng hóa đơn:");
	        jLabel7.setPreferredSize(new java.awt.Dimension(120, 40));
	        jPanel11.add(jLabel7);

	        txtTong.setEditable(false);
	        txtTong.setFont(new java.awt.Font("Roboto Mono Medium", 0, 14)); // NOI18N
	        txtTong.setForeground(new java.awt.Color(255, 51, 0));
	        txtTong.setFocusable(false);
	        txtTong.setPreferredSize(new java.awt.Dimension(200, 40));
	        jPanel11.add(txtTong);

	        jPanel26.add(jPanel11);

	        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

	        jLabel6.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
	        jLabel6.setText("Tiền nhận vào:");
	        jLabel6.setPreferredSize(new java.awt.Dimension(120, 40));
	        jPanel10.add(jLabel6);
	        txtTienNhanVao.setText("Bỏ qua để thanh toán online");
	        txtTienNhanVao.setPreferredSize(new java.awt.Dimension(200, 40));
	        txtTienNhanVao.addKeyListener(new java.awt.event.KeyAdapter() {
	            public void keyReleased(java.awt.event.KeyEvent evt) {
	                txtTienNhanVaoKeyReleased(evt);
	            }
	        });
	        txtTienNhanVao.addFocusListener(new FocusAdapter() {
	            @Override
	            public void focusGained(FocusEvent e) {
	                if (txtTienNhanVao.getText().equals("Bỏ qua để thanh toán online")) {
	                    txtTienNhanVao.setText("");
	                    txtTienNhanVao.setForeground(Color.BLACK);
	                }
	            }

	            @Override
	            public void focusLost(FocusEvent e) {
	                if (txtTienNhanVao.getText().isEmpty()) {
	                    txtTienNhanVao.setText("Bỏ qua để thanh toán online");
	                    txtTienNhanVao.setForeground(Color.GRAY);
	                }
	            }
	        });
	        jPanel10.add(txtTienNhanVao);

	        jPanel26.add(jPanel10);

	        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
	        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

	        jLabel5.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
	        jLabel5.setText("Tiền thừa:");
	        jLabel5.setPreferredSize(new java.awt.Dimension(120, 40));
	        jPanel9.add(jLabel5);

	        txtTienThua.setEditable(false);
	        txtTienThua.setFont(new java.awt.Font("Roboto Mono Medium", 0, 14)); // NOI18N
	        txtTienThua.setFocusable(false);
	        txtTienThua.setPreferredSize(new java.awt.Dimension(200, 40));
	        jPanel9.add(txtTienThua);

	        jPanel26.add(jPanel9);

	        jPanel6.add(jPanel26);

	        billInfoPanel.add(jPanel6, java.awt.BorderLayout.CENTER);

	        pnBtnHoaDon.setBackground(new java.awt.Color(255, 255, 255));

	        btnHuy.setBackground(new java.awt.Color(255, 102, 102));
	        btnHuy.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
	        btnHuy.setForeground(new java.awt.Color(255, 255, 255));
	        btnHuy.setText("HỦY BỎ");
	        btnHuy.setBorderPainted(false);
	        btnHuy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        btnHuy.setFocusPainted(false);
	        btnHuy.setFocusable(false);
	        btnHuy.setPreferredSize(new java.awt.Dimension(200, 40));
	        btnHuy.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnHuyActionPerformed(evt);
	            }
	        });
	        pnBtnHoaDon.add(btnHuy);

	        btnThanhToan.setBackground(new java.awt.Color(0, 204, 51));
	        btnThanhToan.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
	        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
	        btnThanhToan.setText("THANH TOÁN");
	        btnThanhToan.setBorderPainted(false);
	        btnThanhToan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	        btnThanhToan.setFocusPainted(false);
	        btnThanhToan.setFocusable(false);
	        btnThanhToan.setPreferredSize(new java.awt.Dimension(200, 40));
	        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btnThanhToanActionPerformed(evt);
	            }
	        });
	        pnBtnHoaDon.add(btnThanhToan);

	        
	        billInfoPanel.add(pnBtnHoaDon, java.awt.BorderLayout.PAGE_END);

	        billPanel.add(billInfoPanel, java.awt.BorderLayout.SOUTH);

	        add(billPanel, java.awt.BorderLayout.EAST);
	    }
	    
	    private void loadDataThuoc() throws SQLException {
	    	if (tableCartModel != null) {
	    		tableCartModel.setRowCount(0);
	    	}
	    	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    	centerRenderer.setHorizontalAlignment(JLabel.CENTER);

	    	// Áp dụng cho tất cả các cột
	    	for (int i = 0; i < table.getColumnCount(); i++) {
	    		table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	    	}
	    	tableModel.setRowCount(0);
	    	ArrayList<Thuoc> dsThuoc = thuocDAO.getDsThuoc();
	    	int count = 1;
	    	for (Thuoc thuoc : dsThuoc) {
	    		tableModel.addRow(new Object[] {count++,thuoc.getMaThuoc(),thuoc.getTenThuoc(),
	    				dmtDAO.getDanhMucThuocQuaMaDanhMuc(thuoc.getDanhMucThuoc().getMaDanhMuc()).getTenDanhMuc(),thuoc.getXuatXu(),thuoc.getDonViTinh()
	    				,thuoc.getSoLuongTon(),thuoc.getGiaBan() + " VND"});
	    	}
	    }
	    
	    /**
	     * Tìm kiếm khách hàng theo SĐT
	     */
	    private void btnSearchKHActionPerformed(java.awt.event.ActionEvent evt) {
	        String sdt = txtSdtKH.getText().trim();
	        
	        if (sdt.isEmpty()) {
	            JOptionPane.showMessageDialog(this, 
	                "Vui lòng nhập số điện thoại!", 
	                "Cảnh báo", 
	                JOptionPane.WARNING_MESSAGE);
	            txtSdtKH.requestFocus();
	            return;
	        }
	        
	        try {
	            KhachHang kh = khachHangDAO.getKhachHangTheoSDT(sdt);
	            
	            if (kh != null) {
	                txtHoTenKH.setText(kh.getHoTen());
	                txtHoTenKH.setEditable(false);
	                cboxGioiTinhKH.setEnabled(false);
	            } else {
	                JOptionPane.showMessageDialog(this, 
	                    "Không tìm thấy khách hàng!", 
	                    "Thông báo", 
	                    JOptionPane.INFORMATION_MESSAGE);
	                txtHoTenKH.setText("");
	                txtHoTenKH.setEditable(true);
	                cboxGioiTinhKH.setEnabled(true);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, 
	                "Lỗi: " + e.getMessage(), 
	                "Lỗi", 
	                JOptionPane.ERROR_MESSAGE);
	        }
	    }
/**
 * Xử lý thêm vào giỏ hàng
 */
private void btnAddCartActionPerformed(java.awt.event.ActionEvent evt) {
    currentMaThuoc = txtMaThuoc.getText();
    
    if (currentMaThuoc.isEmpty()) {
        showWarning("Vui lòng chọn thuốc trước!");
        return;
    }
    
    String soLuongStr = txtSoLuong.getText().trim();
    if (soLuongStr.isEmpty()) {
        showWarning("Vui lòng nhập số lượng!");
        txtSoLuong.requestFocus();
        return;
    }
    
    try {
        int soLuong = Integer.parseInt(soLuongStr);
        
        if (soLuong <= 0) {
            showWarning("Số lượng phải lớn hơn 0!");
            return;
        }
        
        // Lấy thông tin thuốc đầy đủ từ DAO
        Thuoc thuoc = thuocDAO.getThuocTheoMaThuoc(currentMaThuoc);
        
        if (thuoc == null) {
            showError("Không tìm thấy thuốc!");
            return;
        }
        
        // Lấy số lượng tồn từ table
        int rowSelected = table.getSelectedRow();
        if (rowSelected < 0) {
            showWarning("Vui lòng chọn thuốc từ bảng!");
            return;
        }
        
        Object val = table.getValueAt(rowSelected, 6);
        int soLuongTon = (val instanceof Integer) 
            ? (Integer) val 
            : Integer.parseInt(val.toString());
        
        // Kiểm tra số lượng đã có trong giỏ
        int soLuongDaTrongGio = 0;
        for (ChiTietHoaDon item : dsChiTietHoaDon) {
            if (item.getThuoc().getMaThuoc().equals(currentMaThuoc)) {
                soLuongDaTrongGio = item.getSoLuong();
                break;
            }
        }
        
        // Kiểm tra tổng số lượng
        if (soLuongDaTrongGio + soLuong > soLuongTon + soLuongDaTrongGio) {
            showWarning("Số lượng tồn kho không đủ!\nSố lượng tồn: " + soLuongTon);
            return;
        }
        
        // Kiểm tra thuốc đã có trong giỏ chưa
        boolean found = false;
        for (ChiTietHoaDon item : dsChiTietHoaDon) {
            if (item.getThuoc().getMaThuoc().equals(currentMaThuoc)) {
                item.setSoLuong(item.getSoLuong() + soLuong);
                found = true;
                System.out.println("Cộng dồn số lượng: " + item.getThuoc().getTenThuoc() + " -> " + item.getSoLuong());
                break;
            }
        }
        
        if (!found) {
            // Tạo chi tiết hóa đơn mới
            HoaDon hoaDon = new HoaDon();
            hoaDon.setMaHD(txtMaHoaDon.getText());
            
            ChiTietHoaDon cthd = new ChiTietHoaDon(
                hoaDon,
                thuoc,  
                soLuong,
                thuoc.getGiaBan()
            );
            
            dsChiTietHoaDon.add(cthd);
            System.out.println("✓ Thêm mới: " + thuoc.getTenThuoc() + " x" + soLuong);
        }
        
        updateSoLuongTon(rowSelected, soLuong);
        
        updateCartTable();
        calculateTotal();
        
        // Reset
        txtSoLuong.setText("");
        showSuccess("Đã thêm vào giỏ hàng!");
        
    } catch (NumberFormatException e) {
        showError("Số lượng không hợp lệ!");
    } catch (Exception e) {
        e.printStackTrace();
        showError("Lỗi: " + e.getMessage());
    }
}
/**
 * Cập nhật số lượng tồn sau khi thêm vào giỏ
 */
private void updateSoLuongTon(int rowSelected, int soLuongDaChon) {
    if (rowSelected >= 0) {
        Object val = table.getValueAt(rowSelected, 6);
        int currentSoLuongTon = (val instanceof Integer) 
            ? (Integer) val 
            : Integer.parseInt(val.toString());
        
        int soLuongSau = currentSoLuongTon - soLuongDaChon;
        table.setValueAt(soLuongSau, rowSelected, 6);
        System.out.println("✓ Cập nhật tồn kho: " + currentSoLuongTon + " -> " + soLuongSau);
    }
}

/**
 * Cập nhật table giỏ hàng
 */
private void updateCartTable() {
    tableCartModel.setRowCount(0);
    int stt = 1;
    
    System.out.println("=== CẬP NHẬT GIỎ HÀNG ===");
    for (ChiTietHoaDon item : dsChiTietHoaDon) {
        String tenThuoc = item.getThuoc().getTenThuoc();
        
        System.out.println("STT " + stt + ": " + tenThuoc + 
                         " | SL: " + item.getSoLuong() + 
                         " | Giá: " + item.getDonGia() +
                         " | Thành tiền: " + item.getThanhTien());
        
        tableCartModel.addRow(new Object[]{
            stt++,
            tenThuoc,
            item.getSoLuong(),
            String.format("%,.0f", item.getDonGia()),
            String.format("%,.0f", item.getThanhTien())
        });
    }
    System.out.println("======================");
}
/**
 * Tính tổng tiền
 */
private void calculateTotal() {
    tongTien = 0;
    for (ChiTietHoaDon item : dsChiTietHoaDon) {
        tongTien += item.getThanhTien();
    }
    double phanTramThue = 0;
    try {
		for (Thue thue : thueDAO.getDsThue()) {
			phanTramThue += thue.getPhanTramThue();
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    tongTien = tongTien + tongTien * phanTramThue / 100;
    txtTong.setText(String.format("%,.0f", tongTien));
    System.out.println("✓ Tổng tiền: " + String.format("%,.0f VNĐ", tongTien));
}
	    
/**
 * Xóa item khỏi giỏ hàng
 */
private void btnDeleteCartItemActionPerformed(java.awt.event.ActionEvent evt) {
    int selectedRow = tableCart.getSelectedRow();
    
    if (selectedRow < 0) {
        showWarning("Vui lòng chọn sản phẩm cần xóa!");
        return;
    }
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Bạn có chắc muốn xóa sản phẩm này?", 
        "Xác nhận", 
        JOptionPane.YES_NO_OPTION);
    
    if (confirm == JOptionPane.YES_OPTION) {
        // Lấy thông tin để hoàn lại số lượng tồn
        ChiTietHoaDon itemToRemove = dsChiTietHoaDon.get(selectedRow);
        String maThuoc = itemToRemove.getThuoc().getMaThuoc();
        int soLuong = itemToRemove.getSoLuong();
        
        System.out.println("✓ Xóa khỏi giỏ: " + itemToRemove.getThuoc().getTenThuoc() + " x" + soLuong);
        
        // Hoàn lại số lượng tồn trong table
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 1).equals(maThuoc)) {
                Object val = table.getValueAt(i, 6);
                int currentSoLuongTon = (val instanceof Integer) 
                    ? (Integer) val 
                    : Integer.parseInt(val.toString());
                int soLuongMoi = currentSoLuongTon + soLuong;
                table.setValueAt(soLuongMoi, i, 6);
                System.out.println("✓ Hoàn lại tồn kho: " + currentSoLuongTon + " -> " + soLuongMoi);
                break;
            }
        }
        
        // Xóa khỏi giỏ hàng
        dsChiTietHoaDon.remove(selectedRow);
        updateCartTable();
        calculateTotal();
    }
}
	    
/**
 * Xử lý thanh toán
 */
private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {
    // Validation
    if (dsChiTietHoaDon.isEmpty()) {
        showWarning("Giỏ hàng trống!");
        return;
    }
    
    String sdt = txtSdtKH.getText().trim();
    if (sdt.isEmpty()) {
        sdt = "0000000000";
    }
    String tienNhanVaoStr = txtTienNhanVao.getText().trim().replace(",", "");
    double tienNhanVao = 0;
    try {
    	if (!tienNhanVaoStr.equals("Bỏ qua để thanh toán online")) {
    		if (tienNhanVaoStr.isEmpty()) {
    			JOptionPane.showMessageDialog(this, "Vui lòng nhập tiền nhận vào");
    		}
	        tienNhanVao = Double.parseDouble(tienNhanVaoStr);
	        
	        if (tienNhanVao < tongTien) {
	            showWarning("Tiền nhận vào chưa đủ!");
	            return;
	        }
	        
    	} 
        
        // Lấy thông tin
        String tenKhachHang = txtHoTenKH.getText().trim();
        if (tenKhachHang.isBlank()) {
        	tenKhachHang = "Khách lẻ";
        }
        String maHoaDon = txtMaHoaDon.getText();
        
        // Hiển thị dialog chi tiết hóa đơn
        Window window = SwingUtilities.getWindowAncestor(this);
        Frame frame = null;
        if (window instanceof Frame) {
            frame = (Frame) window;
        }
        DialogThanhToanHoaDon dialog = new DialogThanhToanHoaDon(
            frame,
            maHoaDon,
            tenKhachHang,
            sdt,
            dsChiTietHoaDon,
            tongTien,
            tienNhanVao, taiKhoan.getNhanVien().getMaNV(), null
        );
        
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
        	resetForm();
        }
        
       
        
    } catch (NumberFormatException e) {
        e.printStackTrace();
    }
}
	    private void txtTienNhanVaoKeyReleased(java.awt.event.KeyEvent evt) {
	        try {
	            String tienNhanVaoStr = txtTienNhanVao.getText().trim().replace(",", "");
	            
	            if (tienNhanVaoStr.isEmpty()) {
	                txtTienThua.setText("");
	                return;
	            }
	            
	            double tienNhanVao = Double.parseDouble(tienNhanVaoStr);
	            double tienThua = tienNhanVao - tongTien;
	            
	            if (tienThua < 0) {
	                txtTienThua.setText("Chưa đủ");
	            } else {
	                txtTienThua.setText(String.format("%,.0f", tienThua));
	            }
	        } catch (NumberFormatException e) {
	            txtTienThua.setText("Không hợp lệ");
	        }
	    }
	    
	    
	    /**
	     * Xử lý hủy bỏ
	     */
	    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {
	        int confirm = JOptionPane.showConfirmDialog(this, 
	            "Bạn có chắc muốn hủy hóa đơn này?", 
	            "Xác nhận hủy", 
	            JOptionPane.YES_NO_OPTION);
	        
	        if (confirm == JOptionPane.YES_OPTION) {
	            resetForm();
	        }
	    }
	    
	    /**
	     * Reset form
	     */
	    public void resetForm() {
	        dsChiTietHoaDon.clear();
	        updateCartTable();
	        
	        txtMaThuoc.setText("");
	        txtTenThuoc.setText("");
	        txtThanhPhan.setText("");
	        txtDonGia.setText("");
	        txtHinhAnh.setIcon(null);
	        txtHinhAnh.setText("Chọn thuốc để xem hình ảnh");
	        txtSoLuong.setText("");
	        currentMaThuoc = "";
	        
	        txtSdtKH.setText("");
	        txtHoTenKH.setText("");
	        txtHoTenKH.setEditable(true);
	        cboxGioiTinhKH.setSelectedIndex(0);
	        cboxGioiTinhKH.setEnabled(true);
	        
	        txtTong.setText("");
	        txtTienNhanVao.setText("");
	        txtTienThua.setText("");
	        tongTien = 0;
	        
	        generateMaHoaDon();
	        
	        try {
	            loadDataThuoc();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    /**
	     * Làm mới table thuốc
	     */
	    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {
	        try {
	            loadDataThuoc();
	            JOptionPane.showMessageDialog(this, 
	                "Đã làm mới danh sách thuốc!", 
	                "Thành công", 
	                JOptionPane.INFORMATION_MESSAGE);
	        } catch (SQLException e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(this, 
	                "Lỗi: " + e.getMessage(), 
	                "Lỗi", 
	                JOptionPane.ERROR_MESSAGE);
	        }
	    }
	    
	    /**
	     * Tìm kiếm thuốc
	     */
	    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {
	        String keyword = txtSearch.getText().trim();
	        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(tableModel);
	        table.setRowSorter(sorter);
	        
	        if (keyword.trim().length() == 0) {
	        	sorter.setRowFilter(null);
	        } else {
	        	sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword));
	        }
	    }
	    
	    /**
	     * Load danh sách khuyến mãi đang có hiệu lực
	     */
	    private void loadKhuyenMai() {
	        try {
	            khuyenMaiContentPanel.removeAll();
	            
	            ArrayList<KhuyenMai> dsKhuyenMai = khuyenMaiDAO.getDsKhuyenMai();
	            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	            Date now = new Date();
	            
	            int count = 0;
	            for (KhuyenMai km : dsKhuyenMai) {
	                // Kiểm tra xem hiện tại có nằm trong khoảng ngày bắt đầu - ngày kết thúc không
	                if (km.getNgayBatDau() != null && km.getNgayKetThuc() != null) {
	                    if (isInDateRange(now, km.getNgayBatDau(), km.getNgayKetThuc())) {
	                        // Tạo card cho mỗi khuyến mãi
	                        javax.swing.JPanel kmCard = createKhuyenMaiCardSimple(
	                            km.getTenKM(),
	                            km.getPhanTramGiamGia()
	                        );
	                        
	                        khuyenMaiContentPanel.add(kmCard);
	                        khuyenMaiContentPanel.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 8)));
	                        count++;
	                    }
	                }
	            }
	            
	            if (count == 0) {
	                // Hiển thị thông báo không có khuyến mãi
	                JLabel lblNoPromo = new javax.swing.JLabel("Hiện không có khuyến mãi");
	                lblNoPromo.setFont(new java.awt.Font("Roboto", Font.ITALIC, 13));
	                lblNoPromo.setForeground(java.awt.Color.GRAY);
	                lblNoPromo.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
	                lblNoPromo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	                khuyenMaiContentPanel.add(javax.swing.Box.createVerticalStrut(20));
	                khuyenMaiContentPanel.add(lblNoPromo);
	            }
	            
	            khuyenMaiContentPanel.revalidate();
	            khuyenMaiContentPanel.repaint();
	            
	            System.out.println("✓ Load " + count + " khuyến mãi đang hiệu lực");
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.err.println("✗ Lỗi khi load khuyến mãi: " + e.getMessage());
	            
	            javax.swing.JLabel lblError = new javax.swing.JLabel("Lỗi load khuyến mãi");
	            lblError.setFont(new java.awt.Font("Roboto", Font.ITALIC, 12));
	            lblError.setForeground(java.awt.Color.RED);
	            lblError.setAlignmentX(javax.swing.JComponent.CENTER_ALIGNMENT);
	            khuyenMaiContentPanel.add(lblError);
	        }
	    }

	    /**
	     * Tạo card khuyến mãi đơn giản - CHỈ TÊN + PHẦN TRĂM
	     */
	    private javax.swing.JPanel createKhuyenMaiCardSimple(String tenKM, double phanTramGiamGia) {
	        javax.swing.JPanel card = new javax.swing.JPanel();
	        card.setLayout(new java.awt.BorderLayout(8, 8));
	        card.setBackground(new java.awt.Color(255, 250, 240));
	        card.setBorder(javax.swing.BorderFactory.createCompoundBorder(
	            new javax.swing.border.LineBorder(new java.awt.Color(255, 153, 0), 2, true),
	            new javax.swing.border.EmptyBorder(12, 15, 12, 15)
	        ));
	        card.setMaximumSize(new java.awt.Dimension(300, 80));
	        card.setPreferredSize(new java.awt.Dimension(300, 80));
	        
	        // ===== LEFT: Icon + Tên khuyến mãi =====
	        javax.swing.JPanel leftPanel = new javax.swing.JPanel(new java.awt.BorderLayout(8, 0));
	        leftPanel.setBackground(new java.awt.Color(255, 250, 240));
	        
	        javax.swing.JLabel lblIcon = new javax.swing.JLabel("🎁");
	        lblIcon.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24));
	        leftPanel.add(lblIcon, java.awt.BorderLayout.WEST);
	        
	        javax.swing.JLabel lblTen = new javax.swing.JLabel("<html>" + tenKM + "</html>");
	        lblTen.setFont(new java.awt.Font("Roboto", Font.BOLD, 13));
	        lblTen.setForeground(new java.awt.Color(51, 51, 51));
	        leftPanel.add(lblTen, java.awt.BorderLayout.CENTER);
	        
	        // ===== RIGHT: Phần trăm giảm giá =====
	        javax.swing.JLabel lblGiam = new javax.swing.JLabel(String.format("-%.0f%%", phanTramGiamGia));
	        lblGiam.setFont(new java.awt.Font("Roboto", Font.BOLD, 22));
	        lblGiam.setForeground(new java.awt.Color(255, 51, 0));
	        lblGiam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	        
	        card.add(leftPanel, java.awt.BorderLayout.CENTER);
	        card.add(lblGiam, java.awt.BorderLayout.EAST);
	        
	        // Hover effect
	        card.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	                card.setBackground(new java.awt.Color(255, 245, 230));
	                leftPanel.setBackground(new java.awt.Color(255, 245, 230));
	            }
	            public void mouseExited(java.awt.event.MouseEvent evt) {
	                card.setBackground(new java.awt.Color(255, 250, 240));
	                leftPanel.setBackground(new java.awt.Color(255, 250, 240));
	            }
	        });
	        
	        return card;
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
	        
	        // Kiểm tra: start <= current <= end
	        boolean result = (current.equals(start) || current.after(start)) && 
	                         (current.equals(end) || current.before(end));
	        
	        System.out.println("  Kiểm tra ngày: " + 
	                          new SimpleDateFormat("dd/MM/yyyy").format(currentDate) + 
	                          " trong [" + 
	                          new SimpleDateFormat("dd/MM/yyyy").format(startDate) + " - " + 
	                          new SimpleDateFormat("dd/MM/yyyy").format(endDate) + "] = " + result);
	        
	        return result;
	    }
		private void btnAddCustomerActionPerformed(ActionEvent evt) throws SQLException {
			ArrayList<KhachHang> dsKH = khachHangDAO.getDSKhachHang();
			for (KhachHang kh : dsKH ) {
				System.out.println(kh);
			}
			int oldSize = dsKH.size();
			new DialogThemKhachHang(null, new FormQuanLyKhachHang()).setVisible(true);;
			ArrayList<KhachHang> newDsKH = khachHangDAO.getDSKhachHang();
			for (KhachHang kh : newDsKH ) {
				System.out.println(kh);
			}
			if (oldSize != newDsKH.size()) {
				KhachHang khNew = newDsKH.get(newDsKH.size() - 1);
				txtSdtKH.setText(khNew.getSoDienThoai());
				txtHoTenKH.setText(khNew.getHoTen());
			}
		}

	    private void showError(String message) {
	        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
	    }
	    
	    private void showWarning(String message) {
	        JOptionPane.showMessageDialog(this, message, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	    }
	    
	    private void showInfo(String message) {
	        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    }
	    
	    private void showSuccess(String message) {
	        JOptionPane.showMessageDialog(this, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
	    }

}