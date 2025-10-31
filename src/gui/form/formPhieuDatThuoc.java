package gui.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import dao.ChiTietPhieuDatThuocDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.PhieuDatThuocDAO;
import entity.ChiTietHoaDon;
import entity.ChiTietPhieuDatThuoc;
import entity.HoaDon;
import entity.KhachHang;
import entity.PhieuDatThuoc;
import entity.TaiKhoan;
import entity.Thuoc;
import gui.dialog.DialogChiTietHoaDon;
import gui.dialog.DialogChiTietPhieuDatThuoc;
import gui.dialog.DialogThanhToanHoaDon;

public class formPhieuDatThuoc extends JPanel {
    private JPanel actionPanel;
    private JButton btnThanhToan;
    private JButton btnReload;
    private JButton btnInfo;
    private JComboBox<String> cboxSearch;
    private JPanel headerPanel;
    private JPanel jPanel1;
    private JPanel jPanel3;
    private JPanel jPanel5;
    private JScrollPane jScrollPane1;
    private JLabel lblTable;
    private JTable table;
    private JPanel tablePanel;
    private JTextField txtSearch;
    private DefaultTableModel tableModel;
    private PhieuDatThuocDAO pdtDAO;
    private ChiTietPhieuDatThuocDAO ctpdtDAO;
    private HoaDonDAO hdDAO;
    private TaiKhoan taiKhoan;
    Font headerTable = new Font("Roboto", Font.BOLD, 18);
	private KhachHangDAO khDAO;
    public formPhieuDatThuoc(TaiKhoan tk) {
    	taoNoiDung();
    	this.taiKhoan = tk;
    }

