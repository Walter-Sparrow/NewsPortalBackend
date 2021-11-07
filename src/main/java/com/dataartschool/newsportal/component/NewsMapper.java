package com.dataartschool.newsportal.component;

import com.dataartschool.newsportal.controller.dto.NewsCreateRequestDto;
import com.dataartschool.newsportal.controller.dto.NewsDto;
import com.dataartschool.newsportal.exception.NoNewsSectionFound;
import com.dataartschool.newsportal.persistence.entity.NewsEntity;
import com.dataartschool.newsportal.persistence.entity.NewsSectionEntity;
import com.dataartschool.newsportal.persistence.repository.NewsSectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsMapper {
    private final NewsSectionRepository newsSectionRepository;

    public NewsDto toDto(NewsEntity entity) {
        return NewsDto.builder()
                .id(entity.getId())
                .author(entity.getAuthor())
                .email(entity.getEmail())
                .innerText(entity.getInnerText())
                .title(entity.getTitle())
                .sectionName(entity.getSection().getName())
                .build();
    }

    public NewsEntity fromCreateRequestDtoToEntity(NewsCreateRequestDto dto) {
        NewsSectionEntity sectionEntity = newsSectionRepository.findById(dto.getSectionId())
                .orElseThrow(() -> new NoNewsSectionFound("No news section with this ID was found"));

        return NewsEntity.builder()
                .title(dto.getTitle())
                .innerText(dto.getInnerText())
                .section(sectionEntity)
                .author(dto.getAuthor())
                .email(dto.getEmail())
                .build();
    }

}
