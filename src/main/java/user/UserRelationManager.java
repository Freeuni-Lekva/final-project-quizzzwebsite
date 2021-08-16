package user;

import DBConnect.DataSrc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRelationManager {
    private static Connection con;

    public static boolean sendRequest(String sender, String receiver) throws SQLException {
        if(!userExists(sender) || !userExists(receiver) || isRequestSent(sender,receiver)) {
            return false;
        }
        try {
            con = DataSrc.getConnection();
            PreparedStatement statement = con.prepareStatement("insert into friendshipRequests value (?, ?, ?)");
            java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            statement.setString(1, sender);
            statement.setString(2, receiver);
            statement.setTimestamp(3, date);
            statement.executeUpdate();
        } finally {
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        return true;
    }

    private static boolean userExists(String userName) throws SQLException {
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

    public static boolean removeRequest(String sender, String receiver) throws SQLException{
        if(!userExists(sender) || !userExists(receiver) || !isRequestSent(sender, receiver)) {
            return false;
        }
        try {
            con = DataSrc.getConnection();
            PreparedStatement statement = con.prepareStatement("delete from friendshipRequests where + requestorUsername = ? and requesteeUsername = ?;");
            statement.setString(1,sender);
            statement.setString(2, receiver);
            statement.executeUpdate();
        } finally {
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        return true;
    }

    public static boolean addFriendship(String userName1, String userName2) throws SQLException {
        if(!userExists(userName1) || !userExists(userName2)) {
            return false;
        }
        PreparedStatement statement = null;
        try {
            con = DataSrc.getConnection();
            statement = con.prepareStatement("insert into userFriendships value (?, ?)");
            statement.setString(1, userName1);
            statement.setString(2, userName2);
            statement.executeUpdate();
        } finally {
            try { if (statement != null) statement.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        return true;
    }

    public static boolean removeFriendship(String userName1, String userName2) throws SQLException {
        if(!areFriends(userName1, userName2)) {
            return false;
        }
        try {
            con = DataSrc.getConnection();
            PreparedStatement statement1 = con.prepareStatement("delete from userFriendships where username1 = ? and username2 = ?;");
            statement1.setString(1,userName1);
            statement1.setString(2, userName2);
            statement1.executeUpdate();
            PreparedStatement statement2 = con.prepareStatement("delete from userFriendships where username1 = ? and username2 = ?;");
            statement1.setString(1,userName2);
            statement1.setString(2, userName1);
            statement1.executeUpdate();
        } finally {
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        return true;
    }

    public static List<String> getFriendList(String userName) throws SQLException {
        List<String> result = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement statement = null;
        try {
            con = DataSrc.getConnection();
            statement = con.prepareStatement("select * from userFriendships where username1 = ?");
            statement.setString(1, userName);
            rs = statement.executeQuery();
            while(rs.next()) {
                result.add(rs.getString(2));
            }
            statement.close();
            rs.close();
            statement = con.prepareStatement("select * from userFriendships where username2 = ?");
            statement.setString(1, userName);
            rs = statement.executeQuery();
            while(rs.next()) {
                result.add(rs.getString(1));
            }
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement != null) statement.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }

        return result;
    }

    public static void deleteUserRelations(String userName) throws SQLException {
        PreparedStatement statement1 = null;
        PreparedStatement statement2 = null;
        PreparedStatement statement3 = null;
        PreparedStatement statement4 = null;

        try{
            con = DataSrc.getConnection();
            statement1 = con.prepareStatement("delete from friendshipRequests where requestorUsername = ?");
            statement1.setString(1, userName);
            statement1.executeUpdate();
            statement2 = con.prepareStatement("delete from friendshipRequests where requesteeUsername = ?");
            statement2.setString(1,userName);
            statement2.executeUpdate();
            statement3 = con.prepareStatement("delete from userFriendships where username1 = ?");
            statement3.setString(1,userName);
            statement3.executeUpdate();
            statement4 = con.prepareStatement("delete from userFriendships where username2 = ?");
            statement4.setString(1,userName);
            statement4.executeUpdate();
        }finally{
            try { if (statement1 != null) statement1.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement2 != null) statement2.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement3 != null) statement3.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement4 != null) statement4.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
    }

    public static List<String> getSentRequestList(String userName) throws SQLException {
        List<String> result = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement statement = null;
        try{
            con = DataSrc.getConnection();
            statement = con.prepareStatement("select * from friendshipRequests where requestorUsername = ?");
            statement.setString(1, userName);
            rs = statement.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("requesteeUsername"));
            }
        }finally{
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement != null) statement.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }
        return result;
    }

    public static List<String> getReceivedRequestList(String userName) throws SQLException {
        List<String> result = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement statement = null;
        try {
            con = DataSrc.getConnection();
            statement = con.prepareStatement("select * from friendshipRequests where requesteeUsername = ?");
            statement.setString(1, userName);
            rs = statement.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("requestorUsername"));
            }
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (statement != null) statement.close(); } catch (Exception e) {e.printStackTrace();};
            try { if (con != null) con.close(); } catch (Exception e) {e.printStackTrace();};
        }

        return result;
    }

    public static boolean isRequestSent(String sender, String receiver) throws SQLException {
        ResultSet rs = null;
        PreparedStatement statement = null;
        boolean result = false;
        try {
            con = DataSrc.getConnection();
            statement = con.prepareStatement("select * from friendshipRequests where requestorUsername = ? and requesteeUsername = ?");
            statement.setString(1, sender);
            statement.setString(2, receiver);
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

    public static boolean areFriends(String userName1, String userName2) throws SQLException{
        ResultSet rs = null;
        PreparedStatement statement = null;
        boolean result = false;
        try {
            con = DataSrc.getConnection();
            statement = con.prepareStatement("select * from userFriendships where username1 = ? and username2 = ?");
            statement.setString(1, userName1);
            statement.setString(2, userName2);
            rs = statement.executeQuery();
            if(rs.next()) {
                result = true;
            }
            statement.close();
            rs.close();
            statement = con.prepareStatement("select * from userFriendships where username1 = ? and username2 = ?");
            statement.setString(2, userName1);
            statement.setString(1, userName2);
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

}
