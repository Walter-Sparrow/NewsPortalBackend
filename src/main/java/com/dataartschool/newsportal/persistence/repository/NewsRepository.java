package com.dataartschool.newsportal.persistence.repository;

import com.dataartschool.newsportal.persistence.entity.NewsEntity;
import com.dataartschool.newsportal.persistence.entity.NewsSectionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
    Page<NewsEntity> findAllBySection(NewsSectionEntity newsSectionEntity, Pageable pageable);
}
