package com.example.alpha_shiftmanagement.util;

public class User {
    String name;
    String IDnumber;
    String phoneNumber;
    String city;
    String email;
    String password;
    String salary;
    boolean GardId;
    boolean IsManager;

    public User(){}

    public User(String name,String IDnumber, String phoneNumber, String city, String email, String password, String salary,Boolean IsManager, boolean gardId) {
        this.name = name;
        this.IDnumber = IDnumber;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.email = email;
        this.password = password;
        this.salary = salary;
        this.IsManager = IsManager;
        this.GardId = gardId;
    }

    public String getIDnumber() {
        return IDnumber;
    }

    public void setIDnumber(String IDnumber) {
        this.IDnumber = IDnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public boolean isManager() {
        return IsManager;
    }

    public void setManager(boolean manager) {
        IsManager = manager;
    }

    public boolean isGardId() {
        return GardId;
    }

    public void setGardId(boolean gardId) {
        GardId = gardId;
    }
}
