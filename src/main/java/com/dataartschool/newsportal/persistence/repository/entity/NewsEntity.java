package com.dataartschool.newsportal.persistence.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String author;

    private String email;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private NewsSectionEntity section;
}
