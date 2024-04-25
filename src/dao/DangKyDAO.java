package dao;
import connectDB.ConnectDB;
import controller.PhuHuynhController;
import data.MonHocDangKy;
import data.PhuHuynh;
import data.TreEm;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DangKyDAO {
    private PhuHuynhController phuHuynhController;
    ConnectDB conn = new ConnectDB();
    Connection con = conn.getConnectDB();
    Statement st = null;
    PreparedStatement pst = null;

    public DangKyDAO(Connection conn) {
        this.con = conn;
    }


    public int themPhuHuynh(PhuHuynh phuHuynh) throws SQLException {
        String sql = "INSERT INTO PHUHUYNH (HoTenPH, DiaChi, SoDT, Email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, phuHuynh.getHoTenPH());
            pst.setString(2, phuHuynh.getDiaChi());
            pst.setString(3, phuHuynh.getSoDT());
            pst.setString(4, phuHuynh.getEmail());
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                return layMaPhuHuynhMoi(phuHuynh);
            }
        }
        return -1;
    }

    private int layMaPhuHuynhMoi(PhuHuynh phuHuynh) throws SQLException {
        String sql = "SELECT MaPH FROM PHUHUYNH WHERE HoTenPH = ? AND DiaChi = ? AND SoDT = ? AND Email = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, phuHuynh.getHoTenPH());
            pstmt.setString(2, phuHuynh.getDiaChi());
            pstmt.setString(3, phuHuynh.getSoDT());
            pstmt.setString(4, phuHuynh.getEmail());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("MaPH");
                }
            }
        }
        return -1;
    }

    public int themTreEm(TreEm treEm) throws SQLException {
        String sql = "INSERT INTO TREEM (HoTenTre, NgaySinh, GioiTinh, MaPH) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, treEm.getHoTenTre());
            pstmt.setDate(2, java.sql.Date.valueOf(treEm.getNgaySinh()));
            pstmt.setString(3, treEm.getGioiTinh());
            pstmt.setInt(4, treEm.getMaPH());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return layMaTreEmMoi(treEm);
            }
        }
        return -1;
    }

    private int layMaTreEmMoi(TreEm treEm) throws SQLException {
        String sql = "SELECT MaTre FROM TREEM WHERE HoTenTre = ? AND NgaySinh = ? AND GioiTinh = ? AND MaPH = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, treEm.getHoTenTre());
            pstmt.setDate(2, java.sql.Date.valueOf(treEm.getNgaySinh()));
            pstmt.setString(3, treEm.getGioiTinh());
            pstmt.setInt(4, treEm.getMaPH());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("MaTre");
                }
            }
        }
        return -1;
    }

    public boolean themDangKyLopHoc(int maPH, int maTre, int maLH, LocalDate ngayDangKy) throws SQLException {
        String sql = "INSERT INTO DANGKYTRE (maPH, MaTre, MaLH, NgayDangKy, TrangThai) VALUES (?, ?, ?, ?, N'ChÆ°a duyá»‡t')";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, maPH);
            pstmt.setInt(2, maTre);
            pstmt.setInt(3, maLH);
            pstmt.setDate(4, java.sql.Date.valueOf(ngayDangKy));
            return pstmt.executeUpdate() > 0;
        }
    }


    public List<String> layDanhSachLopHoc() throws SQLException {
        List<String> danhSachLopHoc = phuHuynhController.layDanhSachLopHoc();
        if (danhSachLopHoc.isEmpty()) {
            System.out.println("hông có thông tin lớp học để hiển thị.");
        } else {
            System.out.println(String.format("%-5s %-15s %-20s %-15s %-20s %-10s %-10s %-15s %-20s",
                    "MaLH", "TenMH", "HoTenGV", "NgayHoc", "GioHoc", "PhongHoc", "SoBuoi", "NgayKhaiGiang", "HocPhi"));
            for (String lopHoc : danhSachLopHoc) {
                System.out.println(lopHoc);
            }
        }
        return danhSachLopHoc;
    }
    public void xemThongTinLopHoc() throws SQLException {
        try {
            String sql = "SELECT L.MaLH, M.TenMH, G.HoTenGV, T.NgayHoc, T.GioHoc, L.PhongHoc, L.SoBuoi, L.NgayKhaiGiang, L.HocPhi " +
                    "FROM LOPHOC L " +
                    "JOIN MONHOC M ON L.MaMH = M.MaMH " +
                    "JOIN GIAOVIEN G ON L.MaGV = G.MaGV " +
                    "JOIN THOIGIANHOC T ON L.MaTGH = T.MaTGH";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("MaLH")+
                        rs.getString("TenMH")+
                        rs.getString("HoTenGV")+
                        rs.getString("NgayHoc")+
                        rs.getString("GioHoc")+
                        rs.getString("PhongHoc")+
                        rs.getInt("SoBuoi")+
                        rs.getDate("NgayKhaiGiang")+
                        rs.getBigDecimal("HocPhi"));
            }
        }
        catch (Exception e){
            System.out.println("lỗi: ");
        }
    }
    public boolean kiemTraTonTaiSoDT(String soDT) throws SQLException {
        return kiemTraTonTai("PHUHUYNH", "SoDT", soDT);
    }

    public boolean kiemTraTonTaiEmail(String email) throws SQLException {
        return kiemTraTonTai("PHUHUYNH", "Email", email);
    }

    private boolean kiemTraTonTai(String table, String column, String value) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + table + " WHERE " + column + " = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, value);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

}