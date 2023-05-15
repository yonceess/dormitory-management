package com.diploma.items;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="items_table", catalog = "jwt_security")
public class Items{

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description_dorm;
    private String problem;
    private String phone;

    private String email;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription_dorm() {
        return description_dorm;
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

    public void setDescription_dorm(String description_dorm) {
        this.description_dorm = description_dorm;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
