package data;

public class ThoiGianHoc {
    private String NgayHoc;
    private String GioHoc;

    public ThoiGianHoc() {
    }

    public ThoiGianHoc(String ngayHoc, String gioHoc) {
        NgayHoc = ngayHoc;
        GioHoc = gioHoc;
    }

    public String getNgayHoc() {
        return NgayHoc;
    }

    public void setNgayHoc(String ngayHoc) {
        NgayHoc = ngayHoc;
    }

    public String getGioHoc() {
        return GioHoc;
    }

    public void setGioHoc(String gioHoc) {
        GioHoc = gioHoc;
    }
}
