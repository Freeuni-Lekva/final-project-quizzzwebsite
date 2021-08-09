package user;

public class User {
    public static String ATTRIBUTE_NAME = "user";
    private String userName;
    private String fullName;
    private boolean isAdmin;

    public User(String userName, String fullName, boolean isAdmin) {
        this.userName = userName;
        this.fullName = fullName;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }


}
