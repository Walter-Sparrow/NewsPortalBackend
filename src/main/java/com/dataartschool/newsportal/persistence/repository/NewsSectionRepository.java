package com.dataartschool.newsportal.persistence.repository;

import com.dataartschool.newsportal.persistence.entity.NewsSectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsSectionRepository extends JpaRepository<NewsSectionEntity, Long> {
}
