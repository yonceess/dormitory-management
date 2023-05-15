package com.diploma.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequest {
    private String name;
    private String description;
    private String date;
    private String linkPicture;
}
