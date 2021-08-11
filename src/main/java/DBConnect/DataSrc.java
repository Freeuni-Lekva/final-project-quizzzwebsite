package DBConnect;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataSrc {
    private static Connection con;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz.quiz", "root", "ტრანგალეტკა6");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void close() {
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}