package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import dao.DangKyDAO;
import dao.FindDAO;
import data.MonHocDangKy;
import data.PhuHuynh;
import data.TreEm;

public class DangKyController {
    private DangKyDAO dangKyDAO;

    public DangKyController() {

    }

    public DangKyController(Connection conn) {
        this.dangKyDAO = new DangKyDAO(conn);
    }


    public boolean dangKy(String hoTenPH, String diaChi, String soDT, String email,
                          String hoTenTre, LocalDate ngaySinh, String gioiTinh, int maLH, LocalDate ngayDangKy) {
        try {
            int maPH = dangKyDAO.themPhuHuynh(new PhuHuynh(hoTenPH, diaChi, soDT, email));


            if (maPH < 0) {
                return false;
            }


            TreEm treEm = new TreEm(hoTenTre, ngaySinh, gioiTinh, maPH);
            int maTre = dangKyDAO.themTreEm(treEm);


            if (maTre < 0) {
                return false;
            }


            return dangKyDAO.themDangKyLopHoc(maPH, maTre, maLH, ngayDangKy);
        } catch (SQLException e) {
            System.err.println("lỗi: " + e.getMessage());
            return false;
        }
    }


    public boolean kiemTraDieuKienNhapHoc(LocalDate ngaySinh) {
        LocalDate currentDate = LocalDate.now();
        LocalDate fiveYearsAgo = currentDate.minusYears(5);
        LocalDate fifteenYearsAgo = currentDate.minusYears(15);

        return ngaySinh.isBefore(fiveYearsAgo) && ngaySinh.isAfter(fifteenYearsAgo);
    }

    public void hienThiMonHocNhieuNguoiDangKyNhat() {
        try {
            List<MonHocDangKy> monHocDangKyList = FindDAO.getMonHocNhieuNguoiDangKyNhat();
            if (monHocDangKyList.isEmpty()) {
                System.out.println("Không có dữ liệu về môn học được nhiều người đăng ký nhất.");
            } else {
                System.out.println("Danh sách môn học được nhiều người đăng ký nhất:");
                System.out.println("Môn học ,Số phụ huynh đăng ký");
                for (MonHocDangKy monHocDangKy : monHocDangKyList) {
                    System.out.println(monHocDangKy.getTenMonHoc() + monHocDangKy.getSoPhuHuynh());
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi truy vấn cơ sở dữ liệu: " + e.getMessage());
        }
    }
    public boolean kiemTraTonTaiSoDT(String soDT) throws SQLException {
        return dangKyDAO.kiemTraTonTaiSoDT(soDT);
    }

    public boolean kiemTraTonTaiEmail(String email) throws SQLException {
        return dangKyDAO.kiemTraTonTaiEmail(email);
    }

}
