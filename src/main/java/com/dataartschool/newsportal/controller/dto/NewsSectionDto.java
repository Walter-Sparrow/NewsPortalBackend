package com.dataartschool.newsportal.controller.dto;

import com.dataartschool.newsportal.persistence.entity.NewsSectionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsSectionDto {

    private Long id;

    private String name;

    public NewsSectionDto(NewsSectionEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

}
