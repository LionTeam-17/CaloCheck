package calocheck.base.util.s3.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class EventObjectDTO {
    private String bucket;
    private String key;
}
