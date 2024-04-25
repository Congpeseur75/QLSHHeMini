package view;

import java.util.Scanner;

import dao.AddDAO;
import dao.DeleteDAO;
import dao.EditDAO;
import dao.FindDAO;
import dao.ShowDAO;
import utility.InputUtils;


public class BGVView {

    ShowDAO showDAO = new ShowDAO();
    EditDAO editDAO = new EditDAO();
    AddDAO addDAO = new AddDAO();
    ShowDAO showDao = new ShowDAO();
    InputUtils check = new InputUtils();
    DeleteDAO deleteDAO = new DeleteDAO();
    FindDAO findDAO = new FindDAO();
    Scanner sc = new Scanner(System.in);
    MainView mainView = new MainView();


    public void view() {
        int choice = 0;
        do {
            System.out.println("");
            System.out.println("===================BAN GIAO VỤ===================");
            System.out.println("|	1. Thông tin lớp học                         |");
            System.out.println("|	2. Thông tin phụ huynh                       |");
            System.out.println("|	3. Thông tin trẻ em                          |");
            System.out.println("|	4. Thông tin giáo viên                       |");
            System.out.println("|	5. Thông tin môn học                         |");
            System.out.println("|   6. Thông tin thời gian học                   |");
            System.out.println("|	7. Thông tin đăng ký trẻ                     |");
            System.out.println("|	8. Thống kê                                  |");
            System.out.println("|	9. Quay lại                                  |");
            System.out.println("|	0. Thoát chương trình                        |");
            System.out.println("=================================================");
            try {
                choice = check.getValidChoice(sc);
                switch (choice) {
                    case (1):
                        lopHocView();
                        break;
                    case (2):
                        phuHuynhView();
                        break;
                    case (3):
                        treEmView();
                        break;
                    case (4):
                        giaoVienView();
                        break;
                    case (5):
                        monHocView();
                        break;
                    case (6):
                        thoiGianHocview();
                        break;
                    case (7):
                        dangKyTreView();
                        break;
                    case (8):
                        findView();
                        break;
                    case (9):
                        mainView.showMenu();
                        break;
                    case (0):
                        System.out.println("Cảm ơn bạn đã sử dụng hệ thống.");
                        System.exit(0);
                    default:
                        System.out.println("Nhập sai");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Khong hop le vui long nhap lai 1");
                sc.next();
            }
        } while (choice != 9);
    }

    public void treEmView() {
        boolean chuongTrinh = true;
        while (chuongTrinh) {
            System.out.println("\n==========MENU TRẺ EM==========");
            System.out.println("|  1. Thêm thông tin trẻ em   |");
            System.out.println("|  2. Sửa thông tin trẻ em    |");
            System.out.println("|  3. Xem thông tin trẻ em    |");
            System.out.println("|  4. Quay lại                | ");
            System.out.println("|  5. Thoát chương trình      |");
            System.out.println("===============================");
            System.out.println("chọn chức năng: ");
            int luaChon = check.nhapLuaChon(sc);
            switch (luaChon) {
                case 1:
                    addDAO.themTreEm(sc);
                    break;
                case 2:
                    showDAO.hienThiTreEm();
                    editDAO.suaTreEm(sc);
                    break;
                case 3:
                    showDAO.hienThiTreEm();
                    break;
                case 4:
                    view();
                    break;
                case 5:
                    System.out.println("Đã thoát chương trình");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ, chọn lại");
                    break;
            }
        }
    }

    public void phuHuynhView() {
        boolean ct = true;
        while (ct) {
            System.out.println(" \n===============Menu phụ huynh==================");
            System.out.println("| 1. Thêm phụ huynh                            |");
            System.out.println("| 2. Hiện thị danh sách phụ huynh              |");
            System.out.println("| 3. Tìm kiếm theo tên phụ huynh               |");
            System.out.println("| 4. Sửa thông tin phụ huynh theo mã phụ huynh |");
            System.out.println("| 5. Quay lại                                  |");
            System.out.println("| 0. Thoát chương trình                        |");
            System.out.println("================================================");
            int a = 0;
            boolean ct1 = true;
            while (ct1) {
                try {
                    System.out.println("nhập vào lựa chọn");
                    a = Integer.parseInt(sc.nextLine());
                    if (a < 0 || a > 6) {
                        System.out.println("Lựa chọn chưa được thiết lập vui lòng chọn lại ");
                    } else {
                        ct1 = false;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Lựa chọn không hợp lệ vui lòng nhập lại");
                }
            }
            switch (a) {
                case 1:
                    addDAO.themPhuHuynh(sc);
                    break;
                case 2:
                    showDAO.hienThiPhuHuynh();
                    break;
                case 3:
                    findDAO.timKiemPhuHuynh(sc);
                    break;
                case 4:
                    EditDAO.suaPhuHuynh();
                    break;
                case 5:
                    view();
                    break;
                case 0:
                    ct = false;
                    break;
                default:
                    break;

            }

        }
    }

    public void monHocView() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("");
            System.out.println("\n===================MÔN HỌC=====================");
            System.out.println("|	1. Xem thông tin                           |");
            System.out.println("|	2. Sửa thông tin                           |");
            System.out.println("|	3. Thêm thông tin                          |");
            System.out.println("|	4. Quay lại                                |");
            System.out.println("|	5. Thoát chương trình                      |");
            System.out.println("================================================");
            try {
                choice = check.getValidChoice(sc);
                switch (choice) {
                    case (1):
                        showDAO.hienThiMonHoc();
                        break;
                    case (2):
                        showDAO.hienThiMonHoc();
                        editDAO.suaMonHoc();
                        break;
                    case (3):
                        addDAO.themMonHoc();
                        break;
                    case (4):
                        view();
                        break;
                    case (5):
                        System.out.println("Cảm ơn bạn đã sử dụng hệ thống.");
                        System.exit(0);
                    default:
                        System.out.println("Nhập sai");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Khong hop le vui long nhap lai 2");
                sc.next();
            }
        } while (choice != 6);
    }

    public void giaoVienView() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("");
            System.out.println("\n====================GIÁO VIÊN===================");
            System.out.println("|	1. Xem thông tin                           |");
            System.out.println("|	2. Sửa thông tin                           |");
            System.out.println("|	3. Thêm thông tin                          |");
            System.out.println("|	4. Quay lại                                |");
            System.out.println("|	5. Thoát chương trình                      |");
            System.out.println("================================================");
            try {
                choice = check.getValidChoice(sc);
                switch (choice) {
                    case (1):
                        showDAO.hienThiGiaoVien();
                        break;
                    case (2):
                        showDAO.hienThiGiaoVien();
                        editDAO.editGiaoVien();
                        break;
                    case (3):
                        addDAO.themGV();
                        break;
                    case (4):
                        view();
                        break;
                    case (5):
                        System.out.println("Cảm ơn bạn đã sử dụng hệ thống.");
                        System.exit(0);
                    default:
                        System.out.println("Nhập sai");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Khong hop le vui long nhap lai 3");
                sc.next();
            }
        } while (choice != 6);
    }

