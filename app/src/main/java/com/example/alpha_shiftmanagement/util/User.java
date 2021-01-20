package com.example.alpha_shiftmanagement.util;




public class User {

   public String Name,IdNumber,PhoneNumber,City,Email,Password,Salary;

   public boolean GardId,IsManager;

    public User(){}

    public User(String name, String idNumber, String phoneNumber, String city, String email, String password) {
        this.Name = name;
        this.IdNumber = idNumber;
        this.PhoneNumber = phoneNumber;
        this.City = city;
        this.Email = email;
        this.Password = password;
//        this.Salary = salary;
//        this.GardId = gardId;
//        this.IsManager = isManager;
    }
}
