package com.dataartschool.newsportal.service;

import com.dataartschool.newsportal.controller.dto.NewsSectionDto;
import com.dataartschool.newsportal.persistence.repository.NewsSectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsSectionService {
    private final NewsSectionRepository newsSectionRepository;

    public List<NewsSectionDto> fetchAll() {
        return newsSectionRepository.findAll().stream()
                .map(NewsSectionDto::new)
                .collect(Collectors.toList());
    }
}
