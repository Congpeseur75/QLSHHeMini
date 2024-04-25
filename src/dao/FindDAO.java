package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import connectDB.ConnectDB;
import data.LopHoc;
import data.MonHocDangKy;
import utility.InputUtils;

import static utility.InputUtils.*;

public class FindDAO {
    static Scanner sc = new Scanner(System.in);
    static ConnectDB conn = new ConnectDB();
    static Connection con = conn.getConnectDB();
    static Statement st = null;
    static ResultSet rs = null;
    static PreparedStatement pst = null;
    public static List<MonHocDangKy> getMonHocNhieuNguoiDangKyNhat() throws SQLException {
        List<MonHocDangKy> monHocDangKyList = new ArrayList<>();

        String query = "SELECT MONHOC.TenMH, COUNT(DANGKYTRE.MaLH) AS SoPhuHuynh " +
                "FROM DANGKYTRE " +
                "JOIN LOPHOC ON DANGKYTRE.MaLH = LOPHOC.MaLH " +
                "JOIN MONHOC ON LOPHOC.MaMH = MONHOC.MaMH " +
                "WHERE DANGKYTRE.NgayDangKy BETWEEN '2024-01-01' AND '2024-03-31' " +
                "GROUP BY MONHOC.TenMH " +
                "ORDER BY SoPhuHuynh DESC";

        try (PreparedStatement statement = con.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String tenMonHoc = resultSet.getString("TenMH");
                int soPhuHuynh = resultSet.getInt("SoPhuHuynh");
                MonHocDangKy monHocDangKy = new MonHocDangKy(tenMonHoc, soPhuHuynh);
                monHocDangKyList.add(monHocDangKy);
            }
        }

