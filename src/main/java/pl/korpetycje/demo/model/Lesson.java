package pl.korpetycje.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "lesson")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lesson_id")
    private long lessonId;
    @Column(name = "lesson_number")
    private int lessonNumber;
    @Column(name = "lesson_topic")
    private String lessonTopic;
    @Column(name = "lesson_text")
    private String lessonText;
    @Column(name = "lesson_level")
    private String lessonLevel;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "exercise_topic", referencedColumnName = "lesson_topic")
    private List<Exercise> exercises;


    public List<Exercise> getLessonExercises() {
        return exercises;
    }

    public void setLessonExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", lessonNumber=" + lessonNumber +
                ", lessonTopic='" + lessonTopic + '\'' +
                ", lessonText='" + lessonText + '\'' +
                ", lessonLevel='" + lessonLevel + '\'' +
                ", exercises=" + exercises +
                '}';
    }
}
