package com.diploma.items;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemsRequest {
    private String name;
    private String description_dorm;
    private String problem;
    private String phone;

    private String email;
}