package com.dataartschool.newsportal.controller;

import com.dataartschool.newsportal.controller.dto.NewsDto;
import com.dataartschool.newsportal.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public List<NewsDto> fetchAllNews() {
        return newsService.fetchAllNews();
    }

    @GetMapping("/{id}")
    public NewsDto getById(@PathVariable("id") Long id) {
        return newsService.getById(id);
    }

    @PostMapping
    public ResponseEntity<?> addNews(@RequestBody NewsDto newsDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newsService.addNews(newsDto));
    }
}
