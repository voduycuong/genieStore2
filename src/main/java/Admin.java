package main.java;

public class Admin {
    private String username;
    private String password;

    public Admin(String username, String password) {
        this.username = "admin";
        this.password = "admin123";
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}