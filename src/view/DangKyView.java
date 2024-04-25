package view;

import controller.DangKyController;
import utility.InputUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class DangKyView {
    private DangKyController dangKyController;

    public DangKyView(Connection conn) {
        this.dangKyController = new DangKyController(conn);
    }

    public DangKyView() {

    }

    public void nhapThongTinDangKy() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Mời bạn nhập vào các thông tin cần để đăng ký!");

        // Nhận thông tin phụ huynh
//        String hoTenPH = InputUtils.getStringInput("Nhập họ tên phụ huynh: ");
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
        String soDT;
        String email;
        boolean nhapLai;

        // Kiểm tra và yêu cầu nhập lại nếu số điện thoại đã tồn tại

        do {
            soDT = InputUtils.getInputWithoutSpecialCharacters("Nhập số điện thoại phụ huynh: ");
            nhapLai = false;

            if (dangKyController.kiemTraTonTaiSoDT(soDT)) {
                System.out.println("Số điện thoại đã tồn tại trong hệ thống. Vui lòng nhập lại.");
                nhapLai = true; // Nếu số điện thoại đã tồn tại, thiết lập cờ nhapLai là true để yêu cầu nhập lại
            } else if (!InputUtils.isValidPhoneNumber(soDT)) {
                System.out.println("Số điện thoại không hợp lệ. Vui lòng nhập lại.");
                nhapLai = true; // Nếu số điện thoại không hợp lệ, thiết lập cờ nhapLai là true để yêu cầu nhập lại
            }
        } while (nhapLai);

        // Kiểm tra và yêu cầu nhập lại nếu email đã tồn tại
        do {
            email = InputUtils.getInputWithoutSpecialCharacters("Nhập email phụ huynh: ");
            nhapLai = false;
            if (dangKyController.kiemTraTonTaiEmail(email)) {
                System.out.println("Email đã tồn tại trong hệ thống. Vui lòng nhập lại.");
                nhapLai = true;
            } else if (!InputUtils.isValidEmail(email)) {
                System.out.println("Email không hợp lệ. Vui lòng nhập lại.");
                nhapLai = true;
            }
        } while (nhapLai);

        // Nhận thông tin trẻ em
        String tenTre = null;
        do {
            System.out.println("Họ và tên trẻ:");
            tenTre = sc.nextLine();
            isValid = InputUtils.isValidHoTenPH(tenTre);

            if (!isValid) {
                System.out.println("Họ tên trẻ không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!isValid);

        LocalDate ngaySinh = InputUtils.getDateInput("Nhập ngày sinh trẻ em (dd/mm/yyyy): ");
        // Kiểm tra điều kiện nhập học của trẻ
        boolean coDuDieuKienNhapHoc = dangKyController.kiemTraDieuKienNhapHoc(ngaySinh);
        if (!coDuDieuKienNhapHoc) {
            System.out.println("Trẻ không đủ điều kiện nhập học (tuổi từ 5 đến 15). Vui lòng kiểm tra lại ngày sinh của trẻ.");
            return; // Thoát khỏi phương thức nếu không đủ điều kiện
        }

        //String gioiTinh = InputUtils.getStringInput("Nhập giới tính trẻ em: ");
        String gioiTinh = "";
        //boolean nhapLai;

        do {
            // Hiển thị danh sách tùy chọn giới tính
            System.out.println("Chọn giới tính trẻ em:");
            System.out.println("1. Nam");
            System.out.println("2. Nữ");
            System.out.println("3. Khác");

            // Nhập lựa chọn từ người dùng
            int luaChon = InputUtils.getIntInput("Nhập lựa chọn của bạn: ");
            nhapLai = false;

            // Xử lý lựa chọn của người dùng
            switch (luaChon) {
                case 1:
                    gioiTinh = "Nam";
                    break;
                case 2:
                    gioiTinh = "Nữ";
                    break;
                case 3:
                    gioiTinh = "Khác";
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 3.");
                    nhapLai = true;
                    break;
            }
        } while (nhapLai);


        // Nhận thông tin đăng ký lớp học
        int maLH = InputUtils.getIntInput("Nhập mã lớp học mà trẻ đăng ký: ");
        LocalDate ngayDangKy = LocalDate.now();

        // Gọi controller để xử lý đăng ký
        boolean isSuccess = dangKyController.dangKy(hoTenPH, diaChi, soDT, email, tenTre, ngaySinh, gioiTinh, maLH, ngayDangKy);
        if (isSuccess) {
            System.out.println("Đăng ký thành công!");
        } else {
            System.out.println("Đăng ký không thành công. Vui lòng thử lại.");
        }

        // Hỏi người dùng có muốn nhập thêm đăng ký khác không
        boolean tiepTuc = InputUtils.getBooleanInput("Bạn có muốn nhập đăng ký khác không? (C/K)");
        while (tiepTuc) {
            nhapThongTinDangKy(); // Gọi lại phương thức này để nhập thông tin đăng ký mới
            tiepTuc = InputUtils.getBooleanInput("Bạn có muốn nhập đăng ký khác không? (C/K)");
        }
    }

    public void hienThiMonHocNhieuNguoiDangKyNhat() {
        DangKyController dangKyController = new DangKyController();
        dangKyController.hienThiMonHocNhieuNguoiDangKyNhat();
    }
}
