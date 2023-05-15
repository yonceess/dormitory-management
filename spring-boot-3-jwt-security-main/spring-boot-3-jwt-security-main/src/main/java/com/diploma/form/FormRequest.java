package com.diploma.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormRequest {
    private String name;
    private String date;
    private String address;

    private String reason;

    private String phone;

    private String email;
}