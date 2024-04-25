package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static Connection conn = null;

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyDichVuSinhHoatHe;trustServerCertificate=true";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123";

    public static Connection getConnectDB() {
        try {
        if (conn == null || conn.isClosed()) {

                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        }
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy JDBC Driver của SQL Server! Vui lòng bổ sung vào đường dẫn thư viện của bạn!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại! Kiểm tra lại thông tin đăng nhập và URL.");
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                System.out.println("Đóng kết nối thất bại!");
                e.printStackTrace();
            }
        }
    }


}
