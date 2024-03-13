package pl.korpetycje.demo.dto;

import pl.korpetycje.demo.model.Lesson;

public class LessonToEntity {
    private LessonDto lessonDto;

    public LessonToEntity(LessonDto lessonDto) {
        this.lessonDto = lessonDto;
    }

    public static Lesson lessonDtoToEntity(LessonDto lessonDto){
        Lesson lesson = new Lesson();
        lesson.setLessonNumber(lessonDto.getLessonNumber());
        lesson.setLessonText(lessonDto.getLessonText());
        lesson.setLessonTopic(lessonDto.getLessonTopic());
        lesson.setLessonLevel(lessonDto.getLessonLevel());
        return lesson;
    }
}
