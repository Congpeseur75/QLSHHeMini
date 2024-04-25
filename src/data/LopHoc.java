package data;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LopHoc {
    private int maLH;
    private String tenMH;
    private String hoTenGV;
    private String ngayHoc;
    private String gioHoc;
    private String phongHoc;
    private int soBuoi;
    private LocalDate ngayKhaiGiang;
    private BigDecimal hocPhi;

    // Constructor, getters, and setters
    public LopHoc(int maLH, String tenMH, String hoTenGV, String ngayHoc, String gioHoc, String phongHoc, int soBuoi, LocalDate ngayKhaiGiang, BigDecimal hocPhi) {
        this.maLH = maLH;
        this.tenMH = tenMH;
        this.hoTenGV = hoTenGV;
        this.ngayHoc = ngayHoc;
        this.gioHoc = gioHoc;
        this.phongHoc = phongHoc;
        this.soBuoi = soBuoi;
        this.ngayKhaiGiang = ngayKhaiGiang;
        this.hocPhi = hocPhi;
    }

    //Getters and setter

    public int getMaLH() {
        return maLH;
    }

    public void setMaLH(int maLH) {
        this.maLH = maLH;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public String getHoTenGV() {
        return hoTenGV;
    }

    public void setHoTenGV(String hoTenGV) {
        this.hoTenGV = hoTenGV;
    }

    public String getNgayHoc() {
        return ngayHoc;
    }

    public void setNgayHoc(String ngayHoc) {
        this.ngayHoc = ngayHoc;
    }

    public String getGioHoc() {
        return gioHoc;
    }

    public void setGioHoc(String gioHoc) {
        this.gioHoc = gioHoc;
    }

    public String getPhongHoc() {
        return phongHoc;
    }

    public void setPhongHoc(String phongHoc) {
        this.phongHoc = phongHoc;
    }

    public int getSoBuoi() {
        return soBuoi;
    }

    public void setSoBuoi(int soBuoi) {
        this.soBuoi = soBuoi;
    }

    public LocalDate getNgayKhaiGiang() {
        return ngayKhaiGiang;
    }

    public void setNgayKhaiGiang(LocalDate ngayKhaiGiang) {
        this.ngayKhaiGiang = ngayKhaiGiang;
    }

    public BigDecimal getHocPhi() {
        return hocPhi;
    }

    public void setHocPhi(BigDecimal hocPhi) {
        this.hocPhi = hocPhi;
    }


    // Other methods

    @Override
    public String toString() {
        return String.format("%-5d %-15s %-20s %-15s %-20s %-10s %-10d %-15s %-20.3f",
                maLH, tenMH, hoTenGV, ngayHoc, gioHoc, phongHoc, soBuoi, ngayKhaiGiang, hocPhi);
    }
}