    private void taoNoiDung() {
    	khDAO = new KhachHangDAO();
    	hdDAO = new HoaDonDAO();
    	ctpdtDAO = new ChiTietPhieuDatThuocDAO();
    	pdtDAO = new PhieuDatThuocDAO();
        headerPanel = new JPanel();
        jPanel1 = new JPanel();
        jPanel3 = new JPanel();
        cboxSearch = new JComboBox<>();
        txtSearch = new JTextField();
        btnReload = new JButton();
        actionPanel = new JPanel();
        btnThanhToan = new JButton();
        btnInfo = new JButton();
        tablePanel = new JPanel();
        jScrollPane1 = new JScrollPane();
        table = new JTable();
        jPanel5 = new JPanel();
        lblTable = new JLabel();

        setBackground(new Color(230, 245, 245));
        setBorder(new LineBorder(new Color(230, 245, 245), 6, true));
        setMinimumSize(new Dimension(1130, 800));
        setPreferredSize(new Dimension(1130, 800));
        setLayout(new BorderLayout(0, 10));

        headerPanel.setBackground(new Color(255, 255, 255));
        headerPanel.setBorder(new LineBorder(new Color(232, 232, 232), 2, true));
        headerPanel.setLayout(new BorderLayout());

        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setPreferredSize(new Dimension(590, 100));
        // Increase right padding from 16 to 32, and top/bottom from 24 to 30
        jPanel1.setLayout(new FlowLayout(FlowLayout.RIGHT, 32, 30));

        jPanel3.setBackground(new Color(255, 255, 255));
        jPanel3.setPreferredSize(new Dimension(584, 50));
        // Increase gap between components from default to 24
        jPanel3.setLayout(new FlowLayout(FlowLayout.TRAILING, 24, 5));

        cboxSearch.setToolTipText("");
        cboxSearch.setPreferredSize(new Dimension(120, 40));
        String[] searchType = {"Tất cả", "Mã", "Trạng thái"};
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(searchType);
        cboxSearch.setModel(model);
        jPanel3.add(cboxSearch);

        txtSearch.setToolTipText("Tìm kiếm");
        txtSearch.setPreferredSize(new Dimension(240, 40));
        txtSearch.setSelectionColor(new Color(230, 245, 245));
        
        jPanel3.add(txtSearch);

        btnReload.setIcon(new FlatSVGIcon("./img/reload.svg"));
        btnReload.setToolTipText("Làm mới");
        btnReload.setBorder(null);
        btnReload.setBorderPainted(false);
        btnReload.setContentAreaFilled(false); 
        btnReload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReload.setFocusPainted(false);
        btnReload.setFocusable(false);
        btnReload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReload.setPreferredSize(new Dimension(48, 48)); 
        btnReload.addActionListener(e -> {
        	try {
				loadTableData();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        jPanel3.add(btnReload);

        jPanel1.add(jPanel3);

        headerPanel.add(jPanel1, BorderLayout.CENTER);

        actionPanel.setBackground(new Color(255, 255, 255));
        actionPanel.setPreferredSize(new Dimension(600, 100));
        actionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 24, 15));

        btnThanhToan.setFont(new Font("Roboto", Font.BOLD, 14));
        btnThanhToan.setIcon(new FlatSVGIcon("./img/ticket.svg"));
        btnThanhToan.setText("THANH TOÁN");
        btnThanhToan.setBorder(null);
        btnThanhToan.setBorderPainted(false);
        btnThanhToan.setContentAreaFilled(false); 
        btnThanhToan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThanhToan.setPreferredSize(new Dimension(90, 90));
        btnThanhToan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnThanhToan.addActionListener(e -> {
        	try {
				thanhToanPhieuDatThuoc();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        actionPanel.add(btnThanhToan);
        
        btnInfo.setFont(new Font("Roboto", Font.BOLD, 14));
        btnInfo.setIcon(new FlatSVGIcon("./img/info.svg"));
        btnInfo.setText("INFO");
        btnInfo.setBorder(null);
        btnInfo.setBorderPainted(false);
        btnInfo.setContentAreaFilled(false); 
        btnInfo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnInfo.setFocusPainted(false);
        btnInfo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInfo.setPreferredSize(new Dimension(90, 90));
        btnInfo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInfo.addActionListener(e -> {
        	xemChiTiet();
        });
        actionPanel.add(btnInfo);

        headerPanel.add(actionPanel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.PAGE_START);

        tablePanel.setBorder(new LineBorder(new Color(230, 230, 230), 2, true));
        tablePanel.setLayout(new BorderLayout());
        
        String[] tableTitle = {"Mã phiếu đặt", "Ngày đặt", "Mã khách hàng", "Địa chỉ", "Hình thức thanh toán", "Trạng thái"};
        tableModel = new DefaultTableModel(tableTitle, 0);
        table.getTableHeader().setFont(headerTable);
        table.setModel(tableModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);


        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.setFocusable(false);
        table.setRowHeight(40);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        tablePanel.add(jScrollPane1, BorderLayout.CENTER);

        jPanel5.setBackground(new Color(0, 153, 153));
        jPanel5.setMinimumSize(new Dimension(100, 60));
        jPanel5.setPreferredSize(new Dimension(500, 40));
        jPanel5.setLayout(new BorderLayout());

        lblTable.setFont(new Font("Roboto Medium", Font.BOLD, 30));
        lblTable.setForeground(new Color(255, 255, 255));
        lblTable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTable.setText("THÔNG TIN PHIẾU ĐẶT");
        jPanel5.add(lblTable, BorderLayout.CENTER);

        tablePanel.add(jPanel5, BorderLayout.NORTH);
        
        try {
			loadTableData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        add(tablePanel, BorderLayout.CENTER);
    }
    
    private void thanhToanPhieuDatThuoc() throws SQLException {
    	int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn phiếu đặt thuốc cần xem chi tiết!",
                "Cảnh báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String trangThai = table.getValueAt(selectedRow, 5).toString();
        if (trangThai.equals("Đã hoàn thành")) {
        	JOptionPane.showMessageDialog(this, "Phiếu đặt thuốc đã hoàn thành!");
        	return;
        }
        
        String maPhieuDat = table.getValueAt(selectedRow, 0).toString();
        System.out.println(maPhieuDat);
        PhieuDatThuoc pdt = pdtDAO.getPhieuDatThuocQuaMaPhieuDat(maPhieuDat);
        KhachHang kh = khDAO.getKhachHangTheoMa(pdt.getKhachHang().getMaKH());
        ArrayList<ChiTietPhieuDatThuoc> dsCTPDT = ctpdtDAO.getChiTietPhieuDatThuocQuaMaPhieuDatThuoc(maPhieuDat);
        ArrayList<ChiTietHoaDon> temp = new ArrayList<ChiTietHoaDon>();
        String maHD = hdDAO.generateMaHD();
        double tongTien = 0;
        for (ChiTietPhieuDatThuoc ctpdt : dsCTPDT) {
        	String maThuoc = ctpdt.getThuoc().getMaThuoc();
        	double donGia = ctpdt.getDonGia();
        	int soLuong = ctpdt.getSoLuong();
        	ChiTietHoaDon cthd = new ChiTietHoaDon(new HoaDon(maHD), new Thuoc(maThuoc), soLuong, donGia);
        	tongTien += cthd.getThanhTien();
        	temp.add(cthd);
        }
        Window window = SwingUtilities.getWindowAncestor(this);
        Frame frame = null;
        if (window instanceof Frame) {
            frame = (Frame) window;
        }
        DialogThanhToanHoaDon dialog = new DialogThanhToanHoaDon(frame,maHD,kh.getMaKH(),kh.getSoDienThoai()
        		,temp,tongTien,0,taiKhoan.getNhanVien().getMaNV(),maPhieuDat);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
        	if(pdtDAO.capNhatTrangThaiPhieuDatThuoc(maPhieuDat, "Đã hoàn thành")) {
        		JOptionPane.showMessageDialog(this, "Đã thanh toán phiếu đặt thuốc thành công");
        	}
        }
    }
    
    private void xemChiTiet() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn phiếu đặt thuốc cần xem chi tiết!",
                "Cảnh báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maPhieuDat = table.getValueAt(selectedRow, 0).toString();

        DialogChiTietPhieuDatThuoc dialog = new DialogChiTietPhieuDatThuoc(
            (javax.swing.JFrame) SwingUtilities.getWindowAncestor(this),
            maPhieuDat
        );
        dialog.setVisible(true);
		
	}

	private void loadTableData() throws Exception {
    	ArrayList<PhieuDatThuoc> dsPDT = pdtDAO.getDsPhieuDatThuoc();
    	for (PhieuDatThuoc pdt : dsPDT) {
    		tableModel.addRow(new Object[] {pdt.getMaPhieuDat(), pdt.getNgayDat(),pdt.getKhachHang().getMaKH(),pdt.getDiaChi(),
    				pdt.getHinhThucThanhToan(), pdt.getTrangThai()});
    	}
    }
}