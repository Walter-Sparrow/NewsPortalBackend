package com.dataartschool.newsportal.service;

import com.dataartschool.newsportal.controller.dto.NewsDto;
import com.dataartschool.newsportal.exception.NewsAlreadyExists;
import com.dataartschool.newsportal.exception.NoNewsFound;
import com.dataartschool.newsportal.persistence.entity.NewsEntity;
import com.dataartschool.newsportal.persistence.repository.NewsRepository;
import com.dataartschool.newsportal.persistence.repository.NewsSectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsSectionRepository newsSectionRepository;

    public List<NewsDto> fetchAllNews() {
        return newsRepository.findAll().stream()
                .map(NewsDto::new)
                .collect(Collectors.toList());
    }

    public NewsDto getById(Long id) {
        return new NewsDto(getExistingNewsById(id));
    }

    public NewsDto addNews(NewsDto newsDto) {
        if (newsRepository.findById(newsDto.getId()).isPresent()) {
            throw new NewsAlreadyExists("News already exists");
        }

        return new NewsDto(
                newsRepository.save(
                        NewsEntity.builder()
                        .id(newsDto.getId())
                        .author(newsDto.getAuthor())
                        .email(newsDto.getEmail())
                        .title(newsDto.getTitle())
                        .innerText(newsDto.getInnerText())
                        .section(newsSectionRepository.getById(newsDto.getSectionId()))
                        .build())
        );
    }

    private NewsEntity getExistingNewsById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(()->new NoNewsFound("No news with this ID was found"));
    }

}
