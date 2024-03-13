package pl.korpetycje.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDto{

    private long lessonId;
    private int lessonNumber;
    @NotBlank
    private String lessonTopic;
    private String lessonText;
    private String lessonLevel;

}

