package com.dataartschool.newsportal.controller;

import com.dataartschool.newsportal.component.NewsModelAssembler;
import com.dataartschool.newsportal.controller.dto.NewsCreateRequestDto;
import com.dataartschool.newsportal.controller.dto.PageDto;
import com.dataartschool.newsportal.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final NewsModelAssembler newsModelAssembler;

    @GetMapping
    public ResponseEntity<?> fetchAllNews(PageDto pageDto) {
        return ResponseEntity.ok(
                EntityModel.of(newsService.fetchAllNews(pageDto).map(newsModelAssembler::toModel),
                        linkTo(methodOn(NewsController.class)).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(newsModelAssembler.toModel(newsService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<?> addNews(@Valid @RequestBody NewsCreateRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newsModelAssembler.toModel(newsService.addNews(dto)));
    }

}
