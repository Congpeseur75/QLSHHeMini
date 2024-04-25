package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.LopHocDAO;
import data.LopHoc;

public class PhuHuynhController {
    private LopHocDAO lopHocDAO;

    public PhuHuynhController(LopHocDAO lopHocDAO) throws SQLException {
        this.lopHocDAO = lopHocDAO;
    }

    public List<String> layDanhSachLopHoc() throws SQLException {
        List<LopHoc> danhSachLopHoc = lopHocDAO.xemThongTinLopHoc();
        List<String> thongTinLopHoc = new ArrayList<>();
        for (LopHoc lopHoc : danhSachLopHoc) {
            String thongTin = lopHoc.toString();
            thongTinLopHoc.add(thongTin);
        }
        return thongTinLopHoc;
    }


    public List<String> timKiemLopHocTheoTenMH(String tenMH) throws SQLException {
        List<LopHoc> ketQuaTimKiem = lopHocDAO.timKiemLopHocTheoTenMH(tenMH);

        List<String> thongTinLopHoc = new ArrayList<>();

        for (LopHoc lopHoc : ketQuaTimKiem) {
            String thongTin = lopHoc.toString();
            thongTinLopHoc.add(thongTin);
        }

        return thongTinLopHoc;
    }

}