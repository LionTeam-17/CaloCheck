package calocheck.boundedContext.imageData.repository;

import calocheck.boundedContext.imageData.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDataRepository extends JpaRepository<ImageData, Long> {

}
