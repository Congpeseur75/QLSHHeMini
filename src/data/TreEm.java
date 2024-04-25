package data;

import java.time.LocalDate;

public class TreEm {
    private int maTre;
    private String hoTenTre;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private int maPH;

    public TreEm(String hoTenTre, LocalDate ngaySinh, String gioiTinh, int maPH) {
        this.hoTenTre = hoTenTre;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.maPH = maPH;
    }


    public int getMaTre() {
        return maTre;
    }

    public void setMaTre(int maTre) {
        this.maTre = maTre;
    }

    public String getHoTenTre() {
        return hoTenTre;
    }

    public void setHoTenTre(String hoTenTre) {
        this.hoTenTre = hoTenTre;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getMaPH() {
        return maPH;
    }

    public void setMaPH(int maPH) {
        this.maPH = maPH;
    }
}
