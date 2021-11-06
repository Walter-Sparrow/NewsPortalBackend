package com.dataartschool.newsportal.controller.dto;

import com.dataartschool.newsportal.persistence.entity.NewsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {

    private Long id;

    private String author;

    private String email;

    private String title;

    private String innerText;

    private Long sectionId;

    public NewsDto(NewsEntity newsEntity) {
        this.id = newsEntity.getId();
        this.author = newsEntity.getAuthor();
        this.email = newsEntity.getEmail();
        this.title = newsEntity.getTitle();
        this.innerText = newsEntity.getInnerText();
        this.sectionId = newsEntity.getSection().getId();
    }

}
