package user;

import DBConnect.DataSrc;
import com.example.quizzzwebsite.quizDao;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static Connection con;
    private static boolean numUsersChanged = false;
    private static int numUsers = 0;

    public static User getUser(String email, String password) throws SQLException {
        if(!emailRegistered(email)) {
            return null;
        }
        User user = null;
        ResultSet rs = null;
        PreparedStatement statement = null;
        try {
            con = DataSrc.getConnection();
            statement = con.prepareStatement("select * from users where email = ?");
            statement.setString(1, email);
            rs = statement.executeQuery();
            if(rs.next()) {
                if(rs.getString(3).equals(computeHash(password))) {
                    user = new User(rs.getInt(1) ,rs.getString("userName"), rs.getString(2), rs.getBoolean(5));
                }
            }
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement != null) statement.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        return user;
    }

    public static List<User> getAllUsers() throws SQLException {
        PreparedStatement statement = null;
        List<User> users = new ArrayList<User>();
        ResultSet rs = null;
        try{
            con = DataSrc.getConnection();
            statement = con.prepareStatement("select * from users;");
            rs = statement.executeQuery();
            if(rs == null){
                return null;
            }
            while(rs.next()){
                users.add(new User(rs.getInt(1) ,
                        rs.getString("userName"),
                        rs.getString(2),
                        rs.getBoolean(5)));
            }
        }finally{
            try { if (rs != null) rs.close(); }
            catch (Exception e) {e.printStackTrace();};
            try { if (statement != null) statement.close(); }
            catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); }
            catch (Exception e) {e.printStackTrace();};
        }
        return users;
    }

    public static List<User> getUserByName(String userName) throws SQLException{
        List<User> users = new ArrayList<User>();
        ResultSet rs = null;
        PreparedStatement statement = null;
        try {
            con = DataSrc.getConnection();
            statement = con.prepareStatement("select * from users where userName like ?");
            statement.setString(1, "%" + userName + "%");
            rs = statement.executeQuery();
            while(rs.next()) {
                users.add(new User(rs.getInt(1) ,
                        rs.getString("userName"),
                        rs.getString(2),
                        rs.getBoolean(5)));
            }
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement != null) statement.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        return users;
    }

    public static User registerUser(String userName, String email, String password) throws SQLException{
        if(emailRegistered(email) || userNameUsed(userName) || password == null) {
            return null;
        }
        User user = null;
        PreparedStatement statement = null;
        try {
            con = DataSrc.getConnection();
            statement = con.prepareStatement("insert into users (userName, email, password, isAdmin) values(?, ?, ?, ?)");
            statement.setString(1, userName);
            statement.setString(2, email);
            statement.setString(3, computeHash(password));
            statement.setBoolean(4, false);
            statement.executeUpdate();
            user = getUser(email, password);
        } finally {
            try { if (statement != null) statement.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        numUsers++;
        numUsersChanged = true;
        return user;
    }

    public static boolean promoteUser(String userName) throws SQLException {
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet rs = null;
        try{
            con = DataSrc.getConnection();
            statement1 = con.prepareStatement("select * from users where userName = ?;");
            statement1.setString(1,userName);
            rs = statement1.executeQuery();

            boolean flag = false;
            while(rs.next()){
                flag = true;
                break;
            }
            if(!flag)return false;

            statement2 = con.prepareStatement("update users set isAdmin = 1 where userName = ?;");
            statement2.setString(1,userName);
            statement2.executeUpdate();
        }finally{
            try { if (statement1 != null) statement1.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement2 != null) statement2.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        return true;
    }

    public static boolean deleteUser(String userName) throws SQLException, ClassNotFoundException {
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        ResultSet rs = null;
        try{
            con = DataSrc.getConnection();
            statement1 = con.prepareStatement("select * from users where userName = ? ;");
            statement1.setString(1,userName);
            rs = statement1.executeQuery();

            int userId = 0;
            boolean flag = false;
            while(rs.next()){
                userId = rs.getInt("userId");
                flag = true;
                break;
            }
            if(!flag){
                return false;
            }
            quizDao.removeRecordsByUserID(userId);

            statement2 = con.prepareStatement("delete from users where userName = ?;");
            statement2.setString(1,userName);
            statement2.execute();

        }finally{
            try { if (statement1 != null) statement1.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement2 != null) statement2.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        numUsers--;
        numUsersChanged = true;
        return true;
    }


    public static List<User> searchUsers(String userName) throws SQLException {
        List<User> res = new ArrayList<>();
        if(userName.length()<4) return res;
        ResultSet rs = null;
        PreparedStatement statement = null;
        try {
            con = DataSrc.getConnection();
            statement = con.prepareStatement("select * from users where userName like ?");
            statement.setString(1, "%" + userName + "%");
            rs = statement.executeQuery();
            while(rs.next()) {
                res.add(new User(rs.getInt(1) ,rs.getString("userName"), rs.getString("email"), rs.getBoolean("isAdmin")));
            }
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement != null) statement.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        return res;
    }


    private static boolean emailRegistered(String email) throws SQLException{
        boolean res = false;
        ResultSet rs = null;
        PreparedStatement statement = null;
        try {
            con = DataSrc.getConnection();
            statement = con.prepareStatement("select * from users where email = ?");
            statement.setString(1, email);
            rs = statement.executeQuery();
            if(rs.next()) {
                res = true;
            }

        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement != null) statement.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        return res;
    }
    private static boolean userNameUsed(String userName) throws SQLException {
        ResultSet rs = null;
        PreparedStatement statement = null;
        boolean result = false;
        try {
            con = DataSrc.getConnection();
            statement = con.prepareStatement("select * from users where userName = ?");
            statement.setString(1, userName);
            rs = statement.executeQuery();
            if(rs.next()) {
                result = true;
            }
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement != null) statement.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        return result;
    }

    private static String computeHash(String arg) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(arg.getBytes());
            return hexToString(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static String hexToString(byte[] bytes) {
        StringBuilder buff = new StringBuilder();
        for (int aByte : bytes) {
            int val = aByte;
            val = val & 0xff;  // remove higher bits, sign
            if (val < 16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    public static int getNumUsers() throws SQLException {
        if(numUsersChanged){
            con = DataSrc.getConnection();
            PreparedStatement statement = con.prepareStatement("select * from users;");
            ResultSet rs = statement.executeQuery();
            if(rs == null){
                return 0;
            }
            numUsers = 0;
            while(rs.next()){
                numUsers++;
            }
            numUsersChanged = false;
        }
        return numUsers;
    }
}
