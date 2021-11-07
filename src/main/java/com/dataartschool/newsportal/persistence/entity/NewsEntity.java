package com.dataartschool.newsportal.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String author;

    private String email;

    @NonNull
    private String title;

    @Lob
    @NonNull
    private String innerText;

    @ManyToOne(fetch = FetchType.EAGER)
    private NewsSectionEntity section;

}
