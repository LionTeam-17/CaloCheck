package calocheck.boundedContext.imageData.entity;


import calocheck.boundedContext.imageData.imageTarget.ImageTarget;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;



@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ImageTarget imageTarget;

    private String imageUrl;

    private Long targetId;

}
