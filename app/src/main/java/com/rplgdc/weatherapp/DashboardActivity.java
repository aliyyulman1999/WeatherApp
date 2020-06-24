package com.rplgdc.weatherapp;

public class DashboardActivity{
    private String email,password, yourname, username;

    public DashboardActivity(){

    }
    public DashboardActivity(String email, String password, String yourname, String username) {
        this.email = email;
        this.password = password;
        this.yourname = yourname;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getYourname() {
        return yourname;
    }

    public String getUsername() {
        return username;
    }
}
