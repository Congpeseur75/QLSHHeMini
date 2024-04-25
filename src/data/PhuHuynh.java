package data;

public class PhuHuynh {
    private int maPH;
    private String hoTenPH;
    private String diaChi;
    private String soDT;
    private String email;

    public PhuHuynh(String hoTenPH, String diaChi, String soDT, String email) {
        this.hoTenPH = hoTenPH;
        this.diaChi = diaChi;
        this.soDT = soDT;
        this.email = email;
    }

    // Getters and Setters
    public int getMaPH() {
        return maPH;
    }

    public String getHoTenPH() {
        return hoTenPH;
    }

    public void setHoTenPH(String hoTenPH) {
        this.hoTenPH = hoTenPH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
