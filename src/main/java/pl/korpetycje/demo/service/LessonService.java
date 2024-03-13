package pl.korpetycje.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pl.korpetycje.demo.repository.ExerciseRepository;
import pl.korpetycje.demo.repository.LessonRepository;
import pl.korpetycje.demo.model.Exercise;
import pl.korpetycje.demo.model.Lesson;

import java.util.List;

@Service
public class LessonService {

    public final LessonRepository lessonRepository;
    public final JdbcTemplate jdbcTemplate;
    public final ExerciseRepository exerciseRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository, JdbcTemplate jdbcTemplate, ExerciseRepository exerciseRepository) {
        this.lessonRepository = lessonRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.exerciseRepository = exerciseRepository;
    }

    public List<Lesson> getAllLessons(){
        return lessonRepository.findAll();
    }

    public List<Exercise> getLessonExercise(long id){
        Lesson lesson = lessonRepository.findById(id).orElseThrow();
        List<Exercise> exercises = exerciseRepository.findAllByExerciseId(id);
        lesson.setExercises(exercises);
        return exercises;
    }

    public void saveLesson(Lesson lesson){
        this.lessonRepository.save(lesson);
    }

    public void updateLesson(Lesson lesson){
        jdbcTemplate.update("UPDATE mat_podst.lesson SET lesson_topic=?, lesson_text=?, lesson_level=? WHERE lesson_number=?",
                lesson.getLessonTopic(), lesson.getLessonText(), lesson.getLessonLevel(), lesson.getLessonNumber());
    }

    public void deleteLesson(long id){
        this.lessonRepository.deleteById(id);
    }

    @Transactional
    public void saveLessonIfNotExist(Lesson lesson){
        if (!this.lessonRepository.existsByLessonNumber(lesson.getLessonNumber())){
             this.lessonRepository.save(lesson);
        }
    }


//    @Transactional
//    public void removeLesson(long id){
//       List<Lesson> lessons = lessonRepository.findAll();
//       for (Lesson lesson : lessons){
//           if (lesson.getLessonNumber() == id){
//               int nb = lesson.getLessonId();
//               long index = (long) nb;
//               List<Exercise> exercises = lesson.getExercises();
//               this.exerciseRepository.deleteAll(exercises);
//               this.lessonRepository.deleteById(index);
//           }
//       }
//    }

    public Lesson getLesson(long id){
        List<Lesson> lessons = lessonRepository.findAll();
        Lesson lesson1 = new Lesson();
        for (Lesson lesson : lessons){
            if (lesson.getLessonNumber() == id){
                lesson1 = lesson;
                return lesson1;
            }
        }
        return lesson1;
    }

}
