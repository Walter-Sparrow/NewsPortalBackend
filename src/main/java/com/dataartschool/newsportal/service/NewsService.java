package com.dataartschool.newsportal.service;

import com.dataartschool.newsportal.controller.dto.NewsCreateRequestDto;
import com.dataartschool.newsportal.controller.dto.NewsDto;
import com.dataartschool.newsportal.controller.dto.PageDto;
import com.dataartschool.newsportal.component.NewsMapper;
import com.dataartschool.newsportal.exception.NoNewsFound;
import com.dataartschool.newsportal.exception.NoNewsSectionFound;
import com.dataartschool.newsportal.persistence.entity.NewsEntity;
import com.dataartschool.newsportal.persistence.entity.NewsSectionEntity;
import com.dataartschool.newsportal.persistence.repository.NewsRepository;
import com.dataartschool.newsportal.persistence.repository.NewsSectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsSectionRepository newsSectionRepository;
    private final NewsMapper newsMapper;

    public Page<NewsDto> fetchAll(PageDto pageDto) {
        return newsRepository.findAll(pageDto.getPageable()).map(newsMapper::toDto);
    }

    public NewsDto getById(Long id) {
        return newsMapper.toDto(getExistingById(id));
    }

    public NewsDto add(NewsCreateRequestDto dto) {
        NewsEntity newsEntity = newsMapper.fromCreateRequestDtoToEntity(dto);
        return newsMapper.toDto(newsRepository.save(newsEntity));
    }

    public Collection<NewsDto> getAllBySectionId(Long id) {
        NewsSectionEntity sectionEntity = newsSectionRepository.findById(id)
                .orElseThrow(()->new NoNewsSectionFound("No news section with this ID was found"));

        return sectionEntity.getNews().stream().map(newsMapper::toDto).collect(Collectors.toSet());
    }

    private NewsEntity getExistingById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(()->new NoNewsFound("No news with this ID was found"));
    }

}
