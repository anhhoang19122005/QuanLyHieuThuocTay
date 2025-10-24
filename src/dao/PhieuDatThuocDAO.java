package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectDB.DatabaseConnection;
import entity.KhachHang;
import entity.PhieuDatThuoc;

public class PhieuDatThuocDAO {
    private Connection getSafeConnection() throws SQLException {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        if (conn == null || conn.isClosed()) {
            conn = DatabaseConnection.getInstance().getConnection();
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Không thể thiết lập kết nối đến cơ sở dữ liệu");
            }
        }
        return conn;
    }
    
    public ArrayList<PhieuDatThuoc> getDsPhieuDatThuoc() throws Exception {
    	ArrayList<PhieuDatThuoc> temp = new ArrayList<PhieuDatThuoc>();
    	String sql = "SELECT * FROM PhieuDatThuoc";
    	try (Connection con = getSafeConnection()) {
    		Statement stmt = con.createStatement();
    		try (ResultSet rs = stmt.executeQuery(sql)) {
    			while (rs.next()) {
    				String maPhieuDat = rs.getString("maPhieuDat");
    				Date ngayDat = rs.getDate("ngayDat");
    				String maKH = rs.getString("maKH");
    				String diaChi = rs.getString("diaChi");
    				String hinhThucThanhToan = rs.getString("hinhThucThanhToan");
    				String trangThai = rs.getString("trangThai");
    				PhieuDatThuoc pdt = new PhieuDatThuoc(maPhieuDat, ngayDat, new KhachHang(maKH), diaChi, hinhThucThanhToan, trangThai);
    				temp.add(pdt);
    			}
    		}
    	}
		return temp;
    }
}
