package com.diploma.form;

import com.diploma.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="form_table", catalog = "jwt_security")
public class Form {

    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank(message="Enter your name':")
    private String name;
    @NotBlank(message="Enter date':")
    private String date;
    @NotBlank(message="Enter your address':")
    private String address;
    @NotBlank(message="Enter your phone':")
    private String phone;
    @NotBlank(message="Enter your reason':")
    private String reason;
    @NotBlank(message="Enter your email':")
    @Email(message = "Enter valid email address")
    private String email;

    private String dormitory;
    private String apartment;
    private int room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getReason() {
        return reason;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}