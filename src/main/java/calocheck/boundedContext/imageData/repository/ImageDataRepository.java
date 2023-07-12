package calocheck.boundedContext.imageData.repository;

import calocheck.boundedContext.imageData.entity.ImageData;
import calocheck.boundedContext.imageData.imageTarget.ImageTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<ImageData, Long> {

    Optional<ImageData> findByImageTargetAndTargetId(ImageTarget imageTarget, Long targetId);

    Optional<ImageData> findByTargetId(Long targetId);

}
