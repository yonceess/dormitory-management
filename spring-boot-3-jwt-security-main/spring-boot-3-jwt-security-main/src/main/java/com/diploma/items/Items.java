package com.diploma.items;

import com.diploma.user.User;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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
@Transactional
@Table(name="items_table", catalog = "jwt_security")
public class Items{

    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank(message="Enter your first and second name:")
    private String name;

    @NotBlank(message="Enter your dormitory':")
    private String dormitory;
    @NotBlank(message="Enter your apartment:")
    private String apartment;

    @NotNull
    @Min(value = 1)
    @Max(value = 4)
    private int room;
    @NotBlank(message="Enter your problem:")
    private String problem;
    @NotBlank(message="Enter your phone:")
    private String phone;

    @NotBlank(message="Enter your email':")
    @Email(message = "Enter valid email address")
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public String getProblem() {
        return problem;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setProblem(String problem) {
        this.problem = problem;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
