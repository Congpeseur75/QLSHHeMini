package view;

import java.sql.SQLException;
import java.util.Scanner;

import utility.InputUtils;

public class MainView {
    InputUtils check = new InputUtils();
    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        BGVView bgv = new BGVView();
        PhuHuynhMainView phuHuynhMainView = null;
        try {
            phuHuynhMainView = new PhuHuynhMainView();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        int choice = 0;
        do {
            System.out.println("\n===Chào mừng bạn đến với chương trình quản lý dịch vụ sinh hoạt hè===");
            System.out.println("\n==============Bạn là ai=============");
            System.out.println("|      1. Ban giáo vụ              |");
            System.out.println("|      2. Phụ huynh                |");
            System.out.println("|      3. Thoát khỏi chương trình  |");
            System.out.println("====================================");
            try {
                choice = check.getValidChoice(sc);
                switch (choice) {
                    case (1) :
                        bgv.view();
                        break;
                    case (2):
                        phuHuynhMainView.showPhuHuynhMenu();
                        break;
                    case (3):
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Nhập sai");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Không hợp lệ vui lòng nhập lại");
                sc.next();
            }
        } while (choice != 3);
    }
}
