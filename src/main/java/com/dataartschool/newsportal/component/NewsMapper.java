package com.dataartschool.newsportal.component;

import com.dataartschool.newsportal.controller.dto.NewsCreateRequestDto;
import com.dataartschool.newsportal.controller.dto.NewsDto;
import com.dataartschool.newsportal.controller.dto.NewsSectionDto;
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
                .innerText(entity.getInnerText())
                .title(entity.getTitle())
                .section(new NewsSectionDto(entity.getSection().getId(), entity.getSection().getName()))
                .build();
    }

    public NewsEntity fromCreateRequestDtoToEntity(NewsCreateRequestDto dto) {
        NewsSectionEntity sectionEntity = newsSectionRepository.findById(dto.getSectionId())
                .orElseThrow(() -> new NoNewsSectionFound("No news section with this ID was found"));

        return NewsEntity.builder()
                .title(dto.getTitle())
                .innerText(dto.getInnerText())
                .section(sectionEntity)
                .build();
    }

}
