package pl.korpetycje.demo.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "access")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "access_email")
    private String email;
    @Column(name = "MP")
    private String mp;
    @Column(name = "MR")
    private String mr;
    @Column(name = "FP")
    private String fp;
    @Column(name = "FR")
    private String fr;

    @Override
    public String toString() {
        return "Access{" +
                "MP='" + mp + '\'' +
                ", MR='" + mr + '\'' +
                ", FP='" + fp + '\'' +
                ", FR='" + fr + '\'' +
                '}';
    }
}
