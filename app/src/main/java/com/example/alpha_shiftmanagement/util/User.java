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

    }

    public User(String name, String idNumber, String phoneNumber, String city, String email, String password, String salary, boolean gardId, boolean isManager) {
        Name = name;
        IdNumber = idNumber;
        PhoneNumber = phoneNumber;
        City = city;
        Email = email;
        Password = password;
        Salary = salary;
        GardId = gardId;
        IsManager = isManager;
    }

    public Double SalaryCalc(String StartHour,String EndHour,String EventSalary){

            // fill here

        return 1.1;
    }
}
