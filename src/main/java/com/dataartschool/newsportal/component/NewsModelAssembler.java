package com.dataartschool.newsportal.component;

import com.dataartschool.newsportal.controller.NewsController;
import com.dataartschool.newsportal.controller.dto.PageDto;
import com.dataartschool.newsportal.controller.dto.NewsDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class NewsModelAssembler implements RepresentationModelAssembler<NewsDto, EntityModel<NewsDto>> {

    @Override
    public EntityModel<NewsDto> toModel(NewsDto newsDto) {

        return EntityModel.of(newsDto,
                linkTo(methodOn(NewsController.class).getById(newsDto.getId())).withSelfRel(),
                linkTo(methodOn(NewsController.class).fetchAllNews(new PageDto())).withRel("news"));
    }
}