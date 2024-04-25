package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.LopHoc;

public class LopHocDAO {
    private Connection conn;

    public LopHocDAO(Connection conn) {
        this.conn = conn;
    }
    public List<LopHoc> xemThongTinLopHoc() throws SQLException {
        List<LopHoc> danhSachLopHoc = new ArrayList<>();
        String sql = "SELECT L.MaLH, M.TenMH, G.HoTenGV, T.NgayHoc, T.GioHoc, L.PhongHoc, L.SoBuoi, L.NgayKhaiGiang, L.HocPhi " +
                "FROM LOPHOC L " +
                "JOIN MONHOC M ON L.MaMH = M.MaMH " +
                "JOIN GIAOVIEN G ON L.MaGV = G.MaGV " +
                "JOIN THOIGIANHOC T ON L.MaTGH = T.MaTGH";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                LopHoc lopHoc = new LopHoc(
                        rs.getInt("MaLH"),
                        rs.getString("TenMH"),
                        rs.getString("HoTenGV"),
                        rs.getString("NgayHoc"),
                        rs.getString("GioHoc"),
                        rs.getString("PhongHoc"),
                        rs.getInt("SoBuoi"),
                        rs.getDate("NgayKhaiGiang").toLocalDate(),
                        rs.getBigDecimal("HocPhi")
                );
                danhSachLopHoc.add(lopHoc);
            }
        }

        return danhSachLopHoc;
    }

    public List<LopHoc> timKiemLopHocTheoTenMH(String tenMH) throws SQLException {
        List<LopHoc> danhSachLopHoc = new ArrayList<>();
        String sql = "SELECT L.MaLH, M.TenMH, G.HoTenGV, T.NgayHoc, T.GioHoc, L.PhongHoc, L.SoBuoi, L.NgayKhaiGiang, L.HocPhi " +
                "FROM LOPHOC L " +
                "JOIN MONHOC M ON L.MaMH = M.MaMH " +
                "JOIN GIAOVIEN G ON L.MaGV = G.MaGV " +
                "JOIN THOIGIANHOC T ON L.MaTGH = T.MaTGH " +
                "WHERE M.TenMH LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + tenMH + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LopHoc lopHoc = new LopHoc(
                            rs.getInt("MaLH"),
                            rs.getString("TenMH"),
                            rs.getString("HoTenGV"),
                            rs.getString("NgayHoc"),
                            rs.getString("GioHoc"),
                            rs.getString("PhongHoc"),
                            rs.getInt("SoBuoi"),
                            rs.getDate("NgayKhaiGiang").toLocalDate(),
                            rs.getBigDecimal("HocPhi")
                    );
                    danhSachLopHoc.add(lopHoc);
                }
            }
        }

        return danhSachLopHoc;
    }

}
