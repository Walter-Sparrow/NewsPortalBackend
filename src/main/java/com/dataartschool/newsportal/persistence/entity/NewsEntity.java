package com.dataartschool.newsportal.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String author;

    private String email;

    private String title;

    private String innerText;

    @ManyToOne(fetch = FetchType.EAGER)
    private NewsSectionEntity section;
}
