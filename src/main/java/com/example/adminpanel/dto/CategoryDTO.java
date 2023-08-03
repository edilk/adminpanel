package com.example.adminpanel.dto;

import lombok.Data;

@Data
public class CategoryDTO {

    private Long id;
    private String title;
    private Long parentId;
}
