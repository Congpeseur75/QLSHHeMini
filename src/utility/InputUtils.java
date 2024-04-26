package utility;

import connectDB.ConnectDB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static java.sql.Date.valueOf;


public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}(?:,\\d{10})*$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
    static Scanner sc = new Scanner(System.in);
    static ConnectDB conn = new ConnectDB();
    static Connection con = conn.getConnectDB();
    static PreparedStatement pst = null;
    //utility Huân.
    public static boolean isValidHoTenPH(String hoTenPH) {
        if (hoTenPH == null || hoTenPH.isEmpty()) {
            return false;
        }

        String regex = "^[\\p{L}\\s_\\\\/]+$";

        return Pattern.matches(regex, hoTenPH) && !hoTenPH.matches(".*\\d.*") && !hoTenPH.trim().isEmpty();
    }
    public static boolean isValidDiaChi(String diaChi) {
        if (diaChi == null || diaChi.isEmpty()) {
            return false;
        }
        String regex = "^[\\w+\\p{L}\\s]+$";

        return Pattern.matches(regex, diaChi) && !diaChi.trim().isEmpty();
    }
    public static boolean isValidSDT(String sdt) {
        if (sdt == null || sdt.isEmpty()) {
            return false;
        }
        return sdt.matches("\\d{10}(,\\d{10})?");
    }
    public boolean isValidEmail1(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        return email.matches(regex);
    }
    public static boolean check(int maPH) throws SQLException {
        boolean ct = false;
        try {
            con = ConnectDB.getConnectDB();
            String sql = "SELECT * FROM PHUHUYNH WHERE MaPH = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, maPH);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ct = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally { // Đóng kết nối sau khi hoàn thành hoặc gặp lỗi
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println("Đống kết nối thất bại");
            }
        }

        return ct;
    }

    //utility Thảo
    public static int nhapMaTre (Scanner sc) {
        int maTre = 0;
        boolean kt = false;
        while (!kt) {
            System.out.println("Nhập vào mã của trẻ cần sửa");
            String input = sc.nextLine();
            if (input != null && !input.trim().isEmpty() && input.matches("\\d+")) {
                maTre = Integer.parseInt(input);
                if (kiemTraMa("TREEM", "MaTre", String.valueOf(maTre)) == true) {
                    kt = true;
                } else {
                    System.out.println("Không tìm thấy mã trẻ!");
                }
            } else {
                System.out.println("Mã trẻ không hợp lệ, vui lòng nhập lại");
            }
        }
        return maTre;
    }
    public static boolean kiemTraMa(String tenBang, String tenCot, String maBang) {
        boolean kiemTra = false;
        try {
            con = ConnectDB.getConnectDB();
            String sql = "SELECT * FROM " + tenBang + " WHERE " + tenCot + " = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maBang);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                kiemTra = true;
            }
            con.isClosed();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return kiemTra;
    }
    public String nhapHoTenTre (Scanner sc) {
        System.out.println("Nhập vào họ tên của trẻ");
        String hoTenTre = sc.nextLine();
        while (hoTenTre.trim().isEmpty() || !hoTenTre.matches("[\\p{L}\\s]+")) {
            System.out.println("Họ tên không hợp lệ, vui lòng nhập lại");
            hoTenTre = sc.nextLine();
        }
        return hoTenTre;
    }

    public String nhapGioiTinh (Scanner sc) {
        System.out.println("Nhập vào giới tính của trẻ");
        String gioiTinh = sc.nextLine();
        while (gioiTinh.trim().isEmpty() || (!gioiTinh.equalsIgnoreCase("Nam") && !gioiTinh.equalsIgnoreCase("Nữ"))) { // equalsIgnoreCase:

            System.out.println("Giới tính không hợp lệ, vui lòng nhập lại");
            gioiTinh = sc.nextLine();
        }
        return gioiTinh;
    }

    public int nhapMaPH(Scanner sc) {
        int maPH = 0;
        boolean pH = false;
        while (!pH) {
            System.out.println("Nhập vào mã phụ huynh");
            String input = sc.nextLine();
            if (input.matches("\\d+")) {
                maPH = Integer.parseInt(input);
                try {
                    if (kiemTraMaPHTonTai1(maPH)) {
                        pH = true;
                    } else {
                        System.out.println("Mã phụ huynh không tồn tại");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Mã phụ huynh không hợp lệ");
            }
        }
        return maPH;
    }

    public boolean kiemTraMaPHTonTai1(int monHocID) throws SQLException {
        con = ConnectDB.getConnectDB();
        String sql = "SELECT * FROM PHUHUYNH WHERE MaPH = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, monHocID);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public Date nhapNgaySinh (Scanner sc) {
        java.sql.Date ngaySinhSua = null;
        boolean ns = true;
        while (ns) {
            try {
                System.out.println("Nhập vào ngày sinh của trẻ (YYYY-MM-DD)");
                String NgaySinhSuaStr = sc.nextLine();
                ngaySinhSua = valueOf(NgaySinhSuaStr);
                LocalDate ngaySinhLocalDate = ngaySinhSua.toLocalDate(); // chuyển đối tượng java.sql.date sang

                LocalDate ngayHienTai = LocalDate.now();
                int tuoi = Period.between(ngaySinhLocalDate, ngayHienTai).getYears(); // phương thức period.between:

                if (tuoi <= 5 || tuoi >= 15) {
                    System.out.println("Tuổi của trẻ không hợp lệ, phải lớn hơn 5 và nhỏ hơn 15");
                    continue;
                }
                ns = false;
            } catch (Exception e) {
                System.out.println("Ngày tháng năm không hợp lệ, vui lòng nhập lại");
            }
        }
        return ngaySinhSua;
    }


    //utility Lực

    public  boolean isExists(String tableName, String columnName, String value) {
        boolean exists = false;

        try {
            con = ConnectDB.getConnectDB();
            String sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, value);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return exists;
    }

    public int getValidChoice(Scanner sc) {
        int ma = 0;
        boolean inputValid = false;
        System.out.println("Nhập lựa chọn: ");
        while (!inputValid) {
            try {
                ma = Integer.parseInt(sc.nextLine());
                if (ma < 0 || ma > 9) {
                    System.out.println("Lựa chọn không hợp lệ.");
                } else {
                    inputValid = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Không hợp lệ, vui lòng nhập lại.");
            }
        }
        return ma;
    }
    public boolean containsNumber(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }
    public String getValidName(Scanner sc) {
        String name = "";
        while (name.trim().isEmpty() || containsNumber(name) || !name.matches("[\\p{L}\\s]+")) {
            System.out.println("Nhập tên: ");
            name = sc.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("Không được để trống.");
            } else if (containsNumber(name)) {
                System.out.println("Không được chứa số.");
            } else if (!name.matches("[\\p{L}\\s]+")) {
                System.out.println("Không chứa ký tự đặc biệt.");
            }
        }
        return name;
    }

    public int getValidMa(Scanner sc) {
        int ma = 0;
        boolean inputValid = false;
        System.out.println("Mời nhập mã: ");
        while (!inputValid) {
            try {
                ma = Integer.parseInt(sc.nextLine());
                if (ma <= 0) {
                    System.out.println("Phải là số nguyên dương.");
                } else {
                    inputValid = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Không hợp lệ vui lòng nhập lại");
            }
        }
        return ma;
    }

    //utility Thắng

    public static boolean kiemTraMaLHTonTai1(int lopHocID) throws SQLException {
        String sql = "SELECT 1 FROM LOPHOC WHERE MaLH = ?";
        pst = con.prepareStatement(sql);
        pst.setInt(1, lopHocID);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public static boolean kiemTraMaMHTonTai(int monHocID) throws SQLException {
        String sql = "SELECT 1 FROM MONHOC WHERE MaMH = ?";
        pst = con.prepareStatement(sql);
        pst.setInt(1, monHocID);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public static boolean kiemTraMaGVTonTai(int giaoVienID) throws SQLException {
        String sql = "SELECT 1 FROM GIAOVIEN WHERE MaGV = ?";
        pst = con.prepareStatement(sql);
        pst.setInt(1, giaoVienID);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }
    public static boolean kiemTraMaTGHTonTai(int thoiGianHocID) throws SQLException {
        String sql = "SELECT 1 FROM THOIGIANHOC WHERE MaTGH = ?";
        pst = con.prepareStatement(sql);
        pst.setInt(1, thoiGianHocID);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public static int getCheckLopHocID(int lopHocID) {
        boolean validInput1 = true;
        lopHocID = 0;
        while (validInput1) {
            try {
                con = ConnectDB.getConnectDB();
                System.out.println("Nhập Mã lớp: ");
                String input = sc.nextLine();

                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Mã lớp không được để trống");
                }

                if (input.length() > 5) {
                    throw new IllegalArgumentException("Mã lớp không được dài quá 5 ký tự");
                }

                if (!input.matches("[a-zA-Z0-9]+")) {
                    throw new IllegalArgumentException("Mã lớp không được chứa ký tự đặc biệt");
                }

                lopHocID = Integer.parseInt(input);

                try {
                    if (!kiemTraMaLHTonTai1(lopHocID)) {
                        System.out.println("Mã lớp học không tồn tại. Vui lòng nhập lại.");
                        continue;
                    }
                } catch (SQLException e) { // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                validInput1 = false;
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập lại");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
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
        return lopHocID;
    }


    public static int getCheckMonHocID(int monHocID) {
        boolean validInput1 = true;
        while (validInput1) {
            try {
                con = ConnectDB.getConnectDB();
                System.out.println("Nhập Mã môn học: ");
                String input = sc.nextLine();

                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Mã môn học không được để trống");
                }

                if (input.length() > 5) {
                    throw new IllegalArgumentException("Mã môn học không được dài quá 5 ký tự");
                }

                if (!input.matches("[a-zA-Z0-9]+")) {
                    throw new IllegalArgumentException("Mã môn học không được chứa ký tự đặc biệt");
                }

                monHocID = Integer.parseInt(input);
                try {
                    if (!kiemTraMaMHTonTai(monHocID)) {
                        System.out.println("Mã môn học không tồn tại. Vui lòng nhập lại.");
                        continue;
                    }
                } catch (SQLException e) { // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                validInput1 = false;
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập lại");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
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
        return monHocID;
    }

    public static int getCheckGiaoVienID(int giaoVienID) {
        boolean validInput1 = true;
        // giaoVienID = 0;
        while (validInput1) {
            try {
                con = ConnectDB.getConnectDB();
                System.out.println("Nhập Mã giáo viên: ");
                String input = sc.nextLine();

                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Mã giáo viên không được để trống");
                }

                if (input.length() > 5) {
                    throw new IllegalArgumentException("Mã giáo viên không được dài quá 5 ký tự");
                }

                if (!input.matches("[a-zA-Z0-9]+")) {
                    throw new IllegalArgumentException("Mã giáo viên không được chứa ký tự đặc biệt");
                }

                giaoVienID = Integer.parseInt(input);

                try {
                    if (!kiemTraMaGVTonTai(giaoVienID)) {
                        System.out.println("Mã giáo viên không tồn tại. Vui lòng nhập lại.");
                        continue;
                    }
                } catch (SQLException e) { // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                validInput1 = false;
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập lại");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            finally { // Đóng kết nối sau khi hoàn thành hoặc gặp lỗi
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    System.out.println("Đống kết nối thất bại");
                }
            }

        }
        return giaoVienID;
    }

    public static int getCheckThoiGianHocID(int thoiGianHocID) {
        boolean validInput1 = true;
        thoiGianHocID = 0;
        while (validInput1) {
            try {
                con = ConnectDB.getConnectDB();
                System.out.println("Nhập Mã thời gian học: ");
                String input = sc.nextLine();

                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Mã thời gian học không được để trống");
                }


                if (input.length() > 5) {
                    throw new IllegalArgumentException("Mã thời gian học không được dài quá 5 ký tự");
                }

                if (!input.matches("[a-zA-Z0-9]+")) {
                    throw new IllegalArgumentException("Mã thời gian học không được chứa ký tự đặc biệt");
                }

                thoiGianHocID = Integer.parseInt(input);

                try {
                    if (!kiemTraMaTGHTonTai(thoiGianHocID)) {
                        System.out.println("Mã thời gian học không tồn tại. Vui lòng nhập lại.");
                        continue;
                    }
                } catch (SQLException e) { // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                validInput1 = false;
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập lại");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            finally { // Đóng kết nối sau khi hoàn thành hoặc gặp lỗi
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    System.out.println("Đống kết nối thất bại");
                }
            }

        }
        return thoiGianHocID;
    }


    public static String getCheckPhongHoc() {
        String phongHoc = "";
        boolean validInput1 = true;
        while (validInput1) {
            try {
                con = ConnectDB.getConnectDB();
                System.out.println("Nhập Phòng học: ");
                phongHoc = sc.nextLine();

                if (phongHoc.isEmpty()) {
                    throw new IllegalArgumentException("Phòng học không được để trống");
                }

                if (phongHoc.length() > 5) {
                    throw new IllegalArgumentException("Phòng học không được dài quá 5 ký tự");
                }

                if (!phongHoc.matches("[a-zA-Z0-9]+")) {
                    throw new IllegalArgumentException("Phòng học không được chứa ký tự đặc biệt");
                }


                validInput1 = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            finally { // Đóng kết nối sau khi hoàn thành hoặc gặp lỗi
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    System.out.println("Đống kết nối thất bại");
                }
            }

        }
        return phongHoc;
    }

    public static int getCheckSoBuoi(int soBuoi) {
        boolean validInput1 = true;
        while (validInput1) {
            try {
                con = ConnectDB.getConnectDB();
                System.out.println("Nhập Số buổi học: ");
                String input = sc.nextLine();

                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Số buổi học không được để trống");
                }


                if (input.length() > 5) {
                    throw new IllegalArgumentException("Số buổi học không được dài quá 5 ký tự");
                }

                if (!input.matches("[a-zA-Z0-9]+")) {
                    throw new IllegalArgumentException("Số buổi học không được chứa ký tự đặc biệt");
                }

                soBuoi = Integer.parseInt(input);
                validInput1 = false;
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập lại");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            finally { // Đóng kết nối sau khi hoàn thành hoặc gặp lỗi
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    System.out.println("Đống kết nối thất bại");
                }
            }

        }
        return soBuoi;
    }

    public static int getCheckHocPhi(int hocPhi) {
        boolean validInput1 = true;
        while (validInput1) {
            try {
                con = ConnectDB.getConnectDB();
                System.out.println("Nhập Học phí: ");
                String input = sc.nextLine();

                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Học phí không được để trống");
                }

                if (input.length() > 5) {
                    throw new IllegalArgumentException("Học phí không được dài quá 5 ký tự");
                }

                if (!input.matches("[a-zA-Z0-9]+")) {
                    throw new IllegalArgumentException("Học phí không được chứa ký tự đặc biệt");
                }

                hocPhi = Integer.parseInt(input);
                validInput1 = false;
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập lại");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            finally { // Đóng kết nối sau khi hoàn thành hoặc gặp lỗi
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException ex) {
                    System.out.println("Đống kết nối thất bại");
                }
            }

        }
        return hocPhi;
    }

    public static String getCheckNgayKhaiGiang() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        while (true) {
            try {
                con = ConnectDB.getConnectDB();
                System.out.println("Nhập ngày Khai giảng (định dạng yyyy-MM-dd): ");
                String ngayKhaiGiangString = sc.nextLine();


                if (!isValidDate(ngayKhaiGiangString)) {
                    System.out.println("Ngày không hợp lệ. Vui lòng nhập lại đúng định dạng.");
                    continue;
                }


                return ngayKhaiGiangString;
            } catch (Exception e) {
                System.out.println("Ngày không hợp lệ. Vui lòng nhập lại đúng định dạng.");
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

    public static boolean isValidDate(String ngayKhaiGiangString) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formatter.setLenient(false);


            java.util.Date ngayKhaiGiang = formatter.parse(ngayKhaiGiangString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    //utility Công
    public static boolean validateMa(String ma){
        if (ma.trim().isEmpty()) {
            System.out.println("không được để trống mã, vui lòng nhập lại: ");
            return false;
        }
        if (!ma.trim().matches("[0-9]+")) {
            System.out.println("không được nhập chữ hoặc ký tự đặt biệt, vui lòng nhập lại: ");
            return false;
        }
        return true;
    }
    public static boolean validateDate(String dateStr) {
        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Ngày đăng ký không hợp lệ. Vui lòng nhập lại.");
            return false;
        }
    }
    public static boolean validateTrangThai(String trangThai) {
        if (trangThai.equalsIgnoreCase("Đã duyệt") || trangThai.equalsIgnoreCase("Chưa duyệt")) {
            return true;
        } else {
            System.out.println("Trạng thái đăng ký không hợp lệ. Vui lòng nhập lại.");
            return false;
        }
    }

    public static boolean kiemTraMaDKTonTai(int MaDK) throws SQLException {
        con = ConnectDB.getConnectDB();
        String sql = "SELECT 1 FROM DANGKYTRE WHERE MaDK = ?";
        pst = con.prepareStatement(sql);
        pst.setInt(1, MaDK);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public static boolean kiemTraMaPHTonTai(int MaPH) throws SQLException {
        con = ConnectDB.getConnectDB();
        String sql = "SELECT 1 FROM PHUHUYNH WHERE MaPH = ?";
        pst = con.prepareStatement(sql);
        pst.setInt(1, MaPH);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public static boolean kiemTraMaTreTonTai(int MaTre) throws SQLException {
        con = ConnectDB.getConnectDB();
        String sql = "SELECT 1 FROM TREEM WHERE MaTre = ?";
        pst = con.prepareStatement(sql);
        pst.setInt(1, MaTre);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public static boolean kiemTraMaLHTonTai(int MaLH) throws SQLException {
        con = ConnectDB.getConnectDB();
        String sql = "SELECT 1 FROM LOPHOC WHERE MaLH = ?";
        pst = con.prepareStatement(sql);
        pst.setInt(1, MaLH);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public String getValidNgayHoc(Scanner sc) {
        String NgayHoc = "";
        boolean validInput = false;

        while (!validInput) {
            NgayHoc = sc.nextLine().toLowerCase();
            if (NgayHoc.trim().isEmpty() || NgayHoc.matches("^[\\s\\p{Punct}]+$")) {
                System.out.println("Không được để trống hoặc chỉ chứa khoảng trắng và ký tự đặc biệt.");
            } else {
                  switch (NgayHoc) {
                    case "thứ hai":
                    case "thứ ba":
                    case "thứ tư":
                    case "thứ năm":
                    case "thứ sáu":
                    case "thứ bảy":
                    case "chủ nhật":
                    case "cuối tuần":
                        validInput = true;
                        break;
                    default:
                        System.out.println("Ngày không hợp lệ. Vui lòng nhập lại ngày trong tuần hoặc cuối tuần.");
                        break;
                }
            }
        }
        return NgayHoc;
    }
    public String getValidGioHoc(Scanner sc) {
        String GioHoc = "";
        boolean validInput = false;

        while (!validInput) {
            GioHoc = sc.nextLine();
            if (GioHoc.trim().isEmpty() || GioHoc.matches("^[\\s\\p{Punct}]+$")) {
                System.out.println("Không được để trống hoặc chỉ chứa khoảng trắng và ký tự đặc biệt. Vui lòng nhập lại");
            } else {
                if (GioHoc.matches("^\\d{1,2}h(-\\d{1,2}[hp])?$")) {
                    validInput = true;
                } else {
                    System.out.println("Giờ không hợp lệ. Vui lòng nhập lại theo định dạng ví dụ: 8h-10 hoặc 9h-00p.");
                }
            }
        }
        return GioHoc;
    }
    public static boolean kiemTraMaTGH(int MaTGH) throws SQLException {
        con = ConnectDB.getConnectDB();
        String sql = "SELECT 1 FROM THOIGIANHOC WHERE MaTGH = ?";
        pst = con.prepareStatement(sql);
        pst.setInt(1, MaTGH);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    //utility Hà Bá
    public static String getStringInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Dữ liệu không được rỗng.");
                }
                return checkNullInput(input, "Dữ liệu không được rỗng.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static <T> T checkNullInput(T input, String errorMessage) {
        if (input == null) {
            throw new IllegalArgumentException(errorMessage);
        }
        return input;
    }

    public static int getIntInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên.");
            }
        }
    }


    public static LocalDate getDateInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine();
                String[] parts = input.split("/");


                if (parts.length != 3) {
                    throw new DateTimeException("Định dạng ngày không hợp lệ. Vui lòng nhập lại.");
                }

                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);

                if (year < 1 || year > 9999 || month < 1 || month > 12 || day < 1 || day > LocalDate.of(year, month, 1).lengthOfMonth()) {
                    throw new DateTimeException("Ngày tháng không hợp lệ. Vui lòng nhập lại.");
                }

                return LocalDate.parse(input, DATE_FORMATTER);

            } catch (DateTimeException | NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static boolean getBooleanInput(String message) {
        while (true) {
            System.out.print(message + " (C/K): ");
            String choice = scanner.nextLine().toUpperCase();
            if (choice.equals("C")) {
                return true;
            } else if (choice.equals("K")) {
                return false;
            } else {
                System.out.println("Vui lòng chỉ chọn 'C' hoặc 'K'.");
            }
        }
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidInput(String input) {
        // Kiểm tra xem chuỗi nhập vào có chứa ký tự đặc biệt hay không
        return !Pattern.compile("[^a-zA-Z0-9]").matcher(input).find();
    }

    public static String getInputWithoutSpecialCharacters(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Dữ liệu không được rỗng.");
                }
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static int getCheckMenu9() {
        int choice = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice < 0 || choice > 9) {
                    throw new IllegalArgumentException("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                }
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return choice;
    }
    public static int getCheckMenu7() {
        int choice = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice < 0 || choice > 7) {
                    throw new IllegalArgumentException("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                }
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return choice;
    }
    public static int getCheckMenu6() {
        int choice = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice < 0 || choice > 6) {
                    throw new IllegalArgumentException("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                }
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return choice;
    }
    public static int getCheckMenu4() {
        int choice = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice < 0 || choice > 4) {
                    throw new IllegalArgumentException("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                }
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return choice;
    }
    public static int getCheckMenu5() {
        int choice = -1;
        boolean validInput = false;
        while (!validInput) {
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice < 0 || choice > 5) {
                    throw new IllegalArgumentException("Lựa chọn không hợp lệ. Vui lòng nhập lại");
                }
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ. Vui lòng nhập lại");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return choice;
    }
}
