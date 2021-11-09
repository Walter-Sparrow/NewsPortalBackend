package com.dataartschool.newsportal.controller;

import com.dataartschool.newsportal.service.NewsSectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/sections")
@RequiredArgsConstructor
public class NewsSectionController {
    private final NewsSectionService newsSectionService;

    @GetMapping
    public ResponseEntity<?> fetchAll() {
        return ResponseEntity.ok(
                CollectionModel.of(newsSectionService.fetchAll(),
                        linkTo(methodOn(NewsSectionController.class).fetchAll()).withSelfRel())
        );
    }
}
