package com.tatvasoftassignment.myapplication.Model;

public class StudentData {
    int id;
    String name, email, contactNo, address, birthDate, bloodGroup, gender, language;

    public StudentData(int id,
                       String name,
                       String email,
                       String contactNo,
                       String address,
                       String birthDate,
                       String bloodGroup,
                       String gender,
                       String language) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
        this.address = address;
        this.birthDate = birthDate;
        this.bloodGroup = bloodGroup;
        this.gender = gender;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getGender() {
        return gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setName(String name) {
        this.name = name;
    }

}
