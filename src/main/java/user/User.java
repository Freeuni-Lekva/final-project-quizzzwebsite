package user;

public class User {
    public static String ATTRIBUTE_NAME = "user";
    private String userName;
    private int id;
    private String email;
    private boolean isAdmin;

    public User(int id, String userName, String email, boolean isAdmin) {
        this.userName = userName;
        this.email = email;
        this.isAdmin = isAdmin;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public int getId() {return id;}
}
