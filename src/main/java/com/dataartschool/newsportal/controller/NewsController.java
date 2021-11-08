package com.dataartschool.newsportal.controller;

import com.dataartschool.newsportal.component.NewsModelAssembler;
import com.dataartschool.newsportal.controller.dto.NewsCreateRequestDto;
import com.dataartschool.newsportal.controller.dto.PageDto;
import com.dataartschool.newsportal.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> fetchAllNews(@RequestParam(value = "section_id", required = false) Long id, PageDto pageDto) {
        Page<?> newsDtos = id == null
                ? newsService.fetchAll(pageDto).map(newsModelAssembler::toModel)
                : newsService.getAllBySectionId(id, pageDto).map(newsModelAssembler::toModel);

        return ResponseEntity.ok(
                EntityModel.of(newsDtos,
                        linkTo(methodOn(NewsController.class).fetchAllNews(id, pageDto)).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(newsModelAssembler.toModel(newsService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<?> addNews(@RequestParam("file") MultipartFile file, @RequestParam("section_id") Long sectionId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newsModelAssembler.toModel(newsService.add(file, sectionId)));
    }

}
