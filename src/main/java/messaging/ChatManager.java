package messaging;

import DBConnect.DataSrc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatManager {


     private static String NAME="t_chats";

    public static  boolean add(String sender, String receiver, String message) throws SQLException, ClassNotFoundException {
        Connection connection= DataSrc.getConnection();
        PreparedStatement st = connection.prepareStatement("insert into "+NAME+ " values (?,?,?,str_to_date(NOW(),'%Y-%m-%d %H:%i:%s'),true)");
        st.setString(1, sender);
        st.setString(2, receiver);
        st.setString(3, message);
        return 1 == st.executeUpdate();
    }



    private static void fillList(List<Message> l, ResultSet res) throws SQLException {
        while(res.next()){
            l.add(new Message(res.getString("sender"),res.getString("receiver"),res.getString(
                    "message"),res.getString("time"),res.getBoolean("unseen")));
        }

    }


   public static List<Message> getAll(String person) throws SQLException, ClassNotFoundException {
       Connection connection=DataSrc.getConnection();
       List<Message> l=new ArrayList<Message>();
       String sql = " select * from "+NAME+" Where receiver=? or sender=? order by time asc;";
       PreparedStatement st= connection.prepareStatement(sql);
       st.setString(1, person);
       st.setString(2, person);
       ResultSet answer=st.executeQuery();
       fillList(l,answer);
       return l ;

   }

   public static boolean clearTable() throws SQLException, ClassNotFoundException {
       Connection connection=DataSrc.getConnection();
       PreparedStatement st = connection.prepareStatement("DELETE from "+NAME+ " ;");
      return 1 == st.executeUpdate();
   }

   public static List<Message> getChat(String first,String second) throws SQLException, ClassNotFoundException {
       Connection connection=DataSrc.getConnection();
       List<Message> l=new ArrayList<Message>();
       String sql = " select  * from "+NAME+" Where (sender=? and receiver=?) or (receiver=? and sender=?) order by time asc;";
       PreparedStatement st= connection.prepareStatement(sql);
       st.setString(1, first);
       st.setString(2, second);
       st.setString(3, first);
       st.setString(4, second);
       ResultSet answer=st.executeQuery();
       fillList(l,answer);
       return l ;

   }
    public static List<Message> getUnseen(String sender,String receiver) throws SQLException, ClassNotFoundException {
        Connection connection=DataSrc.getConnection();
        List<Message> l=new ArrayList<Message>();
        String sql = " select  * from "+NAME+" Where receiver=? and unseen=true and sender=? ;";
        PreparedStatement st= connection.prepareStatement(sql);
        st.setString(1, receiver);
        st.setString(2,sender);
        ResultSet answer=st.executeQuery();
        fillList(l,answer);
        return l ;
    }

    public static boolean changeUnseen(String sender,String receiver) throws SQLException, ClassNotFoundException {
        Connection connection=DataSrc.getConnection();
        PreparedStatement st = connection.prepareStatement("update "+NAME+ " set unseen=false where sender=? and receiver=?;");
        st.setString(1, sender);
        st.setString(2, receiver);
        return 1 == st.executeUpdate();
    }

}
