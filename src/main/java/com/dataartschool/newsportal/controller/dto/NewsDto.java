package com.dataartschool.newsportal.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDto {

    private Long id;

    private String author;

    private String email;

    private String title;

    private String innerText;

    private NewsSectionDto section;

}
