package pl.korpetycje.demo.model;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.Objects;

@Table(name = "exercise")
@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "exercise_id")
    private long exerciseId;
    @Column(name = "exercise_number")
    private int exerciseNumber;
    @Column(name = "exercise_topic")
    private String exerciseTopic;
    @Column(name = "exercise_text")
    private String exerciseText;
    @Column(name = "exercise_link")
    private String exerciseLink;


    @Override
    public String toString() {
        return "Exercise{" +
                "exerciseId=" + exerciseId +
                ", exerciseNumber=" + exerciseNumber +
                ", exerciseTopic='" + exerciseTopic + '\'' +
                ", exerciseText='" + exerciseText + '\'' +
                ", exerciseLink='" + exerciseLink + '\'' +
                '}';
    }
}
