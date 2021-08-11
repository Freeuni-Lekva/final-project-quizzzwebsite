
import org.junit.Test;
import messaging.ChatManager;
import messaging.Message;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ChatManagerTester {

    @Test
    public void testAdd() throws SQLException, ClassNotFoundException {
        ChatManager.add("Gio","Nika","hi!!");
        ChatManager.add("Nika","Gio","hello!!");
        ChatManager.add("Nika","Gio","how are you?");
        ChatManager.add("Anna","Nika","Hello!!");
        List<Message> messageList= ChatManager.getAll("Nika");
        for(Message tmp:messageList){
            System.out.print("From:"+tmp.getSender()+" ");
            System.out.print("To:"+tmp.getReceiver()+ " ");
            System.out.print("txt:"+tmp.getMessage()+" ");
            System.out.println(tmp.getTime());
        }
        assertTrue(messageList.get(0).getSender().equals("Gio"));
        assertTrue(messageList.get(1).getMessage().equals("hello!!"));
        assertTrue(messageList.get(3).getReceiver().equals("Nika"));
        ChatManager.clearTable();
    }

    @Test
    public void testGetChat() throws SQLException, ClassNotFoundException {
        ChatManager.add("Nika","Gio","hello!!");
        ChatManager.add("Nika","Gio","how are you?");
        ChatManager.add("Anna","Nika","Hello!!");
        ChatManager.add("Gio","Nika","Fine...and how are you?");
        ChatManager.add("Anna","Nika","Can you send me homework...");
        ChatManager.add("Nika","Gio","Fine...thanks");
        List<Message> messageList= ChatManager.getChat("Nika","Gio");
        for(Message tmp:messageList){
            System.out.println(tmp.getSender()+":"+tmp.getMessage());
        }
        assertTrue(messageList.get(0).getSender().equals("Nika") && messageList.get(1).getSender().equals("Nika"));
        assertTrue(messageList.get(1).getMessage().equals("how are you?"));
        assertTrue(messageList.get(2).getSender().equals("Gio"));
        ChatManager.clearTable();
    }

    @Test
    public void testUnseen() throws SQLException, ClassNotFoundException {
        ChatManager.add("Nika","Gio","hello!!");
        ChatManager.add("Nika","Gio","how are you?");
        ChatManager.add("Anna","Nika","Hello!!");
        ChatManager.add("Gio","Nika","Fine...and how are you?");
        ChatManager.changeUnseen("Nika","Gio");
        assertTrue ( ChatManager.getUnseen("Nika","Gio").size()==0);
        List<Message> messageList= ChatManager.getUnseen("Gio","Nika");
        for(Message tmp:messageList){
            System.out.println(tmp.getSender()+":"+tmp.getMessage());
        }
        assertTrue(messageList.size()==1);
        assertTrue(messageList.get(0).getSender().equals("Gio"));
        ChatManager.clearTable();
    }

}
