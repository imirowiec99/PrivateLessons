package pl.korpetycje.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.korpetycje.demo.model.Lesson;

@Repository
public interface LessonRepository extends JpaRepository <Lesson, Long> {

    boolean existsByLessonNumber(int lessonNumber);


}
