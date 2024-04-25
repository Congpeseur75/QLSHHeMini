package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import connectDB.ConnectDB;
import utility.InputUtils;
import static utility.InputUtils.*;


public class EditDAO {
    static Scanner sc = new Scanner(System.in);
    static InputUtils check = new InputUtils();
    static ConnectDB conn = new ConnectDB();
    static Connection con = conn.getConnectDB();
    static PreparedStatement pst = null;
    public void suaTreEm(Scanner sc2) {
        try {
            int maSua = 0;
            boolean kt = false;
            while (!kt) {
                System.out.println("Nhập vào mã của trẻ cần sửa");
                maSua = Integer.parseInt(sc.nextLine());
                if (check.isExists("TREEM", "MaTre", String.valueOf(maSua)) == true) {
                    kt = true;
                } else {
                    System.out.println("Không tìm thấy mã trẻ!");
                }
            }
            String tenSua = check.nhapHoTenTre(sc);
            java.sql.Date ngaySinhSua = (Date) check.nhapNgaySinh(sc);
            String gioiTinhSua = check.nhapGioiTinh(sc);
            int maPHSua = check.nhapMaPH(sc);
            con = ConnectDB.getConnectDB();
            String sql = "UPDATE TREEM SET HoTenTre = ?, NgaySinh = ?, GioiTinh = ?, MaPH = ? WHERE MaTre = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, tenSua);
            pst.setDate(2, ngaySinhSua);
            pst.setString(3, gioiTinhSua);
            pst.setInt(4, maPHSua);
            pst.setInt(5, maSua);
            pst.executeUpdate();
            System.out.println("Đã sửa thông tin trẻ em thành công");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Sửa thông tin trẻ em không thành công: " + e.getMessage());
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
    public static void suaPhuHuynh() {
        System.out.println(" nhập mã cần sửa ");
        int maPH = 0;
        boolean t = true;
        while (t) {
            try {
                maPH = Integer.parseInt(sc.nextLine());
                if (check(maPH)) {
                    t = false;
                } else {
                    System.out.println(" nhập  lại mã cần sửa ");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String hoTenPH = null;
        boolean isValid;


        do {
            System.out.println("Họ và tên phụ huynh:");
            hoTenPH = sc.nextLine();
            isValid = InputUtils.isValidHoTenPH(hoTenPH);

            if (!isValid) {
                System.out.println("Họ tên phụ huynh không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!isValid);

        String diaChi = null;

        do {
            System.out.println(" Địa chỉ");
            diaChi = sc.nextLine();
            isValid = InputUtils.isValidDiaChi(diaChi);

            if (!isValid) {
                System.out.println("Địa chỉ không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!isValid);
        String soDT = null;
        do {
            System.out.println("Số điện thoại");
            soDT = sc.nextLine();
            isValid = InputUtils.isValidSDT(soDT);

            if (!isValid) {
                System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!isValid);

        String email = null;
        do {
            System.out.println("Nhập địa chỉ email:");
            email = sc.nextLine();
            isValid = InputUtils.isValidEmail(email);

            if (!isValid) {
                System.out.println("Email không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!isValid);

        try {
            con = ConnectDB.getConnectDB();
            String sql = "UPDATE  PHUHUYNH SET HoTenPH = ?,DiaChi = ?,SoDT =?,Email = ? WHERE MaPH=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, hoTenPH);
            pst.setString(2, diaChi);
            pst.setString(3, soDT);
            pst.setString(4, email);
            pst.setInt(5, maPH);
            int a = pst.executeUpdate();
            System.out.println(a + " phụ huynh đã được sửa vào thành công ");

        } catch (SQLException e) {
            System.out.println(" lỗi " + e.getMessage());
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

    public  void editGiaoVien() {

            Scanner sc = new Scanner(System.in);
            int maGV = check.getValidMa(sc);
            try {
            if (check.isExists("GiaoVien", "MaGV", String.valueOf(maGV)) == true) {
                String tenGV = check.getValidName(sc);
                con = ConnectDB.getConnectDB();
                String sql = "update GiaoVien set HoTenGV = ? where MaGV = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, tenGV);
                pst.setInt(2, maGV);
                pst.executeUpdate();
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Cập nhật giáo viên thành công!");
                } else {
                    System.out.println("Không tìm thấy mã giáo viên!");
                }
            } else {
                System.out.println("Không tìm thấy mã giáo viên!");
                editGiaoVien();
            }
        } catch (SQLException e) {
            System.out.println("Cập nhật giáo viên thất bại!");
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

    public void suaMonHoc() {
        Scanner sc = new Scanner(System.in);
        int maMH = check.getValidMa(sc);
        try {
            if (check.isExists("MonHoc", "MaMH", String.valueOf(maMH)) == true) {
                String tenMH = check.getValidName(sc);
                String sql = "update MonHoc set TenMH = ? where MaMH = ?";
                con = ConnectDB.getConnectDB();
                pst = con.prepareStatement(sql);
                pst.setString(1, tenMH);
                pst.setInt(2, maMH);
                pst.executeUpdate();
                if (pst.executeUpdate() > 0) {
                    System.out.println("Cập nhật môn học thành công!");
                } else {
                    System.out.println("Không tìm thấy mã môn học!");
                }
            } else {
                System.out.println("Không tìm thấy mã môn học!");
                suaMonHoc();
            }
        } catch (SQLException e) {
            System.out.println("Cập nhật môn học thất bại!");
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public  void suaThoiGianHoc(){
        System.out.println("\n=========Sửa thời gian học=========");
        boolean isValidInput = false;
        int MaTGH;
        do {
            try {
                System.out.println("Nhâp mã thời gian học cần sửa");
                String MaTGHStr = sc.nextLine();
                if (!InputUtils.validateMa(MaTGHStr)) {
                    System.out.println("mã đăng ký không hợp lệ");
                    continue;
                }
                MaTGH = Integer.parseInt(MaTGHStr);
                if (!kiemTraMaTGH(MaTGH)) {
                    System.out.println("Mã đăng ký không tồn tại. Vui lòng nhập lại.");
                    continue;
                }
                System.out.println("Nhập ngày học mới: ");
                String NgayHoc = check.getValidNgayHoc(sc);
                System.out.println("Nhập giờ học mới: ");
                String GioHoc = check.getValidGioHoc(sc);

                String sql = "UPDATE THOIGIANHOC SET NgayHoc=? , GioHoc=? WHERE MaTGH=?";
                con = ConnectDB.getConnectDB();
                pst = con.prepareStatement(sql);
                pst.setString(1,NgayHoc);
                pst.setString(2,GioHoc);
                pst.setInt(3,MaTGH);
                pst.executeUpdate();
                System.out.println("Đã sửa thành công");
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
            isValidInput = true;
        }while (!isValidInput);
    }
    public void suaThongTinLopHoc() {
        String sql = "UPDATE LOPHOC SET MaMH = ?, MaGV = ?, MaTGH = ?, PhongHoc = ?,"
                + "SoBuoi = ?, NgayKhaiGiang = ?, HocPhi = ? WHERE MaLH = ?";
        Connection con = null;
        int lopHocID = 0;
        int monHocID = 0;
        int giaoVienID = 0;
        int thoiGianHocID = 0;
        String phongHoc = "";
        int soBuoi = 0;
        String ngayKhaiGiangString = "";
        int hocPhi = 0;
        try {

            System.out.println("Nhâp thông tin cần sửa: ");
            lopHocID = InputUtils.getCheckLopHocID(lopHocID);
            monHocID = InputUtils.getCheckMonHocID(monHocID);
            giaoVienID = InputUtils.getCheckGiaoVienID(giaoVienID);
            thoiGianHocID = InputUtils.getCheckThoiGianHocID(thoiGianHocID);
            phongHoc = InputUtils.getCheckPhongHoc();
            soBuoi = InputUtils.getCheckSoBuoi(soBuoi);
            ngayKhaiGiangString = InputUtils.getCheckNgayKhaiGiang();
            hocPhi = InputUtils.getCheckHocPhi(hocPhi);

            con = ConnectDB.getConnectDB();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, monHocID);
            pst.setInt(2, giaoVienID);
            pst.setInt(3, thoiGianHocID);
            pst.setString(4, phongHoc);
            pst.setInt(5, soBuoi);
            pst.setString(6, ngayKhaiGiangString);
            pst.setInt(7, hocPhi);
            pst.setInt(8, lopHocID);
            // Thực thi câu truy vấn
            pst.executeUpdate();
            System.out.println("Đã sửa thành công");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Vui lòng nhập lại");
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

    public void suaThongTinDangKy() {
        System.out.println("\n===========Sửa thông tin đăng ký==========");
        boolean isValidInput = false;
        int MaDK = 0;
        do {
            try {
                System.out.println("Nhập MaDK cần sửa: ");
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            isValidInput = true;
        } while (!isValidInput);
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


        try {
            con = ConnectDB.getConnectDB();
            String sql = "UPDATE DANGKYTRE SET MaPH=?, MaTre=?, MaLH=?, NgayDangKy=?, TrangThai=? WHERE MaDK=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, MaPH);
            pst.setInt(2, MaTre);
            pst.setInt(3, MaLH);
            pst.setDate(4, Date.valueOf(ngayDangKyStr));
            pst.setString(5, trangThaiInput);
            pst.setInt(6, MaDK);
            pst.executeUpdate();
            System.out.println("Thông tin đăng ký đã sửa thành công");
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

}


