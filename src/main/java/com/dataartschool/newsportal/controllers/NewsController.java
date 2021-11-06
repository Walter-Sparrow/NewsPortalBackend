package com.dataartschool.newsportal.controllers;

import com.dataartschool.newsportal.persistence.repository.NewsRepository;
import com.dataartschool.newsportal.persistence.repository.entity.NewsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsRepository newsRepository;

    @GetMapping
    private List<NewsEntity> all() {
        return newsRepository.findAll();
    }
}