        return monHocDangKyList;
    }
    public void lietKeMonHocChuaDK() {
        String sql = "SELECT DISTINCT TenMH FROM MONHOC as MH, LOPHOC as LH, DANGKYTRE as DKT	 WHERE MH.MaMH = LH.MaMH AND LH.MaLH = DKT.MaLH AND NOT  DKT.NgayDangKy BETWEEN '2024-03-01' AND '2024-03-31'";
        Connection con = null;

        try {
            con = ConnectDB.getConnectDB();
            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            System.out.println("Thông tin Lớp học chưa được PH đăng ký trong tháng 3/2024: ");
            System.out.println(" ----------------");
            System.out.printf("|%15s|", "Tên Môn học");
            System.out.println();
            System.out.println(" ----------------");
            while (rs.next()) {
                System.out.printf("|%15s|", rs.getString("TenMH"));
                System.out.println();
            }
            System.out.println(" ----------------");
        } catch (SQLException e) {
            System.out.println("Liệt kê thất bại");
        } finally {
            // Đóng kết nối sau khi hoàn thành hoặc gặp lỗi
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Đống kết nối thất bại");
            }
        }
    }
    public void top3() {
        System.out.println("\n===========Tim kiếm top 3 phụ huynh đăng ký===========");
        System.out.println("+---------+-------------------+----------------+---------+");
        System.out.println("|   MaPH  |      HoTenPH      |    TenMH       |  TongDK |");
        System.out.println("+---------+-------------------+----------------+---------+");

        try {
            Connection con = ConnectDB.getConnectDB();

            String sql = "SELECT PHUHUYNH.MaPH,HoTenPH,TenMH,TongDK "
                    + "FROM ( SELECT TOP (3) COUNT(DANGKYTRE.MaPH) TongDK,DANGKYTRE.MaPH,MaLH,NgayDangKy "
                    + "FROM DANGKYTRE " + "GROUP BY DANGKYTRE.MaPH,MaLH,NgayDangKy  " + "ORDER BY TongDK DESC ) "
                    + "AS DANGKYTRE " + "JOIN PHUHUYNH ON DANGKYTRE.MaPH = PHUHUYNH.MaPH "
                    + "JOIN LOPHOC ON DANGKYTRE.MaLH = LOPHOC.MaLH " + "JOIN MONHOC ON LOPHOC.MaMH= MONHOC.MaMH "
                    + "WHERE YEAR(DANGKYTRE.NgayDangKy) = 2023;";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int maPH = rs.getInt("MaPH");
                String tenPH = rs.getString("HoTenPH");
                String tenMH = rs.getString("TenMH");
                int tong = rs.getInt("TongDK");

                System.out.printf("| %-7s | %-17s | %-14s | %-7s |\n", maPH, tenPH, tenMH, tong);

            }

            con.close();
        } catch (SQLException e) {
            System.out.println("lỗi " + e.getMessage());

        }
        System.out.println("+---------+-------------------+----------------+---------+");
    }
    public void lietKeGiaoVien() {
        try {
            Connection con = ConnectDB.getConnectDB();
            String sql = "SELECT gv.MaGV, gv.HoTenGV, COUNT(lh.MaLH) AS SoLuongLop\n"
                    + "FROM GIAOVIEN gv JOIN LOPHOC lh ON gv.MaGV = lh.MaGV\n"
                    + "WHERE lh.NgayKhaiGiang BETWEEN '2024-01-01' AND '2024-02-28'\n"
                    + "GROUP BY gv.MaGV, gv.HoTenGV\n" + "ORDER BY COUNT(lh.MaLH) DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            System.out.println("Thông tin các giáo viên dạy nhiều lớp học nhất trong khoảng thời gian 1/2024 - 2/2024");
            System.out.printf("%-10s %-20s\n", "Mã Giáo Viên", "Tên Giáo Viên");
            while (rs.next()) {
                int MaGV = rs.getInt("MaGV");
                String HoTenGV = rs.getString("HoTenGV");
                System.out.printf("%-10d %-20s \n", MaGV, HoTenGV);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Liệt kê thông tin giáo viên không thành công: " + e.getMessage());
        }
    }


    public static void lietKeDangKyThapNhat() {
        try {
            con = ConnectDB.getConnectDB();
            String sql = "SELECT TOP 1 WITH TIES LH.MaLH, LH.MaMH, LH.MaGV, LH.MaTGH, LH.PhongHoc, " +
                    "LH.SoBuoi, LH.NgayKhaiGiang, LH.HocPhi, COUNT(DK.MaLH) AS SoLuongDangKy " +
                    "FROM LOPHOC LH " +
                    "LEFT JOIN DANGKYTRE DK ON LH.MaLH = DK.MaLH " +
                    "WHERE LH.NgayKhaiGiang >= '2023-12-01' AND LH.NgayKhaiGiang <= GETDATE()" +
                    "GROUP BY LH.MaLH, LH.MaMH, LH.MaGV, LH.MaTGH, LH.PhongHoc, " +
                    "LH.SoBuoi, LH.NgayKhaiGiang, LH.HocPhi " +
                    "ORDER BY SoLuongDangKy ASC";
            st = con.createStatement();
            rs = st.executeQuery(sql);
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-10s | %-10s | %-12s | %-17s | %-12s | %-8s | %-15s | %-8s | %-18s |\n",
                    "Mã lớp học", "Mã môn học", "Mã giáo viên", "Mã thời gian học", "Phòng học",
                    "Số buổi", "Ngày khai giảng", "Học phí", "Số lượng đăng ký");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("| %-10s | %-10s | %-12s | %-17s | %-12s | %-8s | %-15s | %-8s | %-18s |\n",
                        rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getString(5),
                        rs.getInt(6), rs.getDate(7), rs.getDouble(8), rs.getInt(9));
            }
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void timKiemThongTinDangKyDatabase() {
        System.out.println("\n========================Tìm Kiếm thông tin đăng ký=========================");
        boolean isValidInput = false;
        int MaDK;
        do {
            try {
                System.out.println("Nhập MaDK cần tìm: ");
                String MaDKStr = sc.nextLine();
                if (!InputUtils.validateMa(MaDKStr)) {
                    System.out.println("mã đăng ký không hợp lệ");
                    continue;
                }
                MaDK = Integer.parseInt(MaDKStr);
                if (!kiemTraMaDKTonTai(MaDK)) {
                    System.out.println("Mã đăng ký không tồn tại. Vui lòng nhập lại.");
                    continue;
                }

                System.out.println("+----------+----------+----------+----------+--------------+--------------+");
                System.out.println("|   MaDK   |   MaPH   |   MaTre  |   MaLH   |  NgayDangKy  |   TrangThai  |");
                System.out.println("+----------+----------+----------+----------+--------------+--------------+");
                String sql = "SELECT * FROM DANGKYTRE WHERE MaDK=?";

                con = ConnectDB.getConnectDB();
                pst = con.prepareStatement(sql);
                pst.setInt(1, MaDK);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    System.out.printf("| %-8s | %-8s | %-8s | %-8s | %-12s | %-12s |\n",
                            rs.getInt("MaDK"),
                            rs.getInt("MaPH"),
                            rs.getInt("MaTre"),
                            rs.getInt("MaLH"),
                            rs.getDate("NgayDangKy"),
                            rs.getString("TrangThai"));
                } else {
                    System.out.println("không tìm thấy thông tin đăng ký");
                }
                rs.close();
            } catch (NumberFormatException | SQLException e) {
                System.out.println("Lỗi" + e);
            }
            finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    System.out.println("Đống kết nối thất bại");
                }
            }
            isValidInput = true;
        } while (!isValidInput);
        System.out.println("+----------+----------+----------+----------+--------------+--------------+");
    }

    public static void lietKeThoiGianHoc() {
        System.out.println("\n==============Liệt kê thời gian học==============");
        System.out.println("+-----------+---------------+-------------------+");
        System.out.println("|  Ngày học |    Giờ học    | Số lượng đăng ký  |");
        System.out.println("+-----------+---------------+-------------------+");
        try {
            con = ConnectDB.getConnectDB();
            String sql = "SELECT TH.NgayHoc, TH.GioHoc, COUNT(DKT.MaDK) AS SoLuongDangKy\n" +
                    "FROM THOIGIANHOC TH\n" +
                    "INNER JOIN LOPHOC LH ON TH.MaTGH = LH.MaTGH\n" +
                    "INNER JOIN DANGKYTRE DKT ON LH.MaLH = DKT.MaLH\n" +
                    "WHERE DKT.NgayDangKy BETWEEN '2024-01-01' AND '2024-03-31'\n" +
                    "GROUP BY TH.NgayHoc, TH.GioHoc\n" +
                    "ORDER BY SoLuongDangKy DESC;";
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String ngayHoc = rs.getString("NgayHoc");
                String gioHoc = rs.getString("GioHoc");
                int soLuongDangKy = rs.getInt("SoLuongDangKy");

                System.out.printf("| %-9s | %-13s | %-17s |\n", ngayHoc, gioHoc, soLuongDangKy);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Đống kết nối thất bại");
            }
        }
        System.out.println("+-----------+---------------+-------------------+");
    }
    public void timKiemPhuHuynh(Scanner sc) {
        System.out.println(" nhập họ tên phụ huynh cần tìm kiếm ");
        String hoTen = sc.nextLine();
        System.out.println("\n===========Danh sách tìm kiếm phụ huynh===========");
        System.out.println(
                "+---------+-------------------+---------------------+-------------------------+----------------------------+");
        System.out.println(
                "|   MaPH  |      HoTenPH      |        DiaChi       |           SoDT          |            Email           |");
        System.out.println(
                "+---------+-------------------+---------------------+-------------------------+----------------------------+");

        try {
            con = ConnectDB.getConnectDB();
            String sql = "SELECT * FROM PHUHUYNH WHERE HoTenPH Like ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + hoTen + "%");

            ResultSet rs = pst.executeQuery();

            int count = 0;
            while (rs.next()) {
                int maPH = rs.getInt("MaPH");
                String tenPH = rs.getString("HoTenPH");
                String diaChi = rs.getString("DiaChi");
                String soDT = rs.getString("SoDT");
                String email = rs.getString("Email");
                count++;
                System.out.printf("| %-7s | %-17s | %-19s | %-23s | %-26s |\n", maPH, tenPH, diaChi, soDT, email);

            }
            if (count == 0) {
                System.out.println(" không có dữ  liệu về " + hoTen);
            }
            con.close();

        } catch (SQLException e) {
            e.getStackTrace();
            System.out.println(" lỗi " + e.getMessage());

        }
        System.out.println(
                "+---------+-------------------+---------------------+-------------------------+----------------------------+");

    }



}
