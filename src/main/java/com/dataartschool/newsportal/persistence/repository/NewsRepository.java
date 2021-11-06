package com.dataartschool.newsportal.persistence.repository;

import com.dataartschool.newsportal.persistence.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
}
