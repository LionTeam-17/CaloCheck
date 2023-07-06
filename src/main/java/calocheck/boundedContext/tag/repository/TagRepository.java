package calocheck.boundedContext.tag.repository;

import calocheck.boundedContext.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
