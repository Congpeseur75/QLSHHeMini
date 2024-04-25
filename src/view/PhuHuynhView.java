
package view;

import controller.PhuHuynhController;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PhuHuynhView {
    private PhuHuynhController phuHuynhController;
    private Scanner scanner;

    public PhuHuynhView(PhuHuynhController phuHuynhController) {
        this.phuHuynhController = phuHuynhController;
        this.scanner = new Scanner(System.in);
    }

    public List<String> layDanhSachLopHoc() throws SQLException {
        List<String> danhSachLopHoc = phuHuynhController.layDanhSachLopHoc();
        if (danhSachLopHoc.isEmpty()) {
            System.out.println("Không có thông tin lớp học để hiển thị.");
        } else {

            System.out.println(String.format("%-5s %-15s %-20s %-15s %-20s %-10s %-10s %-15s %-20s",
                    "MaLH", "TenMH", "HoTenGV", "NgayHoc", "GioHoc", "PhongHoc", "SoBuoi", "NgayKhaiGiang", "HocPhi"));

            for (String lopHoc : danhSachLopHoc) {
                System.out.println(lopHoc);
            }
        }
        return danhSachLopHoc;
    }

    public List<String> timKiemLopHocTheoTenMH() throws SQLException {
        System.out.println("Nhập tên môn học để tìm kiếm:");
        String tenMH = scanner.nextLine();
        List<String> ketQuaTimKiem = phuHuynhController.timKiemLopHocTheoTenMH(tenMH);
        if (ketQuaTimKiem.isEmpty()) {
            System.out.println("Không tìm thấy lớp học nào với tên môn học: " + tenMH);
        } else {
            System.out.println("Kết quả tìm kiếm cho '" + tenMH + "':");
            hienThiBangDanhSachLopHoc(ketQuaTimKiem);
        }
        return ketQuaTimKiem;
    }

    private void hienThiBangDanhSachLopHoc(List<String> danhSachLopHoc) {
        System.out.println(String.format("%-5s %-15s %-40s %-20s %-30s %-10s %-10s %-20s %20s",
                "MaLH", "TenMH", "HoTenGV", "NgayHoc", "GioHoc", "PhongHoc", "SoBuoi", "NgayKhaiGiang", "HocPhi"));

        for (String lopHoc : danhSachLopHoc) {
            System.out.println(lopHoc);
        }
    }

}
