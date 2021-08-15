package user;

import DBConnect.DataSrc;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static Connection con;


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
        return user;
    }

    public static boolean deleteUser(long userId) {

        return false;
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

    public static String getUsernameById(int creatorID){
        String user = null;
        ResultSet rs = null;
        PreparedStatement statement = null;
        try {
            con = DataSrc.getConnection();
            statement = con.prepareStatement("select * from users where userId = ?");
            statement.setInt(1, creatorID);
            rs = statement.executeQuery();
            if(rs.next()) {
                user = rs.getString("userName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement != null) statement.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        return user;
    }
}
