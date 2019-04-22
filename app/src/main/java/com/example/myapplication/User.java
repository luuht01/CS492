package com.example.myapplication;

public class User
{
    protected long id;
    protected String username;
    protected String password;

    public User()
    {
        id = -1;
        username = null;
        password = null;
    }

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    //getters
    public long getId(){ return this.id; }
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }

    //setters
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}
