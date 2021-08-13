package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DB {
    private static String host="localhost";
    private static String DBName="testDataBase";
    private static String userName= "sammy";
    private static String password= "datodvali28";


    private static Connection con;;

    /*
     * constructor, connect database
     */
    public DB() throws SQLException, ClassNotFoundException {

        con=null;



            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection( "jdbc:mysql://" + host, userName ,password);




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
