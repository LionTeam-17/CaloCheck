package calocheck.boundedContext.image.repository;

import calocheck.boundedContext.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
