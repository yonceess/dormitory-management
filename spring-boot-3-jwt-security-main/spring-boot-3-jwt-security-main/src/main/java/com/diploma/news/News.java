package com.diploma.news;

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
@Table(name="news_table", catalog = "jwt_security")
public class News {

    @Id
    @GeneratedValue
    private Integer id;

    private String dame;

    @Column(columnDefinition="TEXT")
    private String description;
    private String date;
    private String fileName;
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDame() {
        return dame;
    }

    public void setDame(String dame) {
        this.dame = dame;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}