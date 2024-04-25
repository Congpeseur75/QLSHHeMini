package dao;
import connectDB.ConnectDB;
import data.DangKyTre;
import data.TreEm;
import utility.InputUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static utility.InputUtils.*;

public class AddDAO {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<DangKyTre> dangKyTres = new ArrayList<>();
    static ArrayList<TreEm> dste = new ArrayList();
    static InputUtils check = new InputUtils();
    static ConnectDB conn = new ConnectDB();
    static Connection con;
    static PreparedStatement pst = null;
    public static void themTreEm(Scanner sc) {
        String hoTenTre = check.nhapHoTenTre(sc);
        Date ngaySinh = (Date) check.nhapNgaySinh(sc);
        String gioiTinh = check.nhapGioiTinh(sc);
        int maPH = check.nhapMaPH(sc);
        TreEm te = new TreEm(hoTenTre, ngaySinh.toLocalDate(), gioiTinh, maPH);
        dste.add(te);
        try {
            con = ConnectDB.getConnectDB();
            String sql = "INSERT INTO TREEM (HoTenTre, NgaySinh, GioiTinh, MaPH) " + " VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, hoTenTre);
            pst.setDate(2, new Date(ngaySinh.getTime()));
            pst.setString(3, gioiTinh);
            pst.setInt(4, maPH);
            pst.executeUpdate();
            System.out.println("Thêm thông tin trẻ em thành công");
        } catch (SQLException e) {
            System.out.println("Thêm thông tin trẻ em không thành công: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Đống kết nối thất bại");
            }
        }
    }
    public void themPhuHuynh(Scanner sc){
        String hoTenPH = null;
        boolean isValid;

        do {
            System.out.println("Họ và tên phụ huynh:");
            hoTenPH = sc.nextLine();
            isValid = isValidHoTenPH(hoTenPH);

            if (!isValid) {
                System.out.println("Họ tên phụ huynh không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!isValid);

        String diaChi = null;

        do {
            System.out.println(" Địa chỉ");
            diaChi = sc.nextLine();
            isValid = isValidDiaChi(diaChi);

            if (!isValid) {
                System.out.println("Địa chỉ không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!isValid);
        String soDT = null;
        do {
            System.out.println("Số điện thoại");
            soDT = sc.nextLine();
            isValid = isValidSDT(soDT);

            if (!isValid) {
                System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!isValid);

        String email = null;
        do {
            System.out.println("Nhập địa chỉ email:");
            email = sc.nextLine();
            isValid = check.isValidEmail1(email);

            if (!isValid) {
                System.out.println("Email không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!isValid);

        try {
            con = ConnectDB.getConnectDB();
            String sql = "INSERT INTO PHUHUYNH (HoTenPH,DiaChi,SoDT,Email) VALUES(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, hoTenPH);
            pst.setString(2, diaChi);
            pst.setString(3, soDT);
            pst.setString(4, email);
            int a = pst.executeUpdate();
            System.out.println(a + " phụ huynh đã được thêm vào thành công ");
            con.close();
        } catch (SQLException e) {
            System.out.println(" số điện thoại và email đã tồn tại ");
        }

    }

    public static void themGV() {
        try {
            con = ConnectDB.getConnectDB();
            Scanner sc = new Scanner(System.in);
            String name = check.getValidName(sc);
            String sql = "INSERT INTO GiaoVien (HoTenGV) VALUES (?)";

            pst = con.prepareStatement(sql);
            pst.setString(1, name);


            int check = pst.executeUpdate();
            if (check > 0) System.out.println("Thêm dữ liệu thành công");
            else System.out.println("Thêm dữ liệu thất bại");
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

    public static void themMonHoc() {
        try {
            con = ConnectDB.getConnectDB();
            Scanner sc = new Scanner(System.in);
            String name = check.getValidName(sc);
            String sql = "INSERT INTO MonHoc (TenMH) VALUES (?)";

            pst = con.prepareStatement(sql);
            pst.setString(1, name);


            int check = pst.executeUpdate();
            if (check > 0) System.out.println("Thêm dữ liệu thành công");
            else System.out.println("Thêm dữ liệu thất bại");
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
    public static void themThoiGianHoc() {
        System.out.println("\n==========Thêm thời gian học==========");
        try {
            con = ConnectDB.getConnectDB();
            System.out.println("Nhập ngày học: ");
            String NgayHoc = check.getValidNgayHoc(sc);
            System.out.println("Nhập giờ học: ");
            String GioHoc = check.getValidGioHoc(sc);

            String sql = "INSERT INTO THOIGIANHOC (NgayHoc,GioHoc) VALUES (?,?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, NgayHoc);
            pst.setString(2, GioHoc);
            pst.executeUpdate();
            System.out.println("thêm thời gian học thành công");
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void themThongTinLop() {
        System.out.println("\n========thêm thông tin lớp học===========");
        String sql = "INSERT INTO LOPHOC (MaMH, MaGV, MaTGH, PhongHoc, SoBuoi, NgayKhaiGiang, HocPhi) VALUES (?, ?, ?, ?, ?, ?, ?)";

        int monHocID = 0;
        int giaoVienID = 0;
        int thoiGianHocID = 0;
        String phongHoc = "";
        int soBuoi = 0;
        String ngayKhaiGiangString = "";
        int hocPhi = 0;

        try {

            System.out.println("Nhâp thông tin cần thêm: ");

            monHocID = getCheckMonHocID(monHocID);
            giaoVienID = getCheckGiaoVienID(giaoVienID);
            thoiGianHocID = getCheckThoiGianHocID(thoiGianHocID);
            phongHoc = getCheckPhongHoc();
            soBuoi = getCheckSoBuoi(soBuoi);
            ngayKhaiGiangString = getCheckNgayKhaiGiang();
            hocPhi = getCheckHocPhi(hocPhi);


            con = ConnectDB.getConnectDB();
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setInt(1, monHocID);
            pst.setInt(2, giaoVienID);
            pst.setInt(3, thoiGianHocID);
            pst.setString(4, phongHoc);
            pst.setInt(5, soBuoi);
            pst.setString(6, ngayKhaiGiangString);
            pst.setInt(7, hocPhi);
            // Thực thi câu truy vấn
            pst.executeUpdate();
            System.out.println("Đã thêm thành công");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Vui lòng nhập lại" + e.getMessage());
        } finally { // Đóng kết nối sau khi hoàn thành hoặc gặp lỗi
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Đống kết nối thất bại");
            }
        }
    }

    public static void themThongTinDangKy() {
        System.out.println("\n==========Thêm thông tin đăng ký==========");
        boolean isValidInput = false;
        int MaPH = 0;
        do {
            try {
                System.out.println("Nhập mã phụ huynh: ");
                String MaPHString = sc.nextLine();
                if (!validateMa(MaPHString)) {
                    continue;
                }
                MaPH = Integer.parseInt(MaPHString);
                if (!kiemTraMaPHTonTai(MaPH)) {
                    System.out.println("Mã Phụ huynh không tồn tại. Vui lòng nhập lại.");
                    continue;
                }
                isValidInput = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!isValidInput);

        int MaTre = 0;
        do {
            try {
                System.out.println("Nhập mã trẻ em: ");
                String MaTreStr = sc.nextLine();
                if (!validateMa(MaTreStr)) {
                    continue;
                }
                MaTre = Integer.parseInt(MaTreStr);
                if (!kiemTraMaTreTonTai(MaTre)) {
                    System.out.println("Mã trẻ em không tồn tại. Vui lòng nhập lại.");
                    continue;
                }
                isValidInput = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!isValidInput);


        int MaLH = 0;
        do {
            try {
                System.out.println("Nhập mã lớp học: ");
                String MaLHStr = sc.nextLine();
                if (!validateMa(MaLHStr)) {
                    continue;
                }
                MaLH = Integer.parseInt(MaLHStr);
                if (!kiemTraMaPHTonTai(MaLH)) {
                    System.out.println("Mã Phụ lớp học tồn tại. Vui lòng nhập lại.");
                    continue;
                }
                isValidInput = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

        } while (!isValidInput);


        String ngayDangKyStr = "";
        do {
            try {
                System.out.println("Nhập ngày đăng ký (yyyy-MM-dd):");
                ngayDangKyStr = sc.nextLine();
                if (!validateDate(ngayDangKyStr)) {
                    System.out.println("Ngày đăng ký không hợp lệ.");
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            isValidInput = true;
        } while (!isValidInput);


        String trangThaiInput = "";
        do {
            try {
                System.out.println("Nhập trạng thái đăng ký (ấn Enter để bỏ qua và sử dụng trạng thái mặc định 'Chưa Duyệt'):");
                trangThaiInput = sc.nextLine();
                String trangThai = trangThaiInput.isEmpty() ? "Chưa Duyệt" : trangThaiInput;
                if (!validateTrangThai(trangThai)) {
                    System.out.println("Trạng thái đăng ký không hợp lệ.");
                    continue;
                }
                isValidInput = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (!isValidInput);

        DangKyTre dangKyTre = new DangKyTre(MaPH, MaTre, MaLH, Date.valueOf(ngayDangKyStr), trangThaiInput);
        dangKyTres.add(dangKyTre);
        con = ConnectDB.getConnectDB();
        themDangKyTreVaoDatabase(dangKyTre);
        System.out.println("đã thêm thông tin đăng ký thành công");

    }
    public static void themDangKyTreVaoDatabase(DangKyTre dangKyTre){
        try {
            String sql = "INSERT INTO DANGKYTRE(MaPH,MaTre,MaLH,NgayDangKy,TrangThai) VALUES (?,?,?,?,?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, dangKyTre.getMaLH());
            pst.setInt(2,dangKyTre.getMaTre());
            pst.setInt(3,dangKyTre.getMaLH());
            pst.setDate(4,dangKyTre.getNgayDangKy());
            pst.setString(5,("Chưa duyệt"));
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("vui lòng nhập lại");
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
    }




}
