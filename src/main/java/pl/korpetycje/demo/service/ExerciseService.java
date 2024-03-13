package pl.korpetycje.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.korpetycje.demo.repository.ExerciseRepository;
import pl.korpetycje.demo.model.Exercise;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {
    public final ExerciseRepository exerciseRepository;
    public final JdbcTemplate jdbcTemplate;

    public ExerciseService(ExerciseRepository exerciseRepository, JdbcTemplate jdbcTemplate) {
        this.exerciseRepository = exerciseRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Exercise> getAllExercises(){
        return exerciseRepository.findAll();
    }

    public Exercise getExercise(long id){
        return exerciseRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void addExercise(Exercise exercise){
        List<Exercise> exercises = this.exerciseRepository.findAll();
        if (!this.exerciseRepository.existsByExerciseNumber(exercise.getExerciseNumber())) {
            this.exerciseRepository.save(exercise);
        }
    }

    public void deleteExercise(long id){
        this.exerciseRepository.deleteById(id);
    }

    public void updateExercise(Exercise exercise){
        jdbcTemplate.update("UPDATE mat_podst.exercise SET exercise_number=?, exercise_topic=?, exercise_text=?, exercise_link=?" +
                "WHERE exercise_id=?",exercise.getExerciseNumber(), exercise.getExerciseTopic(), exercise.getExerciseText(), exercise.getExerciseLink(),
                exercise.getExerciseId());
    }
}
