

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DB {
    private static String host="localhost";
    private static String DBName="testDataBase";
    private static String userName= "sammy";
    private static String password= "password";


    private static Connection con;;

    /*
     * constructor, connect database
     */
    public DB() {

        con=null;
        try {


            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection( "jdbc:mysql://" + host, userName ,password);



        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }

    }

    /*
     *  returns connection
     *  @param
     *  @return Connection
     */
    public Connection getConnection() {

        return con;
    }

}