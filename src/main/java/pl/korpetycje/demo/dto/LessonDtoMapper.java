package pl.korpetycje.demo.dto;

import java.util.List;
import java.util.stream.Collectors;
import pl.korpetycje.demo.model.Lesson;

public class LessonDtoMapper {

    private LessonDtoMapper(){

    }

    public static List<LessonDto> mapToLessonDtos(List<Lesson> lessons){
        return lessons.stream()
                .map(LessonDtoMapper::mapLessonDto)
                .sorted(((o1, o2) -> o1.getLessonNumber() - o2.getLessonNumber()))
                .collect(Collectors.toList());
    }

    private static LessonDto mapLessonDto(Lesson lesson) {
        return LessonDto.builder()
                .lessonId(lesson.getLessonId())
                .lessonLevel(lesson.getLessonLevel())
                .lessonNumber(lesson.getLessonNumber())
                .lessonTopic(lesson.getLessonTopic())
                .lessonText(lesson.getLessonText())
                .build();
    }

}
