package pl.korpetycje.demo.dto;

import pl.korpetycje.demo.model.Exercise;

public class ExerciseToEntity {

    private ExerciseDto exerciseDto;

    public ExerciseToEntity(ExerciseDto exerciseDto) {
        this.exerciseDto = exerciseDto;
    }

    public static Exercise exerciseDtoToEntity(ExerciseDto exerciseDto){
        Exercise exercise = new Exercise();
        exercise.setExerciseId(exerciseDto.getExerciseId());
        exercise.setExerciseNumber(exerciseDto.getExerciseNumber());
        exercise.setExerciseText(exerciseDto.getExerciseText());
        exercise.setExerciseTopic(exerciseDto.getExerciseTopic());
        exercise.setExerciseLink(exerciseDto.getExerciseLink());
        return exercise;
    }
}
