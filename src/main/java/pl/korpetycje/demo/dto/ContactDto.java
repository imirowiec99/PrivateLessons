package pl.korpetycje.demo.dto;


import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {
    private long id;
    private String name;
    private String email;
    private String phone;
    private String message;
}
