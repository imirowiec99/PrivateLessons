package pl.korpetycje.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.korpetycje.demo.model.Exercise;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    boolean existsByExerciseNumber(int exerciseNumber);

    List<Exercise> findAllByExerciseId(long id);
}
