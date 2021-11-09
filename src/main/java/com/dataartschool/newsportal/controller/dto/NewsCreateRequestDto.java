package com.dataartschool.newsportal.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsCreateRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String innerText;

    @Positive
    private Long sectionId;

}
