package com.dataartschool.newsportal.service;

import com.dataartschool.newsportal.controller.dto.NewsCreateRequestDto;
import com.dataartschool.newsportal.controller.dto.NewsDto;
import com.dataartschool.newsportal.controller.dto.PageDto;
import com.dataartschool.newsportal.component.NewsMapper;
import com.dataartschool.newsportal.exception.NoNewsFound;
import com.dataartschool.newsportal.persistence.entity.NewsEntity;
import com.dataartschool.newsportal.persistence.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public Page<NewsDto> fetchAllNews(PageDto pageDto) {
        return newsRepository.findAll(pageDto.getPageable()).map(newsMapper::toDto);
    }

    public NewsDto getById(Long id) {
        return newsMapper.toDto(getExistingNewsById(id));
    }

    public NewsDto addNews(NewsCreateRequestDto dto) {
        NewsEntity newsEntity = newsMapper.fromCreateRequestDtoToEntity(dto);
        return newsMapper.toDto(newsRepository.save(newsEntity));
    }

    private NewsEntity getExistingNewsById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(()->new NoNewsFound("No news with this ID was found"));
    }

}
