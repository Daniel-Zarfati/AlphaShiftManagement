package com.example.alpha_shiftmanagement.util;


public class Event {


    String TakingPlace,Date,StartHour,EndHour,EventSalary;

    //String Day,Month,Year;

    String MaxEmployees,Availability;



    public Event(){}

    public Event(String takingPlace,String Date, String startHour, String endHour,String Availability, String eventSalary) {
        this.TakingPlace = takingPlace;
        this.Date = Date;
        this.StartHour = startHour;
        this.EndHour = endHour;
        this.Availability = Availability;
        this.EventSalary = eventSalary;
    }
    public Event(String takingPlace,String Date, String startHour, String endHour, String maxEmployees,String Availability, String eventSalary) {
        this.TakingPlace = takingPlace;
        this.Date = Date;
//        this.Day = day;
//        this.Month = month;
//        this.Year = year;
        this.StartHour = startHour;
        this.EndHour = endHour;
        this.MaxEmployees = maxEmployees;
        this.Availability = Availability;
        this.EventSalary = eventSalary;
    }

    public String getTakingPlace() {
        return TakingPlace;
    }

    public void setTakingPlace(String takingPlace) {
        TakingPlace = takingPlace;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStartHour() {
        return StartHour;
    }

    public void setStartHour(String startHour) {
        StartHour = startHour;
    }

    public String getEndHour() {
        return EndHour;
    }

    public void setEndHour(String endHour) {
        EndHour = endHour;
    }

    public String getEventSalary() {
        return EventSalary;
    }

    public void setEventSalary(String eventSalary) {
        EventSalary = eventSalary;
    }

    public String getMaxEmployees() {
        return MaxEmployees;
    }

    public void setMaxEmployees(String maxEmployees) {
        MaxEmployees = maxEmployees;
    }

    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }

}

