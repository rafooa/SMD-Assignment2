package com.example.smd_a2;

public class Restaurant {
    private String Name, Location, PhoneNumber, Description;

    public Restaurant() {
    }

    public Restaurant(String name, String location, String phoneNumber, String description) {
        Name = name;
        Location = location;
        PhoneNumber = phoneNumber;
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public String getLocation() {
        return Location;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getDescription() {
        return Description;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
