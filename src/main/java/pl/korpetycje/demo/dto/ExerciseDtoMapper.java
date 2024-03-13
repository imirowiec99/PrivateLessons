package pl.korpetycje.demo.dto;

import pl.korpetycje.demo.model.Exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExerciseDtoMapper {

    public ExerciseDtoMapper(){

    }


    public static List<ExerciseDto> mapToExerciseDtos(List<Exercise> exercises){
        return exercises.stream()
                .map(ExerciseDtoMapper::mapToExerciseDto)
                .collect(Collectors.toList());
    }

    public static ExerciseDto mapToExerciseDto(Exercise exercise) {
        return ExerciseDto.builder()
                .exerciseId(exercise.getExerciseId())
                .exerciseLink(exercise.getExerciseLink())
                .exerciseNumber(exercise.getExerciseNumber())
                .exerciseText(exercise.getExerciseText())
                .exerciseTopic(exercise.getExerciseTopic())
                .build();
    }

//    List<ExerciseDto> exercises = lesson.getLessonExercises().stream()
//            .map(exercise -> new ExerciseDto(exercise.getExerciseNumber(), exercise.getExerciseTopic(),
//                    exercise.getExerciseText(), exercise.getExerciseLink()))
//            .collect(Collectors.toCollection(ArrayList::new));
}
