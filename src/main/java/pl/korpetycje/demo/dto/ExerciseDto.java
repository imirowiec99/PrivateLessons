package pl.korpetycje.demo.dto;


import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseDto {
    private long exerciseId;
    private int exerciseNumber;
    private String exerciseTopic;
    private String exerciseText;
    private String exerciseLink;


    @Override
    public String toString() {
        return "ExerciseDto{" +
                "exerciseId=" + exerciseId +
                ", exerciseNumber=" + exerciseNumber +
                ", exerciseTopic='" + exerciseTopic + '\'' +
                ", exerciseText='" + exerciseText + '\'' +
                ", exerciseLink='" + exerciseLink + '\'' +
                '}';
    }

}