    public void thoiGianHocview() {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("\n===============MENU TGH===============");
                System.out.println("|       1.Thêm thời gian học           |");
                System.out.println("|       2.Hiện thị thông tin TGH       |");
                System.out.println("|       3.Sửa thời gian học            |");
                System.out.println("|       4.Quay lại                     |");
                System.out.println("========================================");
                System.out.println("Chọn chức năng: ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        addDAO.themThoiGianHoc();
                        break;
                    case 2:
                        showDAO.hienThiThoiGianHoc();
                        break;
                    case 3:
                        editDAO.suaThoiGianHoc();
                        break;
                    case 4:
                        view();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println("lựa chọn không hợp lệ vui lòng chọn lại");
                }
            } catch (NumberFormatException e) {
                System.out.println("lỗi " + e);
            } catch (Exception e) {
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            }
        }
    }

    public void lopHocView() {
        int choice;
        do {
            System.out.println("\n==========MENU LỚP HỌC==========");
            System.out.println("|  1. Hiển thị thông tin Lớp    |");
            System.out.println("|  2. Thêm thông tin Lớp        |");
            System.out.println("|  3. Sửa thông tin Lớp         |");
            System.out.println("|  4. Quay lại                  |");
            System.out.println("|  0. Thoát chương trình        |");
            System.out.println("=================================");
            System.out.println("Nhập lựa chọn của bạn: ");
            choice = InputUtils.getCheckMenu();
            try {
                switch (choice) {
                    case 1:
                        showDAO.hienThiThongTinLop();
                        break;
                    case 2:
                        addDAO.themThongTinLop();
                        break;
                    case 3:
                        editDAO.suaThongTinLopHoc();
                        break;
                    case 4:
                        view();
                        break;
                    case 0:
                        System.out.println("Kết thúc chương trình.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại!!!");
                        System.out.println();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (choice != 0);
    }

    public void dangKyTreView() {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println("\n============MENU ĐĂNG KÝ===============");
                System.out.println("|       1.Thêm thông tin đăng ký       |");
                System.out.println("|       2.Hiện thị thông tin đăng ký   |");
                System.out.println("|       3.Sửa thông tin đăng ký        |");
                System.out.println("|       4.Xóa thông tin đăng ký        |");
                System.out.println("|       5.Tìm kiếm thông tin đăng ký   |");
                System.out.println("|       6.Quay lại                     |");
                System.out.println("========================================");
                System.out.println("Chọn chức năng: ");
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        addDAO.themThongTinDangKy();
                        break;
                    case 2:
                        showDAO.hienThiToanBoThongTinDangKy();
                        break;
                    case 3:
                        showDAO.hienThiToanBoThongTinDangKy();
                        editDAO.suaThongTinDangKy();
                        break;
                    case 4:
                        deleteDAO.xoaThongTinDangKyDatabase();
                        break;
                    case 5:
                        FindDAO.timKiemThongTinDangKyDatabase();
                        break;
                    case 6:
                        view();
                        break;
                    case 0:
                        exit = true;
                        break;
                    default:
                        System.out.println("lựa chọn không hợp lệ vui lòng chọn lại");
                }
            } catch (NumberFormatException e) {
                System.out.println("lỗi " + e);
            } catch (Exception e) {
                System.out.println("Đã xảy ra lỗi: " + e.getMessage());
            }
        }
    }

    public void findView() {
        FindDAO findDAO = new FindDAO();
        int choice = 0;
        do {
            System.out.println("\n===========================================TÌM KIẾM==============================================");
            System.out.println("| 1. Liệt kê môn học được nhiều PH đăng ký nhất trong khoảng thời gian 1/2024 ~ 3/2024          |");
            System.out.println("| 2. Liệt kê những môn học chưa được PH nào đăng kí trong tháng 3/2024                          |");
            System.out.println("| 3. Liệt kê top 3 PH đăng kí nhiều môn học nhất trong năm 2023                                 |");
            System.out.println("| 4. Liệt kê những giáo viên dạy nhiều lớp học nhất trong khoảng thời gian 1/2024 ~ 2/2024      |");
            System.out.println("| 5. Liệt kê lớp học có số lượng đăng ký học thấp nhất từ tháng 12/2023 đến hiện tại            |");
            System.out.println("| 6. Liệt kê những thời gian học có nhiều người đăng kí học từ 1/1/2024 đến 31/3/2024           |");
            System.out.println("| 7. Quay lại                                                                                   |");
            System.out.println("=================================================================================================");
            System.out.println("Nhap vao so cua chuong trinh: ");
            try {
                choice = check.getValidChoice(sc);
                switch (choice) {
                    case (1):
                        DangKyView dangKyView = new DangKyView();
                        dangKyView.hienThiMonHocNhieuNguoiDangKyNhat();
                        break;
                    case (2):
                        findDAO.lietKeMonHocChuaDK();
                        break;
                    case (3):
                        findDAO.top3();
                        break;
                    case (4):
                        findDAO.lietKeGiaoVien();
                        break;
                    case (5):
                        findDAO.lietKeDangKyThapNhat();
                        break;
                    case (6):
                        findDAO.lietKeThoiGianHoc();
                        break;
                    case (7):
                        view();
                        break;
                    default:
                        System.out.println("Nhap sai");
                        break;
                }
            } catch (Exception e) {
                System.out.println(choice);
                System.out.println("Khong hop le vui long nhap lai 4");
                sc.next();
            }
        } while (choice != 7);
    }
}
