package calocheck.boundedContext.post.form;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@SuperBuilder(toBuilder = true)
public class Viewer {
    private long memberId;
    private String nickname;
    private long postId;
    private LocalDateTime createDate;
    private String subject;
    private String content;
    private long popularity;
}
