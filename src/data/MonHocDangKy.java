package data;

public class MonHocDangKy {
    private String tenMonHoc;
    private int soPhuHuynh;

    public MonHocDangKy(String tenMonHoc, int soPhuHuynh) {
        this.tenMonHoc = tenMonHoc;
        this.soPhuHuynh = soPhuHuynh;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public int getSoPhuHuynh() {
        return soPhuHuynh;
    }
}
